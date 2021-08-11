package io.commerce.ecommerceapi.app.payload.request.product

import io.commerce.ecommerceapi.app.models.product.Attribute
import io.commerce.ecommerceapi.app.models.product.AttributeTranslations
import io.commerce.ecommerceapi.core.db.LocalizedId
import io.commerce.ecommerceapi.core.io.Request
import io.commerce.ecommerceapi.core.io.TranslatableRequest
import javax.validation.constraints.NotBlank


data class AddAttribute(
    @get:NotBlank
    val name: String,
    @get:NotBlank
    val description: String,
): TranslatableRequest<Attribute,AttributeTranslations>(){

    override fun fill(): Attribute {
        var attribute = Attribute()
        attribute.version = 1
        return attribute
    }

    override fun fillTranslatable(e: Attribute): AttributeTranslations {
        val translation = AttributeTranslations(e,name,description)
        translation.localizedId = LocalizedId(locale)
        translation.localizedId.id = e.id
        translation.attribute = e
        return translation
    }

}