package io.commerce.ecommerceapi.configuration.security

import io.commerce.ecommerceapi.app.models.user.CommerceRoles
import io.commerce.ecommerceapi.configuration.security.jwt.AuthEntryPointJwt
import io.commerce.ecommerceapi.configuration.security.jwt.AuthTokenFilter
import io.commerce.ecommerceapi.configuration.security.jwt.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.web.servlet.invoke
import org.springframework.web.servlet.function.router
import org.springframework.context.support.beans
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true
)
class WebSecurityConfig(
    private val userDetailsService : UserDetailsService,
    private val unauthorizedHandler : AuthEntryPointJwt,
    private val jwtUtils : JwtUtils,
) : WebSecurityConfigurerAdapter() {

    @Bean
    public fun authenticationJwtTokenFilter() : AuthTokenFilter = AuthTokenFilter(jwtUtils,userDetailsService)


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()


    override fun configure(http: HttpSecurity) {
        http {
            cors {}
            csrf { disable() }
            exceptionHandling {
                authenticationEntryPoint = unauthorizedHandler
            }
            authorizeRequests {
                authorize("/api/auth/**", permitAll)
                authorize("/api/public/**",permitAll)
                authorize("/api/private/**", authenticated)
                authorize("/api/private/admin/**", hasAuthority(CommerceRoles.ROLE_ADMIN.name) )
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            httpBasic {  }
            addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter::class.java)

        }
    }

}