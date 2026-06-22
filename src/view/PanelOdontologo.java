package view;

import model.Odontologo;
import repository.RepositorioOdontologo;
import service.ServicioOdontologo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelOdontologo extends JPanel {

    private ServicioOdontologo servicio;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtMail;
    private JTextField txtMatricula;

    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelOdontologo() {

        servicio = new ServicioOdontologo(
                new RepositorioOdontologo()
        );

        setLayout(new BorderLayout());

        //----------------------------------
        // FORMULARIO
        //----------------------------------

        JPanel formulario = new JPanel(
                new GridLayout(5,2)
        );

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtMail = new JTextField();
        txtMatricula = new JTextField();

        formulario.add(new JLabel("ID"));
        formulario.add(txtId);

        formulario.add(new JLabel("Nombre"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Apellido"));
        formulario.add(txtApellido);

        formulario.add(new JLabel("Mail"));
        formulario.add(txtMail);

        formulario.add(new JLabel("Matricula"));
        formulario.add(txtMatricula);

        add(formulario, BorderLayout.NORTH);

        //----------------------------------
        // TABLA
        //----------------------------------

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Mail");
        modelo.addColumn("Matricula");

        tabla = new JTable(modelo);

        add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        //----------------------------------
        // BOTONES
        //----------------------------------

        JPanel botones = new JPanel();

        JButton btnGuardar =
                new JButton("Guardar");

        JButton btnEliminar =
                new JButton("Eliminar");

        botones.add(btnGuardar);
        botones.add(btnEliminar);

        add(botones, BorderLayout.SOUTH);

        //----------------------------------
        // EVENTOS
        //----------------------------------

        btnGuardar.addActionListener(
                e -> guardarOdontologo()
        );

        btnEliminar.addActionListener(
                e -> eliminarOdontologo()
        );

        tabla.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila = tabla.getSelectedRow();

                    if(fila >= 0){

                        txtId.setText(
                                tabla.getValueAt(fila,0)
                                        .toString()
                        );

                        txtNombre.setText(
                                tabla.getValueAt(fila,1)
                                        .toString()
                        );

                        txtApellido.setText(
                                tabla.getValueAt(fila,2)
                                        .toString()
                        );

                        txtMail.setText(
                                tabla.getValueAt(fila,3)
                                        .toString()
                        );

                        txtMatricula.setText(
                                tabla.getValueAt(fila,4)
                                        .toString()
                        );
                    }
                });

        actualizarTabla();
    }

    private void guardarOdontologo() {

        try {

            Odontologo odontologo =
                    new Odontologo(
                            Integer.parseInt(txtId.getText()),
                            txtNombre.getText(),
                            txtApellido.getText(),
                            txtMail.getText(),
                            txtMatricula.getText()
                    );

            servicio.registrarOdontologo(
                    odontologo
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Odontólogo guardado correctamente"
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

    private void eliminarOdontologo() {

        try {

            int fila =
                    tabla.getSelectedRow();

            if(fila == -1){

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un odontólogo"
                );
                return;
            }

            Integer id =
                    (Integer) tabla.getValueAt(
                            fila,
                            0
                    );

            servicio.eliminarOdontologo(
                    id.longValue()
            );

            actualizarTabla();

        } catch (Exception ex){

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void actualizarTabla() {

        modelo.setRowCount(0);

        for(Odontologo o :
                servicio.listarOdontologos()) {

            modelo.addRow(
                    new Object[]{
                            o.getId(),
                            o.getNombre(),
                            o.getApellido(),
                            o.getMail(),
                            o.getMatricula()
                    }
            );
        }
    }

    private void limpiarFormulario() {

        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtMail.setText("");
        txtMatricula.setText("");
    }
}