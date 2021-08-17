package io.commerce.ecommerceapi.app.controller.product

import io.commerce.ecommerceapi.app.models.product.Attribute
import io.commerce.ecommerceapi.app.models.product.AttributeTranslations
import io.commerce.ecommerceapi.app.payload.request.product.AddAttribute
import io.commerce.ecommerceapi.app.service.product.AttributeService
import io.commerce.ecommerceapi.core.io.CrudController
import io.commerce.ecommerceapi.core.io.TranslatableRequest
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/private/attribute")
class AttributeController(
    val attributeService: AttributeService
): CrudController<AttributeService,Attribute,AttributeTranslations>(attributeService){

    @PutMapping
    fun create(@Valid @RequestBody request: AddAttribute) =  service.add(request)

}