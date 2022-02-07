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
import kotlinx.android.synthetic.main.apod_list_item.view.*

class FavListRecyclerViewAdapter(val context: Context, val favList: List<APOD>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.apod_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val apodItem = favList?.get(position)
        println("${apodItem?.date} : ${apodItem?.url} : ${apodItem?.fav} : ${apodItem?.title}")
        apodItem?.let { apod ->
            holder.itemView.titleTxt.text = apod.title
            holder.itemView.dateTxt.text = apod.date
            if (apod.mediaType.equals("image")) holder.itemView.imageView.load(apod.url)
        }
    }


    override fun getItemCount(): Int {
        return favList?.size ?: 0
    }

    class FavListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ApodListItemBinding? = DataBindingUtil.bind(itemView)
    }
}