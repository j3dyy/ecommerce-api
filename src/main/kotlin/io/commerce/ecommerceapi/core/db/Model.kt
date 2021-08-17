package io.commerce.ecommerceapi.core.db

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

/**
 * @author jedy
 */

@MappedSuperclass
open class EntityModel(
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     var id: Long = 1L,
     var created_at: Timestamp = Timestamp(System.currentTimeMillis()),
     var updated_at: Timestamp = Timestamp(System.currentTimeMillis()),
     var deleted_at: Timestamp? = null
): Serializable

@MappedSuperclass
open class Sortable(
     var sort: Int? = 0
): EntityModel()


@MappedSuperclass
abstract class Model<E: Translatable> (
     @Version
     var version: Int = 1,

): Sortable(){

     @JsonIgnore
     abstract fun getTranslations(): Map<String,E>
     abstract fun setTranslations(locale: String, translatable: E)
     abstract fun removeTranslation(locale: String)
}


@MappedSuperclass
open class Translatable(
     @EmbeddedId
     var localizedId: LocalizedId = LocalizedId("ka")
) {

}

@Embeddable
class LocalizedId(
     var locale: String,
     var  id: Long = 0
): Serializable {
     private val serialVersionUID = 1089196571270403924L

     override fun hashCode(): Int {
          val prime = 31
          var result = 1
          result = prime * result + if (locale == null) 0 else locale.hashCode()
          result = (prime * result + if (id == null) 0 else id.hashCode())
          return result
     }

     override fun equals(obj: Any?): Boolean {
          if (this === obj) return true
          if (obj == null) return false
          if (javaClass != obj.javaClass) return false
          val other = obj as LocalizedId
          if (locale == null) {
               if (other.locale != null) return false
          } else if (locale != other.locale) return false
          if (id == null) {
               if (other.id != null) return false
          } else if (id != other.id) return false
          return true
     }
}