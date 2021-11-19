package AdapterViewHolder

import Interface.ItemClickListener
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListNewsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView),View.OnClickListener{
    private lateinit var itemClickListener: ItemClickListener

    var article title = itemView.article_title
    var article time = itemView.article_time
    var article image = itemView.article_image

    init{
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }



    override fun onClick(v: View) {
        itemClickListener.onClick(v ,adapterPosition)

    }

}