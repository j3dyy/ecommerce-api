package io.commerce.ecommerceapi.app.models.product

import io.commerce.ecommerceapi.core.db.BaseRepository
import io.commerce.ecommerceapi.core.db.BaseTranslationRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface CategoryRepository : BaseRepository<Category> {


}

@Repository
interface CategoryTranslationRepository : BaseTranslationRepository<CategoryTranslation>{

}