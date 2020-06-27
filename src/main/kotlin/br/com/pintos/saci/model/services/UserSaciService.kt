package br.com.pintos.saci.model.services

import br.com.pintos.saci.model.beans.UserSaci
import br.com.pintos.saci.model.dtos.UserSaciDto

interface UserSaciService {
  fun buscaPorLogin(login: String): UserSaciDto?
  
  fun salva(user: UserSaciDto): UserSaciDto
}

fun UserSaciDto.toBean() = UserSaci(no, name, login, senha, bitAcesso)

fun UserSaci.toDto() = UserSaciDto(no, name, login, senha, bitAcesso)