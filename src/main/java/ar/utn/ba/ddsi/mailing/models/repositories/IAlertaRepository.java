package ar.utn.ba.ddsi.mailing.models.repositories;

import ar.utn.ba.ddsi.mailing.models.entities.Alerta;

import java.util.List;

public interface IAlertaRepository {
  Alerta save(Alerta alerta);

  List<Alerta> findAll();
}