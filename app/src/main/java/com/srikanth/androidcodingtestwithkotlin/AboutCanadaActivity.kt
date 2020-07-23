package com.srikanth.androidcodingtestwithkotlin

import Rows
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.srikanth.androidcodingtestwithkotlin.adapter.AboutCanadaAdapter
import com.srikanth.androidcodingtestwithkotlin.model.AboutCanadaModel
import com.srikanth.androidcodingtestwithkotlin.retrofit.ApiService
import com.srikanth.androidcodingtestwithkotlin.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *AboutCanadaActivity displays information about Canada in recyclerview by fetching data from
 */
class AboutCanadaActivity : AppCompatActivity() {

    lateinit var mApiService: ApiService
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: AboutCanadaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Configure recyclerView
        recyclerview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager

        //Handle api
        mApiService = RetrofitClient.client.create(ApiService::class.java)
        getCanadaFactsResponse()
    }

    // Fetching data from AboutCanada API
    private fun getCanadaFactsResponse() {
        val call = mApiService.fetchAboutCanadaApi()
        call.enqueue(object : Callback<AboutCanadaModel> {
            override fun onResponse(
                call: Call<AboutCanadaModel>,
                response: Response<AboutCanadaModel>
            ) {
                val aboutCanadaModel: MutableList<Rows> = response.body()?.rows as MutableList<Rows>
                adapter = AboutCanadaAdapter(baseContext, aboutCanadaModel)
                adapter.notifyDataSetChanged()
                recyclerview.adapter = adapter
                Log.d(TAG, "Total ROWS: " + response.body()!!.rows.size)

                //set Action bar title
                val actionBar = supportActionBar!!
                actionBar.title = response.body()!!.title
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