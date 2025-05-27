package com.plcoding.echojournal.echos.ui.create_echo

sealed interface CreateEchoEvent {
    data object FailedToSaveFile : CreateEchoEvent
}