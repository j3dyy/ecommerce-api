package io.commerce.ecommerceapi.app.models.product

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AttributeRepository : PagingAndSortingRepository<Attribute,Long>{
}

@Repository
interface AttributeTranslationRepository: PagingAndSortingRepository<AttributeTranslations,Long>{

}