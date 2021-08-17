package io.commerce.ecommerceapi.core.io

import io.commerce.ecommerceapi.app.payload.request.PagingSupport
import io.commerce.ecommerceapi.core.Servicable
import io.commerce.ecommerceapi.core.db.EntityModel
import io.commerce.ecommerceapi.core.db.Translatable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

open class BasicController<S>(
    protected open val service: S
){

}

open class CrudController<S: Servicable<E,T>, E: EntityModel, T: Translatable>(
    override val service: S
): BasicController<S>(service) {


    @GetMapping
    fun list(@Valid @RequestBody pagingAndSorting: PagingSupport?) = service.all(pagingAndSorting)

    @GetMapping("/{id}")
    fun show(@PathVariable id: Long) = service.findById(id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.remove(id)

}
