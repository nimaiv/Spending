package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.util.getTodayDate

class ExtractRacunInfoFromOCR {

    operator fun invoke(ocrTekst: String): Racun {


        val brojRacunaRegex = Regex("""([\d]{2,6}/[\d]{2,6}/[\d]{2,5})""")

        val datumRegex = Regex("""([\d]{2}\.[\d]{2}\.[\d]{4})""")
        val ukupanIznosRegex = Regex("""ukupno\n.*?\n*([\d]+\.[\d]{2})""", RegexOption.IGNORE_CASE)

        var datumRacuna = datumRegex.find(ocrTekst)?.value

        if (!datumRacuna.isNullOrBlank()) datumRacuna += "." else datumRacuna = getTodayDate()

        return Racun(
            id_racuna = 0,
            broj_racuna = brojRacunaRegex.find(ocrTekst)?.value ?: "",
            datum_racuna = datumRacuna,
            ukupan_iznos_racuna = ukupanIznosRegex.find(ocrTekst)?.groupValues?.get(1)?.toDouble()
                ?: 0.00,
            ocr_tekst = ocrTekst
        )
    }
}