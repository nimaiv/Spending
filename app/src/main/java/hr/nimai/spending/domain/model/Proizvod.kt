package hr.nimai.spending.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = TipProizvoda::class,
                                    parentColumns = ["id_tipa_proizvoda"],
                                    childColumns = ["tip_proizvoda"],
                                    onDelete = SET_NULL)])
data class Proizvod(
    @PrimaryKey(autoGenerate = true) val id_proizvoda: Int,
    val naziv_proizvoda: String,
    val skraceni_naziv_proizvoda: String,
    val barkod: String? = null,
    val uri_slike: String? = null,
    val tip_proizvoda: Int? = null,
)

class NoProizvodWithIDException(message: String): Exception(message)
