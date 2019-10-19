package com.artnest.networkingsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.artnest.networkingsample.data.NetworkModule
import com.artnest.networkingsample.data.response.CatResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var catsRv: RecyclerView
    private lateinit var catsAdapter: CatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catsRv = findViewById(R.id.cats_rv)
        catsRv.setHasFixedSize(true)

        catsAdapter = CatsAdapter()
        catsRv.adapter = catsAdapter
    }

    override fun onResume() {
        super.onResume()

        val theCatApiService = NetworkModule().theCatApiService

        repeat(10) {
            val catsCall: Call<List<CatResponse>> = theCatApiService.getCats()
            catsCall.enqueue(object : Callback<List<CatResponse>> {
                override fun onFailure(call: Call<List<CatResponse>>, t: Throwable) {
                    // handle failure
                }

                override fun onResponse(
                    call: Call<List<CatResponse>>,
                    response: Response<List<CatResponse>>
                ) {
                    val cats: List<CatResponse> = response.body()!!
                    catsAdapter.submitList(cats)
                }
            })
        }
    }
}
