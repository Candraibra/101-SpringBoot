package org.example.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Product(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long = 0,
    val name: String,
    val description: String,
    val price: Double
)