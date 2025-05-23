package com.plcoding.echojournal.echos.ui.echos

import com.plcoding.echojournal.echos.ui.echos.models.EchoFilterChip
import com.plcoding.echojournal.echos.ui.echos.models.TrackSizeInfo
import com.plcoding.echojournal.echos.ui.models.MoodUi

sealed interface EchosAction {
    data object OnMoodChipClick : EchosAction
    data object OnDismissMoodDropDown : EchosAction
    data class OnFilterByMoodClick(val moodUi: MoodUi) : EchosAction
    data object OnTopicChipClick : EchosAction
    data object OnDismissTopicDropDown : EchosAction
    data class OnFilterByTopicClick(val topic: String) : EchosAction
    data object OnFabClick : EchosAction
    data object OnFabLongClick : EchosAction
    data object OnSettingsClick : EchosAction
    data class OnRemoveFilters(val filterType: EchoFilterChip) : EchosAction
    data class OnPlayEchoClick(val echoId: Int) : EchosAction
    data object OnPauseClick : EchosAction
    data class OnTrackSizeAvailable(val trackSizeInfo: TrackSizeInfo) : EchosAction
}