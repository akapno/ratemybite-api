package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.FoodCategory
import gr.uth.ratemybite.repositories.FoodCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

@Service
class FoodCategoryService @Autowired constructor(val foodCategoryRepository: FoodCategoryRepository) {

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

    fun findFoodCategoryByName(name: String): List<FoodCategory> {
        return foodCategoryRepository.findByName(name)
    }

    fun saveFoodCateogory(foodCategory: FoodCategory): FoodCategory {
        return foodCategoryRepository.save(foodCategory)
    }
}