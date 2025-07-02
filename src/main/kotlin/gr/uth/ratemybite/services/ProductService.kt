package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.entities.Product
import gr.uth.ratemybite.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository) {
    fun findAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun findProductById(id: Long): Optional<Product> {
        return productRepository.findById(id)
    }

    fun findProductByIdOrThrow(id: Long): Product {
        return productRepository.findById(id)
            .orElseThrow() {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Product with $id not found.")
            }
    }

    fun findProductsByName(name: String): List<Product> {
        return productRepository.findByNameIgnoreCase(name)
    }

    fun findProductByBarcode(barcode: String): Product {
        return productRepository.findByBarcode(barcode)
    }

    fun saveProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun deleteProductById(id: Long) {
        return productRepository.deleteById(id)
    }
}