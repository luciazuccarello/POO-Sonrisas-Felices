package exception;

public class PacienteNoEncontradoException extends exception.ClinicaException {
    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
