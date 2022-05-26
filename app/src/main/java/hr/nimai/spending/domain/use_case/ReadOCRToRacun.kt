package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Racun

class ReadOCRToRacun {

    operator fun invoke(ocrTekst: String): Racun {


        // TODO: Update brojRacunaRegex to match other patterns
        val brojRacunaRegex = Regex("""([\d]{5}/[\d]{5}/[\d]{3})""")

        val datumRegex = Regex("""([\d]{2}\.[\d]{2}\.[\d]{4})""")
        val ukupanIznosRegex = Regex("""ukupno\n.*?\n*([\d]+\.[\d]{2})""", RegexOption.IGNORE_CASE)

        return Racun(
            id_racuna = 0,
            broj_racuna = brojRacunaRegex.find(ocrTekst)?.value ?: "",
            datum_racuna = datumRegex.find(ocrTekst)?.value ?: "",
            ukupan_iznos_racuna = ukupanIznosRegex.find(ocrTekst)?.groupValues?.get(1)?.toDouble()
                ?: 0.00,
            ocr_tekst = ocrTekst
        )
    }
}