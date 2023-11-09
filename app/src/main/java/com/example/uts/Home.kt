package com.example.uts

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.uts.Fragment.*
import com.example.uts.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedData = this.intent.getStringExtra("key")
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val home = Home_f()
        val conver = conversi_uang()
        val profil = Profile()
        val convert_sa = Convert_satuan()
        val cal_umur = Calculator_umur()

        if (receivedData != null) {
            loadFragment(home, receivedData)
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_1 -> {
                    loadFragment(home, receivedData.toString())
                    true
                }
                R.id.item_2 -> {
                    loadFragment(conver, receivedData.toString())
                    true
                }
                R.id.item_3 -> {
                    loadFragment(profil, receivedData.toString())
                    true
                }
                R.id.item_4 -> {
                    loadFragment(convert_sa, receivedData.toString())
                    true
                }
                R.id.item_5 -> {
                    loadFragment(cal_umur, receivedData.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun showCustomAlertDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_alert2)
        dialog.setCancelable(true) // Membuat dialog dapat dibatalkan dengan menyentuh di luar dialog

        Log.i("value", "test")
        val okButton = dialog.findViewById<Button>(R.id.ok)
        val cancelButton = dialog.findViewById<Button>(R.id.no)

        okButton.setOnClickListener {
            // Tambahkan logika yang sesuai di sini
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun loadFragment(fragment: Fragment, nama: String) {

        val bundle = Bundle()
        bundle.putString("data", nama)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().apply {
            Bundle().putString("data",nama)
            replace(R.id.fl_wrapper,fragment)
            commit()
        }
    }
}
