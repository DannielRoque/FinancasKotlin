package com.example.financask.ui.activity

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.Extension.formataParaBrasileiro
import com.example.financask.R
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import com.example.financask.ui.ResumoView
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        val transacoes: List<Transacao> = transacoesDeExemplo()
        configuraResumo(transacoes)
        configuraLista(transacoes)

        lista_transacoes_adiciona_receita.setOnClickListener {
            val view = window.decorView
            val viewCriada =
                LayoutInflater.from(this).inflate(R.layout.form_transacao,
                    view as ViewGroup, false)

            val ano = 2019
            val mes = 9
            val dia = 16

            val hoje = Calendar.getInstance()
            viewCriada.form_transacao_data.setText(hoje.formataParaBrasileiro())

            viewCriada.form_transacao_data.setOnClickListener {
                DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
                        var dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano,mes,dia)
                        viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())
                    }
                    , ano, mes, dia).show()
            }

            val adapter =
                ArrayAdapter.createFromResource(this, R.array.categorias_de_despesa,
                    android.R.layout.simple_spinner_dropdown_item)
            viewCriada.form_transacao_categoria.adapter = adapter

            AlertDialog.Builder(this)
                .setTitle(R.string.adiciona_receita)
                .setPositiveButton("Adicionar"
                ) { dialogInterface, i ->
                    val valorEmTexto = viewCriada
                        .form_transacao_valor.text.toString()
                    val dataEmTexto = viewCriada
                        .form_transacao_data.text.toString()
                    val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()

                    val valor = BigDecimal(valorEmTexto)
                    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyy")
                    val dataConvertida = formatoBrasileiro.parse(dataEmTexto)
                    val data = Calendar.getInstance()
                    data.time = dataConvertida

                    val transacaoCriada = Transacao(
                        tipo = Tipo.RECEITA,
                        valor = valor,
                        data = data,
                        categoria = categoriaEmTexto
                    )
                    Toast.makeText(this, "${transacaoCriada.valor} - " +
                            "${transacaoCriada.data.formataParaBrasileiro()} - ${transacaoCriada.categoria}" +
                            "- ${transacaoCriada.tipo}", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancelar", null)
                .setView(viewCriada)
                .show()
        }

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