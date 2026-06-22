package view;

import model.*;
import repository.RepositorioPaciente;
import service.ServicioPaciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class PanelPaciente extends JPanel {

    private ServicioPaciente servicio;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;
    private JTextField txtMail;

    // domicilio
    private JTextField txtCalle;
    private JTextField txtNumero;
    private JTextField txtLocalidad;
    private JTextField txtProvincia;

    // tipo paciente
    private JComboBox<String> cmbTipoPaciente;

    // obra social
    private JTextField txtObraSocial;
    private JTextField txtAfiliado;

    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelPaciente() {

        servicio = new ServicioPaciente(
                new RepositorioPaciente()
        );

        setLayout(new BorderLayout());

        //-----------------------------------
        // FORMULARIO
        //-----------------------------------

        JPanel formulario = new JPanel(
                new GridLayout(0,2)
        );

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtDni = new JTextField();
        txtMail = new JTextField();

        txtCalle = new JTextField();
        txtNumero = new JTextField();
        txtLocalidad = new JTextField();
        txtProvincia = new JTextField();

        cmbTipoPaciente =
                new JComboBox<>(
                        new String[]{
                                "Particular",
                                "Obra Social"
                        });

        txtObraSocial = new JTextField();
        txtAfiliado = new JTextField();

        formulario.add(new JLabel("ID"));
        formulario.add(txtId);

        formulario.add(new JLabel("Nombre"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Apellido"));
        formulario.add(txtApellido);

        formulario.add(new JLabel("DNI"));
        formulario.add(txtDni);

        formulario.add(new JLabel("Mail"));
        formulario.add(txtMail);

        formulario.add(new JLabel("Tipo"));
        formulario.add(cmbTipoPaciente);

        formulario.add(new JLabel("Calle"));
        formulario.add(txtCalle);

        formulario.add(new JLabel("Numero"));
        formulario.add(txtNumero);

        formulario.add(new JLabel("Localidad"));
        formulario.add(txtLocalidad);

        formulario.add(new JLabel("Provincia"));
        formulario.add(txtProvincia);

        formulario.add(new JLabel("Obra Social"));
        formulario.add(txtObraSocial);

        formulario.add(new JLabel("Nro Afiliado"));
        formulario.add(txtAfiliado);

        add(formulario, BorderLayout.NORTH);

        //-----------------------------------
        // TABLA
        //-----------------------------------

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("DNI");
        modelo.addColumn("Tipo");

        tabla = new JTable(modelo);

        add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        //-----------------------------------
        // BOTONES
        //-----------------------------------

        JPanel botones = new JPanel();

        JButton btnGuardar =
                new JButton("Guardar");

        JButton btnEliminar =
                new JButton("Eliminar");

        botones.add(btnGuardar);
        botones.add(btnEliminar);

        add(botones, BorderLayout.SOUTH);

        //-----------------------------------
        // EVENTOS
        //-----------------------------------

        btnGuardar.addActionListener(
                e -> guardarPaciente()
        );

        btnEliminar.addActionListener(
                e -> eliminarPaciente()
        );

        cmbTipoPaciente.addActionListener(
                e -> actualizarCamposTipo()
        );

        actualizarCamposTipo();
        actualizarTabla();
    }

    private void actualizarCamposTipo() {

        boolean obraSocial =
                cmbTipoPaciente.getSelectedItem()
                        .equals("Obra Social");

        txtObraSocial.setEnabled(obraSocial);
        txtAfiliado.setEnabled(obraSocial);
    }

    private void guardarPaciente() {

        try {

            Domicilio domicilio =
                    new Domicilio(
                            txtCalle.getText(),
                            Integer.parseInt(txtNumero.getText()),
                            txtLocalidad.getText(),
                            txtProvincia.getText()
                    );

            Paciente paciente;

            if(cmbTipoPaciente.getSelectedItem()
                    .equals("Particular")) {

                paciente =
                        new PacienteParticular(
                                Integer.parseInt(txtId.getText()),
                                txtNombre.getText(),
                                txtApellido.getText(),
                                txtDni.getText(),
                                txtMail.getText(),
                                new Date(),
                                domicilio
                        );

            } else {

                paciente =
                        new PacienteObraSocial(
                                Integer.parseInt(txtId.getText()),
                                txtNombre.getText(),
                                txtApellido.getText(),
                                txtDni.getText(),
                                txtMail.getText(),
                                new Date(),
                                domicilio,
                                txtObraSocial.getText(),
                                Integer.parseInt(txtAfiliado.getText())
                        );
            }

            servicio.registrarPaciente(paciente);

            JOptionPane.showMessageDialog(
                    this,
                    "Paciente guardado correctamente"
            );

            limpiarFormulario();

            actualizarTabla();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void eliminarPaciente() {

        try {

            int fila =
                    tabla.getSelectedRow();

            if(fila == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un paciente"
                );
                return;
            }

            Integer id =
                    (Integer) tabla.getValueAt(
                            fila,
                            0
                    );

            servicio.eliminarPaciente(
                    id.longValue()
            );

            actualizarTabla();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void actualizarTabla() {

        modelo.setRowCount(0);

        for(Paciente p :
                servicio.listarPacientes()) {

            String tipo =
                    p instanceof PacienteObraSocial
                            ? "Obra Social"
                            : "Particular";

            modelo.addRow(
                    new Object[]{
                            p.getId(),
                            p.getNombre(),
                            p.getApellido(),
                            p.getDni(),
                            tipo
                    }
            );
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
    }
}

