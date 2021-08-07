package io.commerce.ecommerceapi.core

import io.commerce.ecommerceapi.app.payload.request.PagingSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import javax.validation.Valid

open class BaseService< R:PagingAndSortingRepository<T,Long>, T>(
    protected val repository: R
): Servicable<T> {

    override fun save(obj: T){
        repository.save(obj)
    }

    override fun findById(id: Long) = repository.findById(id)

    override fun all(pagingAndSorting: PagingSupport?): Page<T>? {
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

interface Servicable<T>{
    fun save(obj: T)
    fun findById(id: Long): Optional<T>
    fun all(pagingAndSorting: PagingSupport?): Page<T>?
    fun remove(id: Long)
}