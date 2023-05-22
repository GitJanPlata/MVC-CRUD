package MVC.controller;

import MVC.model.DadesInvalidesException;
import MVC.model.Model;
import MVC.model.UsuariDAOImpl;
import MVC.model.UsuariTableModel;
import MVC.view.View;

import javax.swing.*;
import java.util.List;

public class Controlador {
    private View vista;
    private UsuariDAOImpl model;

    private void actualitzarTabla() {
        List<Model.Usuari> usuaris = model.obtenirUsuaris();
        UsuariTableModel tableModel = new UsuariTableModel(usuaris);
        vista.getTable1().setModel(tableModel);
    }
    public Controlador(View vista, UsuariDAOImpl modelo) {
        this.vista = vista;
        this.model = modelo;
        actualitzarTabla();

        // agregar ActionListeners a los botones
        vista.getInsertarButton().addActionListener(e -> {
            try {
                insertarUsuari();
            } catch (DadesInvalidesException ex) {
                throw new RuntimeException(ex);
            }
        });
        vista.getModificarButton().addActionListener(e -> {
            actualitzarUsuari();
        });
        vista.getLlegirButton().addActionListener(e -> llegirUsuari());
        vista.getBorrarButton().addActionListener(e -> {
            eliminarUsuari();
        });
    }

    private void insertarUsuari() throws DadesInvalidesException {

        // Crea els campos de text per al formulari.
        JTextField nomField = new JTextField(5);
        JTextField correuElectronicField = new JTextField(5);
        JTextField edatField = new JTextField(5);

        // Crea el panel y añade los campos de texto.
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Nom:"));
        myPanel.add(nomField);
        myPanel.add(new JLabel("Correu:"));
        myPanel.add(correuElectronicField);
        myPanel.add(new JLabel("Edat:"));
        myPanel.add(edatField);

        // Mostra el panel en un JOptionPane.
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Introdueix els valors", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Si l'usuari presiona OK, obtindrem els valors dels camps de text.
            String nom = nomField.getText();
            String correuElectronic = correuElectronicField.getText();
            String edat = edatField.getText();

            // Crea un nou objecte Usuari.
            Model.Usuari usuari = new Model.Usuari();
            usuari.setNom(nom);
            usuari.setCorreuElectronic(correuElectronic);
            usuari.setEdat(edat);

            // Inserta l'usuari a la base de dades.
            model.insertarUsuari(usuari);
            actualitzarTabla();
        }
    }

    private void actualitzarUsuari() {
        String id = JOptionPane.showInputDialog(null, "Introdueix l'ID de l'usuari a modificar:", "ID Usuari", JOptionPane.QUESTION_MESSAGE);

        // Comprova si l'usuari ha especificat un ID
        if (id != null && !id.isEmpty()) {
            try {
                Integer.parseInt(id); // intenta convertir el ID a un número enter

                JTextField fieldNom = new JTextField(5);
                JTextField fieldCorreuElectronic = new JTextField(5);
                JTextField fieldEdat = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
                myPanel.add(new JLabel("Nom:"));
                myPanel.add(fieldNom);
                myPanel.add(new JLabel("Correu Electronic:"));
                myPanel.add(fieldCorreuElectronic);
                myPanel.add(new JLabel("Edat:"));
                myPanel.add(fieldEdat);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Introdueix els valors de l'usuari", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    Model.Usuari usuari = new Model.Usuari();
                    usuari.setId(id);
                    usuari.setNom(fieldNom.getText());
                    usuari.setCorreuElectronic(fieldCorreuElectronic.getText());
                    usuari.setEdat(fieldEdat.getText());

                    model.actualitzarUsuari(usuari);
                    actualitzarTabla();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El ID ha de ser un número enter.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No s'ha introduït un ID d'usuari. valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void llegirUsuari() {
        JTextField fieldId = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Id:"));
        myPanel.add(fieldId);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Introdueix l'id de l'usuari", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Lee el usuario de la base de datos.
            Model.Usuari usuari = model.llegirUsuari(fieldId.getText());

            if (usuari != null) {
                // Create a new JPanel to display the user information
                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                infoPanel.add(new JLabel("Nom: " + usuari.getNom()));
                infoPanel.add(Box.createVerticalStrut(10));
                infoPanel.add(new JLabel("Correu Electronic: " + usuari.getCorreuElectronic()));
                infoPanel.add(Box.createVerticalStrut(10));
                infoPanel.add(new JLabel("Edat: " + usuari.getEdat()));

                // Show the user information in a new JOptionPane
                JOptionPane.showMessageDialog(null, infoPanel, "Informació de l'usuari", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Introdueix un ID d'usuari valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarUsuari() {
        JTextField fieldId = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Id:"));
        myPanel.add(fieldId);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Insereix el Id de l'usuari a borrar", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String id = fieldId.getText();
            try {
                Integer.parseInt(id);

                model.eliminarUsuari(id);
                actualitzarTabla();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El ID ha de ser un número enter.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}