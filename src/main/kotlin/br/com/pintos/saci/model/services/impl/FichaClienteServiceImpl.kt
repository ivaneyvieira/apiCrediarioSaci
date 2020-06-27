package br.com.pintos.saci.model.services.impl

import br.com.pintos.saci.model.dtos.FichaClienteDto
import br.com.pintos.saci.model.repositories.FichaClienteRepository
import br.com.pintos.saci.model.services.FichaClienteService
import br.com.pintos.saci.model.services.toDto
import org.springframework.stereotype.Service

@Service
class FichaClienteServiceImpl(val fichaClienteRepository: FichaClienteRepository): FichaClienteService {
  override fun buscaPorCodigo(codigo: Int): FichaClienteDto? = fichaClienteRepository.findByCodigo(codigo)?.toDto()
}