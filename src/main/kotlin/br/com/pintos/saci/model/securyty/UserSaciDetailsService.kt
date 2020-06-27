package br.com.pintos.saci.model.securyty

import br.com.pintos.saci.model.services.UserSaciService
import br.com.pintos.saci.model.services.toBean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserSaciDetailsService(val userSaciService: UserSaciService) : UserDetailsService {
  override fun loadUserByUsername(username: String?): UserDetails {
    username ?: throw UsernameNotFoundException(username)
    val user = userSaciService.buscaPorLogin(username) ?: throw UsernameNotFoundException(username)
    return UserSaciPrincipal(user.toBean())
  }
}