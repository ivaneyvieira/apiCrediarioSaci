package br.com.pintos.saci.model.services.impl

import br.com.pintos.saci.model.dtos.UserSaciDto
import br.com.pintos.saci.model.repositories.UserSaciRepository
import br.com.pintos.saci.model.services.UserSaciService
import br.com.pintos.saci.model.services.toBean
import br.com.pintos.saci.model.services.toDto
import org.springframework.stereotype.Service

@Service
class UserSaciServiceImpl(val userSaciRepository : UserSaciRepository) : UserSaciService {
  override fun buscaPorLogin(login: String): UserSaciDto? {
    return userSaciRepository.findByLogin(login)?.toDto()
  }
  
  override fun salva(user: UserSaciDto): UserSaciDto {
    return userSaciRepository.persist(user.toBean()).toDto()
  }
}