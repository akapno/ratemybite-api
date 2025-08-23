package gr.uth.ratemybite.dto

import gr.uth.ratemybite.entities.NutritionScore

data class ProductRequestDTO(
    val barcode: String,
    val name: String,
    val nutritionScore: NutritionScore,
    val companyId: Long,
    val foodCategoryId: Long,
    val ingredientIds: List<Long>? = null
)