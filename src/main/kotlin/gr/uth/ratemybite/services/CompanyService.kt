package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.repositories.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class CompanyService @Autowired constructor(val companyRepository: CompanyRepository) {

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

    fun findCompanyByName(name: String): List<Company> {
        return companyRepository.findByName(name)
    }

    fun saveCompany(company: Company): Company {
        return companyRepository.save(company)
    }
}