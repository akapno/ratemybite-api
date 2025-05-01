package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.services.CompanyService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/companies")
class CompanyController @Autowired constructor(val companyService: CompanyService) {

    @GetMapping("/all")
    fun findAllCompanies(): List<Company> {
        return companyService.findAllCompanies()
    }

    @GetMapping("/get/{id}")
    fun findCompanyById(@PathVariable id: Long): Optional<Company> {
        return companyService.findCompanyById(id)
    }

    @GetMapping("/get")
    fun findCompanyByName(@RequestParam name: String): List<Company> {
        return companyService.findCompanyByName(name)
    }

    @PostMapping("/add")
    fun addCompany(@RequestParam name: String): ResponseEntity<Company> {
        val saved = companyService.saveCompany(Company(name = name))
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }
}