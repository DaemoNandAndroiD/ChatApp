package com.example.firebasecloudmessagingapplication.presentation.chat_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun ChatScreen(
    viewModel: ChatScreenViewModel
) {
    val state = viewModel.screenState.collectAsState()
    val context = LocalContext.current
    var enabled by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.screenState.collect { state ->
            when (state) {
                is ChatScreenState.Error -> {
                    enabled = true
                    Toast.makeText(
                        context,
                        state.msg,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ChatScreenState.Loading -> {
                    enabled = false
                }

                is ChatScreenState.Success -> {
                    enabled = true
                    Toast.makeText(
                        context,
                        "Message sent successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ChatScreenState.Initial -> {}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.state.messageText,
            onValueChange = {viewModel.processEvent(ChatEvent.NewMessage(it))},
            modifier = Modifier.padding(horizontal = 24.dp),
            trailingIcon = {
                if (!enabled) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    IconButton(
                        onClick = {
                            viewModel.processEvent(ChatEvent.SendMessage)
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = null
                        )
                    }
                }

            },
            placeholder = {
                Text("Enter a message")
            }
        )
    }
}