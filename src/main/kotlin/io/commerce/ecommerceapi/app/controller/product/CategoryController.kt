package io.commerce.ecommerceapi.app.controller.product

import io.commerce.ecommerceapi.app.models.product.Category
import io.commerce.ecommerceapi.app.payload.request.product.AddCategory
import io.commerce.ecommerceapi.app.service.product.CategoryService
import io.commerce.ecommerceapi.core.io.CrudController
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/private/category")
class CategoryController(
    val categoryService: CategoryService
): CrudController<CategoryService,Category>(categoryService){

    @PutMapping
    fun create(@Valid @RequestBody addCategory: AddCategory) = categoryService.add(addCategory)

}