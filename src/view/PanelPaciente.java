package view;

import model.Paciente;
import model.PacienteParticular;
import model.Domicilio;
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

    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelPaciente() {

        servicio = new ServicioPaciente(
                new RepositorioPaciente()
        );

        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(5,2));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtDni = new JTextField();
        txtMail = new JTextField();

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

        add(formulario, BorderLayout.NORTH);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("DNI");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");

        botones.add(btnGuardar);
        botones.add(btnEliminar);

        add(botones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarPaciente());

        btnEliminar.addActionListener(e -> eliminarPaciente());

        actualizarTabla();
    }

    private void guardarPaciente() {

        try {

            Paciente paciente =
                    new PacienteParticular(
                            Integer.parseInt(txtId.getText()),
                            txtNombre.getText(),
                            txtApellido.getText(),
                            txtDni.getText(),
                            txtMail.getText(),
                            new Date(),
                            new Domicilio("Sin Calle",0,"","")
                    );

            servicio.registrarPaciente(paciente);

            JOptionPane.showMessageDialog(
                    this,
                    "Paciente guardado"
            );

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

            int fila = tabla.getSelectedRow();

            if(fila == -1){
                return;
            }

            Integer id =
                    (Integer) tabla.getValueAt(fila,0);

            servicio.eliminarPaciente(
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

    private void actualizarTabla(){

        modelo.setRowCount(0);

        for(Paciente p :
                servicio.listarPacientes()){

            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getDni()
            });

        }

    }

}

