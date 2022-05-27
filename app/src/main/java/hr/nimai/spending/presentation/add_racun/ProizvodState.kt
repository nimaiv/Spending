package hr.nimai.spending.presentation.add_racun

import hr.nimai.spending.domain.util.ProizvodKupnjaHolder

data class ProizvodiState(
    val proizvodi: List<ProizvodKupnjaHolder> = emptyList(),
)