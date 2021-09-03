package io.commerce.ecommerceapi.app.payload.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank


enum class SortDirections{
    ASCENDING,DESCENDING
}


data class Paging(
    @get:NotBlank
    @get:Min(0)
    val page: Int,
    @get:NotBlank
    @get:Min(1)
    val size: Int,
)

data class Sorting(
    val sortBy: Set<Pair<String,SortDirections>>
)

data class PagingSupport(
    val paging: Paging?,
    val sorting: Sorting?,
)

data class PagingTranslationSupport(
    val paging: Paging?,
    val sorting: Sorting?,
    @get:NotBlank
    val locale: String
)