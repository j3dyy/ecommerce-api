package io.commerce.ecommerceapi.app.controller.product

import io.commerce.ecommerceapi.app.models.product.Product
import io.commerce.ecommerceapi.app.models.product.ProductTranslations
import io.commerce.ecommerceapi.app.payload.request.product.AddAttribute
import io.commerce.ecommerceapi.app.payload.request.product.AddProduct
import io.commerce.ecommerceapi.app.service.product.ProductService
import io.commerce.ecommerceapi.core.io.CrudController
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/private/product")
class ProductController(
    val productService: ProductService
): CrudController<ProductService,Product,ProductTranslations>(productService) {

    @PutMapping
    fun create(@Valid @RequestBody request: AddProduct) =  service.add(request)
}