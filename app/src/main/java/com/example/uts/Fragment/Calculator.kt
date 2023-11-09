package com.example.uts.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uts.R
import net.objecthunter.exp4j.ExpressionBuilder

/**
 * A simple [Fragment] subclass.
 * Use the [Calculator.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Calculator : Fragment() {
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
        val rootView = inflater.inflate(R.layout.fragment_calculator, container, false)
        val inputText = rootView.findViewById<EditText>(R.id.input_text)
        val resultText = rootView.findViewById<TextView>(R.id.result_text)

        val buttonAdd = rootView.findViewById<TextView>(R.id.buttonAdd)
        val buttonSubtract = rootView.findViewById<TextView>(R.id.buttonSubtract)
        val buttonMultiply = rootView.findViewById<TextView>(R.id.buttonMultiply)
        val buttonDivide = rootView.findViewById<TextView>(R.id.buttonDivide)
        val buttonEquals = rootView.findViewById<TextView>(R.id.buttonEquals)

        buttonAdd.setOnClickListener { appendInput("+",inputText) }
        buttonSubtract.setOnClickListener { appendInput("-",inputText) }
        buttonMultiply.setOnClickListener { appendInput("*",inputText) }
        buttonDivide.setOnClickListener { appendInput("/",inputText) }

        // Tambahkan tindakan klik untuk tombol "="
        buttonEquals.setOnClickListener { calculateResult(inputText, resultText) }
        return rootView
    }
    private fun appendInput(value: String,inputText: EditText) {


        val currentText = inputText.text.toString()
        inputText.setText(currentText + value)
    }

    private fun calculateResult(inputText: EditText, resultText: TextView) {
        try {
            val expression = ExpressionBuilder(inputText.text.toString()).build()
            val result = expression.evaluate()
            resultText.text = "Hasil: $result"
        } catch (e: Exception) {
            resultText.text = "Error"
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Calculator.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Calculator().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}