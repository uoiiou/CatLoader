package com.androidapp.testapp.favorite.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.testapp.common.Download
import com.androidapp.testapp.R
import com.androidapp.testapp.common.SharedData
import com.androidapp.testapp.favorite.adapter.FavoriteRecyclerViewAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var sharedData: SharedData
    private lateinit var download: Download

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: FavoriteRecyclerViewAdapter

    private var favoriteArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        initActivity()
    }

    private fun initActivity() {
        setSupportActionBar(findViewById(R.id.toolbarFavorite))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        sharedData = SharedData(applicationContext)
        sharedData.loadFavorite()
        favoriteArray = sharedData.favoriteArray

        if (favoriteArray.size == 0)
            loadEmptyList()

        download = Download(applicationContext)

        initRecyclerView()
    }

    private fun loadEmptyList() {
        val textViewEmpty = findViewById<TextView>(R.id.textViewEmptyFavorite)
        textViewEmpty.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        val f1: (String) -> Unit = download::downloadImage

        recyclerView = findViewById(R.id.recyclerViewFavorite)
        recyclerViewAdapter = FavoriteRecyclerViewAdapter(favoriteArray, f1)
        recyclerView.adapter = recyclerViewAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.ic_delete) {
            if (favoriteArray.size != 0) {
                sharedData.clearFavorite()
                favoriteArray.clear()
                recyclerViewAdapter.notifyItemInserted(favoriteArray.size)
                recyclerViewAdapter.notifyDataSetChanged()
                loadEmptyList()
            }
        } else if (id == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}