package io.commerce.ecommerceapi.configuration.security

import io.commerce.ecommerceapi.app.models.user.CommerceRoles
import io.commerce.ecommerceapi.configuration.security.jwt.AuthEntryPointJwt
import io.commerce.ecommerceapi.configuration.security.jwt.AuthTokenFilter
import io.commerce.ecommerceapi.configuration.security.jwt.JwtUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


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

    @Bean
    fun corsFilter(): CorsFilter? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.setAllowedOriginPatterns(listOf("*")) // this allows all origin
        config.addAllowedHeader("*") // this allows all headers
        config.addAllowedMethod("OPTIONS")
        config.addAllowedMethod("HEAD")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("DELETE")
        config.addAllowedMethod("PATCH")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

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