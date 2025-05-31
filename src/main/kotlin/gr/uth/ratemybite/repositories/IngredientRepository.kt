package gr.uth.ratemybite.repositories

import gr.uth.ratemybite.entities.Ingredient
import org.springframework.data.repository.ListCrudRepository

interface IngredientRepository : ListCrudRepository<Ingredient, Long> {
    fun findByName(name: String): List<Ingredient>
}