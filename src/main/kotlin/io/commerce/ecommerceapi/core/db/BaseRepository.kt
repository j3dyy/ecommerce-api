package io.commerce.ecommerceapi.core.db

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param

@NoRepositoryBean
interface BaseRepository<T>: PagingAndSortingRepository<T,Long> {

}

@NoRepositoryBean
interface BaseTranslationRepository<T>: PagingAndSortingRepository<T,Long>{

    @Query("SELECT e FROM #{#entityName} as e  where e.localizedId.locale = :locale")
    fun findAllByLocale(@Param("locale") locale: String, pageable: Pageable): Page<T>
}