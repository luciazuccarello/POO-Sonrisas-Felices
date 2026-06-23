package view;

import repository.RepositorioOdontologo;
import repository.RepositorioPaciente;
import repository.RepositorioTurno;
import service.ServicioOdontologo;
import service.ServicioPaciente;
import service.ServicioTurno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {

    private PanelPaciente panelPaciente;
    private PanelOdontologo panelOdontologo;
    private PanelTurno panelTurno;
    private PanelBusquedaAvanzada panelBusquedaAvanzada;

    public VentanaPrincipal() {

        setTitle("Sistema Clínica Odontológica - Sonrisas Felices");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        RepositorioPaciente repoPaciente = new RepositorioPaciente();
        RepositorioOdontologo repoOdontologo = new RepositorioOdontologo();
        RepositorioTurno repoTurno = new RepositorioTurno();

        ServicioPaciente servicioPaciente = new ServicioPaciente(repoPaciente);
        ServicioOdontologo servicioOdontologo = new ServicioOdontologo(repoOdontologo);
        ServicioTurno servicioTurno = new ServicioTurno(repoTurno);

        JLabel titulo = new JLabel(
                "CLÍNICA ODONTOLÓGICA SONRISAS FELICES",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        panelPaciente = new PanelPaciente(servicioPaciente);
        panelOdontologo = new PanelOdontologo(servicioOdontologo);
        panelTurno = new PanelTurno(servicioTurno, servicioPaciente, servicioOdontologo);
        panelBusquedaAvanzada = new PanelBusquedaAvanzada(servicioPaciente, servicioOdontologo);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Pacientes", panelPaciente);
        tabs.addTab("Odontólogos", panelOdontologo);
        tabs.addTab("Turnos", panelTurno);
        tabs.addTab("Búsquedas", panelBusquedaAvanzada);

        tabs.addChangeListener(e -> {
            Component seleccionado = tabs.getSelectedComponent();

            if (seleccionado == panelPaciente) {
                panelPaciente.actualizarTabla();
            }

            if (seleccionado == panelOdontologo) {
                panelOdontologo.actualizarTabla();
            }

            if (seleccionado == panelTurno) {
                panelTurno.actualizarDatos();
            }
        });

        add(tabs, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                int opcion = JOptionPane.showConfirmDialog(
                        VentanaPrincipal.this,
                        "¿Desea salir del sistema?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(
                            VentanaPrincipal.this,
                            "Datos guardados correctamente."
                    );
                    dispose();
                    System.exit(0);
                }
            }
        });
    }
}