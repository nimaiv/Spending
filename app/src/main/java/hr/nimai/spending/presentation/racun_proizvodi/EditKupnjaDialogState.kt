package hr.nimai.spending.presentation.racun_proizvodi

data class EditKupnjaDialogState(
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
    val idProizvoda: Int? = null

)
