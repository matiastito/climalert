package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Email {
  private Long id;
  private String destinatario;
  private String remitente;
  private final Alerta alerta;
  private String asunto;
  private String contenido;
  private boolean enviado;

  public Email(String destinatario, String remitente, Alerta alerta) {
    this.destinatario = destinatario;
    this.remitente = remitente;
    this.alerta = alerta;
    this.enviado = false;
  }

  public String getAsunto() {
    return alerta.getAsunto();
  }

  public String getContenido() {
    return alerta.getMensaje();
  }

  public void enviar() {
    //TODO: Implementación pendiente. Podríamos usar adapters
  }
} 