package io.commerce.ecommerceapi.core.io

import io.commerce.ecommerceapi.core.db.EntityModel
import io.commerce.ecommerceapi.core.db.Translatable
import javax.validation.constraints.NotBlank

abstract class Request<E: EntityModel>(
    val id: Long? = null,
) {
    abstract fun fill() : E
}

abstract class TranslatableRequest<E:EntityModel,T:Translatable>(
    @get:NotBlank
    val locale: String = "ka" /* todo default locale from settings or session */
): Request<E>(){
    abstract fun fillTranslatable(e: E): T
}

