package model;

public class Domicilio {
    private String calle;
    private int numero;
    private String localidad;
    private String provincia;

    // Constructor vacío
    public Domicilio() {
    }

    // Constructor con parámetros
    public Domicilio(String calle, int numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    // Getters y Setters
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    // toString
    @Override
    public String toString() {
        return calle + " " + numero + ", " + localidad + ", " + provincia;
    }
}



