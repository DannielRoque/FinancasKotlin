package com.example.financask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.financask.Extension.formataParaBrasileiro
import com.example.financask.R
import com.example.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    transacoes: List<Transacao>,
    context: Context) : BaseAdapter(){
    private val transacoes = transacoes
    private val context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
        val transacao = transacoes[position]
        viewCriada.transacao_valor.text = transacao.valor.toString()
        viewCriada.transacao_categoria.text=transacao.categoria
        viewCriada.transacao_data.text=transacao.data.formataParaBrasileiro()

        return viewCriada
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }

}