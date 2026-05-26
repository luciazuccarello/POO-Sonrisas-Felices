package model.src;

import src.exception.ClinicaException;
import src.model.*;
import src.repository.RepositorioOdontologo;
import src.repository.RepositorioPaciente;
import src.repository.RepositorioTurno;
import src.service.ServicioOdontologo;
import src.service.ServicioPaciente;
import src.service.ServicioTurno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final SimpleDateFormat FORMATO_FECHA_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    static {
        FORMATO_FECHA_HORA.setLenient(false);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        RepositorioPaciente repoPaciente = new RepositorioPaciente();
        RepositorioOdontologo repoOdontologo = new RepositorioOdontologo();
        RepositorioTurno repoTurno = new RepositorioTurno();

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

            opcionPrincipal = leerEntero(scanner, "Seleccione una opcion: ");

            switch (opcionPrincipal) {
                case 1:
                    menuPacientes(scanner, servicioPaciente);
                    break;
                case 2:
                    menuOdontologos(scanner, servicioOdontologo);
                    break;
                case 3:
                    menuTurnos(scanner, servicioPaciente, servicioOdontologo, servicioTurno);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcionPrincipal != 0);

        scanner.close();
    }

    private static void menuPacientes(Scanner scanner, ServicioPaciente servicioPaciente) {
        int opcionPaciente;
        do {
            System.out.println("\n===== MENU PACIENTES =====");
            System.out.println("1. Registrar paciente particular");
            System.out.println("2. Registrar paciente obra social");
            System.out.println("3. Listar pacientes particulares");
            System.out.println("4. Listar pacientes obra social");
            System.out.println("5. Calcular costo consulta");
            System.out.println("6. Buscar paciente por ID");
            System.out.println("7. Buscar paciente por DNI");
            System.out.println("8. Listar pacientes ordenados por apellido");
            System.out.println("9. Eliminar paciente");
            System.out.println("0. Volver");

            opcionPaciente = leerEntero(scanner, "Seleccione una opcion: ");

            try {
                switch (opcionPaciente) {
                    case 1:
                        registrarPacienteParticular(scanner, servicioPaciente);
                        break;
                    case 2:
                        registrarPacienteObraSocial(scanner, servicioPaciente);
                        break;
                    case 3:
                        listarPacientesPorTipo(servicioPaciente.listarPacientes(), PacienteParticular.class);
                        break;
                    case 4:
                        listarPacientesPorTipo(servicioPaciente.listarPacientes(), PacienteObraSocial.class);
                        break;
                    case 5:
                        calcularCostoConsulta(scanner, servicioPaciente);
                        break;
                    case 6:
                        buscarPacientePorId(scanner, servicioPaciente);
                        break;
                    case 7:
                        buscarPacientePorDni(scanner, servicioPaciente);
                        break;
                    case 8:
                        listarPacientesOrdenados(servicioPaciente);
                        break;
                    case 9:
                        eliminarPaciente(scanner, servicioPaciente);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (ClinicaException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcionPaciente != 0);
    }

    private static void menuOdontologos(Scanner scanner, ServicioOdontologo servicioOdontologo) {
        int opcionOdontologo;
        do {
            System.out.println("\n===== MENU ODONTOLOGOS =====");
            System.out.println("1. Registrar odontologo");
            System.out.println("2. Listar odontologos");
            System.out.println("3. Buscar odontologo por ID");
            System.out.println("4. Eliminar odontologo");
            System.out.println("0. Volver");

            opcionOdontologo = leerEntero(scanner, "Seleccione una opcion: ");

            try {
                switch (opcionOdontologo) {
                    case 1:
                        registrarOdontologo(scanner, servicioOdontologo);
                        break;
                    case 2:
                        for (Odontologo odontologo : servicioOdontologo.listarOdontologos()) {
                            System.out.println(odontologo);
                        }
                        break;
                    case 3:
                        Long idBusqueda = leerLong(scanner, "Ingrese ID del odontologo: ");
                        System.out.println(servicioOdontologo.buscarOdontologo(idBusqueda));
                        break;
                    case 4:
                        Long idEliminar = leerLong(scanner, "Ingrese ID del odontologo: ");
                        servicioOdontologo.eliminarOdontologo(idEliminar);
                        System.out.println("Odontologo eliminado correctamente.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (ClinicaException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcionOdontologo != 0);
    }

    private static void menuTurnos(Scanner scanner, ServicioPaciente servicioPaciente,
                                   ServicioOdontologo servicioOdontologo, ServicioTurno servicioTurno) {
        int opcionTurno;
        do {
            System.out.println("\n===== MENU TURNOS =====");
            System.out.println("1. Crear turno");
            System.out.println("2. Listar turnos");
            System.out.println("3. Cancelar turno");
            System.out.println("4. Buscar turno por ID");
            System.out.println("5. Eliminar turno");
            System.out.println("6. Buscar turnos por rango de fechas");
            System.out.println("7. Filtrar turnos por odontologo");
            System.out.println("8. Filtrar turnos por paciente");
            System.out.println("0. Volver");

            opcionTurno = leerEntero(scanner, "Seleccione una opcion: ");

            try {
                switch (opcionTurno) {
                    case 1:
                        crearTurno(scanner, servicioPaciente, servicioOdontologo, servicioTurno);
                        break;
                    case 2:
                        imprimirTurnos(servicioTurno.listarTurnos());
                        break;
                    case 3:
                        Long idCancelar = leerLong(scanner, "Ingrese ID del turno: ");
                        servicioTurno.cancelarTurno(idCancelar);
                        System.out.println("Turno cancelado correctamente.");
                        break;
                    case 4:
                        Long idBuscar = leerLong(scanner, "Ingrese ID del turno: ");
                        System.out.println(servicioTurno.buscarTurno(idBuscar));
                        break;
                    case 5:
                        Long idEliminar = leerLong(scanner, "Ingrese ID del turno: ");
                        servicioTurno.eliminarTurno(idEliminar);
                        System.out.println("Turno eliminado correctamente.");
                        break;
                    case 6:
                        buscarTurnosPorRango(scanner, servicioTurno);
                        break;
                    case 7:
                        Integer idOdontologo = leerEntero(scanner, "Ingrese ID del odontologo: ");
                        imprimirTurnos(servicioTurno.filtrarPorOdontologo(idOdontologo));
                        break;
                    case 8:
                        Integer idPaciente = leerEntero(scanner, "Ingrese ID del paciente: ");
                        imprimirTurnos(servicioTurno.filtrarPorPaciente(idPaciente));
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (ClinicaException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcionTurno != 0);
    }

    private static void registrarPacienteParticular(Scanner scanner, ServicioPaciente servicioPaciente)
            throws ClinicaException {

        Integer id = leerEntero(scanner, "Ingrese ID: ");
        String nombre = leerTexto(scanner, "Ingrese nombre: ");
        String apellido = leerTexto(scanner, "Ingrese apellido: ");
        String dni = leerTexto(scanner, "Ingrese DNI: ");
        String mail = leerTexto(scanner, "Ingrese mail: ");
        Domicilio domicilio = leerDomicilio(scanner);

        Paciente paciente = new PacienteParticular(id, nombre, apellido, dni, mail, new Date(), domicilio);
        servicioPaciente.registrarPaciente(paciente);
        System.out.println("Paciente particular registrado con exito.");
    }

    private static void registrarPacienteObraSocial(Scanner scanner, ServicioPaciente servicioPaciente)
            throws ClinicaException {

        Integer id = leerEntero(scanner, "Ingrese ID: ");
        String nombre = leerTexto(scanner, "Ingrese nombre: ");
        String apellido = leerTexto(scanner, "Ingrese apellido: ");
        String dni = leerTexto(scanner, "Ingrese DNI: ");
        String mail = leerTexto(scanner, "Ingrese mail: ");
        Domicilio domicilio = leerDomicilio(scanner);
        String obraSocial = leerTexto(scanner, "Ingrese nombre de obra social: ");
        Integer numeroAfiliado = leerEntero(scanner, "Ingrese numero de afiliado: ");

        Paciente paciente = new PacienteObraSocial(id, nombre, apellido, dni, mail, new Date(), domicilio,
                obraSocial, numeroAfiliado);
        servicioPaciente.registrarPaciente(paciente);
        System.out.println("Paciente de obra social registrado con exito.");
    }

    private static void registrarOdontologo(Scanner scanner, ServicioOdontologo servicioOdontologo)
            throws ClinicaException {

        Integer id = leerEntero(scanner, "Ingrese ID: ");
        String nombre = leerTexto(scanner, "Ingrese nombre: ");
        String apellido = leerTexto(scanner, "Ingrese apellido: ");
        String mail = leerTexto(scanner, "Ingrese mail: ");
        String matricula = leerTexto(scanner, "Ingrese matricula: ");

        Odontologo odontologo = new Odontologo(id, nombre, apellido, mail, matricula);
        servicioOdontologo.registrarOdontologo(odontologo);
        System.out.println("Odontologo registrado con exito.");
    }

    private static void crearTurno(Scanner scanner, ServicioPaciente servicioPaciente,
                                   ServicioOdontologo servicioOdontologo, ServicioTurno servicioTurno)
            throws ClinicaException {

        Long idPaciente = leerLong(scanner, "Ingrese ID del paciente: ");
        Long idOdontologo = leerLong(scanner, "Ingrese ID del odontologo: ");
        Date fechaHora = leerFechaHora(scanner, "Ingrese fecha y hora (dd/MM/yyyy HH:mm): ");

        Paciente paciente = servicioPaciente.buscarPaciente(idPaciente);
        Odontologo odontologo = servicioOdontologo.buscarOdontologo(idOdontologo);

        Turno turno = servicioTurno.crearTurno(paciente, odontologo, fechaHora, fechaHora, EstadoTurno.CONFIRMADO);
        System.out.println("Turno creado correctamente. ID: " + turno.getId());
    }

    private static void buscarTurnosPorRango(Scanner scanner, ServicioTurno servicioTurno)
            throws ClinicaException {

        Date fechaInicio = leerFechaHora(scanner, "Ingrese fecha/hora de inicio (dd/MM/yyyy HH:mm): ");
        Date fechaFin = leerFechaHora(scanner, "Ingrese fecha/hora de fin (dd/MM/yyyy HH:mm): ");

        if (fechaInicio.after(fechaFin)) {
            throw new ClinicaException("La fecha inicial no puede ser mayor a la final.");
        }

        imprimirTurnos(servicioTurno.buscarTurnosPorRango(fechaInicio, fechaFin));
    }

    private static void buscarPacientePorId(Scanner scanner, ServicioPaciente servicioPaciente)
            throws ClinicaException {

        Long id = leerLong(scanner, "Ingrese ID del paciente: ");
        System.out.println(servicioPaciente.buscarPaciente(id));
    }

    private static void buscarPacientePorDni(Scanner scanner, ServicioPaciente servicioPaciente)
            throws ClinicaException {

        String dni = leerTexto(scanner, "Ingrese DNI del paciente: ");
        System.out.println(servicioPaciente.buscarPorDni(dni));
    }

    private static void calcularCostoConsulta(Scanner scanner, ServicioPaciente servicioPaciente)
            throws ClinicaException {

        Long idPaciente = leerLong(scanner, "Ingrese ID del paciente: ");
        Paciente paciente = servicioPaciente.buscarPaciente(idPaciente);
        double costo = paciente.calcularCostoConsulta();
        System.out.println("Costo de consulta: $" + costo);
    }

    private static void listarPacientesOrdenados(ServicioPaciente servicioPaciente) {
        List<Paciente> pacientesOrdenados = servicioPaciente.listarPacientesOrdenadosPorApellido();
        if (pacientesOrdenados.isEmpty()) {
            System.out.println("No hay pacientes cargados.");
            return;
        }
        for (Paciente paciente : pacientesOrdenados) {
            System.out.println(paciente);
        }
    }

    private static void eliminarPaciente(Scanner scanner, ServicioPaciente servicioPaciente)
            throws ClinicaException {

        Long id = leerLong(scanner, "Ingrese ID del paciente a eliminar: ");
        servicioPaciente.eliminarPaciente(id);
        System.out.println("Paciente eliminado correctamente.");
    }

    private static void listarPacientesPorTipo(List<Paciente> pacientes, Class<?> tipo) {
        boolean hayResultados = false;
        for (Paciente paciente : pacientes) {
            if (tipo.isInstance(paciente)) {
                System.out.println(paciente);
                hayResultados = true;
            }
        }
        if (!hayResultados) {
            System.out.println("No hay pacientes para el tipo seleccionado.");
        }
    }

    private static void imprimirTurnos(List<Turno> turnos) {
        if (turnos.isEmpty()) {
            System.out.println("No se encontraron turnos.");
            return;
        }
        for (Turno turno : turnos) {
            System.out.println(turno);
        }
    }

    private static Domicilio leerDomicilio(Scanner scanner) {
        String calle = leerTexto(scanner, "Ingrese calle: ");
        Integer numero = leerEntero(scanner, "Ingrese numero: ");
        String localidad = leerTexto(scanner, "Ingrese localidad: ");
        String provincia = leerTexto(scanner, "Ingrese provincia: ");
        return new Domicilio(calle, numero, localidad, provincia);
    }

    private static Date leerFechaHora(Scanner scanner, String mensaje) throws ClinicaException {
        String texto = leerTexto(scanner, mensaje);
        try {
            return FORMATO_FECHA_HORA.parse(texto);
        } catch (ParseException e) {
            throw new ClinicaException("Formato de fecha/hora invalido. Use dd/MM/yyyy HH:mm.");
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero valido.");
            }
        }
    }

    private static Long leerLong(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                return Long.parseLong(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            if (!texto.trim().isEmpty()) {
                return texto.trim();
            }
            System.out.println("El valor no puede estar vacio.");
        }
    }
}
