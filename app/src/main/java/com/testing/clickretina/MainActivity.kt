package com.testing.clickretina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

import android.widget.Toast
import com.google.gson.Gson

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        titleText = findViewById(R.id.title)
        descriptionText = findViewById(R.id.description)

        // Make the API call
        RetrofitClient.apiService.getData().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()!!.choices[0].message.content
                    val jsonResponse = Gson().fromJson(content, ApiResponse.TitlesAndDescription::class.java)
                    titleText.text = jsonResponse.titles.joinToString("\n")
                    descriptionText.text = jsonResponse.description
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
