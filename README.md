# Event Driven Orders

Software Architecture Lab orientado al aprendizaje progresivo de arquitecturas distribuidas mediante un proyecto construido desde cero.

El objetivo de este laboratorio no es únicamente aprender tecnologías, sino comprender las decisiones de diseño detrás de sistemas modernos utilizando TDD, Arquitectura Hexagonal y Event Driven Architecture.

---

# Tecnologías

- Java 21
- Spring Boot
- Apache Kafka
- RabbitMQ
- Docker
- Kubernetes
- AWS

---

# Objetivos

- Comprender Event Driven Architecture.
- Dominar Apache Kafka y RabbitMQ.
- Diseñar microservicios desacoplados.
- Aplicar TDD desde el dominio hasta la infraestructura.
- Construir un proyecto con estándares cercanos a producción.

---

# Filosofía del laboratorio

Este laboratorio sigue un enfoque incremental.

Cada tecnología se incorpora únicamente cuando resuelve una necesidad real del sistema.

El objetivo no es construir muchos microservicios rápidamente, sino comprender por qué cada decisión arquitectónica existe y cómo impacta en el diseño de software.

Cada nueva funcionalidad sigue el mismo flujo de trabajo:

1. Diseño.
2. TDD (Red → Green → Refactor).
3. Integración.
4. Validación.

---

# Principios del laboratorio

- El dominio se diseña antes que la infraestructura.
- Las pruebas guían el desarrollo.
- La arquitectura tiene prioridad sobre el framework.
- Cada tecnología se incorpora únicamente cuando resuelve un problema real.
- Se prioriza comprender el **por qué** de cada decisión antes que simplemente hacer funcionar el código.
- El laboratorio evoluciona como un sistema real, incorporando nuevas capacidades de forma incremental.

---

# Roadmap

## Etapa 1 — Fundamentos del dominio

- [x] Modelado del dominio.
- [x] Casos de uso.
- [x] Arquitectura Hexagonal.
- [x] TDD.
- [x] Productor Kafka.
- [x] Pruebas unitarias.
- [x] Pruebas de integración del productor.

---

## Etapa 2 — Order Service

Objetivo: completar el primer flujo vertical de la aplicación.

- [x] Diseño del contrato REST.
- [x] Adaptador REST.
- [ ] Adaptador de persistencia.
- [x] Manejo global de excepciones HTTP.
- [x] PostgreSQL mediante Docker Compose.
- [ ] Ejecución local del servicio.
- [ ] Kafka mediante Docker Compose.
- [ ] Flujo end-to-end (HTTP → Application → PostgreSQL → Kafka).

---

## Etapa 3 — Notification Service

Objetivo: introducir el primer consumidor de eventos.

- [ ] Nuevo microservicio.
- [ ] Consumidor de `orders.created`.
- [ ] Consumidor de `orders.paid`.
- [ ] Consumer Groups.
- [ ] Pruebas unitarias.
- [ ] Pruebas de integración entre microservicios.

---

## Etapa 4 — Resiliencia

Objetivo: comprender los desafíos reales de los sistemas distribuidos.

- [ ] Retry.
- [ ] Backoff.
- [ ] Dead Letter Topic (DLT).
- [ ] Idempotencia.
- [ ] Transactional Outbox.

---

## Etapa 5 — Contenerización

Objetivo: ejecutar la arquitectura completa de manera reproducible.

- [ ] Docker Compose.
- [ ] Contenerización de todos los microservicios.
- [ ] Redes.
- [ ] Variables de entorno.
- [ ] Health Checks.

---

## Etapa 6 — RabbitMQ

Objetivo: comprender cuándo utilizar colas frente a eventos.

- [ ] Introducción de RabbitMQ.
- [ ] Worker de notificaciones.
- [ ] Comparativa Kafka vs RabbitMQ.
- [ ] Casos de uso de cada tecnología.

---

## Etapa 7 — Despliegue

Objetivo: preparar el laboratorio para un entorno cercano a producción.

- [ ] Kubernetes.
- [ ] Configuración.
- [ ] Observabilidad.
- [ ] AWS.

---

# Estado actual

Actualmente el laboratorio cuenta con un `order-service` capaz de:

- Crear órdenes.
- Pagar órdenes.
- Publicar eventos mediante Apache Kafka.
- Aplicar Arquitectura Hexagonal.
- Aplicar TDD.
- Contar con pruebas unitarias e integración.

La siguiente etapa consiste en completar el primer flujo vertical del sistema incorporando:

- Adaptador REST.
- Adaptador de persistencia.
- Persistencia en PostgreSQL.
- Ejecución local del servicio.
- Integración completa con Apache Kafka.

Una vez finalizado este flujo, se incorporará un `notification-service` como primer consumidor de eventos.

---

# Estructura del laboratorio

```text
event-driven-orders/

├── docs/
│
├── order-service/
│   ├── Domain
│   ├── Application
│   ├── Ports
│   └── Infrastructure
│
└── notification-service/
    ├── Domain
    ├── Application
    ├── Ports
    └── Infrastructure
```

Cada microservicio se desarrolla de forma independiente respetando los principios de Arquitectura Hexagonal y TDD.

La infraestructura (Kafka, RabbitMQ, Docker, Kubernetes y AWS) se incorpora progresivamente conforme el laboratorio lo requiere.