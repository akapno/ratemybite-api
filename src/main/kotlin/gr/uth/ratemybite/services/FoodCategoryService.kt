package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.entities.FoodCategory
import gr.uth.ratemybite.repositories.CompanyRepository
import gr.uth.ratemybite.repositories.FoodCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.ListCrudRepository
import org.springframework.stereotype.Service

@Service
class FoodCategoryService @Autowired constructor(val foodCategoryRepository: FoodCategoryRepository) {

    fun findAllFoodCategories(): List<FoodCategory> {
        return foodCategoryRepository.findAll()
    }

    fun findFoodCategoryByName(name: String): FoodCategory {
        return foodCategoryRepository.findByName(name)
    }

    fun saveFoodCateogory(foodCategory: FoodCategory) {
        return foodCategoryRepository.save(foodCategory)
    }
}