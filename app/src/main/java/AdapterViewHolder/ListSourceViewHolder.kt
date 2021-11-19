package AdapterViewHolder

import Interface.ItemClickListener
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListSourceViewHolder (itemView:View) :RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private lateinit var itemClickListener: ItemClickListener

    var source_title = itemView.source_news_name

    fun setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(p0: View?) {
        itemClickListener.onClick(v!!,adapterPosition,)
    }





}