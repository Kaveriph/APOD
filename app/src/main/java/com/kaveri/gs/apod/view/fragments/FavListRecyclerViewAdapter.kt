package com.kaveri.gs.apod.view.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.databinding.ApodListItemBinding
import com.kaveri.gs.apod.model.pojo.APOD

class FavListRecyclerViewAdapter(
    val context: Context,
    val favList: List<APOD>?,
    val actionListener: IListActionListener
) :
    RecyclerView.Adapter<FavListRecyclerViewAdapter.FavListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavListViewHolder {
        return FavListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.apod_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return favList?.size ?: 0
    }

    class FavListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ApodListItemBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onBindViewHolder(holder: FavListViewHolder, position: Int) {
        val apodItem = favList?.get(position)
        println("${apodItem?.date} : ${apodItem?.url} : ${apodItem?.fav} : ${apodItem?.title}")
        apodItem?.let { apod ->
            holder.binding?.titleTxt?.text = apod.title
            holder.binding?.dateTxt?.text = apod.date
            if (apod.mediaType.equals("image")) holder.binding?.imageView?.load(apod.url)
        }
        holder.binding?.item = favList?.get(position)
        holder.binding?.actionListener = actionListener
    }
}