package src;

import src.model.*;
import src.repository.*;
import src.service.*;
import src.exception.*; // Importamos las excepciones personalizadas

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    // Validaciones sencillas integradas en Main (reintentan hasta entrada válida)
    private static int readIntSimple(Scanner sc, String prompt) {
        while (true) {
            if (prompt != null && !prompt.isEmpty()) System.out.println(prompt);
            String line = sc.nextLine();
            try {
                int val = Integer.parseInt(line.trim());
                if (val < 0) {
                    System.out.println("El número no puede ser negativo. Intente nuevamente.");
                    continue;
                }
                return val;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Ingrese un número entero no negativo.");
            }
        }
    }

    private static long readLongSimple(Scanner sc, String prompt) {
        while (true) {
            if (prompt != null && !prompt.isEmpty()) System.out.println(prompt);
            String line = sc.nextLine();
            try {
                long val = Long.parseLong(line.trim());
                if (val < 0L) {
                    System.out.println("El número no puede ser negativo. Intente nuevamente.");
                    continue;
                }
                return val;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Ingrese un número entero no negativo.");
            }
        }
    }

    private static String readNonEmptySimple(Scanner sc, String prompt) {
        while (true) {
            if (prompt != null && !prompt.isEmpty()) System.out.println(prompt);
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("Entrada vacía. Intente nuevamente.");
        }
    }

    private static String readEmailSimple(Scanner sc, String prompt) {
        while (true) {
            String mail = readNonEmptySimple(sc, prompt);
            if (mail.contains("@") && mail.contains(".")) return mail;
            System.out.println("Email inválido. Debe tener formato usuario@dominio.com. Intente nuevamente.");
        }
    }

    private static Date readDateSimple(Scanner sc, String prompt) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        while (true) {
            if (prompt != null && !prompt.isEmpty()) System.out.println(prompt);
            String line = sc.nextLine().trim();
            try {
                return formatter.parse(line);
            } catch (ParseException e) {
                System.out.println("Fecha inválida. Use el formato dd/MM/yyyy, por ejemplo 25/05/2026.");
            }
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // REPOSITORIES
        RepositorioPaciente repoPaciente = new RepositorioPaciente();
        RepositorioOdontologo repoOdontologo = new RepositorioOdontologo();
        RepositorioTurno repoTurno = new RepositorioTurno();

        // SERVICES
        ServicioPaciente servicioPaciente = new ServicioPaciente(repoPaciente);
        ServicioOdontologo servicioOdontologo = new ServicioOdontologo(repoOdontologo);
        ServicioTurno servicioTurno = new ServicioTurno(repoTurno);

        int opcionPrincipal;

        do {
            System.out.println("\n===== SONRISAS FELICES =====");
            System.out.println("1. Gestionar pacientes");
            System.out.println("2. Gestionar odontologos");
            System.out.println("3. Gestionar turnos");
            System.out.println("0. Salir");

            opcionPrincipal = readIntSimple(scanner, "");

            switch (opcionPrincipal) {

                // MENU PACIENTES
                case 1:
                    int opcionPaciente;
                    do {
                        System.out.println("\n===== MENU PACIENTES =====");
                        System.out.println("1. Registrar paciente particular");
                        System.out.println("2. Registrar paciente obra social");
                        System.out.println("3. Listar pacientes particulares");
                        System.out.println("4. Listar pacientes obra social");
                        System.out.println("5.Pacientes ordenados alfabéticamente");
                        System.out.println("6. Calcular costo consulta");
                        System.out.println("7. Buscar paciente por ID");
                        System.out.println("8. Buscar paciente por DNI");
                        System.out.println("9. Eliminar paciente");
                        System.out.println("0. Volver");

                        opcionPaciente = readIntSimple(scanner, "");

                        switch (opcionPaciente) {

                            // REGISTRAR PARTICULAR
                            case 1:
                                try {
                                    System.out.println("\n--- REGISTRAR PACIENTE PARTICULAR ---");
                                    System.out.println("Ingrese ID:");
                                    int idPaciente = readIntSimple(scanner, "");

                                    String nombrePaciente = readNonEmptySimple(scanner, "Ingrese nombre:");
                                    String apellidoPaciente = readNonEmptySimple(scanner, "Ingrese apellido:");
                                    String dniPaciente = readNonEmptySimple(scanner, "Ingrese DNI:");
                                    String mailPaciente = readEmailSimple(scanner, "Ingrese mail:");
                                    String calle = readNonEmptySimple(scanner, "Ingrese calle:");
                                    int numero = readIntSimple(scanner, "Ingrese numero:");
                                    String localidad = readNonEmptySimple(scanner, "Ingrese localidad:");
                                    String provincia = readNonEmptySimple(scanner, "Ingrese provincia:");

                                    Domicilio domicilio = new Domicilio(calle, numero, localidad, provincia);
                                    Paciente paciente = new PacienteParticular(idPaciente, nombrePaciente, apellidoPaciente, dniPaciente, mailPaciente, new Date(), domicilio);

                                    servicioPaciente.registrarPaciente(paciente);
                                    System.out.println("Paciente particular registrado con éxito.");
                                } catch (DatoInvalidoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // REGISTRAR OBRA SOCIAL
                            case 2:
                                try {
                                    System.out.println("\n--- REGISTRAR PACIENTE OBRA SOCIAL ---");
                                    System.out.println("Ingrese ID:");
                                    int idPacienteOS = readIntSimple(scanner, "");

                                    String nombrePacienteOS = readNonEmptySimple(scanner, "Ingrese nombre:");
                                    String apellidoPacienteOS = readNonEmptySimple(scanner, "Ingrese apellido:");
                                    String dniPacienteOS = readNonEmptySimple(scanner, "Ingrese DNI:");
                                    String mailPacienteOS = readEmailSimple(scanner, "Ingrese mail:");
                                    String calleOS = readNonEmptySimple(scanner, "Ingrese calle:");
                                    int numeroOS = readIntSimple(scanner, "Ingrese numero:");
                                    String localidadOS = readNonEmptySimple(scanner, "Ingrese localidad:");
                                    String provinciaOS = readNonEmptySimple(scanner, "Ingrese provincia:");
                                    String obraSocial = readNonEmptySimple(scanner, "Ingrese nombre obra social:");

                                    Domicilio domicilioOS = new Domicilio(calleOS, numeroOS, localidadOS, provinciaOS);
                                    Paciente pacienteOS = new PacienteObraSocial(idPacienteOS, nombrePacienteOS, apellidoPacienteOS, dniPacienteOS, mailPacienteOS, new Date(), domicilioOS, obraSocial, numeroOS);

                                    servicioPaciente.registrarPaciente(pacienteOS);
                                    System.out.println("Paciente obra social registrado con éxito.");
                                } catch (DatoInvalidoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // LISTAR PARTICULARES
                            case 3:
                                System.out.println("\n--- PACIENTES PARTICULARES ---");
                                for (Paciente p : servicioPaciente.listarPacientes()) {
                                    if (p instanceof PacienteParticular) {
                                        System.out.println(p);
                                    }
                                }
                                break;

                            // LISTAR OBRA SOCIAL
                            case 4:
                                System.out.println("\n--- PACIENTES OBRA SOCIAL ---");
                                for (Paciente p : servicioPaciente.listarPacientes()) {
                                    if (p instanceof PacienteObraSocial) {
                                        System.out.println(p);
                                    }
                                }
                                break;
                            case 5:
                                System.out.println("\n--- PACIENTES ORDENADOS ALFABÉTICAMENTE ---");
                                // Asumiendo que el método listarPacientesOrdenados() está en ServicioPaciente
                                servicioPaciente.listarPacientes().stream()
                                        .sorted() // Esto requiere que Paciente implemente Comparable
                                        .forEach(System.out::println);
                                break;

                            // CALCULAR COSTO
                            case 6:
                                try {
                                    System.out.println("\n--- CALCULAR COSTO CONSULTA ---");
                                    System.out.println("Ingrese ID del paciente:");
                                    long idCosto = readLongSimple(scanner, "");

                                    Paciente pacienteCosto = servicioPaciente.buscarPaciente(idCosto);
                                    double costo = pacienteCosto.calcularCostoConsulta();
                                    System.out.println("Costo consulta: $" + costo);
                                } catch (PacienteNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // BUSCAR PACIENTE POR ID
                            case 7:
                                try {
                                    System.out.println("\n--- BUSCAR PACIENTE ---");
                                    System.out.println("Ingrese ID del paciente:");
                                    long idBusqueda = readLongSimple(scanner, "");

                                    Paciente pacienteBuscado = servicioPaciente.buscarPaciente(idBusqueda);
                                    System.out.println(pacienteBuscado);
                                } catch (PacienteNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // BUSCAR PACIENTE POR DNI
                            case 8:
                                try {
                                    System.out.println("\n--- BUSCAR PACIENTE POR DNI ---");
                                    String dniBusqueda = readNonEmptySimple(scanner, "Ingrese DNI del paciente:");

                                    Paciente pacienteBuscado = servicioPaciente.buscarPorDni(dniBusqueda);
                                    System.out.println(pacienteBuscado);
                                } catch (PacienteNoEncontradoException | DatoInvalidoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // ELIMINAR PACIENTE
                            case 9:
                                try {
                                    System.out.println("\n--- ELIMINAR PACIENTE ---");
                                    System.out.println("Ingrese ID del paciente:");
                                    long idEliminar = readLongSimple(scanner, "");

                                    servicioPaciente.eliminarPaciente(idEliminar);
                                    System.out.println("Paciente eliminado correctamente");
                                } catch (PacienteNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("Opción invalida");
                        }
                    } while (opcionPaciente != 0);
                    break;

                // MENU ODONTOLOGOS
                case 2:
                    int opcionOdontologo;
                    do {
                        System.out.println("\n===== MENU ODONTOLOGOS =====");
                        System.out.println("1. Registrar odontólogo");
                        System.out.println("2. Listar odontólogos");
                        System.out.println("3. Buscar odontólogo por ID");
                        System.out.println("4. Eliminar odontólogo");
                        System.out.println("0. Volver");

                        opcionOdontologo = readIntSimple(scanner, "");

                        switch (opcionOdontologo) {

                            // REGISTRAR ODONTÓLOGO
                            case 1:
                                try {
                                    System.out.println("\n--- REGISTRAR ODONTOLOGO ---");
                                    System.out.println("Ingrese ID:");
                                    int idOdontologo = readIntSimple(scanner, "");

                                    String nombreOdontologo = readNonEmptySimple(scanner, "Ingrese nombre:");
                                    String apellidoOdontologo = readNonEmptySimple(scanner, "Ingrese apellido:");
                                    String mailOdontologo = readEmailSimple(scanner, "Ingrese mail:");
                                    String matricula = readNonEmptySimple(scanner, "Ingrese matricula:");

                                    Odontologo odontologo = new Odontologo(idOdontologo, nombreOdontologo, apellidoOdontologo, mailOdontologo, matricula);
                                    servicioOdontologo.registrarOdontologo(odontologo);
                                    System.out.println("Odontólogo registrado");
                                } catch (DatoInvalidoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // LISTAR ODONTÓLOGOS
                            case 2:
                                System.out.println("\n--- LISTA DE ODONTOLOGOS ---");
                                for (Odontologo o : servicioOdontologo.listarOdontologos()) {
                                    System.out.println(o);
                                }
                                break;

                            // BUSCAR ODONTÓLOGO
                            case 3:
                                try {
                                    System.out.println("\n--- BUSCAR ODONTOLOGO ---");
                                    System.out.println("Ingrese ID del odontólogo:");
                                    long idBusquedaOdo = readLongSimple(scanner, "");

                                    Odontologo odontologoBuscado = servicioOdontologo.buscarOdontologo(idBusquedaOdo);
                                    System.out.println(odontologoBuscado);
                                } catch (OdontologoNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // ELIMINAR ODONTÓLOGO
                            case 4:
                                try {
                                    System.out.println("\n--- ELIMINAR ODONTOLOGO ---");
                                    System.out.println("Ingrese ID del odontólogo:");
                                    long idEliminarOdo = readLongSimple(scanner, "");

                                    servicioOdontologo.eliminarOdontologo(idEliminarOdo);
                                    System.out.println("Odontólogo eliminado");
                                } catch (OdontologoNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("Opción invalida");
                        }
                    } while (opcionOdontologo != 0);
                    break;

                // MENU TURNOS
                case 3:
                    int opcionTurno;
                    do {
                        System.out.println("\n===== MENU TURNOS =====");
                        System.out.println("1. Crear turno");
                        System.out.println("2. Listar turnos");
                        System.out.println("3. Filtrar turnos por paciente y/o odontólogo");
                        System.out.println("4. Filtrar turnos por rango de fechas");
                        System.out.println("5. Cancelar turno");
                        System.out.println("6. Buscar turno por ID");
                        System.out.println("7. Eliminar turno");
                        System.out.println("0. Volver");

                        opcionTurno = readIntSimple(scanner, "");

                        switch (opcionTurno) {

                            // CREAR TURNO
                            case 1:
                                System.out.println("\n--- CREAR TURNO ---");
                                System.out.println("Ingrese ID del paciente:");
                                long idPac = readLongSimple(scanner, "");

                                System.out.println("Ingrese ID del odontólogo:");
                                long idOdo = readLongSimple(scanner, "");

                                try {
                                    Paciente pacienteTurno = servicioPaciente.buscarPaciente(idPac);
                                    Odontologo odontologoTurno = servicioOdontologo.buscarOdontologo(idOdo);

                                    Turno turnoCreado = servicioTurno.crearTurno(pacienteTurno, odontologoTurno, new Date(), new Date(), EstadoTurno.CONFIRMADO);
                                    System.out.println("Turno creado correctamente");
                                    System.out.println("ID del turno: " + turnoCreado.getId());

                                } catch (PacienteNoEncontradoException | OdontologoNoEncontradoException e) {
                                    System.out.println("No se pudo iniciar el turno: " + e.getMessage());
                                } catch (DatoInvalidoException | TurnoYaReservadoException e) {
                                    System.out.println("Error en la reserva: " + e.getMessage());
                                }
                                break;

                            // LISTAR TURNOS
                            case 2:
                                System.out.println("\n--- LISTA DE TURNOS ---");
                                servicioTurno.listarTurnos().forEach(System.out::println);
                                break;

                            // Filtrar por paciente y/o odontólogo
                            case 3:
                                System.out.println("\n--- FILTRAR TURNOS ---");
                                System.out.println("Ingrese ID del paciente para filtrar (0 para omitir):");
                                long idPacienteFiltro = readLongSimple(scanner, "");
                                System.out.println("Ingrese ID del odontólogo para filtrar (0 para omitir):");
                                long idOdontologoFiltro = readLongSimple(scanner, "");

                                if (idPacienteFiltro <= 0 && idOdontologoFiltro <= 0) {
                                    System.out.println("Debe ingresar al menos un ID válido para filtrar.");
                                } else if (idPacienteFiltro > 0 && idOdontologoFiltro > 0) {
                                    servicioTurno.listarTurnos().stream()
                                            .filter(t -> t.getPaciente().getId().equals((int) idPacienteFiltro)
                                                    && t.getOdontologo().getId().equals((int) idOdontologoFiltro))
                                            .forEach(System.out::println);
                                } else if (idPacienteFiltro > 0) {
                                    servicioTurno.filtrarPorPaciente((int) idPacienteFiltro).forEach(System.out::println);
                                } else {
                                    servicioTurno.filtrarPorOdontologo((int) idOdontologoFiltro).forEach(System.out::println);
                                }
                                break;

                            // FILTRAR POR RANGO DE FECHAS
                            case 4:
                                System.out.println("\n--- BUSCAR TURNOS POR RANGO DE FECHAS ---");
                                Date fechaInicio;
                                Date fechaFin;
                                while (true) {
                                    fechaInicio = readDateSimple(scanner, "Ingrese fecha de inicio (dd/MM/yyyy):");
                                    fechaFin = readDateSimple(scanner, "Ingrese fecha fin (dd/MM/yyyy):");

                                    if (fechaFin.before(fechaInicio)) {
                                        System.out.println("La fecha fin no puede ser anterior a la fecha inicio. Intente nuevamente.");
                                    } else {
                                        break;
                                    }
                                }

                                try {
                                    servicioTurno.buscarTurnosPorRango(fechaInicio, fechaFin)
                                            .forEach(System.out::println);
                                } catch (TurnoNoEncontradoException e) {
                                    System.out.println("No se encontraron turnos en ese rango. " + e.getMessage());
                                } catch (DatoInvalidoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;


                            // CANCELAR TURNO
                            case 5:
                                try {
                                    System.out.println("\n--- CANCELAR TURNO ---");
                                    System.out.println("Ingrese ID del turno:");
                                    long idTurno = readLongSimple(scanner, "");

                                    servicioTurno.cancelarTurno(idTurno);
                                    System.out.println("Turno cancelado.");
                                } catch (TurnoNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // BUSCAR TURNO
                            case 6:
                                try {
                                    System.out.println("\n--- BUSCAR TURNO ---");
                                    System.out.println("Ingrese ID del turno:");
                                    long idBusquedaTurno = readLongSimple(scanner, "");

                                    Turno turnoBuscado = servicioTurno.buscarTurno(idBusquedaTurno);
                                    System.out.println(turnoBuscado);
                                } catch (TurnoNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // ELIMINAR TURNO
                            case 7:
                                try {
                                    System.out.println("\n--- ELIMINAR TURNO ---");
                                    System.out.println("Ingrese ID del turno:");
                                    long idEliminarTurno = readLongSimple(scanner, "");

                                    servicioTurno.eliminarTurno(idEliminarTurno);
                                    System.out.println("Turno eliminado");
                                } catch (TurnoNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("Opción invalida");
                        }
                    } while (opcionTurno != 0);
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción invalida");
            }
        } while (opcionPrincipal != 0);

        scanner.close();
    }
}
