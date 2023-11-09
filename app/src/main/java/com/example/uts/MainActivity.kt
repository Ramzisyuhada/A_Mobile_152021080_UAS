package com.example.uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.uts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private var passwordVisibility = false

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
            if(binding.username.text.toString().isBlank() && binding.password.text.toString().isBlank()){
                Toast.makeText(this,"Nama tidak boleh kosong dan Password tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else if (binding.username.text.toString().isBlank()){
                Toast.makeText(this,"Nama tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else if(binding.password.text.toString().isBlank()){
                Toast.makeText(this,"Password tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }
            if(binding.username.text.toString().isNotBlank()&&binding.password.text.toString().isNotBlank()&&!binding.username.text.toString().isEmpty()  && !binding.password.text.toString().isEmpty()){
                val intent = Intent(this, Home::class.java)
                intent.putExtra("key", binding.username.text.toString())
                startActivity(intent)
            }

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
