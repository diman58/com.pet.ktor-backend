package com.example.features.auth.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequest(
    val username: String,
    val password: String,
    val email: String
)

@Serializable
data class UserLoginRequest(
    val email: String,
    val password: String
)


@Serializable
data class UserLoginResponse(
    val token: String
)

@Serializable
data class UserLogoutRequest(
    val email: String
)
