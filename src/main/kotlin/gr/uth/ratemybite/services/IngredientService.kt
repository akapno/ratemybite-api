package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Ingredient
import gr.uth.ratemybite.repositories.IngredientRepository
import gr.uth.ratemybite.repositories.ProductRepository
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class IngredientService
@Autowired
constructor(
        val ingredientRepository: IngredientRepository,
        private val productRepository: ProductRepository
) {

    fun findAllIngredients(): List<Ingredient> = ingredientRepository.findAll()

    fun findIngredientById(id: Long): Optional<Ingredient> = ingredientRepository.findById(id)

    fun findIngredientByIdOrThrow(id: Long): Ingredient =
            ingredientRepository.findById(id).orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id $id not found.")
            }

    fun findIngredientByName(name: String): Optional<Ingredient> =
            ingredientRepository.findByName(name)

    fun findIngredientByNameOrThrow(name: String): Ingredient =
            ingredientRepository.findByName(name).orElseThrow {
                ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ingredient with name $name not found."
                )
            }

    fun saveIngredient(ingredient: Ingredient) = ingredientRepository.save(ingredient)

    fun deleteIngredientById(id: Long) {
        val ingredient = findIngredientByIdOrThrow(id)
        if (productRepository.existsByIngredientsContains(ingredient)) {
            throw ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete company still used by products."
            )
        }
        ingredientRepository.deleteById(id)
    }
}
