package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.repositories.CompanyRepository
import gr.uth.ratemybite.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class CompanyService @Autowired constructor(
    val companyRepository: CompanyRepository,
    val productRepository: ProductRepository
) {

    fun findAllCompanies(): List<Company> {
        return companyRepository.findAll()
    }

    fun findCompanyById(id: Long): Optional<Company> {
        return companyRepository.findById(id)
    }

    fun findCompanyByIdOrThrow(id: Long): Company {
        return companyRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Company(id: $id) not found.")
            }
    }

    fun findCompanyByName(name: String): Optional<Company> {
        return companyRepository.findByName(name)
    }

    fun findCompanyByNameOrThrow(name: String): Company {
    return companyRepository.findByName(name)
        .orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Company(name: $name) not found.")
        }
    }

    fun saveCompany(company: Company): Company {
        return companyRepository.save(company)
    }

    fun deleteCompanyById(id: Long) {
        val company = findCompanyByIdOrThrow(id)
        if (productRepository.existsByCompany(company)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete company still used by products.")
        }
        companyRepository.deleteById(id)
    }
}