package io.commerce.ecommerceapi.core.io

import io.commerce.ecommerceapi.app.payload.request.PagingSupport
import io.commerce.ecommerceapi.core.Servicable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

open class CrudController<S: Servicable<E>, E>(
    protected val service: S
) {

    @GetMapping
    fun list(@Valid @RequestBody pagingAndSorting: PagingSupport?) = service.all(pagingAndSorting)


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.remove(id)

}