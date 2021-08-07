package io.commerce.ecommerceapi.core.engine.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

@ControllerAdvice
class AdvisorHandler: ResponseEntityExceptionHandler() {

    /**
     * todo
     */
}