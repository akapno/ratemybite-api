package gr.uth.ratemybite.repositories

import gr.uth.ratemybite.entities.Product
import org.springframework.data.repository.ListCrudRepository

interface ProductRepository : ListCrudRepository<Product, Long> {
    fun findByName(name: String): List<Product>
    fun save(product: Product)
}