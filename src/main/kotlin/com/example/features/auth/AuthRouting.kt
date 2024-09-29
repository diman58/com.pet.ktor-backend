package com.example.features.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*

val authService = AuthService()

fun Application.configureAuthRouting() {

    routing {
        post("/login") {
            authService.login(call)
        }
        post("/register") {
            authService.register(call)
        }
        post("/logout") {
            authService.logout(call)
        }
    }
}