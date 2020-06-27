package br.com.pintos.saci.model.repositories

import br.com.pintos.saci.model.beans.UserSaci
import org.springframework.data.repository.Repository

interface UserSaciRepository: Repository<UserSaci, String> {
  fun findByLogin(login : String) : UserSaci?
  
  fun persist(user : UserSaci) : UserSaci
}