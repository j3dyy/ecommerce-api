package io.commerce.ecommerceapi.app.models.settings

import io.commerce.ecommerceapi.core.db.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface LocaleRepository: BaseRepository<Locales> {

    fun findByLocale(locale: String): Locales?
}