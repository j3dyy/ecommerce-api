package io.commerce.ecommerceapi.core.io.presenter


sealed class RequestResult<T>(
    val data:Any? = null,
    val message: String? = null,
    val code: Int = 0,
    val error: Boolean = false
){

    class Success<T>(data: Any, message: String? = ""): RequestResult<T>(data, message)

    class Error<T>(data: Any?): RequestResult<T>(data, code = 9000, error = true)

    class Loading<T>: RequestResult<T>()
}

