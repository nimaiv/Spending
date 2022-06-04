package hr.nimai.spending.presentation.trgovine

import hr.nimai.spending.domain.model.Trgovina

data class TrgovineState(
    val trgovine: List<Trgovina> = emptyList(),
    val isDialogOpen: Boolean = false,
    val nazivTrgovine: String = "",
    val adresaTrgovine: String = "",
    val idTrgovine: Int = 0,
)
