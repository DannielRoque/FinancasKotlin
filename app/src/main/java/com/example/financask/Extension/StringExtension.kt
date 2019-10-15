package com.example.financask.Extension


fun String.limitaEmAte(caracteres: Int) : String{
    if(this.length>caracteres){
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, caracteres)}..."
    }
    return this
}