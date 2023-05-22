package MVC.view;

import javax.swing.*;

public class View extends JFrame {
    private JTextField campNom;
    private JTextField campCorreuElectronic;
    private JTextField campEdat;




    public JTextField getCampNom() {
        return campNom;
    }

    public JTextField getCampCorreuElectronic() {
        return campCorreuElectronic;
    }

    public JTextField getCampEdat() {
        return campEdat;
    }


    public JButton getModificarButton() {
        return modificarButton;
    }



    public JButton getLlegirButton() {
        return llegirButton;
    }



    public JButton getBorrarButton() {
        return borrarButton;
    }



    private JPanel panel1;
    private JLabel label1;
    private JTable table1;
    private JButton insertarButton;
    private JButton modificarButton;
    private JButton llegirButton;
    private JButton borrarButton;


    //Constructor de la classe
    public View() {
        campNom = new JTextField();
        campCorreuElectronic = new JTextField();
        campEdat = new JTextField();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);

    }


    public JButton getInsertarButton() {
        return insertarButton;
    }

    public JTable getTable1() {
        return table1;
    }
}

