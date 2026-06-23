package view;

import model.Odontologo;
import service.ServicioOdontologo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelOdontologo extends JPanel {

    private ServicioOdontologo servicio;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtMail;
    private JTextField txtMatricula;

    private JTable tabla;
    private DefaultTableModel modelo;

    private Border bordeOriginal;

    public PanelOdontologo(ServicioOdontologo servicio) {

        this.servicio = servicio;

        setLayout(new BorderLayout(10, 10));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del odontólogo"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(10);
        txtNombre = new JTextField(15);
        txtApellido = new JTextField(15);
        txtMail = new JTextField(15);
        txtMatricula = new JTextField(15);

        bordeOriginal = txtNombre.getBorder();

        agregarCampo(formulario, gbc, 0, "ID:", txtId);
        agregarCampo(formulario, gbc, 1, "Nombre:", txtNombre);
        agregarCampo(formulario, gbc, 2, "Apellido:", txtApellido);
        agregarCampo(formulario, gbc, 3, "Mail:", txtMail);
        agregarCampo(formulario, gbc, 4, "Matrícula:", txtMatricula);

        add(formulario, BorderLayout.WEST);

        modelo = new DefaultTableModel(new String[]{
                "ID", "Nombre", "Apellido", "Mail", "Matrícula"
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

        btnGuardar.addActionListener(e -> guardarOdontologo());
        btnEliminar.addActionListener(e -> eliminarOdontologo());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarOdontologoSeleccionado();
            }
        });

        JTextField[] campos = {
                txtId, txtNombre, txtApellido, txtMail, txtMatricula
        };

        for (JTextField campo : campos) {
            campo.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    campo.setBorder(bordeOriginal);
                }
            });
        }

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

    private boolean validarFormulario() {
        boolean valido = true;

        JTextField[] campos = {
                txtId, txtNombre, txtApellido, txtMail, txtMatricula
        };

        for (JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                campo.setBorder(new LineBorder(Color.RED, 2));
                valido = false;
            }
        }

        return valido;
    }

    private void guardarOdontologo() {

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
            Odontologo odontologo = new Odontologo(
                    Integer.parseInt(txtId.getText().trim()),
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtMail.getText().trim(),
                    txtMatricula.getText().trim()
            );

            servicio.registrarOdontologo(odontologo);

            JOptionPane.showMessageDialog(this, "Odontólogo guardado correctamente.");

            limpiarFormulario();
            actualizarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminarOdontologo() {

        try {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un odontólogo.");
                return;
            }

            Integer id = (Integer) tabla.getValueAt(fila, 0);

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea eliminar el odontólogo seleccionado?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar == JOptionPane.YES_OPTION) {
                servicio.eliminarOdontologo(id.longValue());
                actualizarTabla();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Odontólogo eliminado correctamente.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void cargarOdontologoSeleccionado() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            return;
        }

        txtId.setText(tabla.getValueAt(fila, 0).toString());
        txtNombre.setText(tabla.getValueAt(fila, 1).toString());
        txtApellido.setText(tabla.getValueAt(fila, 2).toString());
        txtMail.setText(tabla.getValueAt(fila, 3).toString());
        txtMatricula.setText(tabla.getValueAt(fila, 4).toString());
    }

    public void actualizarTabla() {

        modelo.setRowCount(0);

        for (Odontologo odontologo : servicio.listarOdontologos()) {
            modelo.addRow(new Object[]{
                    odontologo.getId(),
                    odontologo.getNombre(),
                    odontologo.getApellido(),
                    odontologo.getMail(),
                    odontologo.getMatricula()
            });
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtMail.setText("");
        txtMatricula.setText("");

        JTextField[] campos = {
                txtId, txtNombre, txtApellido, txtMail, txtMatricula
        };

        for (JTextField campo : campos) {
            campo.setBorder(bordeOriginal);
        }
    }
}