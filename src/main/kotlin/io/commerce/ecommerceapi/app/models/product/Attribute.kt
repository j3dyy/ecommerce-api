package io.commerce.ecommerceapi.app.models.product

import com.fasterxml.jackson.annotation.JsonIgnore
import io.commerce.ecommerceapi.core.db.Model
import io.commerce.ecommerceapi.core.db.Translatable
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.*

enum class AttributeTypes{
    TEXT_FIELD,DATE_FIELD,LONGTEXT_FIELD,COLOR_PICKER,DATE_PICKER,
    DATETIME_PICKER,CUSTOM_FIELD
}

@Entity
@Table(name = "attributes")
class Attribute(

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var type: AttributeTypes = AttributeTypes.CUSTOM_FIELD,

    @OneToMany(
        mappedBy = "attribute",
        cascade = [CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    var localizations: Map<String,AttributeTranslations> = mutableMapOf()

) : Model<AttributeTranslations>(){

    override fun getTranslations(): Map<String, AttributeTranslations> = localizations

    override fun setTranslations(locale: String, translatable: Translatable) {
        this.localizations += mutableMapOf(locale to translatable as AttributeTranslations)
    }

    override fun removeTranslation(locale: String) {
        this.localizations -= locale
    }
}

@Entity
@Table(name = "attribute_translations")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
class AttributeTranslations(
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnore
    var attribute: Attribute,

    var name: String,
    var description: String,

): Translatable(){
    companion object
}

