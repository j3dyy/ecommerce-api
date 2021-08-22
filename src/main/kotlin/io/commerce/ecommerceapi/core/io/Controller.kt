package io.commerce.ecommerceapi.core.io

import io.commerce.ecommerceapi.app.payload.request.PagingSupport
import io.commerce.ecommerceapi.core.Servicable
import io.commerce.ecommerceapi.core.db.EntityModel
import io.commerce.ecommerceapi.core.db.Translatable
import io.commerce.ecommerceapi.core.io.presenter.RequestResult
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


open class BasicController<S>(
    protected open val service: S
){

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleValidateException(manv: MethodArgumentNotValidException): RequestResult.Error<MutableMap<String, Array<String>>> {
        val errors: MutableMap<String, Array<String>> = HashMap()
        manv.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage: String = error.defaultMessage!!
            errors[fieldName].let {
                if (it == null){
                    errors[fieldName] = arrayOf(errorMessage)
                }else{
                    errors[fieldName] = arrayOf(errorMessage, *it)
                }
            }
        }
        return RequestResult.Error(errors)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleMessageNotReadable(msgNor: HttpMessageNotReadableException): RequestResult.Error<String?> {
        return RequestResult.Error(msgNor.message)
    }

}

open class CrudController<S: Servicable<E,T>, E: EntityModel, T: Translatable>(
    override val service: S,
    var response: RequestResult<E> = RequestResult.Loading()
): BasicController<S>(service) {



    @GetMapping
    fun list(@Valid @RequestBody pagingAndSorting: PagingSupport?): RequestResult<E> {
        service.all(pagingAndSorting)?.let {
            response = RequestResult.Success(it)
        }
        return response
    }

    @GetMapping("/{id}")
    fun show(@PathVariable id: Long): RequestResult<E> {
        service.findById(id).ifPresent {
            response = RequestResult.Success(it)
        }
        return response
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        service.remove(id)
    }

}
