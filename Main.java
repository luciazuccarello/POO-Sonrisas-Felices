package src;

import src.model.*;
import src.repository.*;
import src.service.*;

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

        int opcion;

        do {

            System.out.println("\n===== SONRISAS FELICES =====");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Listar pacientes");
            System.out.println("3. Registrar odontologo");
            System.out.println("4. Listar odontologos");
            System.out.println("5. Crear turno");
            System.out.println("6. Listar turnos");
            System.out.println("7. Cancelar turno");
            System.out.println("0. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:

                    System.out.println("\n--- REGISTRAR PACIENTE ---");

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

                    Domicilio domicilio = new Domicilio(
                            calle,
                            numero,
                            localidad,
                            provincia
                    );

                    Paciente paciente = new PacienteParticular(
                            idPaciente,
                            nombrePaciente,
                            apellidoPaciente,
                            dniPaciente,
                            mailPaciente,
                            new Date(),
                            domicilio
                    );

                    servicioPaciente.registrarPaciente(paciente);

                    System.out.println("Paciente registrado correctamente");

                    break;

                case 2:

                    System.out.println("\n--- LISTA DE PACIENTES ---");

                    for (Paciente p : servicioPaciente.listarPacientes()) {
                        System.out.println(p);
                    }

                    break;

                case 3:

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

                    Odontologo odontologo = new Odontologo(
                            idOdontologo,
                            nombreOdontologo,
                            apellidoOdontologo,
                            mailOdontologo,
                            matricula
                    );

                    servicioOdontologo.registrarOdontologo(odontologo);

                    System.out.println("Odontologo registrado correctamente");

                    break;

                case 4:

                    System.out.println("\n--- LISTA DE ODONTOLOGOS ---");

                    for (Odontologo o : servicioOdontologo.listarOdontologos()) {
                        System.out.println(o);
                    }

                    break;

                case 5:

                    System.out.println("\n--- CREAR TURNO ---");

                    System.out.println("Ingrese ID del paciente:");
                    Long idPac = scanner.nextLong();

                    System.out.println("Ingrese ID del odontologo:");
                    Long idOdo = scanner.nextLong();

                    scanner.nextLine();

                    Paciente pacienteTurno = servicioPaciente.buscarPaciente(idPac);
                    Odontologo odontologoTurno = servicioOdontologo.buscarOdontologo(idOdo);

                    if (pacienteTurno == null || odontologoTurno == null) {

                        System.out.println("Paciente u odontologo inexistente");

                    } else {

                        servicioTurno.crearTurno(
                                pacienteTurno,
                                odontologoTurno,
                                new Date(),
                                new Date(),
                                EstadoTurno.CONFIRMADO
                        );

                        System.out.println("Turno creado correctamente");
                    }

                    break;

                case 6:

                    System.out.println("\n--- LISTA DE TURNOS ---");

                    for (Turno t : servicioTurno.listarTurnos()) {
                        System.out.println(t);
                    }

                    break;

                case 7:

                    System.out.println("\n--- CANCELAR TURNO ---");

                    System.out.println("Ingrese ID del turno:");
                    Long idTurno = scanner.nextLong();

                    servicioTurno.cancelarTurno(idTurno);

                    System.out.println("Turno cancelado");

                    break;

                case 8:
                    System.out.println("\n--- CALCULAR COSTO CONSULTA ---");
                    System.out.println("Ingrese ID del paciente: ");
                    Long idCosto = scanner.nextLong();

                    Paciente pacienteCosto = servicioPaciente.buscarPaciente(idCosto);

                    if (pacienteCosto != null) {
                        double costo = pacienteCosto.calcularCostoConsulta();
                        System.out.println("Costo de la consulta: $"+ costo);
                    } else {
                        System.out.println("Paciente no encontrado");
                    }
                    break;

                case 0:

                    System.out.println("Saliendo del sistema...");
                    break;

                default:

                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);

        scanner.close();
    }
}
