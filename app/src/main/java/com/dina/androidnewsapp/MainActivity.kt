package com.dina.androidnewsapp

import AdapterViewHolder.ListSourceAdapter
import Common.Common
import Interface.NewsService
import Model.WebSite
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var newsService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        init cache
        Paper.init(this)

//       Init service
        mService = Common.NewsService

//        init view
        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }

       recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)

        loadWebSiteSource(false)


    }

    private fun loadWebSiteSource(isRefresh: Boolean) {

        if (!isRefresh) {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null") {
//                Read cache
                val webSite = Gson().fromJson<WebSite>(cache, WebSite::class.java)
                adapter = ListSourceAdapter(baseContext, webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            } else {
//                load website and write cache
                dialog.show()

                mService.sources.enqueue(object : retrofit2.Callback<WebSite> {
                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT)
                            .show()

                    }

                    //                  Ctrl+O
                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(baseContext, response.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter
//                  save to cache

                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                        dialog.dismiss()
                    }

                })

            }
            swipe_to_refresh.isRefreshing = true
            mService.sources.enqueue(object : retrofit2.Callback<WebSite> {
                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT)
                        .show()

                }

                //                  Ctrl+O
                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(baseContext, response.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter
//                  save to cache

                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                   dialog.dismiss()8
                }

            })
        }
    }
}