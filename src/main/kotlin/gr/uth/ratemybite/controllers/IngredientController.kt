package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.entities.Ingredient
import gr.uth.ratemybite.services.IngredientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/ingredients")
class IngredientController @Autowired constructor(val ingredientService: IngredientService) {

    @GetMapping("/all")
    fun findAllIngredients(): List<Ingredient> =
        ingredientService.findAllIngredients()

    @GetMapping("/get/{id}")
    fun findIngredientById(@PathVariable id: Long): Optional<Ingredient> =
        ingredientService.findIngredientById(id)

    @GetMapping("/get")
    fun findIngredientByName(@RequestParam name: String): List<Ingredient> =
        ingredientService.findIngredientByName(name)

    @PostMapping("/add")
    fun addIngredient(@RequestBody ingredient: Ingredient): ResponseEntity<Ingredient> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            ingredientService.saveIngredient(ingredient)
        )

    @PutMapping("/update/{id}")
    fun updateIngredient(@PathVariable id: Long, @RequestBody req: Ingredient): ResponseEntity<Ingredient> =
        ResponseEntity.ok(
            ingredientService.saveIngredient(
                ingredientService.findIngredientByIdOrThrow(id).apply {
                    name = req.name
                    allergen = req.allergen
                    points = req.points
                    description = req.description
                })
        )
}