package hr.nimai.spending.presentation.tipovi_proizvoda

import hr.nimai.spending.domain.model.TipProizvoda

data class TipoviProizvodaState(
    val tipoviProizvoda: List<TipProizvoda> = emptyList(),
    val isDialogOpen: Boolean = false,
    val nazivTipaProizvoda: String = "",
    val idTipaProizvoda: Int = 0,
    val isEdit: Boolean = false
)
