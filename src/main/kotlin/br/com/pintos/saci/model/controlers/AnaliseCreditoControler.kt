package br.com.pintos.saci.model.controlers

import br.com.pintos.saci.model.dtos.FichaClienteDto
import br.com.pintos.saci.model.responce.Response
import br.com.pintos.saci.model.services.FichaClienteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/analise")
class AnaliseCreditoControler(val service : FichaClienteService) {
  @GetMapping("/ficha/{codigo}")
  fun buscaPorCodigo(@PathVariable("codigo") codigo : String) : ResponseEntity<Response<FichaClienteDto>> {
    val response = Response<FichaClienteDto>()
    val ficha = service.buscaPorCodigo(codigo.toIntOrNull() ?: 0)
    return if(ficha == null){
      response.erros.add("Cliente não encontrado")
      ResponseEntity.badRequest().body(response)
    }else{
      response.data = ficha
      ResponseEntity.ok(response)
    }
  }
}