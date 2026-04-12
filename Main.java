package main;

import model.*;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        //  Crear domicilio
        Domicilio domicilio = new Domicilio( "Av. Siempre Viva", 742, "Springfield", "Buenos Aires");

        //  Crear paciente
        Paciente paciente = new Paciente(1, "Juan", "Perez", "12345678", new Date(), domicilio);

        // ️ Crear odontólogo
        Odontologo odontologo = new Odontologo(1, "Ana", "Gomez", "MAT-123");

        //  Crear recepcionista
        Recepcion recepcionista = new Recepcion("Laura");

        //  Crear turno usando la recepcionista
        Turno turno = recepcionista.crearTurno(paciente, odontologo, new Date(), new Date());

        //  Imprimir todo
        System.out.println("----- DOMICILIO -----");
        System.out.println(domicilio);

        System.out.println("\n----- PACIENTE -----");
        System.out.println(paciente);

        System.out.println("\n----- ODONTOLOGO -----");
        System.out.println(odontologo);

        System.out.println("\n----- TURNO -----");
        System.out.println(turno);

        System.out.println("\n----- RECEPCIONISTA -----");
        System.out.println(recepcionista);

        //  Cancelar turno
        recepcionista.cancelarTurno(turno);
        System.out.println("\n----- TURNO CANCELADO -----");
        System.out.println(turno);

        //  Reprogramar turno
        recepcionista.reprogramarTurno(turno, new Date(), new Date());
        System.out.println("\n----- TURNO REPROGRAMADO -----");
        System.out.println(turno);
    }
}
