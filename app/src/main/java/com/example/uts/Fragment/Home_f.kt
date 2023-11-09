package com.example.uts.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.uts.Alert
import com.example.uts.Home
import com.example.uts.MainActivity
import com.example.uts.R
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

/**
 * A simple [Fragment] subclass.
 * Use the [Home_f.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var nilai_rata2 = ArrayList<Int>()
var nilai  = ArrayList<Int>()
var nama = ArrayList<String>()
var j = 0
var a =1 ;
class Home_f : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private fun showCustomAlertDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.activity_alert2)
        dialog.setCancelable(true) // Membuat dialog dapat dibatalkan dengan menyentuh di luar dialog

        Log.i("value", "test")
        val okButton = dialog.findViewById<Button>(R.id.ok)
        val cancelButton = dialog.findViewById<Button>(R.id.no)

        okButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



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
        val rootView = inflater.inflate(R.layout.fragment_home_f, container, false)
        val rootView1 = inflater.inflate(R.layout.activity_alert2, container, false)

        val aaChartView = rootView.findViewById<AAChartView>(R.id.aa_chart_view)
        val button = rootView.findViewById<Button>(R.id.masukan)
        val input = rootView.findViewById<EditText>(R.id.inputnilai)
        var text = rootView.findViewById<TextView>(R.id.matpel)
        var halo = rootView.findViewById<TextView>(R.id.hallo)
        val cal = rootView.findViewById<Button>(R.id.calculator)
        val about = rootView.findViewById<Button>(R.id.about)
        val logout = rootView.findViewById<Button>(R.id.Logout)
        about.setOnClickListener {
            val newFragment = about_itenas()

            val fragmentManager: FragmentManager? = activity?.supportFragmentManager

            val transaction = fragmentManager?.beginTransaction()

            transaction?.replace(R.id.fl_wrapper, newFragment) // Replace "fragment_container" with the ID of your layout container

            // Add the transaction to the back stack (optional)
            transaction?.addToBackStack(null)

            // Commit the transaction
            transaction?.commit()
        }
        logout.setOnClickListener {
            showCustomAlertDialog(requireContext())

        }
        cal.setOnClickListener {
            val newFragment = Calculator()

            val fragmentManager: FragmentManager? = activity?.supportFragmentManager

            val transaction = fragmentManager?.beginTransaction()

            transaction?.replace(R.id.fl_wrapper, newFragment) // Replace "fragment_container" with the ID of your layout container

            // Add the transaction to the back stack (optional)
            transaction?.addToBackStack(null)

            // Commit the transaction
            transaction?.commit()
        }
        halo.setText("Hallo "+arguments?.getString("data"))






        button.setOnClickListener {
            if (j == 0) {
                var value = input.text.toString()
                nama.add(value)
                Log.i("Value", nama.toString())
                j++
            } else {
                var value = input.text.toString().toInt()

                nilai.add(value)
                Log.i("Nilai", nilai.toString())
            }
            if (nilai.size == 0) {
                text.setText("Masukan Matakuliah Indonesia")
            } else if (nilai.size == 1) {
                text.setText("Masukan Matakuliah Agama")
            } else if (nilai.size == 2) {
                text.setText("Masukan Matakuliah Matematika")
            } else if (nilai.size == 3) {
                text.setText("Masukan Matakuliah Fisika")
            } else if (nilai.size == 4) {
                text.setText("Masukan Matakuliah Kimia")
            } else if (nilai.size == 5) {
                text.setText("Masukan Matakuliah Ekonomi")
                var nil = 0
                for (i in nilai){
                    nil += i
                }
                nil /= nilai.size
                nilai_rata2.add(nil)
                nilai.clear()
                if (nilai.isEmpty()){
                    text.setText("Masukan Nama Siswa ke -  " + a+" = ")

                    a++
                    j=0
                }
            }



            var myList = listOf(70, 80)
            var myArray = nilai_rata2.toTypedArray()
            var nmearray = nama.toTypedArray()
            val aaChartModel: AAChartModel = AAChartModel()
                .chartType(AAChartType.Bar)
                .title("Rata rata nilai")
                .categories(arrayOf(*nmearray))
                .subtitle("subtitle")
                .backgroundColor("#4b2b7f")
                .dataLabelsEnabled(true)
                .series(
                    arrayOf(
                        AASeriesElement()
                            .name("Nilai Rata Rata")
                            .data(arrayOf(*myArray))
                    )
                )
            aaChartView.aa_drawChartWithChartModel(aaChartModel)


        }
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home_f.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home_f().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}