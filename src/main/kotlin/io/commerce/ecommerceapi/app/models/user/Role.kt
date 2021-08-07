package io.commerce.ecommerceapi.app.models.user

import javax.persistence.*

enum class CommerceRoles {
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN
}

@Entity
@Table(name = "roles")
class Role(

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var name : CommerceRoles,

    @Id
    @GeneratedValue
    var id : Long = 1L

){
}