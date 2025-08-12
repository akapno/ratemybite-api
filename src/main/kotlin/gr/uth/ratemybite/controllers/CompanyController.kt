package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.services.CompanyService
import gr.uth.ratemybite.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/companies")
class CompanyController @Autowired constructor(val companyService: CompanyService, val productService: ProductService) {

    @GetMapping("/all")
    fun findAllCompanies() = companyService.findAllCompanies()

    @GetMapping("/get/{id}")
    fun findCompanyById(@PathVariable id: Long) = companyService.findCompanyById(id)

    @GetMapping("/get")
    fun findCompanyByName(@RequestParam name: String) = companyService.findCompanyByName(name)

    @PostMapping("/add")
    fun addCompany(@RequestParam name: String): ResponseEntity<Any> {
        if (companyService.findCompanyByName(name).isNotEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(mapOf("error" to "Company with the same name already exists."))
        }
        val saved = companyService.saveCompany(Company(name = name))
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @PutMapping("/update/{id}")
    fun updateCompany(@PathVariable id: Long, @RequestBody req: Company): ResponseEntity<Company> {
        val existingCompany = companyService.findCompanyByIdOrThrow(id)
        existingCompany.apply {
            name = req.name
        }
        return ResponseEntity.ok(companyService.saveCompany(existingCompany))
    }

    @DeleteMapping("/{id}")
    fun deleteCompanyById(@PathVariable id: Long): ResponseEntity<Void> {
        val company = companyService.findCompanyByIdOrThrow(id)
        companyService.deleteCompanyById(company.id!!)
        return ResponseEntity.noContent().build()
    }
}