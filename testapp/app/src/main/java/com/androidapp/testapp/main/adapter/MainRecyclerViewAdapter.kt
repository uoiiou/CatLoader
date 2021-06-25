package com.androidapp.testapp.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.testapp.R
import com.bumptech.glide.Glide

class MainRecyclerViewAdapter(
    private val imgList: ArrayList<String>,
    val onClickFavorite: (String) -> Unit,
    val onClickDownload: (String) -> Unit) : RecyclerView.Adapter<MainRecyclerViewAdapter.AdapterViewHolder>() {

    private lateinit var context: Context

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_main, parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item = imgList[position]

        Glide.with(context).load(item).into(holder.imageView)

        val buttonFavorite = holder.buttonFavorite
        val buttonDownload = holder.buttonDownload

        buttonFavorite.setOnClickListener{onClickFavorite(imgList[position])}
        buttonDownload.setOnClickListener{onClickDownload(imgList[position])}
    }

    override fun getItemCount() = imgList.size

    class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewMain)
        val buttonFavorite: ImageView = view.findViewById(R.id.buttonFavoriteMain)
        val buttonDownload: ImageView = view.findViewById(R.id.buttonDownloadMain)
    }
}