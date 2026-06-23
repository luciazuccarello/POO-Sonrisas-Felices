package view;

import exception.ClinicaException;
import model.*;
import service.ServicioOdontologo;
import service.ServicioPaciente;
import service.ServicioTurno;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class PanelTurno extends JPanel {

    private ServicioTurno servicioTurno;
    private ServicioPaciente servicioPaciente;
    private ServicioOdontologo servicioOdontologo;

    private JComboBox<Paciente> cmbPacientes;
    private JComboBox<Odontologo> cmbOdontologos;
    private JSpinner spFecha;
    private JComboBox<String> cmbHora;

    private JTable tabla;
    private DefaultTableModel modelo;
    private Long turnoSeleccionadoId;

    private Border bordeOriginal;
    private final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    public PanelTurno(ServicioTurno servicioTurno,
                      ServicioPaciente servicioPaciente,
                      ServicioOdontologo servicioOdontologo) {

        this.servicioTurno = servicioTurno;
        this.servicioPaciente = servicioPaciente;
        this.servicioOdontologo = servicioOdontologo;

        formato.setLenient(false);

        setLayout(new BorderLayout(10, 10));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Reserva de turno"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cmbPacientes = new JComboBox<>();
        cmbOdontologos = new JComboBox<>();
        
        // Configurar JSpinner para fecha
        SpinnerDateModel modeloFecha = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spFecha = new JSpinner(modeloFecha);
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spFecha, "dd/MM/yyyy");
        spFecha.setEditor(editorFecha);
        
        // Configurar JComboBox para horas
        cmbHora = new JComboBox<>();
        for (int i = 0; i < 24; i++) {
            cmbHora.addItem(String.format("%02d:00", i));
        }

        bordeOriginal = spFecha.getBorder();

        agregarCampo(formulario, gbc, 0, "Paciente:", cmbPacientes);
        agregarCampo(formulario, gbc, 1, "Odontólogo:", cmbOdontologos);
        agregarCampo(formulario, gbc, 2, "Fecha:", spFecha);
        agregarCampo(formulario, gbc, 3, "Hora:", cmbHora);

        add(formulario, BorderLayout.WEST);

        modelo = new DefaultTableModel(new String[]{
                "ID", "Paciente", "Odontólogo", "Fecha/Hora", "Estado", "Monto"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel();

        JButton btnCrear = new JButton("Crear Turno");
        JButton btnCancelar = new JButton("Cancelar Turno");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnReprogramar = new JButton("Reprogramar Turno");
        JButton btnActualizar = new JButton("Actualizar");

        botones.add(btnCrear);
        botones.add(btnReprogramar);
        botones.add(btnCancelar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        add(botones, BorderLayout.SOUTH);

        btnCrear.addActionListener(e -> crearTurno());
        btnReprogramar.addActionListener(e -> reprogramarTurno());
        btnCancelar.addActionListener(e -> cancelarTurno());
        btnEliminar.addActionListener(e -> eliminarTurno());
        btnActualizar.addActionListener(e -> actualizarDatos());
        cmbPacientes.addActionListener(e -> cmbPacientes.setBorder(bordeOriginal));
        cmbOdontologos.addActionListener(e -> cmbOdontologos.setBorder(bordeOriginal));
        cmbHora.addActionListener(e -> cmbHora.setBorder(bordeOriginal));
        spFecha.addChangeListener(e -> spFecha.setBorder(bordeOriginal));

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarTurnoSeleccionadoEnFormulario();
            }
        });

        turnoSeleccionadoId = null;
        actualizarDatos();
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent componente) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(componente, gbc);
    }

    public void actualizarDatos() {
        cargarPacientes();
        cargarOdontologos();
        actualizarTabla();
    }

    private void cargarPacientes() {
        cmbPacientes.removeAllItems();

        for (Paciente paciente : servicioPaciente.listarPacientes()) {
            cmbPacientes.addItem(paciente);
        }
    }

    private void cargarOdontologos() {
        cmbOdontologos.removeAllItems();

        for (Odontologo odontologo : servicioOdontologo.listarOdontologos()) {
            cmbOdontologos.addItem(odontologo);
        }
    }

    private boolean validarFormulario() {
        boolean valido = true;

        cmbPacientes.setBorder(bordeOriginal);
        cmbOdontologos.setBorder(bordeOriginal);
        spFecha.setBorder(bordeOriginal);
        cmbHora.setBorder(bordeOriginal);

        if (cmbPacientes.getSelectedItem() == null) {
            cmbPacientes.setBorder(new LineBorder(Color.RED, 2));
            valido = false;
        }

        if (cmbOdontologos.getSelectedItem() == null) {
            cmbOdontologos.setBorder(new LineBorder(Color.RED, 2));
            valido = false;
        }

        if (spFecha.getValue() == null) {
            spFecha.setBorder(new LineBorder(Color.RED, 2));
            valido = false;
        }

        if (cmbHora.getSelectedItem() == null) {
            cmbHora.setBorder(new LineBorder(Color.RED, 2));
            valido = false;
        }

        if (valido) {
            Date fechaHora = obtenerFechaHoraSeleccionada();

            if (fechaHora.before(new Date())) {
                spFecha.setBorder(new LineBorder(Color.RED, 2));
                cmbHora.setBorder(new LineBorder(Color.RED, 2));
                JOptionPane.showMessageDialog(
                        this,
                        "La fecha y hora del turno no puede ser anterior al momento actual.",
                        "Validacion",
                        JOptionPane.WARNING_MESSAGE
                );
                return false;
            }
        }

        return valido;
    }

    private Date obtenerFechaHoraSeleccionada() {
        Date fecha = (Date) spFecha.getValue();
        String horaSeleccionada = (String) cmbHora.getSelectedItem();

        String[] parteHora = horaSeleccionada.split(":");
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parteHora[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(parteHora[1]));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    private void crearTurno() {
        if (!validarFormulario()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar paciente, odontólogo, fecha y hora.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Paciente paciente = (Paciente) cmbPacientes.getSelectedItem();
            Odontologo odontologo = (Odontologo) cmbOdontologos.getSelectedItem();
            Date fechaHora = obtenerFechaHoraSeleccionada();

            Turno turno = servicioTurno.crearTurno(
                    paciente,
                    odontologo,
                    fechaHora,
                    fechaHora,
                    EstadoTurno.CONFIRMADO
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Turno creado correctamente.\nID: " + turno.getId()
            );

            limpiarFormulario();
            actualizarTabla();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void reprogramarTurno() {
        if (!validarFormulario()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar paciente, odontologo, fecha y hora validos.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (turnoSeleccionadoId == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un turno de la tabla para reprogramar.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Paciente pacienteSeleccionado = (Paciente) cmbPacientes.getSelectedItem();
            Odontologo odontologoSeleccionado = (Odontologo) cmbOdontologos.getSelectedItem();
            Date fechaHora = obtenerFechaHoraSeleccionada();

            Turno turnoActual = servicioTurno.buscarTurno(turnoSeleccionadoId);

            if (!turnoActual.getPaciente().getId().equals(pacienteSeleccionado.getId())
                    || !turnoActual.getOdontologo().getId().equals(odontologoSeleccionado.getId())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Para reprogramar, debe mantener el mismo paciente y odontologo del turno seleccionado.",
                        "Validacion",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            servicioTurno.reprogramarTurno(turnoSeleccionadoId, fechaHora, fechaHora);

            JOptionPane.showMessageDialog(this, "Turno reprogramado correctamente.");
            actualizarTabla();
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cancelarTurno() {
        try {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un turno.");
                return;
            }

            Integer id = (Integer) tabla.getValueAt(fila, 0);

            servicioTurno.cancelarTurno(id.longValue());

            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this, "Turno cancelado correctamente.");

        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminarTurno() {
        try {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un turno.");
                return;
            }

            Integer id = (Integer) tabla.getValueAt(fila, 0);

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea eliminar el turno seleccionado?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar == JOptionPane.YES_OPTION) {
                servicioTurno.eliminarTurno(id.longValue());
                actualizarTabla();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Turno eliminado correctamente.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);

        for (Turno turno : servicioTurno.listarTurnos()) {
            modelo.addRow(new Object[]{
                    turno.getId(),
                    turno.getPaciente().getNombre() + " " + turno.getPaciente().getApellido(),
                    turno.getOdontologo().getNombre() + " " + turno.getOdontologo().getApellido(),
                    formato.format(turno.getFecha()),
                    turno.getEstado(),
                    "$" + turno.getMontoFinal()
            });
        }
    }

    private void cargarTurnoSeleccionadoEnFormulario() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            return;
        }

        try {
            Long id = Long.parseLong(tabla.getValueAt(fila, 0).toString());
            Turno turno = servicioTurno.buscarTurno(id);

            turnoSeleccionadoId = id;

            seleccionarPaciente(turno.getPaciente().getId());
            seleccionarOdontologo(turno.getOdontologo().getId());

            spFecha.setValue(turno.getFecha());
            cmbHora.setSelectedItem(formatoHora.format(turno.getHora()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void seleccionarPaciente(Integer idPaciente) {
        for (int i = 0; i < cmbPacientes.getItemCount(); i++) {
            Paciente paciente = cmbPacientes.getItemAt(i);
            if (paciente != null && paciente.getId().equals(idPaciente)) {
                cmbPacientes.setSelectedIndex(i);
                return;
            }
        }
        cmbPacientes.setSelectedIndex(-1);
    }

    private void seleccionarOdontologo(Integer idOdontologo) {
        for (int i = 0; i < cmbOdontologos.getItemCount(); i++) {
            Odontologo odontologo = cmbOdontologos.getItemAt(i);
            if (odontologo != null && odontologo.getId().equals(idOdontologo)) {
                cmbOdontologos.setSelectedIndex(i);
                return;
            }
        }
        cmbOdontologos.setSelectedIndex(-1);
    }

    private void limpiarFormulario() {
        turnoSeleccionadoId = null;
        cmbPacientes.setSelectedIndex(-1);
        cmbOdontologos.setSelectedIndex(-1);
        spFecha.setValue(new Date());
        cmbHora.setSelectedIndex(0);

        cmbPacientes.setBorder(bordeOriginal);
        cmbOdontologos.setBorder(bordeOriginal);
        spFecha.setBorder(bordeOriginal);
        cmbHora.setBorder(bordeOriginal);

        tabla.clearSelection();
    }
}
