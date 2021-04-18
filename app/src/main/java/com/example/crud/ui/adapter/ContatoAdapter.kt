package com.example.crud.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.data.model.Contato
import com.example.crud.R
import kotlinx.android.synthetic.main.contato_item.view.*

class ContatoAdapter(
    var listContato: ArrayList<Contato>,
    var listener: OnClickItemContatoListener
): RecyclerView.Adapter<ContatoAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.contato_item, parent, false)
        return viewHolder(
            view,
            listContato,
            listener
        )
    }

    override fun getItemCount(): Int = listContato.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var contato = listContato[position]
        with(holder.itemView){
            txt_nome.text = contato.nome
            txt_telefone.text = contato.telefone
        }
    }

    class viewHolder(itemView: View, var listContato: ArrayList<Contato>, listener: OnClickItemContatoListener) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener { listener.clickContato(listContato[adapterPosition]) }
        }
    }
}

interface OnClickItemContatoListener{
    fun clickContato(contato: Contato)
}