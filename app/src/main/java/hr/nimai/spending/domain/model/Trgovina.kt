package hr.nimai.spending.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Trgovina(
    @PrimaryKey(autoGenerate = true) val id_trgovine: Int,
    val naziv_trgovine: String,
    val adresa: String? = null,
)
