package br.com.pintos.saci.model.responce

data class Response<T>(val erros: MutableList<String> = mutableListOf(), var data: T? = null)