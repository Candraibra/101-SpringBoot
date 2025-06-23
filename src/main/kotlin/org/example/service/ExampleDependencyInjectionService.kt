package org.example.service

import org.example.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ExampleDependencyInjectionService {

    // 1. Field Injection with @Autowired
    @Autowired
    private lateinit var userRepository: UserRepository

    // 2. Field Injection with @Autowired and @Qualifier (for multiple beans of same type)
    @Autowired
    @Qualifier("passwordEncoder")
    private lateinit var encoder: PasswordEncoder

    // 3. Property Injection with @Value
    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration:86400000}")
    private var jwtExpiration: Long = 0

    // 4. Setter Injection with @Autowired
    private lateinit var userService: UserService

    @Autowired
    fun setUserService(userService: UserService) {
        this.userService = userService
    }

    // 5. Method Injection with @Autowired (can inject multiple dependencies)
    private lateinit var jwtUtil: org.example.security.JwtUtil

    @Autowired
    fun configureDependencies(jwtUtil: org.example.security.JwtUtil) {
        this.jwtUtil = jwtUtil
    }

    // 6. Optional Injection (won't fail if bean doesn't exist)
    @Autowired(required = false)
    private var optionalService: OptionalService? = null

    // 7. Collection Injection (injects all beans of a type)
    @Autowired
    private lateinit var allPasswordEncoders: List<PasswordEncoder>

    // 8. PostConstruct - runs after all dependencies are injected
    @PostConstruct
    fun initialize() {
        println("All dependencies injected successfully!")
        println("JWT Secret length: ${jwtSecret.length}")
        println("JWT Expiration: $jwtExpiration ms")
        println("Available password encoders: ${allPasswordEncoders.size}")
        println("Optional service available: ${optionalService != null}")
    }

    fun demonstrateInjectedDependencies(): Map<String, Any> {
        return mapOf(
            "userRepositoryClass" to userRepository::class.simpleName,
            "encoderClass" to encoder::class.simpleName,
            "jwtSecretLength" to jwtSecret.length,
            "jwtExpiration" to jwtExpiration,
            "userServiceClass" to userService::class.simpleName,
            "jwtUtilClass" to jwtUtil::class.simpleName,
            "optionalServicePresent" to (optionalService != null),
            "passwordEncodersCount" to allPasswordEncoders.size
        )
    }
}

// Example optional service that may or may not exist
interface OptionalService {
    fun doSomething(): String
}