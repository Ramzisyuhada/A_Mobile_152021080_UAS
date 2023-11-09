package com.example.uts.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uts.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [Calculator_umur.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Calculator_umur : Fragment() {
    private lateinit var datePicker: DatePicker
    private lateinit var buttonCalculate: Button
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
        val view = inflater.inflate(R.layout.fragment_calculator_umur, container, false)

        datePicker = view.findViewById(R.id.datePicker)
        buttonCalculate = view.findViewById(R.id.buttonCalculate)
        textViewResult = view.findViewById(R.id.textViewResult)

        buttonCalculate.setOnClickListener {
            calculateAge()
        }

        return view
    }
    private fun calculateAge() {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val birthDate = Calendar.getInstance()
        birthDate.set(year, month, day)
        val currentDate = Calendar.getInstance()

        var age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)

        if (currentDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = sdf.format(birthDate.time)

        textViewResult.text = "Ulang taun kamu $formattedDate. Umur  $age ."
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Calculator_umur.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Calculator_umur().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}