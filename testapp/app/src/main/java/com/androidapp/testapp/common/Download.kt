package com.androidapp.testapp.common

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.androidapp.testapp.R
import org.apache.commons.io.FilenameUtils
import java.io.File

class Download(private val applicationContext: Context) {

    fun downloadImage(imgURL: String) {
        Toast.makeText(applicationContext, applicationContext.getString(R.string.toast_downloading), Toast.LENGTH_SHORT).show()

        val fileName = FilenameUtils.getBaseName(imgURL)
        val fileWithExtension = FilenameUtils.getName(imgURL)
        val fileExtension = FilenameUtils.getExtension(imgURL)

        val downloadManager = applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(imgURL)
        val request = DownloadManager.Request(uri)

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(fileName)
            .setMimeType("image/$fileExtension")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator + fileWithExtension)

        downloadManager.enqueue(request)
    }
}