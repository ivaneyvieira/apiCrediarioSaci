package br.com.pintos.saci.model.repositories.impl

import br.com.pintos.saci.model.beans.FichaCliente
import br.com.pintos.saci.model.repositories.FichaClienteRepository
import br.com.pintos.saci.model.saci
import org.springframework.stereotype.Repository

@Repository
class FichaClienteRepositoryImpl: FichaClienteRepository {
  override fun findByCodigo(codigo: Int): FichaCliente? {
    return saci.fichaClienteByCodigo(codigo)
  }
}