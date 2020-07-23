package com.srikanth.androidcodingtestwithkotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.srikanth.androidcodingtestwithkotlin.model.AboutCanadaModel
import com.srikanth.androidcodingtestwithkotlin.retrofit.ApiService
import com.srikanth.androidcodingtestwithkotlin.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutCanadaActivity : AppCompatActivity() {

    private var mApiService: ApiService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mApiService = RetrofitClient.client.create(ApiService::class.java)
        getCanadaFactsResponse()
    }

    // Fetching data from AboutCanada API
    private fun getCanadaFactsResponse() {
        val call = mApiService!!.fetchAboutCanadaApi()
        call.enqueue(object : Callback<AboutCanadaModel> {
            override fun onResponse(
                call: Call<AboutCanadaModel>,
                response: Response<AboutCanadaModel>
            ) {
                Log.d(TAG, "Total ROWS: " + response.body()!!.rows.size)
                val aboutCanadaModel = response.body()
                if (aboutCanadaModel != null) {
                    //set Action bar title
                    val actionBar = supportActionBar!!
                    actionBar.title = aboutCanadaModel.title
                }
            }

            override fun onFailure(call: Call<AboutCanadaModel>, t: Throwable) {
                Log.e(TAG, "Got error : " + t.localizedMessage)
            }
        })
    }

    companion object {
        private val TAG = AboutCanadaActivity::class.java.simpleName
    }

}