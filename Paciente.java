package model;

import java.util.Date;

public class Paciente {

    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String mail;
    private Date fechaAlta;
    private Domicilio domicilio;

    public Paciente() {
    }

    public Paciente(Integer id, String nombre, String apellido, String dni, String mail, Date fechaAlta, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.mail = mail;
        this.fechaAlta = fechaAlta;
        this.domicilio = domicilio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMail (String mail) {
        return mail;
    }

    public void setMail (String mail) {
        this.mail = mail;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "ID Paciente: " + id + "\n" +
                "Nombre: " + nombre + " " + apellido + "\n" +
                "DNI: " + dni + "\n" +
                "Mail: " + mail + "\n" +
                "Fecha de alta: " + fechaAlta + "\n" +
                "Domicilio: " + domicilio;
    }
}
