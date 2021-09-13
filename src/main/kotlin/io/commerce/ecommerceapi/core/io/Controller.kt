package io.commerce.ecommerceapi.core.io

import io.commerce.ecommerceapi.app.payload.request.PagingTranslationSupport
import io.commerce.ecommerceapi.core.Servicable
import io.commerce.ecommerceapi.core.db.EntityModel
import io.commerce.ecommerceapi.core.db.Translatable
import io.commerce.ecommerceapi.core.io.presenter.RequestResult
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @ExceptionHandler(value = [DataIntegrityViolationException::class])
    open fun handlePreconditionFailed(exception: DataIntegrityViolationException): RequestResult.Error<String?> {
        return RequestResult.Error(exception.message)
    }

}

open class CrudController<S: Servicable<E,T>, E: EntityModel, T: Translatable>(
    override val service: S,
    var response: RequestResult<E> = RequestResult.Loading()
): BasicController<S>(service) {


    @PostMapping
    fun list(@Valid @RequestBody pagingTranslationSupport: PagingTranslationSupport): RequestResult<E> {
        response = RequestResult.Loading()
        service.all(pagingTranslationSupport)?.let {
            response = RequestResult.Success(it)
        }
        return response
    }

    @GetMapping("/{id}")
    fun show(@PathVariable id: Long): RequestResult<E> {
        response = RequestResult.Loading()
        service.findById(id).ifPresent {
            response = RequestResult.Success(it)
        }
        return response
    }

    @GetMapping("/{id}/{locale}")
    fun showTranslation(@PathVariable id: Long, @PathVariable(name= "locale") locale: String): RequestResult<E> {
        response = RequestResult.Loading()
        service.showTranslation(id, locale).let {
            response = if (it != null) {
                RequestResult.Success(it)
            }else{
                ///todo translation here
                RequestResult.Error("no data with this translation: $locale and id: $id")
            }
        }
        return response
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        service.remove(id)
    }

}
