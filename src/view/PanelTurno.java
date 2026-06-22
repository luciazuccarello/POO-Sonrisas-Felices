package view;

import javax.swing.*;
import java.awt.*;

public class PanelTurno extends JPanel {

    public PanelTurno() {

        setLayout(new BorderLayout());

        add(
                new JLabel(
                        "Gestion de Turnos"
                ),
                BorderLayout.NORTH
        );

    }

}