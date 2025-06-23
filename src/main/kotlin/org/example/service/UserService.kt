package org.example.service

import org.example.model.User
import org.example.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
    }

    fun createUser(username: String, password: String, email: String): User {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("Username already exists: $username")
        }
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("Email already exists: $email")
        }

        val user = User(
            username = username,
            password = passwordEncoder.encode(password),
            email = email
        )
        return userRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }
}
