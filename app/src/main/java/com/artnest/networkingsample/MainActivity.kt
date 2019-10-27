package com.artnest.networkingsample

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.artnest.networkingsample.data.NetworkModule
import com.artnest.networkingsample.data.response.CatResponse
import com.artnest.networkingsample.extensions.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val theCatApiService = NetworkModule.theCatApiService

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var catsRv: RecyclerView
    private lateinit var catsAdapter: CatsAdapter

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh = findViewById(R.id.swipe_refresh)

        catsRv = findViewById(R.id.cats_rv)
        catsRv.setHasFixedSize(true)

        catsAdapter = CatsAdapter()
        catsRv.adapter = catsAdapter

        progressBar = findViewById(R.id.loading)
    }

    override fun onResume() {
        super.onResume()

        repeat(10) {
            progressBar.isVisible = true
            val catsCall: Call<List<CatResponse>> = theCatApiService.getCats()
            catsCall.enqueue(object : Callback<List<CatResponse>> {
                override fun onFailure(call: Call<List<CatResponse>>, t: Throwable) {
                    progressBar.isVisible = false
                    toast(R.string.loading_error)
                }

                override fun onResponse(
                    call: Call<List<CatResponse>>,
                    response: Response<List<CatResponse>>
                ) {
                    val cats: List<CatResponse> = response.body() ?: emptyList()
                    progressBar.isVisible = false
                    catsAdapter.addCats(cats)
                }
            })
        }

        swipeRefresh.setOnRefreshListener {
            catsAdapter.clearCats()
            repeat(10) {
                swipeRefresh.isRefreshing = true
                val catsCall: Call<List<CatResponse>> = theCatApiService.getCats()
                catsCall.enqueue(object : Callback<List<CatResponse>> {
                    override fun onFailure(call: Call<List<CatResponse>>, t: Throwable) {
                        swipeRefresh.isRefreshing = false
                        toast(R.string.loading_error)
                    }

                    override fun onResponse(
                        call: Call<List<CatResponse>>,
                        response: Response<List<CatResponse>>
                    ) {
                        val cats: List<CatResponse> = response.body() ?: emptyList()
                        swipeRefresh.isRefreshing = false
                        catsAdapter.addCats(cats)
                    }
                })
            }
        }
    }
}
