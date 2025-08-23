package gr.uth.ratemybite.repositories

import gr.uth.ratemybite.entities.Ingredient
import org.springframework.data.repository.ListCrudRepository
import java.util.Optional

interface IngredientRepository : ListCrudRepository<Ingredient, Long> {
    fun findByName(name: String): Optional<Ingredient>
}