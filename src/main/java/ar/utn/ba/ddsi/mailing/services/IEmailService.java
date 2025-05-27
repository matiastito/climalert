package ar.utn.ba.ddsi.mailing.services;

import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.Email;

import java.util.List;

public interface IEmailService {
  Email crearEmail(Email email);

  List<Email> obtenerEmails(Boolean pendiente);

  void procesarPendientes();

  void loguearEmailsPendientes();

  void generarEmail(Alerta alerta);
}