package gr.uth.ratemybite.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(length = 13)
    var barcode: String,

    @ManyToOne
    @JoinColumn(name = "food_category_id")
    var foodCategory: FoodCategory,

    @ManyToOne
    @JoinColumn(name = "company_id")
    var company: Company,

    @Enumerated(EnumType.STRING)
    var nutritionScore: NutritionScore? = null,

    @Temporal(TemporalType.TIMESTAMP)
    var dateCreated: Date? = null,
    var name: String
)