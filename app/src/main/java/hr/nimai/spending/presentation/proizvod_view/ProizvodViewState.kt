package hr.nimai.spending.presentation.proizvod_view

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import hr.nimai.spending.domain.model.TipProizvoda
import hr.nimai.spending.domain.util.KupnjaDatum

data class ProizvodViewState(
    val idProizvoda: Int = 0,
    val nazivProizvoda: String = "",
    val skraceniNazivProizvoda: String = "",
    val barkod: String? = null,
    val idTipaProizvoda: Int? = null,
    val nazivTipaProizvoda: String = "",
    val uriSlikeProizvoda: String? = null,
    val kupnje: List<KupnjaDatum> = emptyList(),
    val isEditEnabled: Boolean = false,
    val tipoviProizvoda: List<TipProizvoda> = emptyList(),
    val buttonText: String = "Uredi",
    val buttonColor: Color = Color(0xFF1678A3),
    val slika: ByteArray? = null
)
