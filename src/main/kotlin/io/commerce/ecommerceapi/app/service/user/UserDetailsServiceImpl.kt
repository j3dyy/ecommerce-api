package io.commerce.ecommerceapi.app.service.user

import io.commerce.ecommerceapi.app.models.user.User
import io.commerce.ecommerceapi.app.models.user.UserRepository
import io.commerce.ecommerceapi.utils.notNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional


interface UserService: UserDetailsService{
    fun getByUsername(username: String): User?
}

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
): UserService {

    override fun getByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User Not Found")
        return UserDetailsImpl.build(user)
    }

}