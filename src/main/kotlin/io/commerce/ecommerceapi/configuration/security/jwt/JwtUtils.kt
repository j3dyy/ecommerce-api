package io.commerce.ecommerceapi.configuration.security.jwt

import io.commerce.ecommerceapi.app.models.user.User
import io.commerce.ecommerceapi.app.service.user.UserDetailsImpl
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtils {
    private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)

    @Value("\${commerce.app.jwt.secret}")
    private lateinit var jwtSecret : String

    @Value("\${commerce.app.jwt.expiration}")
    private lateinit var jwtExpirationMs : Integer

    fun generateJwtToken(authentication: Authentication): String? {
        val userPrincipal: UserDetailsImpl = authentication.principal as UserDetailsImpl
        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().getTime().plus(jwtExpirationMs.toLong())))
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUserNameFromJwtToken(token: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }

}



