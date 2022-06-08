package hr.nimai.spending.presentation.select_spending

import hr.nimai.spending.domain.util.ItemHolder

data class SelectSpendingState(
    val proizvodi: List<ItemHolder> = emptyList(),
    val trgovine: List<ItemHolder> = emptyList(),
    val tipoviProizvoda: List<ItemHolder> = emptyList(),
    val content: Screens = Screens.PROIZVODI,
)

enum class Screens {
    PROIZVODI, TRGOVINE, TIPOVIPROIZVODA
}




