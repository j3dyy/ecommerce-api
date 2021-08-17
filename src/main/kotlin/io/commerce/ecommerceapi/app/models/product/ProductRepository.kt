package io.commerce.ecommerceapi.app.models.product

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: PagingAndSortingRepository<Product,Long>

@Repository
interface ProductTranslationRepository: PagingAndSortingRepository<ProductTranslations, Long>