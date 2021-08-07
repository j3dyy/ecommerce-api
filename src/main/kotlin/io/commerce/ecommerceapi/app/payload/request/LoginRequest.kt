package io.commerce.ecommerceapi.app.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class LoginRequest(
    @get:NotBlank
    val username : String,

    @get:NotBlank
    val password : String
){
}

data class SignupRequest(
    @get:NotBlank
    @get:Size(min = 3, max = 50)
    val username : String,

    @get:NotBlank
    @get:Size(min = 3, max = 50)
    @get:Email
    val email : String,

    @get:NotBlank
    @get:Size(min = 6)
    val password: String
){

}