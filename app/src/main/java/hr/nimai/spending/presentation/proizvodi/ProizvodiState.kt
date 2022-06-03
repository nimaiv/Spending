package hr.nimai.spending.presentation.proizvodi

import hr.nimai.spending.domain.model.Proizvod

data class ProizvodiState(
    val proizvodi: List<Proizvod> = emptyList(),
    val proizvodiShown: List<Proizvod> = emptyList(),
    val query: String = "",
)
