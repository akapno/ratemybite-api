package gr.uth.ratemybite.repositories

import gr.uth.ratemybite.entities.Company
import org.springframework.data.repository.ListCrudRepository
import java.util.Optional

interface CompanyRepository : ListCrudRepository<Company, Long> {
    fun save(company: Company): Company
    fun findByName(name: String): Optional<Company>
}