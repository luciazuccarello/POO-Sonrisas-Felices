package view;

import javax.swing.*;
import java.awt.*;

public class PanelBusquedaAvanzada extends JPanel {

    public PanelBusquedaAvanzada() {

        setLayout(new BorderLayout());

        add(
                new JLabel(
                        "Busqueda Avanzada"
                ),
                BorderLayout.NORTH
        );

    }

}