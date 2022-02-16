package  com.kloxus.app.retrofit

import com.example.newsapp.model.HomeNewsModel
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    @GET("/v2/everything")
    fun saveLogExercise(
        @Query("q") strq: String?,
        @Query("from") strFrom: String?,
        @Query("sortBy") strSortBy: String?,
        @Query("page") strPageNo: Int?,
        @Query("pageSize") strPageSize: Int?,
        @Query("apiKey") strApiKey: String?
    ): Call<HomeNewsModel?>?


}