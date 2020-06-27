package br.com.pintos.saci.model

import br.com.pintos.saci.model.beans.FichaCliente
import br.com.pintos.saci.model.beans.UserSaci
import br.com.pintos.framework.model.DBProperties
import br.com.pintos.framework.model.QueryDB

class QuerySaci: QueryDB(driver, url, username, password) {
  fun findUser(login: String?): UserSaci? {
    login ?: return null
    val sql = "/sql/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", login)
    }.firstOrNull()
  }
  
  fun findAllUser(): List<UserSaci> {
    val sql = "/sql/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", "TODOS")
    }
  }
  
  fun fichaClienteByCodigo(custno: Int): FichaCliente? {
    val sql = "/sql/fichaCliente.sql"
    return query(sql, FichaCliente::class) {
      addOptionalParameter("custno", custno)
    }.firstOrNull()
  }
  
  fun updateUser(user: UserSaci) {
    val sql = "/sql/updateUser.sql"
    script(sql) {
      addOptionalParameter("login", user.login)
      addOptionalParameter("bitAcesso", user.bitAcesso)
    }
  }
  
  companion object {
    private val db = DBProperties("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
    internal val test = db.test
    val ipServer
      get() = url.split("/")
                .getOrNull(2) ?: "0.0.0.0"
  }
}

val saci = QuerySaci()