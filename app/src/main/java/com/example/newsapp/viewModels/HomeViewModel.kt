package com.example.newsapp.viewModels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.activities.BaseActivity
import com.example.newsapp.model.HomeNewsModel
import com.kloxus.app.retrofit.ApiInterface
import com.letmelisten.app.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var mHomeModel: MutableLiveData<HomeNewsModel?>? = null
    fun getHomeData(
        activity: Activity?,
        mStrq: String,
        mStrDate: String,
        mStrPopular: String,
        strPageNo: Int,
        strPageSize: Int,
        mStrApiKey: String
    ): LiveData<HomeNewsModel?>? {
        mHomeModel = MutableLiveData<HomeNewsModel?>()
        val ApiInterface: ApiInterface =
            ApiClient().getApiClient()!!.create(ApiInterface::class.java)
        ApiInterface.saveLogExercise(mStrq,mStrDate,mStrPopular, strPageNo, strPageSize,mStrApiKey)?.enqueue(object : Callback<HomeNewsModel?> {
            override fun onResponse(call: Call<HomeNewsModel?>, response: Response<HomeNewsModel?>) {
                if (response.body() != null) {
                    mHomeModel!!.setValue(response.body())
                } else {
                }
            }
            override fun onFailure(call: Call<HomeNewsModel?>, t: Throwable) {
                BaseActivity().dismissProgressDialog()
            }
        })
        return mHomeModel
    }
//    fun getHomeData(
//        activity: Activity?,
//        mStrq: String,
//        mStrDate: String,
//        mStrPopular: String,
//        strPageNo: Int,
//        strPageSize: Int,
//        mStrApiKey: String
//    ): LiveData<HomeNewsModel?>? {
//        mHomeModel = MutableLiveData<HomeNewsModel?>()
//        val ApiInterface: ApiInterface =
//            ApiClient().getApiClient()!!.create(ApiInterface::class.java)
//        ApiInterface.saveLogExercise(mStrq,mStrDate,mStrPopular, strPageNo, strPageSize,mStrApiKey)?.enqueue(object : Callback<HomeNewsModel?> {
//            override fun onResponse(call: Call<HomeNewsModel?>, response: Response<HomeNewsModel?>) {
//                if (response.body() != null) {
//                    mHomeModel!!.setValue(response.body())
//                } else {
//                }
//            }
//            override fun onFailure(call: Call<HomeNewsModel?>, t: Throwable) {
//                BaseActivity().dismissProgressDialog()
//            }
//        })
//        return mHomeModel
//    }
}