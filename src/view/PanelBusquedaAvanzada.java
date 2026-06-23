package view;

import model.Odontologo;
import model.Paciente;
import service.ServicioOdontologo;
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

public class PanelBusquedaAvanzada extends JPanel {

    private ServicioPaciente servicioPaciente;
    private ServicioOdontologo servicioOdontologo;

    private JTextField txtBusqueda;
    private JTable tabla;
    private DefaultTableModel modelo;

    private Border bordeOriginal;

    public PanelBusquedaAvanzada(ServicioPaciente servicioPaciente,
                                 ServicioOdontologo servicioOdontologo) {

        this.servicioPaciente = servicioPaciente;
        this.servicioOdontologo = servicioOdontologo;

        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Búsquedas"));

        txtBusqueda = new JTextField(20);
        bordeOriginal = txtBusqueda.getBorder();

        JButton btnBuscarPacienteID = new JButton("Paciente por ID");
        JButton btnBuscarDNI = new JButton("Paciente por DNI");
        JButton btnBuscarOdontologo = new JButton("Odontólogo por ID");
        JButton btnLimpiar = new JButton("Limpiar");

        panelSuperior.add(new JLabel("Valor:"));
        panelSuperior.add(txtBusqueda);
        panelSuperior.add(btnBuscarPacienteID);
        panelSuperior.add(btnBuscarDNI);
        panelSuperior.add(btnBuscarOdontologo);
        panelSuperior.add(btnLimpiar);

        add(panelSuperior, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{
                "Tipo", "ID", "Nombre", "Apellido", "Dato Extra"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnBuscarPacienteID.addActionListener(e -> buscarPacientePorId());
        btnBuscarDNI.addActionListener(e -> buscarPacientePorDni());
        btnBuscarOdontologo.addActionListener(e -> buscarOdontologoPorId());
        btnLimpiar.addActionListener(e -> limpiarBusqueda());

        txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                txtBusqueda.setBorder(bordeOriginal);
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();

                if (fila != -1) {
                    JOptionPane.showMessageDialog(
                            PanelBusquedaAvanzada.this,
                            "Resultado seleccionado: " + tabla.getValueAt(fila, 1)
                    );
                }
            }
        });
    }

    private boolean validarBusqueda() {
        if (txtBusqueda.getText().trim().isEmpty()) {
            txtBusqueda.setBorder(new LineBorder(Color.RED, 2));

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un valor de búsqueda.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        return true;
    }

    private void buscarPacientePorId() {
        if (!validarBusqueda()) {
            return;
        }

        try {
            modelo.setRowCount(0);

            Long id = Long.parseLong(txtBusqueda.getText().trim());

            Paciente paciente = servicioPaciente.buscarPaciente(id);

            modelo.addRow(new Object[]{
                    "Paciente",
                    paciente.getId(),
                    paciente.getNombre(),
                    paciente.getApellido(),
                    paciente.getDni()
            });

        } catch (NumberFormatException ex) {
            txtBusqueda.setBorder(new LineBorder(Color.RED, 2));
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void buscarPacientePorDni() {
        if (!validarBusqueda()) {
            return;
        }

        try {
            modelo.setRowCount(0);

            Paciente paciente = servicioPaciente.buscarPorDni(
                    txtBusqueda.getText().trim()
            );

            modelo.addRow(new Object[]{
                    "Paciente",
                    paciente.getId(),
                    paciente.getNombre(),
                    paciente.getApellido(),
                    paciente.getDni()
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void buscarOdontologoPorId() {
        if (!validarBusqueda()) {
            return;
        }

        try {
            modelo.setRowCount(0);

            Long id = Long.parseLong(txtBusqueda.getText().trim());

            Odontologo odontologo = servicioOdontologo.buscarOdontologo(id);

            modelo.addRow(new Object[]{
                    "Odontólogo",
                    odontologo.getId(),
                    odontologo.getNombre(),
                    odontologo.getApellido(),
                    odontologo.getMatricula()
            });

        } catch (NumberFormatException ex) {
            txtBusqueda.setBorder(new LineBorder(Color.RED, 2));
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiarBusqueda() {
        txtBusqueda.setText("");
        txtBusqueda.setBorder(bordeOriginal);
        modelo.setRowCount(0);
    }
}