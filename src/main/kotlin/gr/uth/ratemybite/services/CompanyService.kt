package gr.uth.ratemybite.services

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.repositories.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CompanyService @Autowired constructor(val companyRepository: CompanyRepository) {

    fun findAllCompanies(): List<Company> {
        return companyRepository.findAll()
    }

    fun findCompanyById(id: Long): Optional<Company> {
        return companyRepository.findById(id)
    }

    fun findCompanyByName(name: String): Company {
        return companyRepository.findByName(name)
    }

    fun saveCompany(company: Company) {
        return companyRepository.save(company)
    }
}