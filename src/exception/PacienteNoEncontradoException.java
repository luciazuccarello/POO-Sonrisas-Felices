package src.exception;

public class PacienteNoEncontradoException extends src.exception.ClinicaException {
    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
