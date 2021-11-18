package com.example.trabalho_taniro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.trabalho_taniro.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        var params = intent.extras
        var text = params?.getString("text")
        binding.resultadoAlterar.text = text
        var valorAlterar = binding.valorDigitado
        var botaoAlterar = binding.botaoAlterar
        var botaoCancelar = binding.botaoCancelar
        botaoAlterar.setOnClickListener{
            var intent = Intent()
            var params = Bundle()
            params.putString("value", valorAlterar.text.toString())
            intent.putExtras(params)
            if(text.equals("Altura:")){
                setResult(2,intent)
            }else{
                setResult(1, intent)
            }
            finish()
        }
        botaoCancelar.setOnClickListener{
            var intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }
    }
}