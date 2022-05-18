package hr.nimai.spending.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(primaryKeys = ["id_proizvoda", "id_racuna"],
        foreignKeys = [ForeignKey(entity = Proizvod::class,
                                    parentColumns = ["id_proizvoda"],
                                    childColumns = ["id_proizvoda"],
                                    onDelete = CASCADE),
                        ForeignKey(entity = Racun::class,
                                    parentColumns = ["id_racuna"],
                                    childColumns = ["id_racuna"],
                                    onDelete = CASCADE)])
data class Kupnja(
    val id_proizvoda: Int,
    val id_racuna: Int,
    val kolicina: Int = 1,
    val cijena: Int = 0,
)
