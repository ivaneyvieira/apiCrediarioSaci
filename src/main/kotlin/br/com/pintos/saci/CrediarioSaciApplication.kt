package br.com.pintos.saci

import br.com.pintos.framework.model.DBProperties
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CrediarioSaciApplication: CommandLineRunner {
  override fun run(vararg args: String?) {
    DBProperties.initializePropertyFile()
  }
}

fun main(args: Array<String>) {
  runApplication<CrediarioSaciApplication>(*args)
}
