package com.manidevs.cryptotrackingev

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.manidevs.cryptotrackingev.Adapter.CoinAdapter
import com.manidevs.cryptotrackingev.model.CryptoCoin
import com.manidevs.cryptotrackingev.utilities.Constants
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CoinAdapter
    private val apiUrl = Constants.apiUrl

    private val client by lazy {
        OkHttpClient()
    }

    private val request by lazy {

         //Takes in the apiUrl and returns the request object.
        Request.Builder()
            .url(apiUrl)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cryptoRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CoinAdapter()
        cryptoRecyclerView.adapter = adapter
        getCoins()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun getCoins() {
        //enqueue ensures that this call does not occur on the Main UI thread, async call, network transactions should be done off the Main UI Thread
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Failed ${e?.toString()}")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println("Body: ${body}")
                val gson = Gson()
                val cryptoCoins: List<CryptoCoin> = gson.fromJson(body, object : TypeToken<List<CryptoCoin>>() {}.type)

                runOnUiThread {
                    adapter.updateData(cryptoCoins)
                }
            }
        })
    }




}
