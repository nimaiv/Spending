package hr.nimai.spending.presentation.select_proizvod

import hr.nimai.spending.domain.model.Proizvod

data class SelectProizvodiState(
    val proizvodi: List<Proizvod> = emptyList(),
    val proizvodiShown: List<Proizvod> = emptyList(),
    val query: String = "",
    val idFilterProizvoda: List<Int> = emptyList(),
)
