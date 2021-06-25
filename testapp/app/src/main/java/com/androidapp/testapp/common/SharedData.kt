package com.androidapp.testapp.common

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.androidapp.testapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedData(private val applicationContext: Context) {

    var favoriteArray = ArrayList<String>()
    private val sharedPreferences: SharedPreferences =
        applicationContext.getSharedPreferences("FAVORITE", Context.MODE_PRIVATE)
    private val stringKey = "favorite"

    fun loadFavorite() {
        val json = sharedPreferences.getString(stringKey, "")

        if (json != "") {
            val type = object : TypeToken<List<String>>(){}.type
            favoriteArray = Gson().fromJson<List<String>>(json, type) as ArrayList<String>
        }
    }

    fun saveFavorite(imgURL: String) {
        Toast.makeText(applicationContext, applicationContext.getString(R.string.toast_added_to_favorite), Toast.LENGTH_SHORT).show()

        favoriteArray.add(imgURL)

        val editor = sharedPreferences.edit()
        val json = Gson().toJson(favoriteArray)
        editor.putString(stringKey, json)
        editor.apply()
    }

    fun clearFavorite() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}