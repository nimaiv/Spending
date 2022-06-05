package hr.nimai.spending.domain.util

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetProizvodFromBarcodeService {

    @GET("/v3/products?formatted=y")
    fun listProizvodi(@Query("key") key: String, @Query("barcode") barcode: String): Call<ProductsJSON>
}