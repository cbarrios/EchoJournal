package com.plcoding.echojournal.echos.ui.echos

sealed interface EchosEvent {
    data object RequestAudioPermission : EchosEvent
}