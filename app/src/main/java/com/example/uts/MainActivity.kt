package com.example.uts

//import Register
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.uts.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.util.concurrent.CompletableFuture


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private var passwordVisibility = false
    var auth = FirebaseAuth.getInstance()

//    fun api(username: String, password: String ): CompletableFuture<String>{
//        val TAG = "API_CALL" // Tag untuk identifikasi log
//        val client = OkHttpClient()
//        val completableFuture = CompletableFuture<String>()
//
//        val url = "http://192.168.1.7/Apiandroid/Login.php"
//        val gson = Gson()
//        val jsonBody = gson.toJson(mapOf("name" to username, "pwd" to password))
//
//        val mhs = RequestBody.create("application/json".toMediaTypeOrNull(), jsonBody)
//
//        val request = Request.Builder()
//            .post(mhs)
//            .url(url)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Gagal melakukan permintaan", e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (!response.isSuccessful) {
//                    Log.e(TAG, "Gagal melakukan permintaan: ${response.code}")
//                    return
//                }
//
//                val responseBody = response.body?.string()
//                Log.d(TAG, "Respon dari API: $responseBody")
//
//                completableFuture.complete(responseBody ?: "") // Mengembalikan respons ke CompletableFuture
//            }
//        })
//
//        return completableFuture
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        binding.daftar.setOnClickListener {
            startActivity(Intent(this, Register::class.java))

        }

        binding.Login.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
//           var responses = api(username,password)
//            val responseFuture: CompletableFuture<String> = api(username,password)
//            val response: String = responseFuture.get()

// Lakukan sesuatu dengan respons yang diterima
//            Log.i("Respone", "$response")
//            println("Response from API: $response")
            if(binding.username.text.toString().isBlank() && binding.password.text.toString().isBlank()){
                Toast.makeText(this,"Nama tidak boleh kosong dan Password tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else if (binding.username.text.toString().isBlank()){
                Toast.makeText(this,"Nama tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else if(binding.password.text.toString().isBlank()){
                Toast.makeText(this,"Password tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else {
                auth.signInWithEmailAndPassword(binding.username.text.toString(), binding.password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, Home::class.java)
                            intent.putExtra("key", binding.username.text.toString())
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Username Dan password salah", Toast.LENGTH_SHORT).show()

                        }
                    }
            }
//            if(response == "1"){
//                val intent = Intent(this, Home::class.java)
//                intent.putExtra("key", binding.username.text.toString())
//                startActivity(intent)
//            }else{
//                Toast.makeText(this,"Username Dan password salah",Toast.LENGTH_SHORT).show()
//
//            }

        }
    }

    private fun setupSpinner() {
        val passwordOptions = resources.getStringArray(R.array.planets_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, passwordOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.passwordFitur.adapter = adapter
        binding.passwordFitur.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (id == 1L) {
            togglePasswordVisibility(true)
        } else {
            togglePasswordVisibility(false)
        }
    }

    private fun togglePasswordVisibility(showPassword: Boolean) {
        passwordVisibility = showPassword
        binding.password.transformationMethod =
            if (showPassword) HideReturnsTransformationMethod.getInstance()
            else PasswordTransformationMethod.getInstance()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Implement this if needed
    }
}
