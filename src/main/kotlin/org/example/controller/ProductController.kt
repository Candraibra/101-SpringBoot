package org.example.controller

import org.example.model.Product
import org.example.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(private val productRepository: ProductRepository) {

    @GetMapping
    fun getAllProducts(): List<Product> = productRepository.findAll().toList()

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productRepository.findById(id).orElse(null)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody product: Product): Product = productRepository.save(product)

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody productDetails: Product): ResponseEntity<Product> {
        return productRepository.findById(id).map { existingProduct ->
            val updatedProduct = existingProduct.copy(
                name = productDetails.name,
                description = productDetails.description,
                price = productDetails.price
            )
            ResponseEntity.ok().body(productRepository.save(updatedProduct))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        return productRepository.findById(id).map { product ->
            productRepository.delete(product)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}