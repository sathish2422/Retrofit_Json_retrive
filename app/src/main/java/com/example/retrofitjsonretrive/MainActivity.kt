package com.example.retrofitjsonretrive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofit()
    }

    private fun retrofit() {
        val BASE_URL = "https://api.github.com"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
        val respTv=findViewById<TextView>(R.id.resp)

        GlobalScope.launch {
            val userDetails = retrofit.getUserDetails()
            withContext(Dispatchers.Main){
                Log.d("resp","${userDetails}")
                respTv.text = userDetails.toString()
            }
        }

    }
}

interface Api {

    @GET("/users/vicky-droid")
    suspend fun getUserDetails() : Response<JSONObject>
}
