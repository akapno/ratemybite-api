package gr.uth.ratemybite.entities

import jakarta.persistence.*

@Entity
@Table(name = "ingredients")
class Ingridient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String,
    var description: String,
    var allergen: Boolean,
    var points: Int
)
