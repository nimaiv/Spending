package hr.nimai.spending.domain.util

data class ProizvodJSON(
    val title: String,
    val images: List<String>,
)

data class ProductsJSON(
    val products: List<ProizvodJSON>
)