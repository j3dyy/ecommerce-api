package io.commerce.ecommerceapi.core.io.presenter


sealed class RequestResult<T>(
    val data:Any? = null,
    val message: String? = null,
    val code: Int = 0
){

    class Success<T>(data: Any): RequestResult<T>(data)

    class Error<T>(data: T): RequestResult<T>(data)

    class Loading<T>: RequestResult<T>()
}

