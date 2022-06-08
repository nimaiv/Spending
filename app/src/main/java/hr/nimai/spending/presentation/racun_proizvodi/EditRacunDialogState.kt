package hr.nimai.spending.presentation.racun_proizvodi

import hr.nimai.spending.domain.model.Trgovina

data class EditRacunDialogState(
    val brojRacuna: String = "",
    val datumRacuna: String = "",
    val ukupanIznosRacuna: String = "",
    val nazivTrgovine: String = "",
    val idTrgovine: Int? = null,
    val trgovine: List<Trgovina> = emptyList(),
    val isUkupanIznosError: Boolean = false,
    val isDatumRacunaError: Boolean = false,
    val isBrojRacunaError: Boolean = false,
    val showErrorMessage: Boolean = false,
)
