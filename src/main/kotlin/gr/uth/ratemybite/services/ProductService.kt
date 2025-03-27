package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Product
import gr.uth.ratemybite.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository) {
    fun findAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun findProductsByName(name: String): List<Product> {
        return productRepository.findByName(name)
    }

    fun saveProduct(product: Product) {
        return productRepository.save(product)
    }
}