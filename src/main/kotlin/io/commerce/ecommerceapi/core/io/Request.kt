package io.commerce.ecommerceapi.core.io

import io.commerce.ecommerceapi.core.db.EntityModel
import io.commerce.ecommerceapi.core.db.Translatable
import javax.validation.constraints.NotBlank

abstract class Request<E: EntityModel>(
    val id: Long? = null,
) {
    abstract fun fill(e: E?) : E
}

abstract class TranslatableRequest<E:EntityModel,T:Translatable>(
    val locale: String? = null
): Request<E>(){
    abstract fun fillTranslatable(e: E): T?
}

