package io.commerce.ecommerceapi.app.payload.request.product

import io.commerce.ecommerceapi.app.models.product.Category
import io.commerce.ecommerceapi.core.io.Request
import javax.validation.constraints.NotBlank

data class AddCategory(
    @get:NotBlank
    val name: String,
    @get:NotBlank
    val description: String,
    @get:NotBlank
    val locale: String
): Request<Category>() {
    override fun fill(): Category {
        TODO("Not yet implemented")
    }

}
