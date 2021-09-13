package io.commerce.ecommerceapi.app.service

import io.commerce.ecommerceapi.app.models.settings.LocaleRepository
import io.commerce.ecommerceapi.app.models.settings.Locales
import io.commerce.ecommerceapi.app.payload.request.SaveLocale
import org.springframework.stereotype.Service
import java.util.*

@Service
class SettingsService(
    val localeRepository: LocaleRepository
) {

    fun listLocales(): MutableIterable<Locales> = localeRepository.findAll()

    fun getLocale(id: Long): Optional<Locales> = localeRepository.findById(id)

    fun saveLocale(saveLocale: SaveLocale) {
        var entity = saveLocale.fill(null)
        saveLocale.id?.let {
            localeRepository.findById(it).ifPresent { e ->
                entity = saveLocale.fill(e)
            }
        }
        localeRepository.save(entity)
    }

    fun removeLocale(locale: String){
        localeRepository.count().let { id ->
            if (id == 1L){
                // todo exception here
            }else{
                localeRepository.findByLocale(locale)?.let {
                    localeRepository.delete(it)
                }
            }
        }

    }
}