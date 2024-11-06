package com.example.cp3_mobile.telas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.R
import com.example.cp3_mobile.database.RoupaDAO
import com.example.cp3_mobile.model.Roupa
import com.google.firebase.firestore.FirebaseFirestore

class AdicionarRoupasActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtTamanho: EditText
    private lateinit var edtCor: EditText
    private lateinit var edtMarca: EditText
    private lateinit var edtTipo: EditText
    private lateinit var edtPreco: EditText
    private lateinit var btnSalvar: Button
    private lateinit var roupaDAO: RoupaDAO
    private lateinit var firestore: FirebaseFirestore

    private var roupaId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_roupa)

        // Inicializar Firebase e banco de dados local
        firestore = FirebaseFirestore.getInstance()
        roupaDAO = RoupaDAO(this)

        // Inicializar campos de entrada e botão
        edtNome = findViewById(R.id.edtNome)
        edtTamanho = findViewById(R.id.edtTamanho)
        edtCor = findViewById(R.id.edtCor)
        edtMarca = findViewById(R.id.edtMarca)
        edtTipo = findViewById(R.id.edtTipo)
        edtPreco = findViewById(R.id.edtPreco)
        btnSalvar = findViewById(R.id.btnSalvar)

        // Receber ID da roupa do banco de dados local, se for uma atualização
        roupaId = intent.getLongExtra("roupa_id", -1).takeIf { it != -1L }

        // Preencher campos se roupaId não for nulo
        roupaId?.let { id ->
            val roupa = roupaDAO.obterRoupaPorId(id)
            roupa?.let { r ->
                edtNome.setText(r.nome)
                edtTamanho.setText(r.tamanho)
                edtCor.setText(r.cor)
                edtMarca.setText(r.marca)
                edtTipo.setText(r.tipo)
                edtPreco.setText(r.preco)
            }
        }

        // Ação do botão Salvar
        btnSalvar.setOnClickListener {
            if (roupaId == null) {
                salvarRoupa()
            } else {
                atualizarRoupa()
            }
        }
    }

    private fun salvarRoupa() {
        val roupa = Roupa(
            nome = edtNome.text.toString(),
            tamanho = edtTamanho.text.toString(),
            cor = edtCor.text.toString(),
            marca = edtMarca.text.toString(),
            tipo = edtTipo.text.toString(),
            preco = edtPreco.text.toString()
        )

        // Adicionar roupa ao Firestore
        firestore.collection("Roupas")
            .add(roupa)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Roupa salva com sucesso no Firestore! ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar roupa no Firestore.", Toast.LENGTH_SHORT).show()
            }

        // Adicionar roupa ao banco de dados local
        roupaDAO.inserirRoupa(roupa)
        Toast.makeText(this, "Roupa salva localmente!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun atualizarRoupa() {
        roupaId?.let { id ->
            val roupa = Roupa(
                id = id,
                nome = edtNome.text.toString(),
                tamanho = edtTamanho.text.toString(),
                cor = edtCor.text.toString(),
                marca = edtMarca.text.toString(),
                tipo = edtTipo.text.toString(),
                preco = edtPreco.text.toString()
            )

            // Atualizar roupa no Firestore
            firestore.collection("Roupas").document(id.toString())
                .set(roupa)
                .addOnSuccessListener {
                    Toast.makeText(this, "Roupa atualizada no Firestore!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao atualizar roupa no Firestore.", Toast.LENGTH_SHORT).show()
                }

            // Atualizar roupa no banco de dados local
            roupaDAO.atualizarRoupa(roupa)
            Toast.makeText(this, "Roupa atualizada localmente!", Toast.LENGTH_SHORT).show()
            finish()
        } ?: run {
            Toast.makeText(this, "ID da roupa inválido.", Toast.LENGTH_SHORT).show()
        }
    }
}