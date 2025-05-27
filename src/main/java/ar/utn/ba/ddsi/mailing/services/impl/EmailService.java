package ar.utn.ba.ddsi.mailing.services.impl;

import static java.util.Arrays.asList;
import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IEmailRepository;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService implements IEmailService {
  private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

  private final IEmailRepository emailRepository;
  private final String remitente;
  private final List<String> destinatarios;

  public EmailService(IEmailRepository emailRepository,
                      @Value("${email.alertas.remitente}") String remitente,
                      @Value("${email.alertas.destinatarios}") String destinatarios) {
    this.emailRepository = emailRepository;
    this.remitente = remitente;
    this.destinatarios = asList(destinatarios.split(","));
  }

  @Override
  public Email crearEmail(Email email) {
    return emailRepository.save(email);
  }

  @Override
  public List<Email> obtenerEmails(Boolean pendiente) {
    if (pendiente != null) {
      return emailRepository.findByEnviado(!pendiente);
    }
    return emailRepository.findAll();
  }

  @Override
  public void procesarPendientes() {
    List<Email> pendientes = emailRepository.findByEnviado(false);
    for (Email email : pendientes) {
      email.enviar();
      email.setEnviado(true);
      emailRepository.save(email);
    }
  }

  @Override
  public void loguearEmailsPendientes() {
    List<Email> pendientes = obtenerEmails(true);
    logger.info("Emails pendientes de envío: {}", pendientes.size());
    pendientes.forEach(email ->
        logger.info("Email pendiente - ID: {}, Destinatario: {}, Asunto: {}",
            email.getId(),
            email.getDestinatario(),
            email.getAsunto())
    );
  }

  @Override
  public void generarEmail(Alerta alerta) {
    for (String destinatario : destinatarios) {
      Email email = new Email(destinatario, remitente, alerta);
      this.crearEmail(email);
    }

    logger.info("Email de alerta generado para {} - Enviado a {} destinatarios",
        alerta.getCiudad(), destinatarios.size());

  }
}