package hr.nimai.spending.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Trgovina::class,
                                    parentColumns = ["id_trgovine"],
                                    childColumns = ["id_trgovine"],
                                    onDelete = SET_NULL)])
data class Racun(
    @PrimaryKey(autoGenerate = true) val id_racuna: Int,
    val broj_racuna: String,
    val id_trgovine: Int? = null,
    val ukupan_iznos_racuna: Double,
    val datum_racuna: String,
    val ocr_tekst: String? = null,
    val uri_slike: String? = null,
)


class InvalidRacunException(message: String): Exception(message)