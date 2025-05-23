package com.plcoding.echojournal.echos.ui.echos

import com.plcoding.echojournal.R
import com.plcoding.echojournal.core.ui.design.dropdowns.Selectable
import com.plcoding.echojournal.core.ui.design.dropdowns.Selectable.Companion.asUnselectedItems
import com.plcoding.echojournal.core.ui.util.UiText
import com.plcoding.echojournal.echos.ui.echos.models.EchoDaySection
import com.plcoding.echojournal.echos.ui.echos.models.EchoFilterChip
import com.plcoding.echojournal.echos.ui.echos.models.MoodChipContent
import com.plcoding.echojournal.echos.ui.models.EchoUi
import com.plcoding.echojournal.echos.ui.models.MoodUi

data class EchosState(
    val echos: Map<UiText, List<EchoUi>> = emptyMap(),
    val hasEchosRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilters: Boolean = false,
    val isLoadingData: Boolean = false,
    val moods: List<Selectable<MoodUi>> = emptyList(),
    val topics: List<Selectable<String>> = listOf("Love", "Happy", "Work").asUnselectedItems(),
    val moodChipContent: MoodChipContent = MoodChipContent(),
    val selectedEchoFilterChip: EchoFilterChip? = null,
    val topicChipTitle: UiText = UiText.StringResource(R.string.all_topics)
) {

    val echoDaySections = echos.toList().map { (dateHeader, echos) ->
        EchoDaySection(dateHeader, echos)
    }
}