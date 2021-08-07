package io.commerce.ecommerceapi.app.service.user

import com.fasterxml.jackson.annotation.JsonIgnore
import io.commerce.ecommerceapi.app.models.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


class UserDetailsImpl(
    private val id: Long,
    private val username: String,
    private val email: String,
    @get:JsonIgnore
    private val password: String,
    private val authorities: Collection<GrantedAuthority?>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return authorities
    }

    fun getId(): Long? {
        return id
    }

    fun getEmail(): String? {
        return email
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val user = o as UserDetailsImpl
        return Objects.equals(id, user.id)
    }

    companion object {
        fun build(user: User): UserDetailsImpl {
            val authorities = user.roles.map { role -> SimpleGrantedAuthority(role.name.name) }
           return UserDetailsImpl(user.id, user.username,user.email, user.password,authorities)
        }
    }



}