package org.example.model

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val pagination: PaginationInfo? = null
) {
    companion object {
        fun <T> success(data: T, message: String = "Success", status: HttpStatus = HttpStatus.OK): ApiResponse<T> {
            return ApiResponse(status.value(), true, message, data)
        }

        fun <T> success(
            data: T,
            page: Int,
            size: Int,
            totalElements: Long,
            totalPages: Int,
            message: String = "Success"
        ): ApiResponse<T> {
            val paginationInfo = PaginationInfo(page, size, totalElements, totalPages)
            return ApiResponse(HttpStatus.OK.value(), true, message, data, paginationInfo)
        }

        fun <T> error(message: String, status: HttpStatus = HttpStatus.BAD_REQUEST): ApiResponse<T> {
            return ApiResponse(status.value(), false, message)
        }
    }
}

data class PaginationInfo(
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)