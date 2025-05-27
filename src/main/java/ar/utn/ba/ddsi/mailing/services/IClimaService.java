package ar.utn.ba.ddsi.mailing.services;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IClimaService {
  Mono<Void> actualizarClimaCiudades();

  List<Clima> pendientes();

  void marcarTodosComoProcesados();
}