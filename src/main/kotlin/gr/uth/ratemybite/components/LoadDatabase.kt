package gr.uth.ratemybite.components

import gr.uth.ratemybite.entities.Company
import gr.uth.ratemybite.entities.FoodCategory
import gr.uth.ratemybite.entities.NutritionScore
import gr.uth.ratemybite.entities.Product
import gr.uth.ratemybite.services.CompanyService
import gr.uth.ratemybite.services.FoodCategoryService
import gr.uth.ratemybite.services.ProductService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoadDatabase {

    private val logger = LoggerFactory.getLogger(LoadDatabase::class.java)

    @Bean
    fun initDatabase(
        @Autowired productService: ProductService,
        @Autowired companyService: CompanyService,
        @Autowired foodCategoryService: FoodCategoryService
    ): CommandLineRunner {
        return CommandLineRunner { args ->
            logger.info(
                "Preloading " + companyService.saveCompany(
                    Company(name = "Jiggles Inc.")
                )
            ).toString()
            logger.info(
                "Preloading " + foodCategoryService.saveFoodCateogory(
                    FoodCategory(name = "Fruit")
                ).toString()
            )
            logger.info(
                "Preloading " + productService.saveProduct(
                    Product(
                        name = "Banana",
                        barcode = "1212121212121",
                        nutritionScore = NutritionScore.A,
                        company = companyService.findCompanyByName("Jiggles Inc."),
                        foodCategory = foodCategoryService.findFoodCategoryByName("Fruit")
                    )
                ).toString()
            )
        }
    }
}