package hr.nimai.spending.domain.use_case

import android.util.Log
import hr.nimai.spending.domain.util.GetProizvodFromBarcodeService
import hr.nimai.spending.domain.util.ProductsJSON
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetProizvodInfoFromBarcode {

    operator fun invoke(barcode: String, onResponseSuccess: (ProductsJSON?) -> Unit) {

        val retro = Retrofit.Builder()
            .baseUrl(URL_BARCODE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retro.create(GetProizvodFromBarcodeService::class.java)

        val proizvodRequest = service.listProizvodi(API_KEY, barcode)

        proizvodRequest.enqueue(object : Callback<ProductsJSON> {
            override fun onResponse(
                call: Call<ProductsJSON>,
                response: Response<ProductsJSON>
            ) {
                val proizvodi = response.body()
                onResponseSuccess(proizvodi)
            }

            override fun onFailure(call: Call<ProductsJSON>, t: Throwable) {
                Log.e("GetProizvodInfoFromBarcode", "Failed request!: ${t.message}")
            }
        })
    }

    companion object {
        const val URL_BARCODE_API = "https://api.barcodelookup.com/"
        const val API_KEY = "noj2focxna5hwilfzm87hm2rtcbw2m"
    }
}