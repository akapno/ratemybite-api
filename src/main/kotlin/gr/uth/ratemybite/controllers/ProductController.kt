package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.entities.Product
import gr.uth.ratemybite.repositories.ProductRepository
import gr.uth.ratemybite.services.CompanyService
import gr.uth.ratemybite.services.FoodCategoryService
import gr.uth.ratemybite.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class ProductController @Autowired constructor(val productService: ProductService, val foodCategoryService: FoodCategoryService, val companyService: CompanyService) {

    @GetMapping("/products/all")
    fun findAllProducts(): List<Product> {
        return productService.findAllProducts()
    }

    @GetMapping("/products/get")
    fun FindProductsByName(@RequestParam name: String): List<Product> {
        return productService.findProductsByName(name)
    }

//    @PostMapping("/products/add")
//    fun addProduct(@RequestBody product: Product): ResponseEntity<Product> {
//        companyService.saveCompany(product.company)
//        foodCategoryService.saveFoodCateogory(product.foodCategory)
//        return productService.saveProduct(product)
//    }
}