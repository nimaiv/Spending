package hr.nimai.spending.presentation.proizvodi

sealed class ProizvodiEvent {
    data class OnSearchQueryChanged(val value: String): ProizvodiEvent()
}