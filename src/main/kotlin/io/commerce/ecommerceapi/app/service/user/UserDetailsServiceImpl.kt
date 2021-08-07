package io.commerce.ecommerceapi.app.service.user

import io.commerce.ecommerceapi.app.models.user.UserRepository
import io.commerce.ecommerceapi.utils.notNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
): UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User Not Found")
        return UserDetailsImpl.build(user)
    }

}