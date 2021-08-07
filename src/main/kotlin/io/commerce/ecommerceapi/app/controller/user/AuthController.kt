package io.commerce.ecommerceapi.app.controller.user

import io.commerce.ecommerceapi.app.models.user.*
import io.commerce.ecommerceapi.app.payload.request.LoginRequest
import io.commerce.ecommerceapi.app.payload.request.SignupRequest
import io.commerce.ecommerceapi.app.payload.response.JwtResponse
import io.commerce.ecommerceapi.app.payload.response.MessageResponse
import io.commerce.ecommerceapi.app.service.user.UserDetailsImpl
import io.commerce.ecommerceapi.configuration.security.jwt.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager : AuthenticationManager,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest) : ResponseEntity<JwtResponse> {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username,loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)!!
        val userDetails= authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.map { item -> item!!.authority  } as List<String>

        return ResponseEntity.ok(
            JwtResponse(
                jwt,
                userDetails.getId()!!,
                userDetails.username!!,
                userDetails.getEmail()!!,
                roles
            )
        )
    }

    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody request: SignupRequest): ResponseEntity<MessageResponse>{
        if (userRepository.existsByUsername(request.username)){
            return ResponseEntity.badRequest().body(MessageResponse("username must be unique", "error"))
        }
        if (userRepository.existsByEmail(request.email)){
            return ResponseEntity.badRequest().body(MessageResponse("email must be unique", "error"))
        }

        val user = User(request.email,request.username,passwordEncoder.encode(request.password))

        val strRoles = mutableSetOf<String>("ADMIN")
        val role = hashSetOf<Role>()
        role.add(Role(CommerceRoles.ROLE_USER))
        user.roles = role
        userRepository.save(user)
        return ResponseEntity.ok(MessageResponse("User Registered Now Signin", "success"))
    }
}