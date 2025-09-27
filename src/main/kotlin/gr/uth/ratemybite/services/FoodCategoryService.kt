package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.FoodCategory
import gr.uth.ratemybite.repositories.FoodCategoryRepository
import gr.uth.ratemybite.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

@Service
class FoodCategoryService @Autowired constructor(
    val foodCategoryRepository: FoodCategoryRepository,
    val productRepository: ProductRepository
) {

    fun findAllFoodCategories(): List<FoodCategory> {
        return foodCategoryRepository.findAll()
    }

    fun findFoodCategoryById(id: Long): Optional<FoodCategory> {
        return foodCategoryRepository.findById(id)
    }

    fun findFoodCategoryByIdOrThrow(id: Long): FoodCategory {
        return foodCategoryRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "FoodCategory(id: $id) not found.")
            }
    }

    fun findFoodCategoryByName(name: String): Optional<FoodCategory> {
        return foodCategoryRepository.findByName(name)
    }

    fun findFoodCategoryByNameOrThrow(name: String): FoodCategory {
        return foodCategoryRepository.findByName(name)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "FoodCategory(name: $name) not found.")
            }
    }

    fun saveFoodCateogory(foodCategory: FoodCategory): FoodCategory {
        return foodCategoryRepository.save(foodCategory)
    }

    fun deleteFoodCategoryById(id: Long) {
        val foodCategory = findFoodCategoryByIdOrThrow(id)
        if (productRepository.existsByFoodCategory(foodCategory)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete food category still used by products.")
        }
        foodCategoryRepository.deleteById(id)
    }
}