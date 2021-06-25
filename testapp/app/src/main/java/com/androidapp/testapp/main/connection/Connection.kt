package com.androidapp.testapp.main.connection

import android.content.Context
import android.net.ConnectivityManager

class Connection {

    fun getNetworkConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected && activeNetworkInfo.isAvailable
    }
}