@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.plcoding.echojournal.echos.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.echojournal.echos.domain.echo.EchoDataSource
import com.plcoding.echojournal.echos.domain.echo.Mood
import com.plcoding.echojournal.echos.domain.settings.SettingsPreferences
import com.plcoding.echojournal.echos.ui.models.MoodUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val settingsPreferences: SettingsPreferences,
    private val echoDataSource: EchoDataSource
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeSettings()
                observeTopicSearchResults()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.OnAddButtonClick -> onAddButtonClick()
            SettingsAction.OnBackClick -> {}
            is SettingsAction.OnSelectTopicClick -> onSelectTopicClick(action.topic)
            SettingsAction.OnDismissTopicDropDown -> onDismissTopicDropDown()
            is SettingsAction.OnMoodClick -> onMoodClick(action.mood)
            is SettingsAction.OnRemoveTopicClick -> onRemoveTopicClick(action.topic)
            is SettingsAction.OnSearchTextChange -> onSearchTextChange(action.text)
        }
    }

    private fun observeTopicSearchResults() {
        state
            .distinctUntilChangedBy { it.searchText }
            .map { it.searchText }
            .debounce(300)
            .flatMapLatest { query ->
                if (query.isNotBlank()) {
                    echoDataSource.searchTopics(query)
                } else emptyFlow()
            }
            .onEach { filteredResults ->
                _state.update {
                    val filteredNonDefaultResults = filteredResults - it.topics.toSet()
                    val searchText = it.searchText.trim()
                    val isNewTopic =
                        searchText !in filteredNonDefaultResults && searchText !in it.topics
                                && searchText.isNotBlank()
                    it.copy(
                        suggestedTopics = filteredNonDefaultResults,
                        isTopicSuggestionsVisible = filteredResults.isNotEmpty() || isNewTopic,
                        showCreateTopicOption = isNewTopic
                    )
                }
            }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun onSearchTextChange(text: String) {
        _state.update {
            it.copy(
                searchText = text
            )
        }
    }

    private fun onDismissTopicDropDown() {
        _state.update {
            it.copy(
                isTopicSuggestionsVisible = false
            )
        }
    }

    private fun onAddButtonClick() {
        _state.update {
            it.copy(
                isTopicTextInputVisible = true
            )
        }
    }

    private fun onMoodClick(mood: MoodUi) {
        viewModelScope.launch {
            settingsPreferences.saveDefaultMood(Mood.valueOf(mood.name))
        }
    }

    private fun onSelectTopicClick(topic: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isTopicTextInputVisible = false,
                    isTopicSuggestionsVisible = false,
                    searchText = ""
                )
            }
            val newDefaultTopics = withContext(Dispatchers.Default) {
                (state.value.topics + topic).distinct()
            }
            settingsPreferences.saveDefaultTopics(newDefaultTopics)
        }
    }

    private fun onRemoveTopicClick(topic: String) {
        viewModelScope.launch {
            val newDefaultTopics = withContext(Dispatchers.Default) {
                (state.value.topics - topic).distinct()
            }
            settingsPreferences.saveDefaultTopics(newDefaultTopics)
        }
    }

    private fun observeSettings() {
        combine(
            settingsPreferences.observeDefaultTopics(),
            settingsPreferences.observeDefaultMood()
        ) { topics, mood ->
            _state.update {
                it.copy(
                    topics = topics,
                    selectedMood = MoodUi.valueOf(mood.name)
                )
            }
        }
            .launchIn(viewModelScope)
    }
}