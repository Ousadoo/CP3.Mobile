package com.example.cp3_mobile.telas

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.R

class InformacoesActivity : AppCompatActivity() {
    private lateinit var txtInformacoes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacoes)

        txtInformacoes = findViewById(R.id.txtInformacoes)
        txtInformacoes.text = "App desenvolvido por [Vinicius e Pedro].\nEste é um aplicativo para gerenciar roupas."
    }
}
