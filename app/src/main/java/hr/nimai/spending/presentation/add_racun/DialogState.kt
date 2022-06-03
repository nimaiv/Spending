package hr.nimai.spending.presentation.add_racun

data class DialogState(
    val isDialogOpen: Boolean,
    val id: Int?,
    val nazivProizvoda: String = "",
    val skraceniNazivProizvoda: String = "",
    val cijenaProizvoda: String = "",
    val kolicinaProizvoda: String = "",
    val isCijenaError: Boolean = false,
    val isKolicinaError: Boolean = false,
    val showErrorMessage: Boolean = false,
    val isNew: Boolean = false,
)
