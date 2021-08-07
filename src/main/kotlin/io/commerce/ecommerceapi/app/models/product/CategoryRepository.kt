package io.commerce.ecommerceapi.app.models.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository


@Repository
interface CategoryRepository : PagingAndSortingRepository<Category,Long> {
}

@Repository
interface CategoryTranslationRepository : PagingAndSortingRepository<CategoryTranslation,Long>{

}