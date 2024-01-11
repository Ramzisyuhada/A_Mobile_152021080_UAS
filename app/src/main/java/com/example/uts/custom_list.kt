package com.example.uts

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import okhttp3.*
import java.io.IOException

class custom_list(
    private val context: Context,
    private val titles: MutableList<String>,
    private val descriptions: MutableList<String>,
    private val ids: MutableList<Int>) : ArrayAdapter<String>(context, R.layout.activity_custom_list, titles) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val holder: ViewHolder
        if (rowView == null) {
            val inflater = LayoutInflater.from(context)
            rowView = inflater.inflate(R.layout.activity_custom_list, parent, false)
            holder = ViewHolder()

            holder.titleText = rowView.findViewById(R.id.title)
//            var delete = rowView.findViewById<Button>(R.id.delete)
//            delete.setOnClickListener {
//                val clickedId = ids[position]
//                api(clickedId)
//
//
//                Log.d("Clicked ID", "Clicked ID: $clickedId")
//
//            }
            holder.subtitleText = rowView.findViewById(R.id.description)
            rowView.tag = holder
        } else {
            holder = rowView.tag as ViewHolder
        }

        holder.titleText.text = titles[position]
        holder.subtitleText.text = descriptions[position]

        return rowView!!
    }

    // ViewHolder untuk menyimpan referensi tampilan
    private class ViewHolder {
        lateinit var titleText: TextView
        lateinit var imageView: ImageView
        lateinit var subtitleText: TextView
    }

    fun api(id : Int) {
        val TAG = "API_CALL" // Tag untuk identifikasi log

        val client = OkHttpClient()

        val url = "http://192.168.1.7/apiandroid/delete.php?id=$id"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Gagal melakukan permintaan", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "Gagal melakukan permintaan: ${response.code}")
                    return
                }

                val responseBody = response.body?.string()
                Log.d(TAG, "Respon dari API: $responseBody")
            }
        })
    }


}