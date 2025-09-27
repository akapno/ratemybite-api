package gr.uth.ratemybite.repositories

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.entities.FoodCategory
import org.springframework.data.repository.ListCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

interface FoodCategoryRepository : ListCrudRepository<FoodCategory, Long> {
    fun save(foodCategory: FoodCategory): FoodCategory
    fun findByName(name: String): Optional<FoodCategory>
}