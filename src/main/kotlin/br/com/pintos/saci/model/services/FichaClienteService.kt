package br.com.pintos.saci.model.services

import br.com.pintos.saci.model.beans.FichaCliente
import br.com.pintos.saci.model.dtos.FichaClienteDto

interface FichaClienteService {
  fun buscaPorCodigo(codigo: Int): FichaClienteDto?
}

fun FichaCliente.toDto() = FichaClienteDto(codigo,
                                           nome,
                                           vlDeclardo,
                                           vlComprovado,
                                           mediaPestacaoQuitada,
                                           idade,
                                           limiteDisponivel,
                                           ultimaAtualizacaoRenda,
                                           tempoAdmissao,
                                           tempoRelacionamento,
                                           totalChequeAberto,
                                           qtdePrestacoesVencidas,
                                           maiorAtraso,
                                           moradiaPropria,
                                           possuiCompraCrediario,
                                           cidade,
                                           estado,
                                           cep,
                                           avalistaContratoAberto,
                                           renegociouContrato)

fun FichaClienteDto.toBean() = FichaCliente(codigo,
                                            nome,
                                            vlDeclardo,
                                            vlComprovado,
                                            mediaPestacaoQuitada,
                                            idade,
                                            limiteDisponivel,
                                            ultimaAtualizacaoRenda,
                                            tempoAdmissao,
                                            tempoRelacionamento,
                                            totalChequeAberto,
                                            qtdePrestacoesVencidas,
                                            maiorAtraso,
                                            moradiaPropria,
                                            possuiCompraCrediario,
                                            cidade,
                                            estado,
                                            cep,
                                            avalistaContratoAberto,
                                            renegociouContrato)

