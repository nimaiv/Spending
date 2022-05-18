package hr.nimai.spending.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tip_proizvoda")
data class TipProizvoda(
    @PrimaryKey(autoGenerate = true) val id_tipa_proizvoda: Int,
    val naziv_tipa_proizvoda: String,
)
