package io.commerce.ecommerceapi.app.models

import io.commerce.ecommerceapi.core.db.Sortable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "resources")
class Resource(
    var extension: String,
    var originalName: String,
    @Column(name = "stabilizedName", unique = true)
    var stabilizedName: String,
    var fullpath:String,
): Sortable()


@Repository
interface ResourceRepository: PagingAndSortingRepository<Resource,Long>{

    fun getByStabilizedName(stabilizedName: String): Resource?


}