package com.example.firebasecloudmessagingapplication.presentation.chat_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasecloudmessagingapplication.data.api.FcmApi
import com.example.firebasecloudmessagingapplication.data.dto.NotificationBody
import com.example.firebasecloudmessagingapplication.data.dto.SendMessageDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val api : FcmApi
) : ViewModel() {
    var state by mutableStateOf(ChatState())
        private set

    var screenState = MutableStateFlow<ChatScreenState>(ChatScreenState.Initial)
        private set

    fun processEvent(event : ChatEvent){
        when(event){
            is ChatEvent.NewMessage -> onNewMessage(event.msg)
            is ChatEvent.TokenChange -> onTokenChange(event.token)
            is ChatEvent.TokenSubmit -> onSubmitToken()
            is ChatEvent.SendMessage -> onSendMessage()
        }
    }

    private fun onTokenChange(newToken : String){
        state = state.copy(remoteToken = newToken)
    }

    private fun onSubmitToken(){
        state = state.copy(isEnteringToken = false)
    }

    private fun onNewMessage(newMsg : String){
        state = state.copy(messageText = newMsg)
    }

    private fun onSendMessage(){
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to = state.remoteToken,
                notification = NotificationBody(
                    title = "New Message!",
                    body = state.messageText
                )
            )

            screenState.value = ChatScreenState.Loading

            runCatching {
                Log.d("ChatTest", "Sending message...")
                api.sendMessage(messageDto)
                Log.d("ChatTest", "Message sent!")
            }.onSuccess {
                screenState.value = ChatScreenState.Success
                Log.d("ChatTest", "Message success!")
            }.onFailure { error->
                screenState.value = ChatScreenState.Error(error.localizedMessage ?: "Unexpected error")
            }
        }
    }
}