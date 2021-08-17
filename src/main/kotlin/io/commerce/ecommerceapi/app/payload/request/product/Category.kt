package io.commerce.ecommerceapi.app.payload.request.product

import io.commerce.ecommerceapi.app.models.product.Category
import io.commerce.ecommerceapi.app.models.product.CategoryTranslation
import io.commerce.ecommerceapi.core.db.LocalizedId
import io.commerce.ecommerceapi.core.io.Request
import io.commerce.ecommerceapi.core.io.TranslatableRequest
import javax.validation.constraints.NotBlank

data class AddCategory(
    @get:NotBlank
    val name: String?,
    @get:NotBlank
    val description: String?,
): TranslatableRequest<Category,CategoryTranslation>() {

    override fun fill(e: Category?): Category {
        var category = Category()
        category.version = 1
        if (e != null){
            category = e
            category.version = category.version +1
        }
        return category
    }

    override fun fillTranslatable(e: Category): CategoryTranslation? {
        if (locale != null){
            val translation = CategoryTranslation(e,name!!,description!!)
            translation.localizedId = LocalizedId(locale)
            translation.localizedId.id = e.id
            translation.category = e
            return translation
        }
        return null
    }

}
