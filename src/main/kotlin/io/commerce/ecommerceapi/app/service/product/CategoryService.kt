package io.commerce.ecommerceapi.app.service.product

import io.commerce.ecommerceapi.app.models.product.Category
import io.commerce.ecommerceapi.app.models.product.CategoryRepository
import io.commerce.ecommerceapi.app.models.product.CategoryTranslation
import io.commerce.ecommerceapi.app.models.product.CategoryTranslationRepository
import io.commerce.ecommerceapi.app.payload.request.product.AddCategory
import io.commerce.ecommerceapi.core.BaseService
import io.commerce.ecommerceapi.core.BaseTranslatableService
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
): BaseTranslatableService<CategoryTranslationRepository,CategoryTranslation,CategoryRepository,Category>(
    categoryRepository,
    categoryTranslationRepository
) {

}