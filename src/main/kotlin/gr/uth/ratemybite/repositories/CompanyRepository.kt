package gr.uth.ratemybite.repositories

import gr.uth.ratemybite.entities.Company
import org.springframework.data.repository.ListCrudRepository

interface CompanyRepository : ListCrudRepository<Company, Long> {
    fun save(company: Company): Company
    fun findByName(name: String): List<Company>
}