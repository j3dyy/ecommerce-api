package io.commerce.ecommerceapi.app.payload.request

import io.commerce.ecommerceapi.app.models.settings.Locales
import io.commerce.ecommerceapi.core.io.Request
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class SaveLocale(
    @get:NotBlank
    val locale: String,
    @get:NotBlank
    val name: String,
    @get:NotNull
    val is_active: Boolean
): Request<Locales>() {
    override fun fill(e: Locales?): Locales {
        var entity =  Locales(locale, name, active = is_active)
        if(e != null){
            entity.id = e.id
        }
        return entity
    }
}