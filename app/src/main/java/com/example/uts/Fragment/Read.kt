package com.example.uts.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.uts.Edit
import com.example.uts.R
import com.example.uts.custom_list
import com.google.gson.JsonSyntaxException
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.util.concurrent.CompletableFuture

/**
 * A simple [Fragment] subclass.
 * Use the [Read.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Read : Fragment() {
    private lateinit var editTextAmount: EditText
    private lateinit var spinnerFromCurrency: Spinner
    private lateinit var spinnerToCurrency: Spinner
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView
    fun api(id : Int) : CompletableFuture<String>{
        val TAG = "API_CALL" // Tag untuk identifikasi log
        val completableFuture = CompletableFuture<String>()
        Log.i("id","$id")
        val client = OkHttpClient()
        val url = "http://192.168.1.7/Apiandroid/delete.php?id=$id"
        val request = Request.Builder()
            .url(url)
            .delete()
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
                completableFuture.complete(responseBody ?: "") // Mengembalikan respons ke CompletableFuture
            }
        })

        return  completableFuture
    }
    // Define currency conversion rates (example rates)
    private val conversionRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "GBP" to 0.73,
        "JPY" to 109.16
    )
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.read, container, false)
        val view1 = inflater.inflate(R.layout.activity_custom_list,container,false)
        var id3 = ArrayList<Int>()
        var nama = ArrayList<String>()
        val client = OkHttpClient()
        var pwd = ArrayList<String>()
//        var delete3  = view1.findViewById<Button>(R.id.delete)
//        var edit  = view1.findViewById<Button>(R.id.edit)

        val request = Request.Builder()
            .url("http://192.168.1.7/Apiandroid/read.php") // Ganti URL dengan URL yang benar
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




                    try {
                        var responseData = response.body?.string() // Mendapatkan response dalam bentuk String
//                        Log.i("Json", "Tanggal: ${responseData}")
                        var jsonArray  = JSONArray(responseData)
                        Log.i("Json1", "Tanggal: ${jsonArray.length()}")

                        for (i in 0 until  jsonArray.length()){

                            var obj = jsonArray.getJSONObject(i)
//                            obj = obj.getJSONObject(i)
                            var id1 = obj.getInt("id")
                            var lang = obj.getString("nama")
                            var desc = obj.getString("pwd")
                            Log.i("Json2", "Tanggal: ${obj}")
                            id3.add(id1)
                            nama.add(lang)
                            pwd.add(desc)
                        }


//                        var parsedData:Infogempa = Gson().fromJson(responseData, Infogempa::class.java)
                        activity?.runOnUiThread {
                            var listView  = view.findViewById<ListView>(R.id.listView)
                            val myListAdapter = custom_list(requireContext(), nama, pwd,id3)

                            listView.adapter = myListAdapter

                            listView.setOnItemClickListener(){adapterView, view, position, id ->
                                val itemIdAtPos = id3[position]
                                val itemAtPos = nama[position]
                                val pwds = pwd[position]
//                                edit.setOnClickListener {
//                                    Log.d("Button_click","Tombol edit di click")
                                    val intent = Intent(requireContext(), Edit::class.java)
                                    intent.putExtra("nama",itemAtPos)
                                    intent.putExtra("pwd",pwds)
                                    intent.putExtra("id",itemIdAtPos.toString())

                                startActivity(intent)
//                                }

//                                delete3.setOnClickListener {
//                                    val clickedId = id3[position]
//
//                                    val responseFuture: CompletableFuture<String> = api(clickedId)
//
//
//                                }


                                Toast.makeText(requireContext(), "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                            }
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




        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment conversi_uang.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Read().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun convertCurrency() {
        val amountText = editTextAmount.text.toString()
        if (amountText.isNotEmpty()) {
            val amount = amountText.toDouble()
            val fromCurrency = spinnerFromCurrency.selectedItem.toString()
            val toCurrency = spinnerToCurrency.selectedItem.toString()

            val fromRate = conversionRates[fromCurrency] ?: 1.0
            val toRate = conversionRates[toCurrency] ?: 1.0

            val convertedAmount = amount * (toRate / fromRate)
            textViewResult.text = "$amount $fromCurrency = $convertedAmount $toCurrency"
        } else {
            textViewResult.text = "Please enter an amount."
        }
    }
}