package gr.uth.ratemybite.dto

import gr.uth.ratemybite.entities.NutritionScore

data class ProductPostDTO(
    val barcode: String,
    val name: String,
    val nutritionScore: NutritionScore,
    val companyName: String,
    val foodCategoryName: String,
    val ingredientNames: List<String>? = null
)