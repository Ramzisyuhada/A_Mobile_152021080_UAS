package com.example.uts.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uts.R
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.util.concurrent.CompletableFuture

/**
 * A simple [Fragment] subclass.
 * Use the [Create.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Create : Fragment() {
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
    fun api(username: String, password: String): CompletableFuture<String> {
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.create, container, false)
        var button = rootView.findViewById<Button>(R.id.buttonRegister)
        button.setOnClickListener {
            var name = rootView.findViewById<EditText>(R.id.username_register)
            var password1 = rootView.findViewById<EditText>(R.id.password_register)
            var password1_second = rootView.findViewById<EditText>(R.id.password_s)
            var value = checkregister(name.text.toString(), password1.text.toString(), password1_second.text.toString())
            when(value){
                1 -> Toast.makeText(requireContext(),"Username tidak boleh kosong/spasi , password tidak boleh kosong dan second password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(requireContext(),"Username tidak boleh kosong/spasi , password tidak boleh kosong dan second password kurang dari 5 ",
                    Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(requireContext(),"Username tidak boleh kosong/spasi , password tidak boleh kurang dari 5 dan second password tidak boleh kurang dari 5 ",
                    Toast.LENGTH_SHORT).show()
                4 -> Toast.makeText(requireContext(),"Username tidak boleh kurang dari 5 , password tidak boleh kosong dan second password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                5 -> Toast.makeText(requireContext(),"Username tidak boleh kurang dari 5, password kurang dari 5 dan second password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                6 -> Toast.makeText(requireContext(),"Username tidak boleh kurang dari 5, password tidak boleh kosongdan second  password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                7 -> Toast.makeText(requireContext(),"Username tidak boleh kurang dari 5, password tidak boleh kurang dari 5 dan second  password tidak boleh kurang dari 5 ",
                    Toast.LENGTH_SHORT).show()
                8 -> Toast.makeText(requireContext(),"Username tidak boleh kosong dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                9 -> Toast.makeText(requireContext(),"password tidak boleh kosong dan second password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                10 -> Toast.makeText(requireContext(),"Username tidka boleh kurang dari 5 dan password tidak boleh kosong ", Toast.LENGTH_SHORT).show()
                11 -> Toast.makeText(requireContext(),"Username tidak boleh kurang dari 5 dan second password tidak boleh kosong ",
                    Toast.LENGTH_SHORT).show()
                12 -> Toast.makeText(requireContext(),"Username tidak boleh kosong dan password tidak boleh kurang dari 5", Toast.LENGTH_SHORT).show()
                13 -> Toast.makeText(requireContext(),"password tidak boleh kosong dan second password tidak boleh kurang dari 5",
                    Toast.LENGTH_SHORT).show()
                14 -> Toast.makeText(requireContext(),"password tidak boleh kurang dari 5 dan second password kurang dari 5", Toast.LENGTH_SHORT).show()
                15 -> Toast.makeText(requireContext(),"Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
                16 -> Toast.makeText(requireContext(),"Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                17 -> Toast.makeText(requireContext(),"Second password tidak boleh kosong ", Toast.LENGTH_SHORT).show()
                18 -> Toast.makeText(requireContext(),"Username tidak boleh kurang dari 5", Toast.LENGTH_SHORT).show()
                19 -> Toast.makeText(requireContext(),"Password tidak boleh kurang dari 5 ", Toast.LENGTH_SHORT).show()
                20 -> Toast.makeText(requireContext(),"Second password tidak boleh kurang dari 5 ", Toast.LENGTH_SHORT).show()
                21 -> Toast.makeText(requireContext(),"Password tidak sama dengan Second password", Toast.LENGTH_SHORT).show()
                0 -> {
//                    api(name, password1

                    val responseFuture: CompletableFuture<String> = api(name.text.toString(),password1.text.toString())
                    val response: String = responseFuture.get()
                    if (response == "0"){
                        Toast.makeText(requireContext(),"Username Telah dipakai", Toast.LENGTH_SHORT).show()

                    }else {
                        Toast.makeText(requireContext(),"Berhasil Di tambahkan", Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }

        return rootView
    }
     fun checkregister(username: String, password: String, password1: String): Any {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Create().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}