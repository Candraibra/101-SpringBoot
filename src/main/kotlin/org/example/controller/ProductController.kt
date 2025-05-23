package org.example.controller

import org.example.model.Product
import org.example.model.ApiResponse
import org.example.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(private val productRepository: ProductRepository) {

    @GetMapping
    fun getAllProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "ASC") direction: String
    ): ApiResponse<List<Product>> {
        val sort = if (direction.equals("DESC", ignoreCase = true)) {
            Sort.by(sortBy).descending()
        } else {
            Sort.by(sortBy).ascending()
        }
        val pageable = PageRequest.of(page, size, sort)
        val productPage = productRepository.findAll(pageable)

        return ApiResponse.success(
            productPage.content,
            page,
            size,
            productPage.totalElements,
            productPage.totalPages,
            "Products retrieved successfully"
        )
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ApiResponse<Product>> {
        val product = productRepository.findById(id).orElse(null)
        return if (product != null) {
            ResponseEntity.ok(ApiResponse.success(product, "Product retrieved successfully"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Product not found with id: $id", HttpStatus.NOT_FOUND))
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody product: Product): ApiResponse<Product> {
        val savedProduct = productRepository.save(product)
        return ApiResponse.success(savedProduct, "Product created successfully", HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody productDetails: Product
    ): ResponseEntity<ApiResponse<Product>> {
        return productRepository.findById(id).map { existingProduct ->
            val updatedProduct = existingProduct.copy(
                name = productDetails.name,
                description = productDetails.description,
                price = productDetails.price
            )
            val savedProduct = productRepository.save(updatedProduct)
            ResponseEntity.ok(ApiResponse.success(savedProduct, "Product updated successfully"))
        }.orElse(
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Product not found with id: $id", HttpStatus.NOT_FOUND))
        )
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<*> {
        val optional = productRepository.findById(id)
        if (optional.isPresent) {
            productRepository.delete(optional.get())
            return ResponseEntity.ok(
                ApiResponse<Void>(
                    HttpStatus.OK.value(),
                    true,
                    "Product deleted successfully"
                )
            )
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error<Void>("Product not found with id: $id", HttpStatus.NOT_FOUND))
    }
}
