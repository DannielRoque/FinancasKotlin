package com.example.financask.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import com.example.financask.ui.ResumoView
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.Calendar

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        val transacoes: List<Transacao> = transacoesDeExemplo()
        configuraResumo(transacoes)
        configuraLista(transacoes)
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view = window.decorView
        val resumoView = ResumoView(this,view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao(
                valor = BigDecimal(20.5),
                tipo = Tipo.DESPESA,
                categoria = "Comida para o final de semana",
                data = Calendar.getInstance()
            ),
            Transacao(
                valor = BigDecimal(70.0),
                tipo = Tipo.RECEITA,
                categoria = "Economia de energia",
                data = Calendar.getInstance()
            ),
            Transacao(
                valor = BigDecimal(10),
                tipo = Tipo.DESPESA,
                data = Calendar.getInstance()
            ),
            Transacao(valor = BigDecimal(150.0), tipo = Tipo.RECEITA, categoria = "Bônus"),
            Transacao(
                valor = BigDecimal(250.5),
                tipo = Tipo.DESPESA,
                data = Calendar.getInstance()
            ),
            Transacao(valor = BigDecimal(150.0), tipo = Tipo.RECEITA, categoria = "Bônus")
        )
    }
}