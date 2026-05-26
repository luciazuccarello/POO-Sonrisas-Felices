package src.model;

import java.util.Date;

public class PacienteParticular extends src.model.Paciente {

    public PacienteParticular(Integer id, String nombre, String apellido, String dni, String mail, Date fechaAlta, src.model.Domicilio domicilio) {
        super(id, nombre, apellido, dni, mail, fechaAlta, domicilio);
    }

    @Override
    public double calcularCostoConsulta() {
        return 40000;
    }
}
