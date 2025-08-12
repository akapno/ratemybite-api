package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.entities.FoodCategory
import gr.uth.ratemybite.services.FoodCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/foodcategories")
class FoodCategoryController @Autowired constructor(val foodCategoryService: FoodCategoryService) {

    @GetMapping("/all")
    fun findAllFoodCategories() = foodCategoryService.findAllFoodCategories()

    @GetMapping("/get/{id}")
    fun findCompanyById(@PathVariable id: Long) = foodCategoryService.findFoodCategoryById(id)

    @GetMapping("/get")
    fun findFoodCategoryByName(@RequestParam name: String) = foodCategoryService.findFoodCategoryByName(name)

    @PostMapping("/add")
    fun addFoodCategory(@RequestParam name: String): ResponseEntity<Any> {
        if (foodCategoryService.findFoodCategoryByName(name).isNotEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(mapOf("error" to "Food category with the same name already exists."))
        }
        val saved = foodCategoryService.saveFoodCateogory(FoodCategory(name = name))
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @PutMapping("/update/{id}")
    fun updateFoodCategory(@PathVariable id: Long, @RequestBody req: FoodCategory): ResponseEntity<FoodCategory> {
        val existingFoodCategory = foodCategoryService.findFoodCategoryByIdOrThrow(id)
        existingFoodCategory.apply {
            name = req.name
        }
        return ResponseEntity.ok(foodCategoryService.saveFoodCateogory(existingFoodCategory))
    }

    @DeleteMapping("/{id}")
    fun deleteCompanyById(@PathVariable id: Long): ResponseEntity<Void> {
        val company = foodCategoryService.findFoodCategoryByIdOrThrow(id)
        foodCategoryService.deleteFoodCategoryById(company.id!!)
        return ResponseEntity.noContent().build()
    }
}