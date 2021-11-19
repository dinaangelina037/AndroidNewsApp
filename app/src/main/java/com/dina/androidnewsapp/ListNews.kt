package com.dina.androidnewsapp

import AdapterViewHolder.ListNewsAdapter
import Interface.NewsService
import Model.News
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source= ""
    var webHotUrl: String?=""

    lateinit var dialog: AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter
    lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService

        dialog = SpotsDialog(this)

        swipe_to_refresh.setOnRefreshListener{ loadNews(source,true) }

        diagonalLayout.setOnClickListener{

        }
        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent !!= null)

          source = intent.getStringExtra("source")
          if (!source.isEmpty())
              loadNews(source,false)

    }
    private fun loadNews(source: String?, isRefreshed: Boolean) {
        if (isRefreshed)
        {
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object: Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                    dialog.dismiss()

                        Picasso.with(baseContext)
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_image)

                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter

                    }

                })
        }

        else{
            swipe_to_refresh.isRefreshing = true
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI())
                .enqueue()
        }

    }

}
