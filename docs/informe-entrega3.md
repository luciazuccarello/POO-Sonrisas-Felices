# Informe Entrega 3 - Sonrisas Felices

## 1. Resumen de la implementacion

En esta entrega se reforzo el sistema en dos ejes principales:

- Manejo robusto de errores con excepciones personalizadas.
- Uso de colecciones y Stream API para busquedas y filtros avanzados.

El objetivo fue mantener una solucion simple y didactica, alineada con el trabajo en clase, pero con mejoras reales de
calidad.

## 2. Excepciones personalizadas

Se utiliza una jerarquia de excepciones basada en la clase `ClinicaException`:

- `DatoInvalidoException`: se lanza cuando faltan datos obligatorios o hay formato invalido.
- `PacienteNoEncontradoException`: se lanza al buscar un paciente inexistente.
- `OdontologoNoEncontradoException`: se lanza al buscar un odontologo inexistente.
- `TurnoYaReservadoException`: se lanza al intentar reservar un horario ocupado.

### Donde se disparan

- En los servicios (`ServicioPaciente`, `ServicioOdontologo`, `ServicioTurno`) durante validaciones de negocio.
- En la interfaz de consola (`Main`) cuando se parsean fechas y datos de entrada.

### Beneficio

El sistema evita errores no controlados y siempre muestra mensajes claros para el usuario final.

## 3. Manejo robusto de errores

Se aplico el siguiente criterio:

- Los servicios validan datos y propagan excepciones con mensajes concretos.
- La UI captura `ClinicaException` y muestra mensajes amigables.
- La lectura de numeros por consola evita `NumberFormatException` no controlada.

Con esto se evita depender de `null` como unica senal de error y se reduce el riesgo de `NullPointerException` en los
flujos principales.

## 4. Colecciones avanzadas aplicadas

Se incorporaron funcionalidades con Stream API y comparadores:

- Busqueda de turnos por rango de fechas (`filter`).
- Filtro de turnos por odontologo y por paciente (`filter`, `collect`).
- Busqueda de paciente por DNI (`filter`, `findFirst`, `orElseThrow`).
- Listado de pacientes ordenado alfabeticamente por apellido (`sorted` con `Comparator`).

Estas operaciones permiten expresar reglas de negocio de forma clara y mantenible.

## 5. Diagramas de secuencia

Se incluyen dos diagramas en `docs/diagramas-secuencia.md`:

- Alta de nuevo turno (con validaciones y excepciones).
- Busqueda de paciente por DNI (exito y excepcion).

Ambos muestran la interaccion entre UI, servicios y repositorios.

## 6. Conclusiones

La entrega 3 mejora notablemente la robustez del sistema:

- Los errores se manejan de forma controlada.
- El usuario recibe mensajes claros.
- Se amplian las capacidades de consulta con colecciones avanzadas.

El resultado mantiene una complejidad adecuada para una materia inicial de POO, pero incorpora practicas propias de
aplicaciones profesionales.
