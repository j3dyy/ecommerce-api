package io.commerce.ecommerceapi.core

import io.commerce.ecommerceapi.app.payload.request.PagingSupport
import io.commerce.ecommerceapi.app.payload.request.PagingTranslationSupport
import io.commerce.ecommerceapi.core.db.*
import io.commerce.ecommerceapi.core.io.Request
import io.commerce.ecommerceapi.core.io.TranslatableRequest
import io.commerce.ecommerceapi.core.io.presenter.RequestResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*
import javax.transaction.Transactional

abstract class BaseService< R:BaseRepository<E>, E: EntityModel, T:Translatable>(
    protected open val repository: R,
): Servicable<E,T> {

    override fun save(obj: E){
        repository.save(obj)
    }

    override fun findById(id: Long) = repository.findById(id)

    override fun all(pagingAndSorting: PagingSupport): Page<E>? {
        var pageable = PageRequest.of(0,40, Sort.by("id").descending())
        pagingAndSorting?.paging?.let {
            pageable = PageRequest.of(
                it.page,
                it.size,
                Sort.by("id").descending()
            )
        }

        return repository.findAll(pageable)
    }

    override fun remove(id: Long) {
        if (this.findById(id).isPresent){
            repository.delete(this.findById(id).get())
        }
    }
}

open class BaseTranslatableService<
        TR: BaseTranslationRepository<T>, T:Translatable,
        R: BaseRepository<E>, E:Model<T>,
>(
    override val repository: R,
    val translatableRepository: TR
) : BaseService<R,E,T>(repository) {


    override fun all(pagingAndSorting: PagingTranslationSupport): Page<T>? {
        var pageable = PageRequest.of(0,40, Sort.by("id").descending())
        pagingAndSorting?.paging?.let {
            pageable = PageRequest.of(
                it.page,
                it.size,
                Sort.by("id").descending()
            )
        }
        return translatableRepository.findAllByLocale(pagingAndSorting.locale,pageable)
    }

    override fun showTranslation(id: Long, locale: String): T? {
        return translatableRepository.findByLocale(id,locale);
    }

    open fun add(request: TranslatableRequest<E, T>){
        var entity: E = request.fill(null)

        request.id?.let { id ->
            repository.findById(id).ifPresent {
                entity = request.fill(it)
            }
        }
        entity = repository.save(entity)
        updateTranslations(entity,request)
    }

    private fun updateTranslations(entity: E, request: TranslatableRequest<E,T>) {
        request.fillTranslatable(entity)?.let {
            translatableRepository.save(it)
        }
    }



}

interface Servicable<E: EntityModel, T:Translatable>{
    fun save(obj: E)
    fun findById(id: Long): Optional<E>
    fun all(pagingAndSorting: PagingSupport): Page<E>?
    fun all(pagingAndSorting: PagingTranslationSupport): Page<T>?
    fun showTranslation(id: Long, locale: String): T?
    fun remove(id: Long)
}

