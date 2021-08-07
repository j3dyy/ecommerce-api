package io.commerce.ecommerceapi.app.payload.request.product

import io.commerce.ecommerceapi.core.io.Request
import javax.validation.constraints.NotBlank

data class AddCategory(
    val id: Long? = null,
    @get:NotBlank
    val name: String,
    @get:NotBlank
    val description: String,
    @get:NotBlank
    val locale: String
): Request() {

}
