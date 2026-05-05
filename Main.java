package model;

import model.repository.RepositorioOdontologo;
import model.repository.RepositorioPaciente;
import model.repository.RepositorioTurno;
import model.service.ServicioOdontologo;
import model.service.ServicioPaciente;
import model.service.ServicioTurno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final ServicioPaciente servicioPaciente =
            new ServicioPaciente(new RepositorioPaciente());
    private static final ServicioOdontologo servicioOdontologo =
            new ServicioOdontologo(new RepositorioOdontologo());
    private static final ServicioTurno servicioTurno =
            new ServicioTurno(new RepositorioTurno(), servicioPaciente, servicioOdontologo);

    public static void main(String[] args) {
        FORMATO_FECHA.setLenient(false);
        cargarDatosIniciales();
        ejecutarMenuPrincipal();
    }

    private static void ejecutarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== SONRISAS FELICES ===");
            System.out.println("1. Pacientes");
            System.out.println("2. Odontologos");
            System.out.println("3. Turnos");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    menuPacientes();
                    break;
                case 2:
                    menuOdontologos();
                    break;
                case 3:
                    menuTurnos();
                    break;
                case 0:
                    System.out.println("Sistema finalizado.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        } while (opcion != 0);
    }

    private static void menuPacientes() {
        int opcion;
        do {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        servicioPaciente.registrarPaciente(leerPaciente());
                        System.out.println("Paciente registrado.");
                        break;
                    case 2:
                        imprimirLista(servicioPaciente.listarPacientes());
                        break;
                    case 3:
                        imprimirObjeto(servicioPaciente.buscarPaciente(leerLong("ID paciente: ")));
                        break;
                    case 4:
                        servicioPaciente.actualizarPaciente(leerPacienteConIdExistente());
                        System.out.println("Paciente actualizado.");
                        break;
                    case 5:
                        servicioPaciente.eliminarPaciente(leerLong("ID paciente: "));
                        System.out.println("Paciente eliminado.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void menuOdontologos() {
        int opcion;
        do {
            System.out.println("\n--- ODONTOLOGOS ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        servicioOdontologo.registrarOdontologo(leerOdontologo());
                        System.out.println("Odontologo registrado.");
                        break;
                    case 2:
                        imprimirLista(servicioOdontologo.listarOdontologos());
                        break;
                    case 3:
                        imprimirObjeto(servicioOdontologo.buscarOdontologo(leerLong("ID odontologo: ")));
                        break;
                    case 4:
                        servicioOdontologo.actualizarOdontologo(leerOdontologo());
                        System.out.println("Odontologo actualizado.");
                        break;
                    case 5:
                        servicioOdontologo.eliminarOdontologo(leerLong("ID odontologo: "));
                        System.out.println("Odontologo eliminado.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void menuTurnos() {
        int opcion;
        do {
            System.out.println("\n--- TURNOS ---");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("6. Cancelar");
            System.out.println("7. Reprogramar");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1:
                        Turno turno = crearTurnoDesdeConsola();
                        System.out.println("Turno creado con ID " + turno.getId());
                        break;
                    case 2:
                        imprimirLista(servicioTurno.listarTurnos());
                        break;
                    case 3:
                        imprimirObjeto(servicioTurno.buscarTurno(leerLong("ID turno: ")));
                        break;
                    case 4:
                        actualizarTurnoDesdeConsola();
                        System.out.println("Turno actualizado.");
                        break;
                    case 5:
                        servicioTurno.eliminarTurno(leerLong("ID turno: "));
                        System.out.println("Turno eliminado.");
                        break;
                    case 6:
                        servicioTurno.cancelarTurno(leerLong("ID turno: "));
                        System.out.println("Turno cancelado.");
                        break;
                    case 7:
                        reprogramarTurnoDesdeConsola();
                        System.out.println("Turno reprogramado.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static Paciente leerPacienteConIdExistente() {
        Long id = leerLong("ID paciente a actualizar: ");
        if (servicioPaciente.buscarPaciente(id) == null) {
            throw new IllegalArgumentException("No existe un paciente con ID " + id);
        }
        return leerPaciente(id);
    }

    private static Paciente leerPaciente() {
        return leerPaciente(null);
    }

    private static Paciente leerPaciente(Long idExistente) {
        Integer id = idExistente == null
                ? leerEntero("ID: ")
                : idExistente.intValue();
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String dni = leerTexto("DNI: ");
        String mail = leerTexto("Mail: ");
        Domicilio domicilio = leerDomicilio();

        System.out.println("Tipo de paciente:");
        System.out.println("1. Particular");
        System.out.println("2. Obra social");
        int tipo = leerEntero("Seleccione tipo: ");

        if (tipo == 2) {
            String obraSocial = leerTexto("Nombre obra social: ");
            int numeroAfiliado = leerEntero("Numero afiliado: ");
            return new PacienteObraSocial(id, nombre, apellido, dni, mail, new Date(), domicilio, obraSocial, numeroAfiliado);
        }
        return new PacienteParticular(id, nombre, apellido, dni, mail, new Date(), domicilio);
    }

    private static Domicilio leerDomicilio() {
        System.out.println("Domicilio");
        String calle = leerTexto("Calle: ");
        int numero = leerEntero("Numero: ");
        String localidad = leerTexto("Localidad: ");
        String provincia = leerTexto("Provincia: ");
        return new Domicilio(calle, numero, localidad, provincia);
    }

    private static Odontologo leerOdontologo() {
        Integer id = leerEntero("ID: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String mail = leerTexto("Mail: ");
        String matricula = leerTexto("Matricula: ");
        return new Odontologo(id, nombre, apellido, mail, matricula);
    }

    private static Turno crearTurnoDesdeConsola() {
        Long idPaciente = leerLong("ID paciente: ");
        Long idOdontologo = leerLong("ID odontologo: ");
        Date fecha = leerFecha("Fecha y hora (yyyy-MM-dd HH:mm): ");
        return servicioTurno.crearTurno(idPaciente, idOdontologo, fecha, fecha, EstadoTurno.PENDIENTE);
    }

    private static void actualizarTurnoDesdeConsola() {
        Long idTurno = leerLong("ID turno: ");
        Long idPaciente = leerLong("Nuevo ID paciente: ");
        Long idOdontologo = leerLong("Nuevo ID odontologo: ");
        Date fecha = leerFecha("Nueva fecha y hora (yyyy-MM-dd HH:mm): ");
        EstadoTurno estado = leerEstadoTurno();
        servicioTurno.actualizarTurno(idTurno, idPaciente, idOdontologo, fecha, fecha, estado);
    }

    private static void reprogramarTurnoDesdeConsola() {
        Long idTurno = leerLong("ID turno: ");
        Date fecha = leerFecha("Nueva fecha y hora (yyyy-MM-dd HH:mm): ");
        servicioTurno.reprogramarTurno(idTurno, fecha, fecha);
    }

    private static EstadoTurno leerEstadoTurno() {
        System.out.println("Estados disponibles:");
        EstadoTurno[] estados = EstadoTurno.values();
        for (int i = 0; i < estados.length; i++) {
            System.out.println((i + 1) + ". " + estados[i]);
        }
        int opcion = leerEntero("Seleccione estado: ");
        if (opcion < 1 || opcion > estados.length) {
            throw new IllegalArgumentException("Estado invalido");
        }
        return estados[opcion - 1];
    }

    private static String leerTexto(String mensaje) {
        String valor;
        do {
            System.out.print(mensaje);
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("El valor es obligatorio.");
            }
        } while (valor.isEmpty());
        return valor;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static Long leerLong(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un ID numerico valido.");
            }
        }
    }

    private static Date leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return FORMATO_FECHA.parse(scanner.nextLine().trim());
            } catch (ParseException e) {
                System.out.println("Formato invalido. Use yyyy-MM-dd HH:mm.");
            }
        }
    }

    private static void imprimirObjeto(Object objeto) {
        if (objeto == null) {
            System.out.println("No se encontraron resultados.");
            return;
        }
        System.out.println(objeto);
    }

    private static void imprimirLista(List<?> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }
        for (Object objeto : lista) {
            System.out.println("--------------------");
            System.out.println(objeto);
        }
    }

    private static void cargarDatosIniciales() {
        Domicilio domicilio = new Domicilio("Av. Avellaneda", 742, "Caballito", "Buenos Aires");
        servicioPaciente.registrarPaciente(new PacienteParticular(
                1, "Juan", "Perez", "12345678", "juanperez@gmail.com", new Date(), domicilio));
        servicioOdontologo.registrarOdontologo(new Odontologo(
                1, "Ana", "Gomez", "anagomez@gmail.com", "MAT-123"));
    }
}
