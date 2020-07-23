package com.srikanth.androidcodingtestwithkotlin

import Rows
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
 *AboutCanadaActivity displays information about Canada in recyclerview by fetching data
 */
class AboutCanadaActivity : AppCompatActivity() {

    lateinit var mApiService: ApiService
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: AboutCanadaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the colors of the Pull To Refresh View
        swipeContainer.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        swipeContainer.setColorSchemeColors(Color.WHITE)

        //Swipe to refresh the recyclerview
        swipeContainer.setOnRefreshListener {
            getCanadaFactsResponse()
        }

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
        swipeContainer.isRefreshing = true
        val call = mApiService.fetchAboutCanadaApi()
        call.enqueue(object : Callback<AboutCanadaModel> {
            // Api call success handling here
            override fun onResponse(
                call: Call<AboutCanadaModel>,
                response: Response<AboutCanadaModel>
            ) {
                swipeContainer.isRefreshing = false
                val aboutCanadaModel: MutableList<Rows> = response.body()?.rows as MutableList<Rows>
                adapter = AboutCanadaAdapter(baseContext, aboutCanadaModel)
                adapter.notifyDataSetChanged()
                recyclerview.adapter = adapter
                Log.d(TAG, "Total ROWS: " + response.body()!!.rows.size)

                //set Action bar title
                val actionBar = supportActionBar!!
                actionBar.title = response.body()!!.title
            }

            // Api call failure handling here
            override fun onFailure(call: Call<AboutCanadaModel>, t: Throwable) {
                Log.e(TAG, "Got error : " + t.localizedMessage)
                swipeContainer.isRefreshing = false
            }
        })
    }

    companion object {
        private val TAG = AboutCanadaActivity::class.java.simpleName
    }

}