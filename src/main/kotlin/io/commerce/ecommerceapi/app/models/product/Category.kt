package io.commerce.ecommerceapi.app.models.product

import com.fasterxml.jackson.annotation.JsonIgnore
import io.commerce.ecommerceapi.core.db.LocalizedId
import io.commerce.ecommerceapi.core.db.Model
import io.commerce.ecommerceapi.core.db.Translatable
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

@Entity
@Table(name = "categories")
class Category(

    @OneToMany(
        mappedBy = "category",
        cascade = [CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    var localizations: Map<String,CategoryTranslation> = mutableMapOf()

): Model<CategoryTranslation>() {

    override fun getTranslations(): Map<String, CategoryTranslation> = localizations

    override fun setTranslations(locale: String, translatable: CategoryTranslation) {
        this.localizations += mutableMapOf(locale to translatable)
    }

    override fun removeTranslation(locale: String) {
        this.localizations -= locale
    }
}

@Entity
@Table(name = "category_translations")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
class CategoryTranslation(
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnore
    var category: Category,
    var name: String,
    var description: String,
): Translatable() {

}

