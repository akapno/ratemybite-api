package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Product
import gr.uth.ratemybite.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository) {
    fun findAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun findProductById(id: Long): Optional<Product> {
        return productRepository.findById(id)
    }

    fun findProductsByName(name: String): List<Product> {
        return productRepository.findByName(name)
    }

    fun findProductByBarcode(barcode: String): Product {
        return productRepository.findByBarcode(barcode)
    }

    fun saveProduct(product: Product) {
        return productRepository.save(product)
    }
}