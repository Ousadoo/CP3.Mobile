package com.example.cp3_mobile.telas

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.R
import com.example.cp3_mobile.database.RoupaDAO

class DetalhesRoupaActivity : AppCompatActivity() {
    private lateinit var txtNome: TextView
    private lateinit var txtTipo: TextView
    private lateinit var txtTamanho: TextView
    private lateinit var txtCor: TextView
    private lateinit var txtMarca: TextView
    private lateinit var txtPreco: TextView
    private lateinit var roupaDAO: RoupaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_roupa)

        txtNome = findViewById(R.id.txtNome)
        txtTipo = findViewById(R.id.txtTipo)
        txtTamanho = findViewById(R.id.txtTamanho)
        txtCor = findViewById(R.id.txtCor)
        txtMarca = findViewById(R.id.txtMarca)
        txtPreco = findViewById(R.id.txtPreco)

        roupaDAO = RoupaDAO(this)

        val roupaId = intent.getLongExtra("roupa_id", -1)
        val roupa = roupaDAO.obterRoupaPorId(roupaId)

        roupa?.let {
            txtNome.text = it.nome
            txtTipo.text = it.tipo
            txtTamanho.text = it.tamanho
            txtCor.text = it.cor
            txtMarca.text = it.marca
            txtPreco.text = "R$ ${it.preco}"
        } ?: run {
            txtNome.text = "Roupa não encontrada"
            txtTipo.text = ""
            txtTamanho.text = ""
            txtCor.text = ""
            txtMarca.text = ""
            txtPreco.text = ""
        }
    }
}
