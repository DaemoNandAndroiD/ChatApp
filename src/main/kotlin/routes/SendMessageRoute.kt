package com.example.routes

import com.example.dto.SendMessageDto
import com.example.dto.toFirebaseMessage
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.sendNotification(){
    route("/send"){
        post {
            val body = call.receiveNullable<SendMessageDto>() ?: run {
                call.respond(HttpStatusCode.BadRequest, "Invalid request")
                return@post
            }

            FirebaseMessaging.getInstance().send(body.toFirebaseMessage())

            call.respond(HttpStatusCode.OK)
        }
    }
}