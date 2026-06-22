package view;

import model.Odontologo;
import repository.RepositorioOdontologo;
import service.ServicioOdontologo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelOdontologo extends JPanel {

    private ServicioOdontologo servicio;

    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelOdontologo() {

        servicio =
                new ServicioOdontologo(
                        new RepositorioOdontologo()
                );

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla),
                BorderLayout.CENTER);

        actualizarTabla();
    }

    private void actualizarTabla(){

        modelo.setRowCount(0);

        for(Odontologo o :
                servicio.listarOdontologos()){

            modelo.addRow(new Object[]{
                    o.getId(),
                    o.getNombre(),
                    o.getApellido()
            });

        }

    }

}