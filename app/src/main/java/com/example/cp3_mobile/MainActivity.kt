package com.example.cp3_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3_mobile.adapter.RoupaAdapter
import com.example.cp3_mobile.database.RoupaDAO
import com.example.cp3_mobile.model.Roupa
import com.example.cp3_mobile.telas.AdicionarRoupasActivity
import com.example.cp3_mobile.telas.ConfiguracoesActivity
import com.example.cp3_mobile.telas.DetalhesRoupaActivity
import com.example.cp3_mobile.telas.InformacoesActivity

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RoupaAdapter
    private lateinit var roupaDAO: RoupaDAO
    private lateinit var btnAddRoupa: Button
    private lateinit var btnInfo: Button
    private lateinit var btnConfig: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roupaDAO = RoupaDAO(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RoupaAdapter(roupaDAO.listarRoupas()) { roupa ->
            abrirDetalhesRoupa(roupa)
        }
        recyclerView.adapter = adapter

        btnAddRoupa = findViewById(R.id.btnAddRoupa)
        btnAddRoupa.setOnClickListener {
            val intent = Intent(this, AdicionarRoupasActivity::class.java)
            startActivity(intent)
        }

        btnInfo = findViewById(R.id.btnInfo)
        btnInfo.setOnClickListener {
            val intent = Intent(this, InformacoesActivity::class.java)
            startActivity(intent)
        }

        btnConfig = findViewById(R.id.btnConfig)
        btnConfig.setOnClickListener {
            val intent = Intent(this, ConfiguracoesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun abrirDetalhesRoupa(roupa: Roupa) {
        val intent = Intent(this, DetalhesRoupaActivity::class.java)
        intent.putExtra("roupa_id", roupa.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        adapter.atualizarLista(roupaDAO.listarRoupas())
    }
}