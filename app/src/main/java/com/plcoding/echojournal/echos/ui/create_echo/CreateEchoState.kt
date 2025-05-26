package com.plcoding.echojournal.echos.ui.create_echo

import com.plcoding.echojournal.core.ui.design.dropdowns.Selectable
import com.plcoding.echojournal.echos.ui.echos.models.PlaybackState
import com.plcoding.echojournal.echos.ui.models.MoodUi
import kotlin.time.Duration

data class CreateEchoState(
    val titleText: String = "",
    val addTopicText: String = "",
    val topics: List<String> = listOf(),
    val noteText: String = "",
    val showMoodSelector: Boolean = true,
    val selectedMood: MoodUi = MoodUi.NEUTRAL,
    val showTopicSuggestions: Boolean = false,
    val mood: MoodUi? = null,
    val searchResults: List<Selectable<String>> = emptyList(),
    val showCreateTopicOption: Boolean = true,
    val canSaveEcho: Boolean = false,
    val playbackAmplitudes: List<Float> = List(32) { 0.3f },
    val playbackTotalDuration: Duration = Duration.ZERO,
    val playbackState: PlaybackState = PlaybackState.STOPPED,
    val durationPlayed: Duration = Duration.ZERO,
) {
    val durationPlayedRatio = (durationPlayed / playbackTotalDuration).toFloat()
}