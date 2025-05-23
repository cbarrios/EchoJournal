package com.plcoding.echojournal.echos.ui.echos.models

import com.plcoding.echojournal.core.ui.util.UiText
import com.plcoding.echojournal.echos.ui.models.EchoUi

data class EchoDaySection(
    val dateHeader: UiText,
    val echos: List<EchoUi>
)