package src;

import src.model.*;
import src.repository.*;
import src.service.*;
import src.exception.*; // Importamos las excepciones personalizadas

import java.util.Date;
import java.util.Scanner;

public class Main {

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

            opcionPrincipal = scanner.nextInt();
            scanner.nextLine();

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
                        System.out.println("5. Calcular costo consulta");
                        System.out.println("6. Buscar paciente por ID");
                        System.out.println("7. Eliminar paciente");
                        System.out.println("0. Volver");

                        opcionPaciente = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionPaciente) {

                            // REGISTRAR PARTICULAR
                            case 1:
                                try {
                                    System.out.println("\n--- REGISTRAR PACIENTE PARTICULAR ---");
                                    System.out.println("Ingrese ID:");
                                    Integer idPaciente = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Ingrese nombre:");
                                    String nombrePaciente = scanner.nextLine();

                                    System.out.println("Ingrese apellido:");
                                    String apellidoPaciente = scanner.nextLine();

                                    System.out.println("Ingrese DNI:");
                                    String dniPaciente = scanner.nextLine();

                                    System.out.println("Ingrese mail:");
                                    String mailPaciente = scanner.nextLine();

                                    System.out.println("Ingrese calle:");
                                    String calle = scanner.nextLine();

                                    System.out.println("Ingrese numero:");
                                    int numero = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Ingrese localidad:");
                                    String localidad = scanner.nextLine();

                                    System.out.println("Ingrese provincia:");
                                    String provincia = scanner.nextLine();

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
                                    Integer idPacienteOS = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Ingrese nombre:");
                                    String nombrePacienteOS = scanner.nextLine();

                                    System.out.println("Ingrese apellido:");
                                    String apellidoPacienteOS = scanner.nextLine();

                                    System.out.println("Ingrese DNI:");
                                    String dniPacienteOS = scanner.nextLine();

                                    System.out.println("Ingrese mail:");
                                    String mailPacienteOS = scanner.nextLine();

                                    System.out.println("Ingrese calle:");
                                    String calleOS = scanner.nextLine();

                                    System.out.println("Ingrese numero:");
                                    int numeroOS = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Ingrese localidad:");
                                    String localidadOS = scanner.nextLine();

                                    System.out.println("Ingrese provincia:");
                                    String provinciaOS = scanner.nextLine();

                                    System.out.println("Ingrese nombre obra social:");
                                    String obraSocial = scanner.nextLine();

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

                            // CALCULAR COSTO
                            case 5:
                                try {
                                    System.out.println("\n--- CALCULAR COSTO CONSULTA ---");
                                    System.out.println("Ingrese ID del paciente:");
                                    Long idCosto = scanner.nextLong();

                                    Paciente pacienteCosto = servicioPaciente.buscarPaciente(idCosto);
                                    double costo = pacienteCosto.calcularCostoConsulta();
                                    System.out.println("Costo consulta: $" + costo);
                                } catch (PacienteNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // BUSCAR PACIENTE POR ID
                            case 6:
                                try {
                                    System.out.println("\n--- BUSCAR PACIENTE ---");
                                    System.out.println("Ingrese ID del paciente:");
                                    Long idBusqueda = scanner.nextLong();

                                    Paciente pacienteBuscado = servicioPaciente.buscarPaciente(idBusqueda);
                                    System.out.println(pacienteBuscado);
                                } catch (PacienteNoEncontradoException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;

                            // ELIMINAR PACIENTE
                            case 7:
                                try {
                                    System.out.println("\n--- ELIMINAR PACIENTE ---");
                                    System.out.println("Ingrese ID del paciente:");
                                    Long idEliminar = scanner.nextLong();

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

                        opcionOdontologo = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionOdontologo) {

                            // REGISTRAR ODONTÓLOGO
                            case 1:
                                try {
                                    System.out.println("\n--- REGISTRAR ODONTOLOGO ---");
                                    System.out.println("Ingrese ID:");
                                    Integer idOdontologo = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("Ingrese nombre:");
                                    String nombreOdontologo = scanner.nextLine();

                                    System.out.println("Ingrese apellido:");
                                    String apellidoOdontologo = scanner.nextLine();

                                    System.out.println("Ingrese mail:");
                                    String mailOdontologo = scanner.nextLine();

                                    System.out.println("Ingrese matricula:");
                                    String matricula = scanner.nextLine();

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
                                    Long idBusquedaOdo = scanner.nextLong();

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
                                    Long idEliminarOdo = scanner.nextLong();

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
                        System.out.println("3. Cancelar turno");
                        System.out.println("4. Buscar turno por ID");
                        System.out.println("5. Eliminar turno");
                        System.out.println("0. Volver");

                        opcionTurno = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionTurno) {

                            // CREAR TURNO
                            case 1:
                                System.out.println("\n--- CREAR TURNO ---");
                                System.out.println("Ingrese ID del paciente:");
                                Long idPac = scanner.nextLong();

                                System.out.println("Ingrese ID del odontólogo:");
                                Long idOdo = scanner.nextLong();
                                scanner.nextLine();

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
                                for (Turno t : servicioTurno.listarTurnos()) {
                                    System.out.println(t);
                                }
                                break;

                            // CANCELAR TURNO
                            case 3:
                                System.out.println("\n--- CANCELAR TURNO ---");
                                System.out.println("Ingrese ID del turno:");
                                Long idTurno = scanner.nextLong();

                                servicioTurno.cancelarTurno(idTurno);
                                System.out.println("Turno procesado.");
                                break;

                            // BUSCAR TURNO
                            case 4:
                                System.out.println("\n--- BUSCAR TURNO ---");
                                System.out.println("Ingrese ID del turno:");
                                Long idBusquedaTurno = scanner.nextLong();

                                Turno turnoBuscado = servicioTurno.buscarTurno(idBusquedaTurno);
                                if (turnoBuscado != null) {
                                    System.out.println(turnoBuscado);
                                } else {
                                    System.out.println("Turno no encontrado");
                                }
                                break;

                            // ELIMINAR TURNO
                            case 5:
                                System.out.println("\n--- ELIMINAR TURNO ---");
                                System.out.println("Ingrese ID del turno:");
                                Long idEliminarTurno = scanner.nextLong();

                                Turno turnoEliminar = servicioTurno.buscarTurno(idEliminarTurno);
                                if (turnoEliminar != null) {
                                    servicioTurno.eliminarTurno(idEliminarTurno);
                                    System.out.println("Turno eliminado");
                                } else {
                                    System.out.println("Turno no encontrado");
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
