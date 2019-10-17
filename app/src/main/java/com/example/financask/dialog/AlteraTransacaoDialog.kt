package com.example.financask.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.financask.Extension.converteParaCalendar
import com.example.financask.Extension.formataParaBrasileiro
import com.example.financask.R
import com.example.financask.delegate.TransacaoDelegate
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {
    private val viewCriada = criaLayout()
    private val campoValor = viewCriada.form_transacao_valor
    private val campoData = viewCriada.form_transacao_data
    private val campoCategoria = viewCriada.form_transacao_categoria

    fun chama(transacao: Transacao,
        transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)

        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formataParaBrasileiro())
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun configuraFormulario(
        tipo: Tipo,
        transacaoDelegate: TransacaoDelegate
    ) {
        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setPositiveButton("Alterar")
            { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()

                var valor = converteCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                transacaoDelegate.delegate(transacaoCriada)
            }

            .setNegativeButton("Cancelar", null)
            .setView(viewCriada)
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
         if (tipo == Tipo.RECEITA) {
           return R.string.altera_receita
        }
           return R.string.altera_despesa

    }

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Conversão de valores FALHOU", Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val tipo = categoriaPor(tipo)
        val adapter =
            ArrayAdapter.createFromResource(
                context, tipo,
                android.R.layout.simple_spinner_dropdown_item
            )
        campoCategoria.adapter = adapter
    }

    private fun categoriaPor(tipo: Tipo): Int {
         if (tipo == Tipo.RECEITA) {
             return R.array.categorias_de_receita
         }
           return R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataParaBrasileiro())

        campoData.setOnClickListener {
            DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                    var dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }
                , ano, mes, dia).show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(
            R.layout.form_transacao,
            viewGroup, false
        )
    }

}