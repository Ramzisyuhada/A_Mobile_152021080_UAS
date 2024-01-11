package com.example.uts.Fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uts.MainActivity
import com.example.uts.R
import com.google.gson.JsonSyntaxException
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import okhttp3.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [Home_f.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var nilai_rata2 = ArrayList<Int>()
var nilai  = ArrayList<Int>()
var nama = ArrayList<String>()
var j = 0
var a =1 ;
class Home_f : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private fun showCustomAlertDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.activity_alert2)
        dialog.setCancelable(true) // Membuat dialog dapat dibatalkan dengan menyentuh di luar dialog

        Log.i("value", "test")
        val okButton = dialog.findViewById<Button>(R.id.ok)
        val cancelButton = dialog.findViewById<Button>(R.id.no)

        okButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.api, container, false)
        val rootView1 = inflater.inflate(R.layout.activity_alert2, container, false)
        val client = OkHttpClient()

        var textview = rootView.findViewById<TextView>(R.id.info_text)
        var textview1 = rootView.findViewById<TextView>(R.id.info_text1)
        var textview2 = rootView.findViewById<TextView>(R.id.info_text2)
        var textview3 = rootView.findViewById<TextView>(R.id.info_text3)
        var textview4 = rootView.findViewById<TextView>(R.id.info_text4)


        var gambar  = rootView.findViewById<ImageView>(R.id.gambar)


        val request = Request.Builder()
            .url("https://data.bmkg.go.id/DataMKG/TEWS/autogempa.json") // Ganti URL dengan URL yang benar
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Penanganan jika permintaan gagal
                Log.e("TAG", "Gagal melakukan permintaan", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        // Penanganan jika respons tidak berhasil
                        Log.e("Json", "Gagal melakukan permintaan: ${response.code}")
                        return@use
                    }


//                    Log.i("Json", "Tanggal: ${responseData}")

                    try {
                        var responseData = response.body?.string() // Mendapatkan response dalam bentuk String
//                        Log.i("Json", "Tanggal: ${responseData}")
                        val jsonObject = JSONObject(responseData)

                        val Infogempa = jsonObject.getJSONObject("Infogempa")
                        val gempa = Infogempa.getJSONObject("gempa")
                        val shakemap = gempa.getString("Shakemap")

                        val baseUrl = "https://data.bmkg.go.id/DataMKG/TEWS/"
                        val imageUrl = baseUrl + shakemap

//                        var parsedData:Infogempa = Gson().fromJson(responseData, Infogempa::class.java)
                        Log.i("Json","${gempa.getString("Tanggal")}")
                        activity?.runOnUiThread {
                            Picasso.get().load(imageUrl).into(gambar)
                            textview.text ="Tanggal : " +gempa.getString("Tanggal") +" "+ gempa.getString("Jam")
                            textview1.text = "Coordinates : "+gempa.getString("Coordinates")
                            textview2.text = "Magnitude : "+gempa.getString("Magnitude")
                            textview3.text = "Kedalaman : "+ gempa.getString("Kedalaman")
                            textview4.text = "Potensi : "+ gempa.getString("Potensi")

                        }
                    } catch (e: JsonSyntaxException) {
                        // Penanganan jika parsing gagal karena kesalahan sintaks JSON
                        Log.e("Json", "Gagal parsing JSON: ${e.message}")
                    } catch (e: Exception) {
                        // Penanganan kesalahan umum
                        Log.e("Json", "Terjadi kesalahan: ${e.message}")
                    }
                }
            }
        })
//        client.newCall(request).execute().use { response ->
//
//
//
//            if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//            val responseData = response.body?.string() // Mendapatkan response dalam bentuk String
//
//            // Mengurai data JSON menggunakan Gson
//            val gson = Gson()
//            val parsedData = gson.fromJson(responseData, Infogempa::class.java)
//            Log.i("Json","${parsedData.gempa.Tanggal}")
//
//        }














        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home_f.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home_f().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


