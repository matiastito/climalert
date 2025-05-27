package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alerta {

  private Long id;
  private Clima clima;

  public Alerta(Clima clima) {
    this.clima = clima;
  }

  public String getAsunto() {
    return "Alerta de Clima - Condiciones Extremas";
  }

  public String getMensaje() {
    return String.format(
        "ALERTA: Condiciones climáticas extremas detectadas en %s\n\n" +
            "Temperatura: %.1f°C\n" +
            "Humedad: %d%%\n" +
            "Condición: %s\n" +
            "Velocidad del viento: %.1f km/h\n\n" +
            "Se recomienda tomar precauciones.",
        clima.getCiudad(),
        clima.getTemperaturaCelsius(),
        clima.getHumedad(),
        clima.getCondicion(),
        clima.getVelocidadVientoKmh()
    );
  }

  public String getCiudad() {
    return clima.getCiudad();
  }
}
