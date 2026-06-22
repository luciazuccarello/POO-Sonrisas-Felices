package view;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {

        setTitle("Clinica Odontologica");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Pacientes", new PanelPaciente());
        tabs.add("Odontologos", new PanelOdontologo());
        tabs.add("Turnos", new PanelTurno());
        tabs.add("Busquedas", new PanelBusquedaAvanzada());

        add(tabs, BorderLayout.CENTER);
    }
}
