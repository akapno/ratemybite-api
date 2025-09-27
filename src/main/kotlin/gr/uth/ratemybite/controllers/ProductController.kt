package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.dto.ProductRequestDTO
import gr.uth.ratemybite.dto.ProductPostDTO
import gr.uth.ratemybite.entities.Product
import gr.uth.ratemybite.services.*
import java.nio.file.Paths
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/products")
class ProductController
@Autowired
constructor(
        val productService: ProductService,
        val foodCategoryService: FoodCategoryService,
        val companyService: CompanyService,
        val ingredientService: IngredientService,
        val imageService: ImageService,
) {
    @Value("\${file.no-image-available-path}") lateinit var defaultImage: String

    @GetMapping("/all")
    fun findAllProducts(): List<Product> {
        return productService.findAllProducts()
    }

    @GetMapping("/get/{id}")
    fun findProductById(@PathVariable id: Long): Optional<Product> {
        return productService.findProductById(id)
    }

    @GetMapping("/get")
    fun findProducts(
            @RequestParam(required = false) name: String?,
            @RequestParam(required = false) barcode: String?
    ): List<Product> {
        return when {
            name != null -> productService.findProductsByName(name)
            barcode != null -> listOf(productService.findProductByBarcode(barcode))
            else -> emptyList()
        }
    }

    @PostMapping("/add")
    fun addProduct(@RequestBody req: ProductRequestDTO): ResponseEntity<Any> {
        if (productService.findProductsByName(req.name).isNotEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(mapOf("error" to "Product with the same name already exists."))
        }

        val company = companyService.findCompanyByIdOrThrow(req.companyId)
        val foodCategory = foodCategoryService.findFoodCategoryByIdOrThrow(req.foodCategoryId)
        val ingredients = mapIdsToIngredients(req)

        val saved =
                productService.saveProduct(
                        Product(
                                barcode = req.barcode,
                                name = req.name,
                                nutritionScore = req.nutritionScore,
                                ingredients = ingredients,
                                company = company,
                                foodCategory = foodCategory,
                                dateCreated = Date(),
                                imagePath =
                                        Paths.get(defaultImage).toString()
                        )
                )
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    
    @PostMapping("/add-with-names")
    fun addProductWithNames(@RequestBody req: ProductPostDTO): ResponseEntity<Any> {
        if (productService.findProductsByName(req.name).isNotEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(mapOf("error" to "Product with the same name already exists."))
        }

        val company = companyService.findCompanyByNameOrThrow(req.companyName)
        val foodCategory = foodCategoryService.findFoodCategoryByNameOrThrow(req.foodCategoryName)
        val ingredients = mapIdsToIngredients(req)

        val saved =
                productService.saveProduct(
                        Product(
                                barcode = req.barcode,
                                name = req.name,
                                nutritionScore = req.nutritionScore,
                                ingredients = ingredients,
                                company = company,
                                foodCategory = foodCategory,
                                dateCreated = Date(),
                                imagePath =
                                        Paths.get(defaultImage).toString()
                        )
                )
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    // Product DTO body request must have all fields completed
    @PutMapping("/update/{id}")
    fun updateProduct(
            @PathVariable id: Long,
            @RequestBody req: ProductRequestDTO
    ): ResponseEntity<Product> {
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

        // Product DTO body request must have all fields completed
    @PutMapping("/update-with-names/{id}")
    fun updateProductWithNames(
            @PathVariable id: Long,
            @RequestBody req: ProductPostDTO
    ): ResponseEntity<Product> {
        val existingProduct = productService.findProductByIdOrThrow(id)

        val company = companyService.findCompanyByNameOrThrow(req.companyName)
        val foodCategory = foodCategoryService.findFoodCategoryByNameOrThrow(req.foodCategoryName)
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

    @PostMapping("/{id}/image")
    fun attachImage(
            @PathVariable id: Long,
            @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Product> {
        val product = productService.findProductByIdOrThrow(id)
        product.imagePath = imageService.saveImage(file)
        val updated = productService.saveProduct(product)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteProductById(@PathVariable id: Long): ResponseEntity<Void> {
        val product = productService.findProductByIdOrThrow(id)
        productService.deleteProductById(product.id!!)
        return ResponseEntity.noContent().build()
    }

    fun mapIdsToIngredients(req: ProductRequestDTO) =
            req.ingredientIds
                    ?.map { ingredientService.findIngredientByIdOrThrow(it) }
                    ?.toMutableSet()
                    ?: mutableSetOf()

    fun mapIdsToIngredients(req: ProductPostDTO) =
            req.ingredientNames
                    ?.map { ingredientService.findIngredientByNameOrThrow(it) }
                    ?.toMutableSet()
                    ?: mutableSetOf()
}
