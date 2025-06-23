package org.example.controller

import org.example.service.ExampleDependencyInjectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/public/examples")
class DependencyInjectionExampleController {

    // Constructor Injection (recommended approach)
    private val exampleService: ExampleDependencyInjectionService

    // Constructor injection - Spring automatically injects dependencies
    constructor(exampleService: ExampleDependencyInjectionService) {
        this.exampleService = exampleService
    }

    // Alternative: Field injection with @Autowired
    // @Autowired
    // private lateinit var exampleService: ExampleDependencyInjectionService

    @GetMapping("/dependency-injection")
    fun getDependencyInjectionExample(): ResponseEntity<Map<String, Any>> {
        val examples = exampleService.demonstrateInjectedDependencies()

        return ResponseEntity.ok(
            mapOf(
                "title" to "Dependency Injection Examples",
                "description" to "This endpoint demonstrates various dependency injection patterns",
                "injectedDependencies" to examples,
                "patterns" to listOf(
                    "Field Injection with @Autowired",
                    "Setter Injection with @Autowired",
                    "Method Injection with @Autowired",
                    "Constructor Injection (recommended)",
                    "Property Injection with @Value",
                    "Optional Injection with required=false",
                    "Collection Injection for multiple beans",
                    "Qualifier for specific bean selection"
                )
            )
        )
    }
}