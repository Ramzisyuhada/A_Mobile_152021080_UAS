package com.example.uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import com.example.uas.databinding.ActivityEditBinding
import com.example.uts.databinding.ActivityEditBinding
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class Edit : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    private fun apidelete (id : Int){
        val client = OkHttpClient()
        Log.i("error","$id")

        val url = "http://192.168.1.7/Apiandroid/delete.php?id=$id"
        val request = Request.Builder()
            .url(url)
            .delete()
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG", "Gagal melakukan permintaan", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("TAG", "Gagal melakukan permintaan: ${response.code}")
                    return
                }

                val responseBody = response.body?.string()
                Log.d("TAG", "Respon dari API: $responseBody")
//                completableFuture.complete(responseBody ?: "") // Mengembalikan respons ke CompletableFuture
            }
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        var intent = getIntent()
        binding.Username.setText(this.intent.getStringExtra("nama"))
        binding.Password.setText(this.intent.getStringExtra("pwd"))
//        val id =this.intent.getIntExtra("id") // Ubah "defaultValue" menjadi nilai default yang sesuai
        //        binding.edit.setOnClickListener {
//
//        }
//      v intent.getStringExtra()
        binding.delete.setOnClickListener {
            apidelete(this.intent.getStringExtra("id")?.toInt() ?: 2)
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        binding.edit.setOnClickListener {
            apiEdit(this.intent.getStringExtra("id")?.toInt() ?: 2,binding.Username.text.toString(),binding.Password.text.toString())
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }


    private fun apiEdit(id: Int,Username:String,password:String){
        val client = OkHttpClient()
        Log.i("error","$id")

        var gson = Gson()
        val jsonBody = gson.toJson(mapOf("name" to Username, "pwd" to password , "id" to id))

        val mhs = RequestBody.create("application/json".toMediaTypeOrNull(), jsonBody)
        val url = "http://192.168.1.7/Apiandroid/edit.php"
        val request = Request.Builder()
            .url(url)
            .put(mhs)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG", "Gagal melakukan permintaan", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("TAG", "Gagal melakukan permintaan: ${response.code}")
                    return
                }

                val responseBody = response.body?.string()
                Log.d("TAG", "Respon dari API: $responseBody")
//                completableFuture.complete(responseBody ?: "") // Mengembalikan respons ke CompletableFuture
            }
        })
    }
}