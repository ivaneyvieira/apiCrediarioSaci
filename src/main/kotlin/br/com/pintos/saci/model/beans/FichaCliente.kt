package br.com.pintos.saci.model.beans

import java.time.LocalDate

data class FichaCliente(val codigo: String?,
                        val nome: String?,
                        val vlDeclardo: Double? = null,
                        val vlComprovado: Double? = null,
                        val mediaPestacaoQuitada: Double? = null,
                        val idade: Int? = null,
                        val limiteDisponivel: Double? = null,
                        val ultimaAtualizacaoRenda: LocalDate? = null,
                        val tempoAdmissao: Int? = null,
                        val tempoRelacionamento: Int? = null,
                        val totalChequeAberto: Int? = null,
                        val qtdePrestacoesVencidas: Int? = null,
                        val maiorAtraso: Int? = null,
                        val moradiaPropria: String? = null,
                        val possuiCompraCrediario: String? = null,
                        val cidade: String? = null,
                        val estado: String? = null,
                        val cep: String? = null,
                        val avalistaContratoAberto: String? = null,
                        val renegociouContrato: String? = null)