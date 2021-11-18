package com.example.trabalho_taniro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.trabalho_taniro.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        when(it.resultCode){
            1->{
                var params = it.data?.extras
                binding.pesoResultado.text = "${params?.getString("value")}"
            }
            2->{
                var params = it.data?.extras
                binding.alturaResultado.text = "${params?.getString("value")}"
            }
        }
    }
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var alteraAltura = binding.botaoAltura
        alteraAltura.setOnClickListener{
            var tituloAltura = binding.alturaTitulo
            Log.i("Jean",tituloAltura.text.toString())
            var intent = Intent(this, MainActivity2::class.java)
            var params = Bundle()
            params.putString("text", tituloAltura.text.toString())
            intent.putExtras(params)
            launcher.launch(intent)
        }
        var alteraPeso = binding.botaoPeso
        alteraPeso.setOnClickListener{
            var tituloPeso = binding.pesoTitulo
            var intent = Intent(this, MainActivity2::class.java)
            var params = Bundle()
            params.putString("text", tituloPeso.text.toString())
            intent.putExtras(params)
            launcher.launch(intent)
        }
        var calcular = binding.botaoCalcular
        calcular.setOnClickListener{
            var resultado = binding.ImcResultado
            var peso = binding.pesoResultado.text.toString().toFloat()
            var altura = binding.alturaResultado.text.toString().toFloat()
            Log.i("teste", altura.toString())
            var calculo = peso / ((altura/100) * (altura/100))
            if(calculo < 16) binding.ImcResultado.text = "$calculo Magreza Grave "
            if(calculo < 17 && calculo > 16) binding.ImcResultado.text = "$calculo Magreza moderado"
            if(calculo < 18.5 && calculo > 17) binding.ImcResultado.text = "$calculo Magreza Leve"
            if(calculo < 25 && calculo > 18.5) binding.ImcResultado.text = "$calculo Saudável"
            if(calculo < 30 && calculo > 25) binding.ImcResultado.text = "$calculo Sobrepeso"
            if(calculo < 35 && calculo > 30) binding.ImcResultado.text = "$calculo Obesidade Grau I"
            if(calculo < 35 && calculo > 30) binding.ImcResultado.text = "$calculo Obesidade Grau II (severa)"
            if(calculo > 40) binding.ImcResultado.text = "$calculo Obesidade Grau III (mórbida)"

        }

    }

    override fun onStart() {
        super.onStart()
        var preferencia = getSharedPreferences("minhasPreferencias", MODE_PRIVATE)
        binding.pesoResultado.text = preferencia.getString("peso","0.0")
        binding.alturaResultado.text = preferencia.getString("altura","0.0")
        binding.ImcResultado.text = preferencia.getString("imc","Resultado..")
    }

    override fun onStop() {
        super.onStop()
        var preferencia = getSharedPreferences("minhasPreferencias", MODE_PRIVATE)
        var edita = preferencia.edit()
        edita.putString("peso", binding.pesoResultado.text.toString())
        edita.putString("altura", binding.alturaResultado.text.toString())
        edita.putString("imc", binding.ImcResultado.text.toString())
        edita.apply()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.pesoResultado.text = savedInstanceState.getString("peso")
        binding.alturaResultado.text = savedInstanceState.getString("altura")
        binding.ImcResultado.text = savedInstanceState.getString("imc")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("peso", binding.pesoResultado.text.toString())
        outState.putString("altura", binding.alturaResultado.text.toString())
        outState.putString("imc", binding.ImcResultado.text.toString())
    }
}