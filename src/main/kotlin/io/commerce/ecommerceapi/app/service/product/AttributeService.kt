package io.commerce.ecommerceapi.app.service.product

import io.commerce.ecommerceapi.app.models.product.Attribute
import io.commerce.ecommerceapi.app.models.product.AttributeRepository
import io.commerce.ecommerceapi.app.models.product.AttributeTranslationRepository
import io.commerce.ecommerceapi.app.models.product.AttributeTranslations
import io.commerce.ecommerceapi.app.payload.request.product.AddAttribute
import io.commerce.ecommerceapi.core.BaseService
import io.commerce.ecommerceapi.core.BaseTranslatableService
import io.commerce.ecommerceapi.core.db.LocalizedId
import io.commerce.ecommerceapi.utils.isNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AttributeService(
    val attributeRepository: AttributeRepository,
    val attributeTranslationRepository: AttributeTranslationRepository
): BaseTranslatableService<AttributeTranslationRepository,AttributeTranslations,AttributeRepository,Attribute>(
    attributeRepository,
    attributeTranslationRepository
) {


}