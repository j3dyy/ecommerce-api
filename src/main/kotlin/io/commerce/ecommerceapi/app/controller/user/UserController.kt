package io.commerce.ecommerceapi.app.controller.user

import io.commerce.ecommerceapi.app.models.user.User
import io.commerce.ecommerceapi.app.service.user.UserService
import io.commerce.ecommerceapi.core.io.BasicController
import io.commerce.ecommerceapi.core.io.presenter.RequestResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/private/user")
class UserController(
    private val userService: UserService
): BasicController<UserService>(userService) {

    @PostMapping("/me")
    fun me(principal: Principal):RequestResult<User> {
        var response: RequestResult<User> = RequestResult.Loading()

        userService.getByUsername(principal.name)?.let {
            response = RequestResult.Success(it)
        }
        return response
    }
}