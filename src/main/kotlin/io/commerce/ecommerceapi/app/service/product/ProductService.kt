package io.commerce.ecommerceapi.app.service.product

import io.commerce.ecommerceapi.app.models.product.Product
import io.commerce.ecommerceapi.app.models.product.ProductRepository
import io.commerce.ecommerceapi.app.models.product.ProductTranslationRepository
import io.commerce.ecommerceapi.app.models.product.ProductTranslations
import io.commerce.ecommerceapi.core.BaseTranslatableService
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
): BaseTranslatableService<ProductTranslationRepository,ProductTranslations,ProductRepository,Product>(productRepository) {



}