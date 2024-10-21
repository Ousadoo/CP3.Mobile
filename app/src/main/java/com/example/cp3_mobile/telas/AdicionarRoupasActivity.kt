package com.example.cp3_mobile.telas

import com.example.cp3_mobile.database.RoupaDAO
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.R
import com.example.cp3_mobile.model.Roupa


class AdicionarRoupasActivity : AppCompatActivity() {
    private lateinit var edtNome: EditText
    private lateinit var edtTamanho: EditText
    private lateinit var edtCor: EditText
    private lateinit var edtMarca: EditText
    private lateinit var edtTipo: EditText
    private lateinit var edtPreco: EditText
    private lateinit var btnSalvar: Button
    private lateinit var roupaDAO: RoupaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_roupa)

        roupaDAO = RoupaDAO(this)
        edtNome = findViewById(R.id.edtNome)
        edtTamanho = findViewById(R.id.edtTamanho)
        edtCor = findViewById(R.id.edtCor)
        edtMarca = findViewById(R.id.edtMarca)
        edtTipo = findViewById(R.id.edtTipo)
        edtPreco = findViewById(R.id.edtPreco)
        btnSalvar = findViewById(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val nome = edtNome.text.toString()
            val tamanho = edtTamanho.text.toString()
            val cor = edtCor.text.toString()
            val tipo= edtTipo.text.toString()
            val marca= edtMarca.text.toString()
            val preco= edtPreco.text.toString()

            val roupa = Roupa(nome = nome, tamanho = tamanho, cor = cor, marca = marca, tipo = tipo, preco =preco)
            roupaDAO.inserirRoupa(roupa)
            finish()
        }
    }
}
