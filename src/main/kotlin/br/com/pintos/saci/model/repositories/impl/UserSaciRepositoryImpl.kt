package br.com.pintos.saci.model.repositories.impl

import br.com.pintos.saci.model.beans.UserSaci
import br.com.pintos.saci.model.repositories.UserSaciRepository
import org.springframework.stereotype.Repository

@Repository
class UserSaciRepositoryImpl : UserSaciRepository {
  override fun findByLogin(login: String): UserSaci? {
    return UserSaci.findUser(login)
  }
  
  override fun persist(user: UserSaci): UserSaci {
    UserSaci.updateUser(user)
    return user
  }
}