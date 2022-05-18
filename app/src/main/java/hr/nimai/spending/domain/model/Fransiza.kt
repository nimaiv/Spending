package hr.nimai.spending.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fransiza(
    @PrimaryKey(autoGenerate = true) val id_fransize: Int,
    val naziv_fransize: String,
)
