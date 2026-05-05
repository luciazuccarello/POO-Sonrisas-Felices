package src.model;

import java.util.Date;
public class PacienteObraSocial extends Paciente {
    private String nombreObraSocial;
    private int numeroAfiliado;

    public PacienteObraSocial (Integer id, String nombre, String apellido, String dni, String mail, Date fechaAlta, Domicilio domicilio, String nombreObraSocial, int numeroAfiliado) {
        super (id, nombre, apellido, dni, mail, fechaAlta, domicilio);
        this.nombreObraSocial = nombreObraSocial;
        this.numeroAfiliado = numeroAfiliado;
    }

    public double calcularCostoConsulta(){
        double costoBase = 40000;
        double descuento = 0.70; // 70% de cobertura
        return costoBase * (1- descuento);
    }

    public String getNombreObraSocial() {
        return nombreObraSocial;
    }
    public void setNombreObraSocial (String nombreObraSocial){
        this.nombreObraSocial = nombreObraSocial;
    }
    public int getNumeroAfiliado(){
        return numeroAfiliado;
    }
    public void setNumeroAfiliado (int numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }
}

