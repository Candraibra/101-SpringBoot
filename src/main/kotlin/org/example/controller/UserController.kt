package org.example.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {

    @GetMapping("/profile")
    fun getUserProfile(authentication: Authentication): ResponseEntity<Map<String, Any>> {
        val user = authentication.principal as org.example.model.User

        val profile = mapOf(
            "id" to user.id,
            "username" to user.username,
            "email" to user.email,
            "role" to user.role,
            "authorities" to user.authorities.map { it.authority }
        )

        return ResponseEntity.ok(profile)
    }

    @GetMapping("/dashboard")
    fun getDashboard(authentication: Authentication): ResponseEntity<Map<String, String>> {
        val user = authentication.principal as org.example.model.User

        return ResponseEntity.ok(
            mapOf(
                "message" to "Welcome to your dashboard, ${user.username}!",
                "timestamp" to java.time.LocalDateTime.now().toString()
            )
        )
    }
}