package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Ingredient
import gr.uth.ratemybite.repositories.IngredientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class IngredientService @Autowired constructor(val ingredientRepository: IngredientRepository) {

    fun findAllIngredients(): List<Ingredient> = ingredientRepository.findAll()

    fun findIngredientById(id: Long): Optional<Ingredient> = ingredientRepository.findById(id)

    fun findIngredientByIdOrThrow(id: Long): Ingredient =
        ingredientRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id $id not found.")
            }

    fun findIngredientByName(name: String): List<Ingredient> = ingredientRepository.findByName(name)

    fun saveIngredient(ingredient: Ingredient) = ingredientRepository.save(ingredient)
}