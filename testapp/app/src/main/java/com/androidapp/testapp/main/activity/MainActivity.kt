package com.androidapp.testapp.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.testapp.*
import com.androidapp.testapp.common.Download
import com.androidapp.testapp.common.SharedData
import com.androidapp.testapp.favorite.activity.FavoriteActivity
import com.androidapp.testapp.main.adapter.MainRecyclerViewAdapter
import com.androidapp.testapp.main.api.CatsRequest
import com.androidapp.testapp.main.connection.Connection

class MainActivity : AppCompatActivity() {

    private lateinit var sharedData: SharedData
    private lateinit var download: Download
    private lateinit var catsRequest: CatsRequest

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: MainRecyclerViewAdapter

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var buttonAgain: Button

    private val imageArray = ArrayList<String>()
    private var isFirstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActivity()
    }

    private fun initActivity() {
        setSupportActionBar(findViewById(R.id.toolbarMain))
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        sharedData = SharedData(applicationContext)
        sharedData.loadFavorite()

        download = Download(applicationContext)

        progressBar = findViewById(R.id.progressBarLoadingMain)
        textView = findViewById(R.id.textViewLoadingMain)
        buttonAgain = findViewById(R.id.buttonAgain)

        initRecyclerView()

        catsRequest = CatsRequest(::updateMainActivity)

        if (Connection().getNetworkConnection(applicationContext))
            catsRequest.getCatPics()
        else
            problemWithInternetConnection()
    }

    private fun initRecyclerView() {
        val f1: (String) -> Unit = sharedData::saveFavorite
        val f2: (String) -> Unit = download::downloadImage

        recyclerView = findViewById(R.id.recyclerViewMain)
        recyclerViewAdapter = MainRecyclerViewAdapter(imageArray, f1, f2)
        recyclerView.adapter = recyclerViewAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && linearLayoutManager.findFirstVisibleItemPosition() > 0) {
                    catsRequest.getCatPics()
                    Toast.makeText(applicationContext, "More photos uploaded!", Toast.LENGTH_SHORT).show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun updateMainActivity(imgURL: String) {
        synchronized(this) {
            imageArray.add(imgURL)
            recyclerViewAdapter.notifyItemInserted(imageArray.size)
            recyclerViewAdapter.notifyDataSetChanged()

            if (isFirstLoad) {
                progressBar.visibility = ProgressBar.GONE
                textView.visibility = TextView.INVISIBLE
                isFirstLoad = false
            }
        }
    }

    private fun problemWithInternetConnection() {
        Toast.makeText(applicationContext, "Problem with internet connection!", Toast.LENGTH_SHORT).show()

        progressBar.visibility = ProgressBar.GONE
        textView.visibility = TextView.INVISIBLE
        buttonAgain.visibility = View.VISIBLE
    }

    fun onButtonAgainClick(view: View) {
        if (Connection().getNetworkConnection(applicationContext)) {
            progressBar.visibility = ProgressBar.VISIBLE
            textView.visibility = TextView.VISIBLE
            buttonAgain.visibility = View.INVISIBLE
            catsRequest.getCatPics()
        } else
            Toast.makeText(applicationContext, "Problem with internet connection!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ic_favorite)
            startActivity(Intent(applicationContext, FavoriteActivity::class.java))
        return true
    }
}