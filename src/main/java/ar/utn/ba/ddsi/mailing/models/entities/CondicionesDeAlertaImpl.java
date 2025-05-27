package ar.utn.ba.ddsi.mailing.models.entities;

import org.springframework.stereotype.Component;

@Component
public class CondicionesDeAlertaImpl implements CondicionesDeAlerta {
  private static final double TEMPERATURA_ALERTA = 35.0;
  private static final int HUMEDAD_ALERTA = 60;

  @Override
  public boolean esAlerta(Clima clima) {
    return clima.getTemperaturaCelsius() > TEMPERATURA_ALERTA &&
        clima.getHumedad() > HUMEDAD_ALERTA;
  }
}
