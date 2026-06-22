package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {

        setTitle("Sistema Clínica Odontológica - Sonrisas Felices");
        setSize(1200, 700);
        setLocationRelativeTo(null);

        //---------------------------------------
        // WINDOW LISTENER (REQUERIDO)
        //---------------------------------------

        setDefaultCloseOperation(
                JFrame.DO_NOTHING_ON_CLOSE
        );

        addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(
                            WindowEvent e
                    ) {

                        int opcion =
                                JOptionPane.showConfirmDialog(
                                        VentanaPrincipal.this,
                                        "¿Desea salir del sistema?",
                                        "Confirmar salida",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if(opcion ==
                                JOptionPane.YES_OPTION){

                            dispose();

                            System.exit(0);
                        }
                    }
                });

        //---------------------------------------
        // TITULO
        //---------------------------------------

        JLabel titulo =
                new JLabel(
                        "CLÍNICA ODONTOLÓGICA SONRISAS FELICES",
                        SwingConstants.CENTER
                );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        add(titulo, BorderLayout.NORTH);

        //---------------------------------------
        // TABS
        //---------------------------------------

        JTabbedPane tabs =
                new JTabbedPane();

        tabs.addTab(
                "Pacientes",
                new PanelPaciente()
        );

        tabs.addTab(
                "Odontólogos",
                new PanelOdontologo()
        );

        tabs.addTab(
                "Turnos",
                new PanelTurno()
        );

        tabs.addTab(
                "Búsquedas",
                new PanelBusquedaAvanzada()
        );

        add(
                tabs,
                BorderLayout.CENTER
        );
    }
}