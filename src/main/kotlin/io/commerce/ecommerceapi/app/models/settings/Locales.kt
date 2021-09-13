package io.commerce.ecommerceapi.app.models.settings

import io.commerce.ecommerceapi.core.db.ActivableModel
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "locales")
class Locales(

    @Column(unique = true, name = "locale")
    var locale: String,
    var name: String,
    var active: Boolean
): ActivableModel(is_active = active)


