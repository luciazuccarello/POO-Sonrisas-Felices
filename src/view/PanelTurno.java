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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanelTurno extends JPanel {

    private ServicioTurno servicioTurno;
    private ServicioPaciente servicioPaciente;
    private ServicioOdontologo servicioOdontologo;

    private JComboBox<Paciente> cmbPacientes;
    private JComboBox<Odontologo> cmbOdontologos;
    private JTextField txtFecha;
    private JTextField txtHora;

    private JTable tabla;
    private DefaultTableModel modelo;

    private Border bordeOriginal;
    private final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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
        txtFecha = new JTextField(12);
        txtHora = new JTextField(8);

        bordeOriginal = txtFecha.getBorder();

        agregarCampo(formulario, gbc, 0, "Paciente:", cmbPacientes);
        agregarCampo(formulario, gbc, 1, "Odontólogo:", cmbOdontologos);
        agregarCampo(formulario, gbc, 2, "Fecha (dd/MM/yyyy):", txtFecha);
        agregarCampo(formulario, gbc, 3, "Hora (HH:mm):", txtHora);

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
        JButton btnActualizar = new JButton("Actualizar");

        botones.add(btnCrear);
        botones.add(btnCancelar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        add(botones, BorderLayout.SOUTH);

        btnCrear.addActionListener(e -> crearTurno());
        btnCancelar.addActionListener(e -> cancelarTurno());
        btnEliminar.addActionListener(e -> eliminarTurno());
        btnActualizar.addActionListener(e -> actualizarDatos());

        txtFecha.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtFecha.setBorder(bordeOriginal);
            }
        });

        txtHora.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtHora.setBorder(bordeOriginal);
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();

                if (fila != -1) {
                    JOptionPane.showMessageDialog(
                            PanelTurno.this,
                            "Turno seleccionado: " + tabla.getValueAt(fila, 0)
                    );
                }
            }
        });

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

        if (cmbPacientes.getSelectedItem() == null) {
            valido = false;
        }

        if (cmbOdontologos.getSelectedItem() == null) {
            valido = false;
        }

        if (txtFecha.getText().trim().isEmpty()) {
            txtFecha.setBorder(new LineBorder(Color.RED, 2));
            valido = false;
        }

        if (txtHora.getText().trim().isEmpty()) {
            txtHora.setBorder(new LineBorder(Color.RED, 2));
            valido = false;
        }

        return valido;
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

            Date fechaHora = formato.parse(
                    txtFecha.getText().trim() + " " + txtHora.getText().trim()
            );

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

            txtFecha.setText("");
            txtHora.setText("");
            cmbPacientes.setSelectedIndex(-1);
            cmbOdontologos.setSelectedIndex(-1);

            actualizarTabla();

        } catch (Exception ex) {
            txtFecha.setBorder(new LineBorder(Color.RED, 2));
            txtHora.setBorder(new LineBorder(Color.RED, 2));

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
}