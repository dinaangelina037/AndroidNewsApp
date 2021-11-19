package AdapterViewHolder

import Common.ISO8601Parser
import Interface.ItemClickListener
import Model.Article
import android.content.Context
import android.net.ParseException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dina.androidnewsapp.R
import java.util.*

class ListNewsAdapter(val articleList: List<Article>, private val context:Context): RecyclerView.Adapter<ListNewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return articleList.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int)
    {
        Picasso.with(context)
            .load(articleList[position]!!.urlToImage)
            .into(holder.article_image)

        if (articleList[position].title!!.length > 65) {
            holder.article_title.text = articleList[position].title!! substring (0, 65)+"..."
        }

        else {
            holder.article_title.text = articleList[position].title!!
        }

        if (articleList[position].publishedAt !=null){
            var date: Date?=null
            try{
                date =ISO8601Parser.parse(articleList[position].publishedAt!!)
            }
            catch (ex: ParseException) {
                ex.printStackTrace()
            }
            holder.article_time.setReferenceTime(date!!.time)
            }

        holder.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {


                }
            })
        }
    }
}