package view;

import model.*;
import service.ServicioPaciente;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class PanelPaciente extends JPanel {

    private ServicioPaciente servicio;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;
    private JTextField txtMail;

    private JTextField txtCalle;
    private JTextField txtNumero;
    private JTextField txtLocalidad;
    private JTextField txtProvincia;

    private JComboBox<String> cmbTipoPaciente;

    private JTextField txtObraSocial;
    private JTextField txtAfiliado;

    private JTable tabla;
    private DefaultTableModel modelo;

    private Border bordeOriginal;

    public PanelPaciente(ServicioPaciente servicio) {

        this.servicio = servicio;

        setLayout(new BorderLayout(10, 10));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del paciente"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(10);
        txtNombre = new JTextField(15);
        txtApellido = new JTextField(15);
        txtDni = new JTextField(15);
        txtMail = new JTextField(15);

        txtCalle = new JTextField(15);
        txtNumero = new JTextField(10);
        txtLocalidad = new JTextField(15);
        txtProvincia = new JTextField(15);

        cmbTipoPaciente = new JComboBox<>(new String[]{"Particular", "Obra Social"});

        txtObraSocial = new JTextField(15);
        txtAfiliado = new JTextField(15);

        bordeOriginal = txtNombre.getBorder();

        agregarCampo(formulario, gbc, 0, "ID:", txtId);
        agregarCampo(formulario, gbc, 1, "Nombre:", txtNombre);
        agregarCampo(formulario, gbc, 2, "Apellido:", txtApellido);
        agregarCampo(formulario, gbc, 3, "DNI:", txtDni);
        agregarCampo(formulario, gbc, 4, "Mail:", txtMail);
        agregarCampo(formulario, gbc, 5, "Tipo:", cmbTipoPaciente);
        agregarCampo(formulario, gbc, 6, "Calle:", txtCalle);
        agregarCampo(formulario, gbc, 7, "Número:", txtNumero);
        agregarCampo(formulario, gbc, 8, "Localidad:", txtLocalidad);
        agregarCampo(formulario, gbc, 9, "Provincia:", txtProvincia);
        agregarCampo(formulario, gbc, 10, "Obra Social:", txtObraSocial);
        agregarCampo(formulario, gbc, 11, "Nro Afiliado:", txtAfiliado);

        add(formulario, BorderLayout.WEST);

        modelo = new DefaultTableModel(new String[]{
                "ID", "Apellido", "Nombre", "DNI", "Mail", "Tipo"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel();

        JButton btnGuardar = new JButton("Guardar / Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        botones.add(btnGuardar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        add(botones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarPaciente());
        btnEliminar.addActionListener(e -> eliminarPaciente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        cmbTipoPaciente.addActionListener(e -> actualizarCamposTipo());

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarPacienteSeleccionado();
            }
        });

        JTextField[] campos = {
                txtId, txtNombre, txtApellido, txtDni, txtMail,
                txtCalle, txtNumero, txtLocalidad, txtProvincia,
                txtObraSocial, txtAfiliado
        };

        for (JTextField campo : campos) {
            campo.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    campo.setBorder(bordeOriginal);
                }
            });
        }

        actualizarCamposTipo();
        actualizarTabla();
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

    private void actualizarCamposTipo() {
        boolean esObraSocial = cmbTipoPaciente.getSelectedItem().equals("Obra Social");

        txtObraSocial.setEnabled(esObraSocial);
        txtAfiliado.setEnabled(esObraSocial);

        if (!esObraSocial) {
            txtObraSocial.setText("");
            txtAfiliado.setText("");
            txtObraSocial.setBorder(bordeOriginal);
            txtAfiliado.setBorder(bordeOriginal);
        }
    }

    private boolean validarFormulario() {
        boolean valido = true;

        JTextField[] obligatorios = {
                txtId, txtNombre, txtApellido, txtDni, txtMail,
                txtCalle, txtNumero, txtLocalidad, txtProvincia
        };

        for (JTextField campo : obligatorios) {
            if (campo.getText().trim().isEmpty()) {
                campo.setBorder(new LineBorder(Color.RED, 2));
                valido = false;
            }
        }

        if (cmbTipoPaciente.getSelectedItem().equals("Obra Social")) {
            if (txtObraSocial.getText().trim().isEmpty()) {
                txtObraSocial.setBorder(new LineBorder(Color.RED, 2));
                valido = false;
            }

            if (txtAfiliado.getText().trim().isEmpty()) {
                txtAfiliado.setBorder(new LineBorder(Color.RED, 2));
                valido = false;
            }
        }

        return valido;
    }

    private void guardarPaciente() {

        if (!validarFormulario()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Complete los campos marcados en rojo.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Domicilio domicilio = new Domicilio(
                    txtCalle.getText().trim(),
                    Integer.parseInt(txtNumero.getText().trim()),
                    txtLocalidad.getText().trim(),
                    txtProvincia.getText().trim()
            );

            Paciente paciente;

            if (cmbTipoPaciente.getSelectedItem().equals("Particular")) {
                paciente = new PacienteParticular(
                        Integer.parseInt(txtId.getText().trim()),
                        txtNombre.getText().trim(),
                        txtApellido.getText().trim(),
                        txtDni.getText().trim(),
                        txtMail.getText().trim(),
                        new Date(),
                        domicilio
                );
            } else {
                paciente = new PacienteObraSocial(
                        Integer.parseInt(txtId.getText().trim()),
                        txtNombre.getText().trim(),
                        txtApellido.getText().trim(),
                        txtDni.getText().trim(),
                        txtMail.getText().trim(),
                        new Date(),
                        domicilio,
                        txtObraSocial.getText().trim(),
                        Integer.parseInt(txtAfiliado.getText().trim())
                );
            }

            servicio.registrarPaciente(paciente);

            JOptionPane.showMessageDialog(this, "Paciente guardado correctamente.");

            limpiarFormulario();
            actualizarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID, número de domicilio y afiliado deben ser números válidos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminarPaciente() {

        try {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente.");
                return;
            }

            Integer id = (Integer) tabla.getValueAt(fila, 0);

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea eliminar el paciente seleccionado?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar == JOptionPane.YES_OPTION) {
                servicio.eliminarPaciente(id.longValue());
                actualizarTabla();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Paciente eliminado correctamente.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void cargarPacienteSeleccionado() {

        try {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                return;
            }

            Long id = Long.parseLong(tabla.getValueAt(fila, 0).toString());

            Paciente paciente = servicio.buscarPaciente(id);

            txtId.setText(paciente.getId().toString());
            txtNombre.setText(paciente.getNombre());
            txtApellido.setText(paciente.getApellido());
            txtDni.setText(paciente.getDni());
            txtMail.setText(paciente.getMail());

            if (paciente.getDomicilio() != null) {
                txtCalle.setText(paciente.getDomicilio().getCalle());
                txtNumero.setText(String.valueOf(paciente.getDomicilio().getNumero()));
                txtLocalidad.setText(paciente.getDomicilio().getLocalidad());
                txtProvincia.setText(paciente.getDomicilio().getProvincia());
            }

            if (paciente instanceof PacienteObraSocial) {
                PacienteObraSocial pacienteOS = (PacienteObraSocial) paciente;

                cmbTipoPaciente.setSelectedItem("Obra Social");
                txtObraSocial.setText(pacienteOS.getNombreObraSocial());
                txtAfiliado.setText(String.valueOf(pacienteOS.getNumeroAfiliado()));
            } else {
                cmbTipoPaciente.setSelectedItem("Particular");
                txtObraSocial.setText("");
                txtAfiliado.setText("");
            }

            actualizarCamposTipo();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void actualizarTabla() {

        modelo.setRowCount(0);

        for (Paciente paciente : servicio.listarPacientesOrdenadosPorApellido()) {

            String tipo = paciente instanceof PacienteObraSocial ? "Obra Social" : "Particular";

            modelo.addRow(new Object[]{
                    paciente.getId(),
                    paciente.getApellido(),
                    paciente.getNombre(),
                    paciente.getDni(),
                    paciente.getMail(),
                    tipo
            });
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtMail.setText("");

        txtCalle.setText("");
        txtNumero.setText("");
        txtLocalidad.setText("");
        txtProvincia.setText("");

        txtObraSocial.setText("");
        txtAfiliado.setText("");

        cmbTipoPaciente.setSelectedItem("Particular");

        JTextField[] campos = {
                txtId, txtNombre, txtApellido, txtDni, txtMail,
                txtCalle, txtNumero, txtLocalidad, txtProvincia,
                txtObraSocial, txtAfiliado
        };

        for (JTextField campo : campos) {
            campo.setBorder(bordeOriginal);
        }

        actualizarCamposTipo();
    }
}