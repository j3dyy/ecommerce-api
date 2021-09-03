package io.commerce.ecommerceapi.app.models.product

import io.commerce.ecommerceapi.core.db.BaseRepository
import io.commerce.ecommerceapi.core.db.BaseTranslationRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AttributeRepository : BaseRepository<Attribute>{
}

@Repository
interface AttributeTranslationRepository: BaseTranslationRepository<AttributeTranslations> {

}