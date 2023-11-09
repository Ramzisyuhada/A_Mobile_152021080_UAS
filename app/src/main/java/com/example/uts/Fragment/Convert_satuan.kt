package com.example.uts.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.uts.R

/**
 * A simple [Fragment] subclass.
 * Use the [Convert_satuan.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Convert_satuan : Fragment() {
    private lateinit var editTextValue: EditText
    private lateinit var spinnerFromUnit: Spinner
    private lateinit var spinnerToUnit: Spinner
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView
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
        val view = inflater.inflate(R.layout.fragment_convert_satuan, container, false)

        // Populate spinners with unit options
        editTextValue = view.findViewById(R.id.editTextValue)
        spinnerFromUnit = view.findViewById(R.id.spinnerFromUnit)
        spinnerToUnit = view.findViewById(R.id.spinnerToUnit)
        buttonConvert = view.findViewById(R.id.buttonConvert)
        textViewResult = view.findViewById(R.id.textViewResult)

        // Inisialisasi dan isi spinner dengan satuan yang sesuai
        val units = listOf("Meter", "Feet", "Inch", "Centimeter", "Kilometer", "Mile")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromUnit.adapter = adapter
        spinnerToUnit.adapter = adapter

        buttonConvert.setOnClickListener {
            convertUnit()
        }

        return view
    }

    private fun convertUnit() {
        // Implement logic untuk konversi satuan di sini
        val valueText = editTextValue.text.toString()
        if (valueText.isNotEmpty()) {
            val value = valueText.toDouble()
            val fromUnit = spinnerFromUnit.selectedItem.toString()
            val toUnit = spinnerToUnit.selectedItem.toString()

            // Implementasikan logika konversi di sini
            val result = performConversion(value, fromUnit, toUnit)
            textViewResult.text = "$value $fromUnit = $result $toUnit"
        } else {
            textViewResult.text = "Please enter a value."
        }
    }
    private fun performConversion(value: Double, fromUnit: String, toUnit: String): Double {
        // Implementasikan logika konversi sesuai kebutuhan aplikasi Anda
        // Contoh: konversi meter ke feet
        return when {
            fromUnit == "Meter" && toUnit == "Feet" -> value * 3.28084
            fromUnit == "Feet" && toUnit == "Meter" -> value / 3.28084
            // Tambahkan logika untuk konversi satuan lainnya di sini
            else -> value // Kembalikan nilai asal jika tidak ada konversi yang tersedia
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Convert_satuan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Convert_satuan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}