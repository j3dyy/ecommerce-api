package io.commerce.ecommerceapi.app.controller

import io.commerce.ecommerceapi.app.models.settings.Locales
import io.commerce.ecommerceapi.app.payload.request.SaveLocale
import io.commerce.ecommerceapi.app.service.SettingsService
import io.commerce.ecommerceapi.core.io.BasicController
import io.commerce.ecommerceapi.core.io.presenter.RequestResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/private/settings")
class SettingsController(
    val settingsService: SettingsService,
) : BasicController<SettingsService>(settingsService) {

    var response: RequestResult<Locales> = RequestResult.Loading()



    @GetMapping("locales")
    fun getLocales(): RequestResult<Locales> {
        response = RequestResult.Loading()
        settingsService.listLocales().let {
            response = RequestResult.Success(it)
        }
        return response
    }

    @PutMapping("locales")
    fun saveLocale(@Valid @RequestBody saveLocale: SaveLocale): RequestResult<Locales> {
        response = RequestResult.Loading()
        settingsService.saveLocale(saveLocale).also {
            response = RequestResult.Success("", message = "Locale Added")
        }
        return response;
    }

    @GetMapping("locales/{id}")
    fun getLocale(@PathVariable id: Long): RequestResult<Locales> {
        response = RequestResult.Loading()
        settingsService.getLocale(id).ifPresent {
            response = RequestResult.Success(it)
        }
        return response
    }

    @DeleteMapping("locales/{locale}")
    fun deleteLocale(@PathVariable locale: String) = settingsService.removeLocale(locale)
}