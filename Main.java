package src;
import src.model.*;

import java.util.Date;
public class Main {
    public static void main(String[] args) {

        //  Crear domicilio
        Domicilio domicilio = new Domicilio( "Av. Avellaneda", 742, "Caballito", "Buenos Aires");

        //  Crear paciente
        Paciente paciente = new Paciente(1, "Juan", "Perez", "12345678", "juanperez@gmail.com" ,new Date(), domicilio);

        // ️ Crear odontólogo
        Odontologo odontologo = new Odontologo(1, "Ana", "Gomez", "anagomez@gmail.com", "MAT-123");

        //  Crear recepcionista
        Recepcion recepcionista = new Recepcion("Laura", "Martinez", "sonrisasfelices@gmail.com");

        //  Crear turno usando la recepcionista
        Turno t1 = recepcionista.crearTurno(paciente, odontologo, new Date(), new Date(), EstadoTurno.CONFIRMADO);

        //  Imprimir
        System.out.println("\n----- DOMICILIO -----");
        System.out.println(domicilio);

        System.out.println("\n----- PACIENTE -----");
        System.out.println(paciente);

        System.out.println("\n----- ODONTOLOGO -----");
        System.out.println(odontologo);

        System.out.println("\n----- TURNO -----");
        System.out.println(t1);

        System.out.println("\n----- RECEPCIONISTA -----");
        System.out.println(recepcionista);

        //  Cancelar turno
        recepcionista.cancelarTurno(t1);
        System.out.println("\n----- TURNO CANCELADO -----");
        System.out.println(t1);

        //  Reprogramar turno
        //Guardo fecha anterior
        Date fechaAnterior = t1.getFecha();
        //Crear nueva fecha
        Date nuevaFecha = new Date(System.currentTimeMillis() + 86400000); // +1 dìa
        Date nuevaHora = nuevaFecha;
        recepcionista.reprogramarTurno(t1, nuevaFecha, nuevaHora);
        System.out.println ("\n----- TURNO REPROGRAMADO -----");
        System.out.println("ID turno: " + t1.getId());
        System.out.println("Turno anterior: " + fechaAnterior);
        System.out.println("Nuevo turno: " + t1.getFecha());
        System.out.println("Estado: " + t1.getEstado());
    }
}
