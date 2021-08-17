package io.commerce.ecommerceapi.app.models.product

import com.fasterxml.jackson.annotation.JsonIgnore
import io.commerce.ecommerceapi.core.db.Model
import io.commerce.ecommerceapi.core.db.Translatable
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*


@Entity
@Table(
    name = "products",
    indexes = [
        Index(columnList = "sku,barcode", unique = true)
    ]
)
class Product(

    var sku: String?,
    var barcode: String?,
    var isActive: Boolean,

    @OneToMany(
        mappedBy = "product",
        cascade = [CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    var localizations: Map<String,ProductTranslations> = mutableMapOf()

): Model<ProductTranslations>() {

    override fun getTranslations(): Map<String, ProductTranslations> = localizations

    override fun setTranslations(locale: String, translatable: ProductTranslations) {
        this.localizations += mutableMapOf(locale to translatable)
    }

    override fun removeTranslation(locale: String) {
        this.localizations -= locale
    }

}


@Entity
@Table(name = "product_translations")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
class ProductTranslations(
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnore
    var product: Product,

    var name: String,
    var description: String,
): Translatable(){

}