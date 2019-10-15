package com.example.financask.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.model.Transacao
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.Calendar

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = listOf(Transacao(BigDecimal(20.5),"Comida", Calendar.getInstance()),
            Transacao(BigDecimal(100.0),"Economia", Calendar.getInstance()))


        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes, this))


    }


}