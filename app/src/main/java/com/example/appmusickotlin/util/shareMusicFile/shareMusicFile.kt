package com.example.appmusickotlin.util.shareMusicFile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.io.File
import java.io.FileOutputStream
import java.net.URL

fun downloadAndShareMusic(context: Context, urlOrPath: String, name: String) {
    // Kiểm tra xem đầu vào là URL hay là đường dẫn tệp cục bộ
    if (isUrl(urlOrPath)) {
        downloadAndShareFromUrl(context, urlOrPath, name)
    } else {
        shareLocalFile(context, urlOrPath)
    }
}

private fun isUrl(input: String): Boolean {
    return try {
        URL(input)
        true
    } catch (e: Exception) {
        false
    }
}

private fun downloadAndShareFromUrl(context: Context, url: String, name: String) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            e.printStackTrace()
            Log.e("Download", "Failed to download file: ${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            response.body?.let { responseBody ->
                Log.d("Download", "File downloaded successfully")
                // Tạo file cache trong thư mục cache của ứng dụng
                val cacheDir = File(context.cacheDir, "music_cache")
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs()
                }

                val cacheFile = File(cacheDir, "${name}.mp3")
                try {
                    val inputStream = responseBody.byteStream()
                    val outputStream = FileOutputStream(cacheFile)

                    // Copy nội dung từ response vào file cache
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } > 0) {
                        outputStream.write(buffer, 0, length)
                    }

                    inputStream.close()
                    outputStream.close()

                    Log.d("Download", "File saved to cache: ${cacheFile.absolutePath}")

                    // Chia sẻ file cache
                    shareRemoteFile(context, cacheFile)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("Share", "Failed to save or share file: ${e.message}")
                } finally {
                    Log.d("Share", "Cache file saved")
                }
            } ?: Log.e("Download", "Response body is null")
        }
    })
}

private fun shareLocalFile(context: Context, filePath: String) {
    val file = File(filePath)
    if (file.exists()) {
        Log.d("Share", "Local file exists: ${file.absolutePath}")
        // Tạo một bản sao trong cache và chia sẻ
        val cacheDir = File(context.cacheDir, "music_cache")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }

        val cacheFile = File(cacheDir, file.name)
        try {
            file.copyTo(cacheFile, overwrite = true)
            Log.d("Share", "Local file copied to cache: ${cacheFile.absolutePath}")
            shareFile(context, cacheFile)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Share", "Failed to copy or share local file: ${e.message}")
        }
    } else {
        Log.e("Share", "Local file does not exist: $filePath")
    }
}

private fun shareFile(context: Context, file: File) {
    try {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "audio/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Music"))
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("Share", "Failed to share file: ${e.message}")
    }
}

private fun shareRemoteFile(context: Context, file: File) {
    val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    Log.d("Share", "File URI: $uri")

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "audio/*"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    val chooser = Intent.createChooser(shareIntent, "Share Music")
    chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(chooser)
    Log.d("Share", "Sharing file")
}