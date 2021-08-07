package io.commerce.ecommerceapi.app.service.product

import io.commerce.ecommerceapi.app.models.product.Category
import io.commerce.ecommerceapi.app.models.product.CategoryRepository
import io.commerce.ecommerceapi.app.models.product.CategoryTranslation
import io.commerce.ecommerceapi.app.models.product.CategoryTranslationRepository
import io.commerce.ecommerceapi.app.payload.request.product.AddCategory
import io.commerce.ecommerceapi.core.BaseService
import io.commerce.ecommerceapi.core.db.LocalizedId
import io.commerce.ecommerceapi.utils.isNull
import io.commerce.ecommerceapi.utils.notNull
import org.springframework.stereotype.Service

/**
 * @author jedy
 */
@Service
class CategoryService(
    val categoryRepository: CategoryRepository,
    val categoryTranslationRepository: CategoryTranslationRepository
): BaseService<CategoryRepository,Category>(categoryRepository) {

    /**
     * add new category if old exists then update
     * also creating translation @see createOrUpdateTranslations(category,addCategory) method
     */
    fun add(addCategory: AddCategory){
        var category = Category();
        category.version = 1;
        addCategory.id?.let { id ->
            categoryRepository.findById(id).ifPresent { cat ->
                category = cat
                category.version = cat.version + 1;
            }
        }
        repository.save(category)
        createOrUpdateTranslations(category,addCategory)
    }


    /**
     * create or update translation on existing category
     */
    private fun createOrUpdateTranslations(category: Category, addCategory: AddCategory): Category{
        category.localizations[addCategory.locale].let {
            when(it){
                null -> {
                    var translation = CategoryTranslation(category,addCategory.name, addCategory.description)
                    translation.localizedId = LocalizedId(addCategory.locale)
                    categoryTranslationRepository.save(translation)
                }
                else -> {
                    var translation = category.localizations[addCategory.locale]!!
                    translation.description = addCategory.description
                    translation.name = addCategory.name
                    categoryTranslationRepository.save(translation)
                }
            }
        }
        return category
    }
}