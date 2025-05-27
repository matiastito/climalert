package ar.utn.ba.ddsi.mailing.models.repositories.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.repositories.IAlertaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AlertaRepository implements IAlertaRepository {
  private final Map<Long, Alerta> alertas = new HashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Alerta save(Alerta alerta) {
    if (alerta.getId() == null) {
      Long id = idGenerator.getAndIncrement();
      alerta.setId(id);
      alertas.put(id, alerta);
    } else {
      // Es una actualización
      alertas.put(alerta.getId(), alerta);
    }
    return alerta;
  }

  @Override
  public List<Alerta> findAll() {
    return new ArrayList<>(alertas.values());
  }
}