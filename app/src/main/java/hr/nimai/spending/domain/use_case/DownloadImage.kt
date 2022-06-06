package hr.nimai.spending.domain.use_case

import android.util.Log
import hr.nimai.spending.domain.util.GetImageService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DownloadImage {

    operator fun invoke(url: String, saveImage: (ByteArray) -> Unit) {
        val retro = Retrofit.Builder()
            .baseUrl("https://images.barcodelookup.com")
            .build()

        val service = retro.create(GetImageService::class.java)

        val call = service.getImage(url)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val image = response.body()?.bytes()

                if (image != null) {
                    saveImage(image)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("DownloadImage", "Failed download!: ${t.message}")
            }

        })
    }
}