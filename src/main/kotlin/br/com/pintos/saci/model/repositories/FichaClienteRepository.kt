package br.com.pintos.saci.model.repositories

import br.com.pintos.saci.model.beans.FichaCliente
import org.springframework.data.repository.Repository

interface FichaClienteRepository : Repository<FichaCliente, Int> {
  fun findByCodigo(codigo : Int) : FichaCliente?
}