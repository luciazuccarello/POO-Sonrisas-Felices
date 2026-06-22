package view;

import exception.ClinicaException;
import model.*;
import repository.RepositorioOdontologo;
import repository.RepositorioPaciente;
import repository.RepositorioTurno;
import service.ServicioOdontologo;
import service.ServicioPaciente;
import service.ServicioTurno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class PanelTurno extends JPanel {

    private ServicioTurno servicioTurno;
    private ServicioPaciente servicioPaciente;
    private ServicioOdontologo servicioOdontologo;

    private JComboBox<Paciente> cmbPacientes;
    private JComboBox<Odontologo> cmbOdontologos;

    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelTurno() {

        servicioTurno = new ServicioTurno(
                new RepositorioTurno()
        );

        servicioPaciente = new ServicioPaciente(
                new RepositorioPaciente()
        );

        servicioOdontologo = new ServicioOdontologo(
                new RepositorioOdontologo()
        );

        setLayout(new BorderLayout());

        //------------------------------------
        // FORMULARIO
        //------------------------------------

        JPanel formulario = new JPanel(
                new GridLayout(2,2)
        );

        cmbPacientes = new JComboBox<>();
        cmbOdontologos = new JComboBox<>();

        cargarPacientes();
        cargarOdontologos();

        formulario.add(
                new JLabel("Paciente")
        );

        formulario.add(cmbPacientes);

        formulario.add(
                new JLabel("Odontólogo")
        );

        formulario.add(cmbOdontologos);

        add(formulario, BorderLayout.NORTH);

        //------------------------------------
        // TABLA
        //------------------------------------

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Paciente");
        modelo.addColumn("Odontólogo");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);

        add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        //------------------------------------
        // BOTONES
        //------------------------------------

        JPanel botones = new JPanel();

        JButton btnCrear =
                new JButton("Crear Turno");

        JButton btnCancelar =
                new JButton("Cancelar Turno");

        JButton btnEliminar =
                new JButton("Eliminar");

        botones.add(btnCrear);
        botones.add(btnCancelar);
        botones.add(btnEliminar);

        add(botones, BorderLayout.SOUTH);

        //------------------------------------
        // EVENTOS
        //------------------------------------

        btnCrear.addActionListener(
                e -> crearTurno()
        );

        btnCancelar.addActionListener(
                e -> cancelarTurno()
        );

        btnEliminar.addActionListener(
                e -> eliminarTurno()
        );

        actualizarTabla();
    }

    private void cargarPacientes() {

        cmbPacientes.removeAllItems();

        for(Paciente p :
                servicioPaciente.listarPacientes()) {

            cmbPacientes.addItem(p);
        }
    }

    private void cargarOdontologos() {

        cmbOdontologos.removeAllItems();

        for(Odontologo o :
                servicioOdontologo.listarOdontologos()) {

            cmbOdontologos.addItem(o);
        }
    }

    private void crearTurno() {

        try {

            Paciente paciente =
                    (Paciente) cmbPacientes.getSelectedItem();

            Odontologo odontologo =
                    (Odontologo) cmbOdontologos.getSelectedItem();

            if(paciente == null ||
                    odontologo == null){

                JOptionPane.showMessageDialog(
                        this,
                        "Debe haber pacientes y odontólogos cargados"
                );
                return;
            }

            servicioTurno.crearTurno(
                    paciente,
                    odontologo,
                    new Date(),
                    new Date(),
                    EstadoTurno.CONFIRMADO
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Turno creado correctamente"
            );

            actualizarTabla();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void cancelarTurno() {

        try {

            int fila =
                    tabla.getSelectedRow();

            if(fila == -1){

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un turno"
                );
                return;
            }

            Integer id =
                    (Integer) tabla.getValueAt(
                            fila,
                            0
                    );

            servicioTurno.cancelarTurno(
                    id.longValue()
            );

            actualizarTabla();

        } catch (ClinicaException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void eliminarTurno() {

        try {

            int fila =
                    tabla.getSelectedRow();

            if(fila == -1){

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un turno"
                );
                return;
            }

            Integer id =
                    (Integer) tabla.getValueAt(
                            fila,
                            0
                    );

            servicioTurno.eliminarTurno(
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

        for(Turno t :
                servicioTurno.listarTurnos()) {

            modelo.addRow(
                    new Object[]{
                            t.getId(),
                            t.getPaciente().getNombre(),
                            t.getOdontologo().getNombre(),
                            t.getEstado()
                    }
            );
        }
    }
}