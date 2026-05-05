package model;

import java.util.Date;

public class PacienteParticular extends Paciente {

    public PacienteParticular(Integer id, String nombre, String apellido, String dni, String mail, Date fechaAlta, Domicilio domicilio) {
        super(id, nombre, apellido, dni, mail, fechaAlta, domicilio);
    }

    @Override
    public double calcularCostoConsulta() {
        return 40000;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Tipo: Particular\n" +
                "Costo consulta: " + calcularCostoConsulta();
    }
}
