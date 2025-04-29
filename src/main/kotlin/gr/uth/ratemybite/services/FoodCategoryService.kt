package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.FoodCategory
import gr.uth.ratemybite.repositories.FoodCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class FoodCategoryService @Autowired constructor(val foodCategoryRepository: FoodCategoryRepository) {

    fun findAllFoodCategories(): List<FoodCategory> {
        return foodCategoryRepository.findAll()
    }

    fun findFoodCategoryById(id: Long): Optional<FoodCategory> {
        return foodCategoryRepository.findById(id)
    }

    fun findFoodCategoryByName(name: String): FoodCategory {
        return foodCategoryRepository.findByName(name)
    }

    fun saveFoodCateogory(foodCategory: FoodCategory) {
        return foodCategoryRepository.save(foodCategory)
    }
}