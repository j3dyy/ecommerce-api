package io.commerce.ecommerceapi.app.payload.request.product

import io.commerce.ecommerceapi.app.models.product.Product
import io.commerce.ecommerceapi.app.models.product.ProductTranslations
import io.commerce.ecommerceapi.core.db.LocalizedId
import io.commerce.ecommerceapi.core.io.TranslatableRequest


data class AddProduct(
    val sku: String?,
    val barcode: String?,
    val isActive: Boolean = false,
    val name: String?,
    val description: String?
): TranslatableRequest<Product,ProductTranslations>(){

    override fun fill(e: Product?): Product {
        var entity = Product(sku,barcode,isActive)
        entity.version = 1
        if(e != null){
            entity = e
            entity.sku = sku
            entity.barcode = barcode
            entity.isActive = isActive
            entity.version = entity.version + 1
        }
        return entity
    }

    override fun fillTranslatable(e: Product): ProductTranslations? {
        if(locale != null){
            val translation = ProductTranslations(e,name!!,description!!)
            translation.localizedId = LocalizedId(locale)
            translation.localizedId.id = e.id
            translation.product = e
            return translation
        }
        return null
    }



}