package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Alerta;
import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.CondicionesDeAlerta;
import ar.utn.ba.ddsi.mailing.models.repositories.IAlertaRepository;
import ar.utn.ba.ddsi.mailing.services.IAlertasService;
import ar.utn.ba.ddsi.mailing.services.IClimaService;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AlertasService implements IAlertasService {
  private static final Logger logger = LoggerFactory.getLogger(AlertasService.class);

  private final IClimaService climaService;
  private final IEmailService emailService;

  private final List<CondicionesDeAlerta> condicionesDeAlerta;

  private final IAlertaRepository alertaRepository;

  public AlertasService(
      IClimaService climaService,
      IEmailService emailService,
      List<CondicionesDeAlerta> condicionesDeAlerta,
      IAlertaRepository alertaRepository) {

    this.alertaRepository = alertaRepository;

    this.climaService = climaService;
    this.emailService = emailService;

    this.condicionesDeAlerta = condicionesDeAlerta;

  }

  @Override
  public Mono<Void> generarAlertasYAvisar() {
    return Mono.fromCallable(() -> climaService.pendientes())
        .flatMap(climas -> {
          logger.info("Procesando {} registros de clima no procesados", climas.size());
          return Mono.just(climas);
        })
        .flatMap(climas -> {

          // Generar Alertas
          climas.stream()
              .filter(c -> condicionesDeAlerta.stream().allMatch(ca -> ca.esAlerta(c)))
              .forEach(this::generarAlertaYEmail);

          // Marcar todos como procesados
          climaService.marcarTodosComoProcesados();
          return Mono.empty();
        })
        .onErrorResume(e -> {
          logger.error("Error al procesar alertas: {}", e.getMessage());
          return Mono.empty();
        })
        .then();
  }

  private void generarAlertaYEmail(Clima clima) {
    Alerta alerta = alertaRepository.save(new Alerta(clima));
    emailService.generarEmail(alerta);
  }
}