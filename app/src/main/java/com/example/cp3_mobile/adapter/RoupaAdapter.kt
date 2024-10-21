package com.example.cp3_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3_mobile.R
import com.example.cp3_mobile.model.Roupa

class RoupaAdapter(
    private var listaRoupas: List<Roupa>,
    private val onItemClick: (Roupa) -> Unit
) : RecyclerView.Adapter<RoupaAdapter.RoupaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoupaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_roupa, parent, false)
        return RoupaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoupaViewHolder, position: Int) {
        val roupa = listaRoupas[position]
        holder.bind(roupa)
    }

    override fun getItemCount(): Int = listaRoupas.size

    fun atualizarLista(novaLista: List<Roupa>) {
        listaRoupas = novaLista
        notifyDataSetChanged()
    }

    inner class RoupaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNome: TextView = itemView.findViewById(R.id.tvNomeRoupa)
        private val tvTipo: TextView = itemView.findViewById(R.id.tvTipoRoupa)
        private val tvPreco: TextView = itemView.findViewById(R.id.tvPrecoRoupa)

        fun bind(roupa: Roupa) {
            tvNome.text = roupa.nome
            tvTipo.text = roupa.tipo
            tvPreco.text = "R$ ${roupa.preco}"

            itemView.setOnClickListener {
                onItemClick(roupa)
            }
        }
    }
}