package hr.nimai.spending.presentation.potrosnja

import hr.nimai.spending.domain.util.getTodayDate
import hr.nimai.spending.presentation.select_spending.Screens

data class PotrosnjaState(
    val startDate: String = getTodayDate(),
    val endDate: String = getTodayDate(),
    val naziv: String = "",
    val items: List<Pair<String, Double>> = emptyList(),
    val isDisplayed: Boolean = false,
    val id: Int? = null,
    val type: Screens? = null,
    val ukupno: Double = 0.00
)
