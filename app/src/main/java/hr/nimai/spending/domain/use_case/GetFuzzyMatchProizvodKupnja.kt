package hr.nimai.spending.domain.use_case

import android.util.Log
import hr.nimai.spending.domain.repository.ProizvodRepository
import hr.nimai.spending.domain.util.ProizvodKupnjaHolder
import kotlinx.coroutines.flow.collectLatest
import me.xdrop.fuzzywuzzy.FuzzySearch

class GetFuzzyMatchProizvodKupnja(
    private val repository: ProizvodRepository
) {

    suspend operator fun invoke(skraceniNazivProizvoda: String): ProizvodKupnjaHolder? {
        val proizvodiFlow = repository.getAll()
        var returnProizvod: ProizvodKupnjaHolder? = null
        try {
            proizvodiFlow.collectLatest { proizvodi ->
                val match = FuzzySearch.extractOne(skraceniNazivProizvoda, proizvodi) { p -> p.skraceni_naziv_proizvoda }

                if (match.score > 85) {
                    returnProizvod = ProizvodKupnjaHolder(
                        id_proizvoda = match.referent.id_proizvoda,
                        naziv_proizvoda = match.referent.naziv_proizvoda,
                        skraceni_naziv_proizvoda = match.referent.skraceni_naziv_proizvoda
                    )
                }
            }

            return returnProizvod
        } catch (e: Exception) {
            Log.e("GetFuzzyMatch", "NoSuchElementException")
            return null
        }
    }
}