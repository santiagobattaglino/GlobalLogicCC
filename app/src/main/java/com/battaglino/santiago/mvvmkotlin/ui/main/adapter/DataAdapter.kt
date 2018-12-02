package com.battaglino.santiago.mvvmkotlin.ui.main.adapter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.battaglino.santiago.mvvmkotlin.R
import com.battaglino.santiago.mvvmkotlin.db.entity.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listitem_data.view.*
import kotlin.properties.Delegates

/**
 * Created by Santiago Battaglino.
 */
class DataAdapter(
        private val context: AppCompatActivity?,
        private val onViewHolderClick: OnViewHolderClick?
) : RecyclerView.Adapter<DataAdapter.ViewHolder>(), AutoUpdatableAdapter {

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_DATA = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (mData.lastIndex == position) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_DATA
        }
    }

    var mData: List<Data> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(createView(context, parent, viewType), onViewHolderClick)
    }

    override fun getItemCount() = mData.size

    private fun getItem(index: Int): Data {
        return mData[index]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    private fun createView(context: AppCompatActivity?, viewGroup: ViewGroup, viewType: Int): View {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return when (viewType) {
            VIEW_TYPE_LOADING -> inflater.inflate(R.layout.listitem_loading, viewGroup, false)
            VIEW_TYPE_DATA -> inflater.inflate(R.layout.listitem_data, viewGroup, false)
            else -> {
                inflater.inflate(R.layout.listitem_loading, viewGroup, false)
            }
        }
    }

    interface OnViewHolderClick {
        fun dataViewClickFromList(view: View, position: Int, data: Data)
    }

    inner class ViewHolder(itemView: View, listener: DataAdapter.OnViewHolderClick?) :
            RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        init {
            if (listener != null)
                itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onViewHolderClick?.dataViewClickFromList(view, adapterPosition, getItem(adapterPosition))
        }

        fun bind(data: Data) = with(itemView) {

            if (itemViewType == VIEW_TYPE_DATA) {
                imageTitle.text = data.title

                // Only for album type
                if (data.images.isNotEmpty() && data.isAlbum) {
                    Picasso.get()
                            .load(data.images[0].link)
                            .fit()
                            .placeholder(R.drawable.picasso_placeholder)
                            .error(R.drawable.picasso_error)
                            .centerCrop()
                            .into(image)
                } else {
                    Picasso.get()
                            .load(data.link)
                            .fit()
                            .placeholder(R.drawable.picasso_placeholder)
                            .error(R.drawable.picasso_error)
                            .centerCrop()
                            .into(image)
                }
            }
        }
    }
}