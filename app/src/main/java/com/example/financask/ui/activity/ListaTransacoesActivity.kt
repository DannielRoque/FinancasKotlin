package com.example.financask.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = listOf("Comida - R$:20,00", "Economia - R$: 100,00")
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transacoes)
        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes, this))

//teste

    }


}