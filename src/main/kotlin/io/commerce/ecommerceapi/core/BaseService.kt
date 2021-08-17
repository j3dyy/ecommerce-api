package io.commerce.ecommerceapi.core

import io.commerce.ecommerceapi.app.payload.request.PagingSupport
import io.commerce.ecommerceapi.core.db.EntityModel
import io.commerce.ecommerceapi.core.db.Model
import io.commerce.ecommerceapi.core.db.Translatable
import io.commerce.ecommerceapi.core.io.Request
import io.commerce.ecommerceapi.core.io.TranslatableRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*
import javax.transaction.Transactional

abstract class BaseService< R:PagingAndSortingRepository<E,Long>, E: EntityModel, T:Translatable>(
    protected open val repository: R,
): Servicable<E,T> {

    override fun save(obj: E){
        repository.save(obj)
    }

    override fun findById(id: Long) = repository.findById(id)

    override fun all(pagingAndSorting: PagingSupport?): Page<E>? {
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
        TR: PagingAndSortingRepository<T,Long>, T:Translatable,
        R: PagingAndSortingRepository<E,Long>, E:Model<T>,
>(
    override val repository: R
) : BaseService<R,E,T>(repository) {

    @Transactional
    open fun add(request: TranslatableRequest<E, T>){
        var entity: E = request.fill(null)

        request.id?.let { id ->
            repository.findById(id).ifPresent {
                entity = request.fill(it)
            }
        }
        entity = updateTranslations(entity,request)
        repository.save(entity)
    }


    private fun updateTranslations(entity: E, request: TranslatableRequest<E,T>): E{
        var translation = request.fillTranslatable(entity)

        if (translation != null){
            if (entity.getTranslations()[request.locale!!] != null){
                entity.removeTranslation(request.locale)
            }
            entity.setTranslations(request.locale, translation)
        }
        return entity;
    }

}

interface Servicable<E: EntityModel, T:Translatable>{
    fun save(obj: E)
    fun findById(id: Long): Optional<E>
    fun all(pagingAndSorting: PagingSupport?): Page<E>?
    fun remove(id: Long)
}

