package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.dto.ProductRequestDTO
import gr.uth.ratemybite.entities.Product
import gr.uth.ratemybite.services.CompanyService
import gr.uth.ratemybite.services.FoodCategoryService
import gr.uth.ratemybite.services.IngredientService
import gr.uth.ratemybite.services.ProductService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController @Autowired constructor(
    val productService: ProductService,
    val foodCategoryService: FoodCategoryService,
    val companyService: CompanyService,
    val ingredientService: IngredientService,
) {

    @GetMapping("/all")
    fun findAllProducts(): List<Product> {
        return productService.findAllProducts()
    }

    @GetMapping("/get/{id}")
    fun findProductById(@PathVariable id: Long): Optional<Product> {
        return productService.findProductById(id)
    }

    @GetMapping("/get")
    fun findProductsByName(@RequestParam name: String): List<Product> {
        return productService.findProductsByName(name)
    }

    @PostMapping("/add")
    fun addProduct(@RequestBody req: ProductRequestDTO): ResponseEntity<Product> {
        val company = companyService.findCompanyByIdOrThrow(req.companyId)
        val foodCategory = foodCategoryService.findFoodCategoryByIdOrThrow(req.foodCategoryId)
        val ingredients = mapIdsToIngredients(req)

        val saved = productService.saveProduct(
            Product(
                barcode = req.barcode,
                name = req.name,
                nutritionScore = req.nutritionScore,
                ingredients = ingredients,
                company = company,
                foodCategory = foodCategory,
                dateCreated = Date()
            )
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    // Product DTO body request must have all fields completed
    @PutMapping("/update/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody req: ProductRequestDTO): ResponseEntity<Product> {
        val existingProduct = productService.findProductByIdOrThrow(id)

        val company = companyService.findCompanyByIdOrThrow(req.companyId)
        val foodCategory = foodCategoryService.findFoodCategoryByIdOrThrow(req.foodCategoryId)
        val ingredients = mapIdsToIngredients(req)

        existingProduct.apply {
            barcode = req.barcode
            name = req.name
            nutritionScore = req.nutritionScore
            this.ingredients = ingredients
            this.company = company
            this.foodCategory = foodCategory
        }

        return ResponseEntity.ok(productService.saveProduct(existingProduct))
    }

    @DeleteMapping("/{id}")
    fun deleteProductById(@PathVariable id: Long): ResponseEntity<Void> {
        val product = productService.findProductByIdOrThrow(id)
        productService.deleteProductById(product.id!!)
        return ResponseEntity.noContent().build()
    }

    fun mapIdsToIngredients(req: ProductRequestDTO) =
        req.ingredientIds?.map {
            ingredientService.findIngredientByIdOrThrow(it)
        }?.toMutableSet() ?: mutableSetOf()
}