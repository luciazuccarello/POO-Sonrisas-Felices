package view;

import model.Odontologo;
import model.Paciente;
import repository.RepositorioOdontologo;
import repository.RepositorioPaciente;
import service.ServicioOdontologo;
import service.ServicioPaciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelBusquedaAvanzada extends JPanel {

    private ServicioPaciente servicioPaciente;
    private ServicioOdontologo servicioOdontologo;

    private JTextField txtBusqueda;

    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelBusquedaAvanzada() {

        servicioPaciente = new ServicioPaciente(
                new RepositorioPaciente()
        );

        servicioOdontologo = new ServicioOdontologo(
                new RepositorioOdontologo()
        );

        setLayout(new BorderLayout());

        //--------------------------------
        // PANEL SUPERIOR
        //--------------------------------

        JPanel panelSuperior = new JPanel();

        txtBusqueda = new JTextField(20);

        JButton btnBuscarPacienteID =
                new JButton("Paciente por ID");

        JButton btnBuscarDNI =
                new JButton("Paciente por DNI");

        JButton btnBuscarOdontologo =
                new JButton("Odontólogo por ID");

        panelSuperior.add(
                new JLabel("Valor:")
        );

        panelSuperior.add(txtBusqueda);

        panelSuperior.add(btnBuscarPacienteID);
        panelSuperior.add(btnBuscarDNI);
        panelSuperior.add(btnBuscarOdontologo);

        add(panelSuperior,
                BorderLayout.NORTH);

        //--------------------------------
        // TABLA
        //--------------------------------

        modelo = new DefaultTableModel();

        modelo.addColumn("Tipo");
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dato Extra");

        tabla = new JTable(modelo);

        add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        //--------------------------------
        // EVENTOS
        //--------------------------------

        btnBuscarPacienteID.addActionListener(
                e -> buscarPacientePorId()
        );

        btnBuscarDNI.addActionListener(
                e -> buscarPacientePorDni()
        );

        btnBuscarOdontologo.addActionListener(
                e -> buscarOdontologoPorId()
        );
    }

    private void buscarPacientePorId() {

        try {

            modelo.setRowCount(0);

            Long id = Long.parseLong(
                    txtBusqueda.getText()
            );

            Paciente p =
                    servicioPaciente.buscarPaciente(id);

            modelo.addRow(new Object[]{
                    "Paciente",
                    p.getId(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getDni()
            });

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void buscarPacientePorDni() {

        try {

            modelo.setRowCount(0);

            Paciente p =
                    servicioPaciente.buscarPorDni(
                            txtBusqueda.getText()
                    );

            modelo.addRow(new Object[]{
                    "Paciente",
                    p.getId(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getDni()
            });

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void buscarOdontologoPorId() {

        try {

            modelo.setRowCount(0);

            Long id = Long.parseLong(
                    txtBusqueda.getText()
            );

            Odontologo o =
                    servicioOdontologo.buscarOdontologo(id);

            modelo.addRow(new Object[]{
                    "Odontólogo",
                    o.getId(),
                    o.getNombre(),
                    o.getApellido(),
                    o.getMatricula()
            });

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }
}