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
    fun findAllFoodCategories(): List<FoodCategory> {
        return foodCategoryService.findAllFoodCategories()
    }

    @GetMapping("/get/{id}")
    fun findCompanyById(@PathVariable id: Long): Optional<FoodCategory> {
        return foodCategoryService.findFoodCategoryById(id)
    }

    @GetMapping("/get")
    fun findFoodCategoryByName(@RequestParam name: String): List<FoodCategory> {
        return foodCategoryService.findFoodCategoryByName(name)
    }

    @PostMapping("/add")
    fun addFoodCategory(@RequestParam name: String): ResponseEntity<FoodCategory> {
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
}