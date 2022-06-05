package hr.nimai.spending.domain.util

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GetImageService {

    @GET
    fun getImage(@Url url: String): Call<ResponseBody>
}