package com.example.firebasecloudmessagingapplication.presentation.chat_screen

data class ChatState(
    val isEnteringToken : Boolean = true,
    val remoteToken : String = "",
    val messageText : String = ""
)


sealed class ChatScreenState{
    data object Loading : ChatScreenState()
    class Error(val msg : String) : ChatScreenState()
    data object Success : ChatScreenState()
    data object Initial : ChatScreenState()
}