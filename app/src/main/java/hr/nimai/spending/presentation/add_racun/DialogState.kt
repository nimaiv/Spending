package hr.nimai.spending.presentation.add_racun

data class DialogState(
    val isDialogOpen: Boolean,
    val id: Int?,
    val nazivProizvoda: String = "",
    val skraceniNazivProizvoda: String = "",
    val barkod: String = "",
    val cijenaProizvoda: String = "",
    val kolicinaProizvoda: String = "",
    val isCijenaError: Boolean = false,
    val isKolicinaError: Boolean = false,
    val isNazivEmptyError: Boolean = false,
    val isSkraceniNazivEmptyError: Boolean = false,
    val showErrorMessage: Boolean = false,
    val isNew: Boolean = false,
    val uriSlike: String = "",
    val slika: ByteArray? = null,
    val idProizvoda: Int = 0,
)
