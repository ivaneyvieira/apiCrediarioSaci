package br.com.pintos.saci.model.services.impl

import br.com.pintos.saci.model.beans.FichaCliente
import br.com.pintos.saci.model.repositories.FichaClienteRepository
import br.com.pintos.saci.model.services.FichaClienteService
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.mockito.BDDMockito


@SpringBootTest
internal class FichaClienteServiceImplTest {
  @MockBean
  private val repository : FichaClienteRepository? = null
  
  @Autowired
  private val service : FichaClienteService? = null
  
  private val codigo = 123456
  private val nome = "RITA DE CASSIA"
  
  @BeforeEach
  @Throws(Exception::class)
  fun setUp() {
    BDDMockito.given(repository?.findByCodigo(codigo)).willReturn(fichaCliente())
  }
  
  private fun fichaCliente(): FichaCliente? {
    return FichaCliente(codigo = codigo.toString(),  nome = nome)
  }
  
  @DisplayName("busca por código")
  @Test
  fun buscaPorCodigo() {
    val ficha = service?.buscaPorCodigo(codigo)
    Assert.assertNotNull(ficha)
  }
}