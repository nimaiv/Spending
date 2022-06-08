package hr.nimai.spending.domain.util

import hr.nimai.spending.presentation.select_spending.Screens
import java.io.Serializable


data class TypedItemSerializable(
    val id: Int,
    val type: Screens
) : Serializable
