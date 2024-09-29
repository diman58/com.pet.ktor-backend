package com.example.features.auth

import com.example.features.auth.entities.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.UUID

class AuthService {
    private val users = mutableSetOf<User>()
    private val userTokens = mutableMapOf<String, MutableSet<String>>()

    suspend fun register(call: ApplicationCall) {
        val registrationCall = call.receive<UserRegistrationRequest>()
        val existingUser = users.find { it.email == registrationCall.email }

        if (existingUser != null) {
            call.respond(HttpStatusCode.Conflict, "User is already registered")
        }

        val user = User(
            username = registrationCall.username,
            password = registrationCall.password,
            email = registrationCall.email,
        )

        users.add(user)
        userTokens.put(user.id, mutableSetOf())
        call.respond(HttpStatusCode.Created, "User successfully registered")
    }

    suspend fun login(call: ApplicationCall) {
        val loginCall = call.receive<UserLoginRequest>()
        val user = users.find { it.email == loginCall.email }

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "User is not found")
        }

        if (user!!.password != loginCall.password) {
            call.respond(HttpStatusCode.Forbidden, "Invalid credentials")
        }
        val userToken = UUID.randomUUID().toString()
        userTokens.getOrPut(user.id) { mutableSetOf() }.add(userToken)
        call.respond(HttpStatusCode.OK, UserLoginResponse(userToken))
    }

    suspend fun logout(call: ApplicationCall) {
        val logoutCall = call.receive<UserLogoutRequest>()
        val user = users.find { it.email == logoutCall.email }

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "User is not found")
        }

        userTokens.remove(user!!.id)
        call.respond(HttpStatusCode.OK, "User successfully logged out")
    }
}
