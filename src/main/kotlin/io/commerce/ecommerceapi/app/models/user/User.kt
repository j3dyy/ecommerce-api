package io.commerce.ecommerceapi.app.models.user

import org.hibernate.Hibernate
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


@Entity
@Table(
    name = "users",
    uniqueConstraints = arrayOf(
        UniqueConstraint(columnNames = arrayOf("email", "username"))
    )
)
class User (
    var email : String,
    var username : String,
    var password : String,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "userRoles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles : Set<Role> = mutableSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 1L

){

}