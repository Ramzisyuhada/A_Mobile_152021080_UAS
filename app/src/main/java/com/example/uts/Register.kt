package com.example.uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.uts.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.net.URLEncoder
import java.util.concurrent.CompletableFuture


class Register : AppCompatActivity() {
     var auth = FirebaseAuth.getInstance()


    private lateinit var binding : ActivityRegisterBinding
    fun api(username: String, password: String):CompletableFuture<String> {
        val TAG = "API_CALL" // Tag untuk identifikasi log
        val gson = Gson()
        val completableFuture = CompletableFuture<String>()
        val jsonBody = gson.toJson(mapOf("name" to username, "pwd" to password))

        val client = OkHttpClient()
//        val mediaType = "application/json".toMediaTypeOrNull()
        val mhs = RequestBody.create("application/json".toMediaTypeOrNull(), jsonBody)
//        mhs = gson.toJson(mhs)
        val url = "http://192.168.1.7/Apiandroid/create.php"
        val request = Request.Builder()
            .url(url)
            .post(mhs)
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
        return completableFuture
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContentView(binding.root)


        binding.loginD.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.buttonRegister.setOnClickListener{
            var name = binding.usernameRegister.text.toString()
            var password1 = binding.passwordRegister.text.toString()
            var password1_second = binding.passwordS.text.toString()
            var value = checkregister(name, password1, password1_second)
            Log.i("value : ", password1_second)

            when(value){
                1 -> Toast.makeText(this,"Username tidak boleh kosong/spasi , password tidak boleh kosong dan second password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this,"Username tidak boleh kosong/spasi , password tidak boleh kosong dan second password kurang dari 5 ",
                    Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(this,"Username tidak boleh kosong/spasi , password tidak boleh kurang dari 5 dan second password tidak boleh kurang dari 5 ",
                    Toast.LENGTH_SHORT).show()
                4 -> Toast.makeText(this,"Username tidak boleh kurang dari 5 , password tidak boleh kosong dan second password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                5 -> Toast.makeText(this,"Username tidak boleh kurang dari 5, password kurang dari 5 dan second password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                6 -> Toast.makeText(this,"Username tidak boleh kurang dari 5, password tidak boleh kosongdan second  password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                7 -> Toast.makeText(this,"Username tidak boleh kurang dari 5, password tidak boleh kurang dari 5 dan second  password tidak boleh kurang dari 5 ",
                    Toast.LENGTH_SHORT).show()
                8 -> Toast.makeText(this,"Username tidak boleh kosong dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                9 -> Toast.makeText(this,"password tidak boleh kosong dan second password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                10 -> Toast.makeText(this,"Username tidka boleh kurang dari 5 dan password tidak boleh kosong ", Toast.LENGTH_SHORT).show()
                11 -> Toast.makeText(this,"Username tidak boleh kurang dari 5 dan second password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                12 -> Toast.makeText(this,"Username tidak boleh kosong dan password tidak boleh kurang dari 5", Toast.LENGTH_SHORT).show()
                13 -> Toast.makeText(this,"password tidak boleh kosong dan second password tidak boleh kurang dari 5",
                    Toast.LENGTH_SHORT).show()
                14 -> Toast.makeText(this,"password tidak boleh kurang dari 5 dan second password kurang dari 5", Toast.LENGTH_SHORT).show()
                15 -> Toast.makeText(this,"Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
                16 -> Toast.makeText(this,"Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                17 -> Toast.makeText(this,"Second password tidak boleh kosong ", Toast.LENGTH_SHORT).show()
                18 -> Toast.makeText(this,"Username tidak boleh kurang dari 5", Toast.LENGTH_SHORT).show()
                19 -> Toast.makeText(this,"Password tidak boleh kurang dari 5 ", Toast.LENGTH_SHORT).show()
                20 -> Toast.makeText(this,"Second password tidak boleh kurang dari 5 ", Toast.LENGTH_SHORT).show()
                21 -> Toast.makeText(this,"Password tidak sama dengan Second password", Toast.LENGTH_SHORT).show()
                0 -> {
//                    api(name, password1
//                    )
                    auth.createUserWithEmailAndPassword(name,password1).addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this, MainActivity::class.java))

                        }else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
//                    val responseFuture: CompletableFuture<String> = api(name,password1)
//                    val response: String = responseFuture.get()
//                    if (response == "0"){
//                        Toast.makeText(this,"Username Telah dipakai", Toast.LENGTH_SHORT).show()
//
//                    }else {
//                        startActivity(Intent(this,MainActivity::class.java))
//
//                    }
                }
            }


        }
    }
    private fun checkregister(username: String, password: String, password1: String): Any {
        var value = 0

        if(username.isBlank() && password.isBlank() && password1.isBlank()){
            return 1
        }else if(username.isBlank() && password.isBlank() && password1.length < 5 ) {
            return 2
        }else if(username.isBlank() && password.length < 5 && password1.length < 5){
            return 3
        }else if(username.length < 5 && password.isBlank()  && password1.isBlank()){
            return 4
        }else if(username.length < 5 && password.length < 5 && password1.isBlank()){
            return 5
        }else if(username.length < 5 && password.isBlank() && password.length < 5){
            return 6
        }else if(username.length < 5 && password.length < 5 && password.length < 5){
            return 7
        }else if (username.isBlank() && password.isBlank()){
            return 8
        }else if(username.isBlank() && password1.isBlank()){
            return 9
        }else if(username.length < 5 && password.isBlank()){
            return 10
        }else if(username.length < 5 && password1.isBlank()){
            return 11
        }else if(username.isBlank() && password.length < 5){
            return 12
        }else if(username.isBlank() && password1.length < 5){
            return 13
        }else if(password.length < 5 && password1.length < 5){
            return 14
        }else if(username.isBlank()){
            return 15
        }else if(password.isBlank()){
            return 16
        }else if(password1.isBlank()){
            return 17
        }else if(username.length < 5){
            return 18
        }else if(password.length < 5){
            return 19
        }else if (password1.length < 5){
            return  20
        }else if(password != password1){
            return 21
        }

        return 0
    }
}