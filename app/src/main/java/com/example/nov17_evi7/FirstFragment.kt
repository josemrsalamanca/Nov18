package com.example.nov17_evi7

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nov17_evi7.databinding.ActivityMainBinding
import com.example.nov17_evi7.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {
    //PASO 1, se hace estatico el archivo
    companion object{
        //nombre del archivo en "DEVICE FILE EXPLORER"
        private const val SHARED_PREF_NAME = "settings"
        //Siguientes 4 lineas son el par valor que estara dentro del archivo. Par/valor -> ( key,value)
        private const val NUMERO_ENTERO_KEY = "numero-entero"
        private const val NUMERO_DECIMAL_KEY = "numero-decimal"
        private const val TEXTO_KEY = "texto"
        private const val SWITCH_KEY = "switch"
    }

    //PASO 2, Llevan private por ser parte del encapsulamiento (maquina de sacar peluches) (SharedPreferences)
    private lateinit var binding: FragmentFirstBinding
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentFirstBinding.inflate(inflater,container,false)
        return binding.root
    }

    // PASO 3, hacer un OVC. Añadir metodos e implementar mas abajo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        // PASO 5, initShared, aparece en rojo, hay que implementar este metodo tbn
        initSharedPrefences()
        loadDefaultValues()
    }

    //PASO 6
    private fun initSharedPrefences() {
        // Aca era así esta línea
        //SharedPreferences = activity!!.getSharedPreferences, pero hay que modificarla, como se llama
        //al activity, es null, por eso el !!, pero el ide recomienda una mejor opcion al poner el mouse sobre el activity!!
        // despues se modifica y queda como esta en el codigo -> requireActivity().getSharedPreferences
        // pero aun así sigue siendo nulo xd, hay que hacer una validacion con if, pero despues lo cambia por un let
        // al final lo vuelve a cambiar y lo dejar como "safeActivity
        activity?.let{safeActivity->
            sharedPreferences = safeActivity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    //PASO 4:  explica
    private fun setupClickListener() {
        // Es lo mismo que decir binding.btnGuardar.setOnClickListener
        binding.run {
            btnGuardar.setOnClickListener {
                saveValues()
            }
            btnBorrar.setOnClickListener {
                deleteValues()
            }
        }
    }


    //PASO 8: con este metodo almacenamos la informacion en el archivo
    private fun saveValues() {
        sharedPreferences.edit()
            .putString(TEXTO_KEY,binding.edt2.text.toString())
            .putInt(NUMERO_ENTERO_KEY,binding.edt1.text.toString().toInt())
            .putFloat(NUMERO_DECIMAL_KEY,binding.edt3.text.toString().toFloat())
            .putBoolean(SWITCH_KEY,binding.switch1.isChecked)
            .apply()
    }

    //PASO 9
    private fun deleteValues() {
        sharedPreferences.edit()
            .putString(TEXTO_KEY,"")
            .putInt(NUMERO_ENTERO_KEY,0)
            .putFloat(NUMERO_DECIMAL_KEY,0.0f)
            .putBoolean(SWITCH_KEY,false)
            .apply()
            //PASO 10: Se llama a un método ya creado para no repetir codigo
            loadDefaultValues()
    }


    //PASO 7: con esto estamos cargando los valores
    private fun loadDefaultValues() {
        val numeroEntero = sharedPreferences.getInt(NUMERO_ENTERO_KEY,0)
        val numeroDecimal = sharedPreferences.getFloat(NUMERO_DECIMAL_KEY,0.0f)
        val texto = sharedPreferences.getString(TEXTO_KEY,"")
        val switchValue = sharedPreferences.getBoolean(SWITCH_KEY,false)


        binding.edt1.setText(numeroEntero.toString())
        binding.edt2.setText(texto)
        binding.edt3.setText(numeroDecimal.toString())
        binding.switch1.isChecked = switchValue
    }
 }