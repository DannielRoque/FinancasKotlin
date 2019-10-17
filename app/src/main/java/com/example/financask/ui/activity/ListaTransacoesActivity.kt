package com.example.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes :  MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)
        configuraResumo()
        configuraLista()

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
                .setPositiveButton("Adicionar")
                { dialogInterface, i ->
                    val valorEmTexto = viewCriada
                        .form_transacao_valor.text.toString()
                    val dataEmTexto = viewCriada
                        .form_transacao_data.text.toString()
                    val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()

                    var valor = try{
                        BigDecimal(valorEmTexto)
                    }catch (exception : NumberFormatException){
                        Toast.makeText(this, "Conversão de valores FALHOU", Toast.LENGTH_LONG).show()
                        BigDecimal.ZERO
                    }

                    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyy")
                    val dataConvertida = formatoBrasileiro.parse(dataEmTexto)
                    val data = Calendar.getInstance()
                    data.time = dataConvertida


                    val transacaoCriada = Transacao(
                        tipo = Tipo.RECEITA,
                        valor = valor,
                        data = data,
                        categoria = categoriaEmTexto)

                    atualizaTransacoes(transacaoCriada)
                }

                .setNegativeButton("Cancelar", null)
                .setView(viewCriada)
                .show()
        }
    }

    private fun atualizaTransacoes(transacaoCriada: Transacao) {
        transacoes.add(transacaoCriada)
        configuraLista()
        configuraResumo()
        lista_transacoes_adiciona_menu.close(true)
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this,view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }
}