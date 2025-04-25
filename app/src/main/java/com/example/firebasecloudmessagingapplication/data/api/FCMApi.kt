package com.example.firebasecloudmessagingapplication.data.api

import com.example.firebasecloudmessagingapplication.data.dto.SendMessageDto
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {

    @POST("/send")
    suspend fun sendMessage(
        @Body body : SendMessageDto
    )
}