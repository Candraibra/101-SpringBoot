package org.example.controller

import org.example.model.*
import org.example.security.JwtUtil
import org.example.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        return try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )

            val userDetails: UserDetails = userService.loadUserByUsername(loginRequest.username)
            val user = userService.findByUsername(loginRequest.username)!!
            val token = jwtUtil.generateToken(userDetails)

            ResponseEntity.ok(
                AuthResponse(
                    token = token,
                    username = user.username,
                    email = user.email
                )
            )
        } catch (e: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(MessageResponse("Invalid username or password"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageResponse("Authentication failed"))
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<MessageResponse> {
        return try {
            if (userService.existsByUsername(registerRequest.username)) {
                return ResponseEntity.badRequest()
                    .body(MessageResponse("Username is already taken"))
            }

            if (userService.existsByEmail(registerRequest.email)) {
                return ResponseEntity.badRequest()
                    .body(MessageResponse("Email is already in use"))
            }

            userService.createUser(
                username = registerRequest.username,
                password = registerRequest.password,
                email = registerRequest.email
            )

            ResponseEntity.ok(MessageResponse("User registered successfully"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageResponse("Registration failed: ${e.message}"))
        }
    }
}
