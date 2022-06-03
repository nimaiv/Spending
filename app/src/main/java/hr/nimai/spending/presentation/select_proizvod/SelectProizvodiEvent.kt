package hr.nimai.spending.presentation.select_proizvod

sealed class SelectProizvodiEvent {
    data class OnSearchQueryChanged(val value: String): SelectProizvodiEvent()
}
