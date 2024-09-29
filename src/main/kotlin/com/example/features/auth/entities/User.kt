package com.example.features.auth.entities

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val password: String,
    val email: String
)
