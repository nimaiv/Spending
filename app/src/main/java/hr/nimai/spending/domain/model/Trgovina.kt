package hr.nimai.spending.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Fransiza::class,
                                    parentColumns = ["id_fransize"],
                                    childColumns = ["id_fransize"],
                                    onDelete = SET_NULL)])
data class Trgovina(
    @PrimaryKey(autoGenerate = true) val id_trgovine: Int,
    val naziv_trgovine: String,
    val adresa: String? = null,
    val id_fransize: Int,
)
