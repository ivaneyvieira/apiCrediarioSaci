package br.com.pintos.saci.model.dtos

data class UserSaciDto (
  var no: Int = 0,
  var name: String = "",
  var login: String = "",
  var senha: String = "",
  var bitAcesso: Int = 0
                       )