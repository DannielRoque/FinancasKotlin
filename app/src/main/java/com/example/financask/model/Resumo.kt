package com.example.financask.model

import java.math.BigDecimal

class Resumo (private val transacoes: List<Transacao>){

    fun receita() = somaPor(Tipo.RECEITA)

    fun despesa() = somaPor(Tipo.DESPESA)

    fun total() = receita().subtract(despesa())

    private fun somaPor(tipo : Tipo) : BigDecimal{
        val somaDeTransacaoPorTipo = transacoes.filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() }
        return BigDecimal(somaDeTransacaoPorTipo)
    }
}