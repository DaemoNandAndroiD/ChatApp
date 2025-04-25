package com.example.firebasecloudmessagingapplication.presentation.chat_screen

sealed class ChatEvent {
    class TokenChange(val token : String) : ChatEvent()
    data object TokenSubmit : ChatEvent()
    class NewMessage(val msg : String) : ChatEvent()
    data object SendMessage : ChatEvent()
}