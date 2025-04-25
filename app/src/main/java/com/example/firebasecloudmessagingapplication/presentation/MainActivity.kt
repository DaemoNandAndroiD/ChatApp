package com.example.firebasecloudmessagingapplication.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.firebasecloudmessagingapplication.presentation.chat_screen.ChatScreen
import com.example.firebasecloudmessagingapplication.presentation.chat_screen.ChatScreenViewModel
import com.example.firebasecloudmessagingapplication.presentation.chat_screen.EnterTokenDialog
import com.example.firebasecloudmessagingapplication.ui.theme.FIrebaseCloudMessagingApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ChatScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPostNotificationsPermission()

        enableEdgeToEdge()
        setContent {
            FIrebaseCloudMessagingApplicationTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val state = viewModel.state

                    if (state.isEnteringToken) {
                        EnterTokenDialog(
                            viewModel = viewModel
                        )
                    } else {
                        ChatScreen(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }

    private fun requestPostNotificationsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
    }
}
