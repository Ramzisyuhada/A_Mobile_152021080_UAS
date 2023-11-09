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
 * Use the [conversi_uang.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class conversi_uang : Fragment() {
    private lateinit var editTextAmount: EditText
    private lateinit var spinnerFromCurrency: Spinner
    private lateinit var spinnerToCurrency: Spinner
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView

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
        val view = inflater.inflate(R.layout.fragment_conversi_uang, container, false)

        editTextAmount = view.findViewById(R.id.editTextAmount)
        spinnerFromCurrency = view.findViewById(R.id.spinnerFromCurrency)
        spinnerToCurrency = view.findViewById(R.id.spinnerToCurrency)
        buttonConvert = view.findViewById(R.id.buttonConvert)
        textViewResult = view.findViewById(R.id.textViewResult)

        // Set up spinners with currency options (you can load these dynamically)
        val currencies = listOf("USD", "EUR", "GBP", "JPY")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromCurrency.adapter = adapter
        spinnerToCurrency.adapter = adapter

        // Button click listener
        buttonConvert.setOnClickListener {
            convertCurrency()
        }
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
            conversi_uang().apply {
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