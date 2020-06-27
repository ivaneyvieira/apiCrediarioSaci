package br.com.pintos.saci.model.utils

import br.com.pintos.framework.util.SystemUtils.md5
import org.springframework.security.crypto.password.PasswordEncoder

class SaciPasswordEncoder: PasswordEncoder {
  override fun encode(rawPassword: CharSequence?): String {
    val pass =rawPassword.toString()
    return md5(pass.trim()).toUpperCase()
  }
  
  override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
    val passEncode = encode(rawPassword)
    return encodedPassword == passEncode
  }
}
