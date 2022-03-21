package com.dcastalia.localappupdate

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by piashsarker on 1/16/18, rewritten by Cyb3rKo on 12/21/20.
 */

class DownloadApk(var context: Context) : AppCompatActivity() {

    @JvmOverloads
    fun startDownloadingApk(url: String, fileName: String = "App Update") {
        if (URLUtil.isValidUrl(url)) {
            DownloadNewVersion(context, url, fileName).execute()
        }
    }

    @Suppress("DEPRECATION")
    private class DownloadNewVersion(
        val context: Context,
        val downloadUrl: String,
        val fileName: String
    ): AsyncTask<String, Int, Boolean>() {
        private lateinit var bar: ProgressDialog
        override fun onPreExecute() {
            super.onPreExecute()
            bar = ProgressDialog(context).apply {
                setCancelable(false)
                setMessage("Downloading...")
                isIndeterminate = true
                setCanceledOnTouchOutside(false)
                show()
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            var msg = ""
            val progress = values[0]
            if (progress != null) {
                bar.progress = progress
                msg = if (progress > 99) "Finishing... " else "Downloading... $progress%"
            }

            bar.apply {
                isIndeterminate  = false
                max = 100
                setMessage(msg)
            }
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            bar.dismiss()
            if (result != null && result) {
                Toast.makeText(context, "Update Done Dona Done Done", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Update Failed. Please Update From Website", Toast.LENGTH_SHORT).show()
            }
        }

        override fun doInBackground(vararg p0: String?): Boolean {
            var flag = false

            try {
                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
                var outputFile = File("$path$fileName.apk")
                var repetition = 1
                while (outputFile.exists()) {
                    outputFile = File("$path$fileName ($repetition).apk")
                    repetition++
                }

                val directory = File(path)
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val url = URL(downloadUrl)
                val c = url.openConnection() as HttpURLConnection
                c.requestMethod = "GET"
                c.connect()

                val fos = FileOutputStream(outputFile)
                val inputStream = c.inputStream
                val totalSize = c.contentLength.toFloat() //size of apk

                val buffer = ByteArray(1024)
                var len1: Int
                var per: Float
                var downloaded = 0f
                while (inputStream.read(buffer).also { len1 = it } != -1) {
                    fos.write(buffer, 0, len1)
                    downloaded += len1
                    per = (downloaded * 100 / totalSize)
                    publishProgress(per.toInt())
                }
                fos.close()
                inputStream.close()
                openNewVersion(outputFile.path)
                flag = true
            } catch (e: MalformedURLException) {
                Log.e("DownloadApk", "Update Error: " + e.message)
                flag = false
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return flag
        }

        private fun openNewVersion(location: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                getUriFromFile(location),
                "application/vnd.android.package-archive"
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)
        }

        private fun getUriFromFile(filePath: String): Uri {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Uri.fromFile(File(filePath))
            } else {
                FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    File(filePath)
                )
            }
        }
    }
}