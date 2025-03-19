package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.entities.Product
import gr.uth.ratemybite.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class MainController @Autowired constructor(val productService: ProductService) {

    @GetMapping("/all")
    fun findAllProducts(): List<Product> {
        return productService.findAllProducts()
    }

    @GetMapping("/get/{name}")
    fun FindProductsByName(@PathVariable name: String): List<Product> {
        return productService.findProductsByName(name)
    }
}