package br.com.pintos.saci.model.beans

import br.com.pintos.saci.model.saci

class UserSaci(val no: Int = 0,
               val name: String = "",
               val login: String = "",
               val senha: String = "",
               var bitAcesso: Int = 0) {
  //Otiros campos
  var ativo
    get() = (bitAcesso and BIT_ATIVO) != 0 || login == "ADM"
    set(value) {
      bitAcesso = when {
        value -> bitAcesso or BIT_ATIVO
        else  -> bitAcesso and BIT_ATIVO.inv()
      }
    }
  var admin
    get() = (bitAcesso and BIT_ADMIN) != 0
    set(value) {
      bitAcesso = when {
        value -> bitAcesso or BIT_ADMIN
        else  -> bitAcesso and BIT_ADMIN.inv()
      }
    }
  val perfil get() = if(admin) "ADMIN" else "USER"
  
  companion object {
    const val BIT_ATIVO: Int = 1
    const val BIT_ADMIN: Int = 2
    
    fun findAll(): List<UserSaci>? {
      return saci.findAllUser()
        .filter {it.ativo}
    }
    
    fun updateUser(user: UserSaci) {
      saci.updateUser(user)
    }
    
    fun findUser(login: String?): UserSaci? {
      return saci.findUser(login)
    }
  }
}

