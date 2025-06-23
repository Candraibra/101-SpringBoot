package org.example.model

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
)

data class AuthResponse(
    val token: String,
    val type: String = "Bearer",
    val username: String,
    val email: String
)

data class MessageResponse(
    val message: String
)