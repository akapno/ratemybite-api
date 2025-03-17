package gr.uth.trofoscan.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "food_category_id")
    var foodCategory: FoodCategory? = null,

    @ManyToOne
    @JoinColumn(name = "company_id")
    var company: Company? = null,

    @Enumerated(EnumType.STRING)
    var nutritionScore: NutritionScore? = null,

    @Temporal(TemporalType.TIMESTAMP)
    var dateCreated: Date? = null,
    var name: String
)