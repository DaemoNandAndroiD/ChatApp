package com.example.firebasecloudmessagingapplication.presentation.chat_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.CoroutineContext

@Composable
fun EnterTokenDialog(
    viewModel: ChatScreenViewModel
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.state.remoteToken,
                onValueChange = { viewModel.processEvent(ChatEvent.TokenChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Enter token")
                },
                maxLines = 1
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            val fbToken = Firebase.messaging.token.await()
                            clipboardManager.setText(AnnotatedString(fbToken))

                            Toast.makeText(
                                context,
                                "Token copied",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                ) {
                    Text("Copy token")
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = {viewModel.processEvent(ChatEvent.TokenSubmit)}
                ) {
                    Text("Submit")
                }
            }
        }
    }
}