package hr.nimai.spending.domain.use_case

import android.util.Log
import hr.nimai.spending.domain.repository.ProizvodRepository
import hr.nimai.spending.domain.util.ProizvodKupnjaHolder

class ExtractProductInfoFromOCR(
    val repository: ProizvodRepository
) {

    suspend operator fun invoke(ocrTekst: String): List<ProizvodKupnjaHolder> {

        val imeProizvodaRegex =
            Regex("""^(.+)\n(\d+)\s*[x*]+""", setOf(RegexOption.MULTILINE, RegexOption.IGNORE_CASE))
        val cijeneProizvodaRegex = Regex(
            """^(\d+.\d{2}).*\n(\d+.\d{2})\n\D""",
            setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE)
        )

        val imena = imeProizvodaRegex.findAll(ocrTekst).toList()
        val cijene = cijeneProizvodaRegex.findAll(ocrTekst).toList()

        try {
            val proizvodi = mutableListOf<ProizvodKupnjaHolder>()
            for (i in 1 until imena.size) {
                val nazivProizvoda = imena[i].groupValues[1]
                val kolicinaProizvoda = imena[i].groupValues[2].toInt()
                var proizvod: ProizvodKupnjaHolder? = GetFuzzyMatchProizvodKupnja(repository).invoke(nazivProizvoda)
                proizvod = if (proizvod != null) {
                    Log.d("EXTRACT", "NOT NULL")
                    ProizvodKupnjaHolder(
                        naziv_proizvoda = proizvod.naziv_proizvoda,
                        skraceni_naziv_proizvoda = proizvod.skraceni_naziv_proizvoda,
                        kolicina = kolicinaProizvoda,
                        id_proizvoda = proizvod.id_proizvoda
                    )
                } else {
                    Log.d("EXTRACT", "NULL")
                    ProizvodKupnjaHolder(
                        naziv_proizvoda = nazivProizvoda,
                        skraceni_naziv_proizvoda = nazivProizvoda,
                        kolicina = kolicinaProizvoda
                    )
                }
                proizvodi.add(proizvod)
            }
            for (i in 1 until cijene.size) {
                val cijenaProizvoda = cijene[i].groupValues[1].toDouble()
                try {
                    proizvodi[i-1].cijena = cijenaProizvoda
                } catch (e: Exception) {
                    Log.e("ExtractProductInfoFromOCR-inner", e.message?: "Greska kod citanja OCR-a")
                }
            }
            return proizvodi

        } catch (e: Exception) {
            Log.e("ExtractProductInfoFromOCR-outer", e.message?: "Greska kod citanja OCR-a")
        }
        return emptyList()
    }
}