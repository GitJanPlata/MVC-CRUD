package MVC.model;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class UsuariDAOImpl implements UsuariDAOInterface<Model.Usuari> {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "sys as sysdba";
    private static final String PASS = "162710";


    public Connection getConnection() throws ConnexioInvalidaException {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnexioInvalidaException("Error a l'establir la connexió: " + e.getMessage());
        }
        return connection;
    }
    public void insertarUsuari(Model.Usuari usuari)  {
        String sql = "INSERT INTO Usuaris ( nom, correu_electronic, edat) VALUES (?, ?, ?)";

        // Validar les dades d'entrada
        try {
            if (!usuari.getNom().matches("[a-zA-Z ]+")) {
                throw new DadesInvalidesException("El nom nomes pot contindre lletres i espais");
            }
            if (!usuari.getCorreuElectronic().contains("@")) {
                throw new DadesInvalidesException("El correu electrónic ha de contindre un '@'");
            }
            try {
                Integer.parseInt(usuari.getEdat());
            } catch (NumberFormatException e) {
                throw new DadesInvalidesException("La edat ha de ser un número enter");
            }
        } catch ( DadesInvalidesException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
            return;
        }

        // Si les dades son válides, procedir amb la inserció
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuari.getNom());
            pstmt.setString(2, usuari.getCorreuElectronic());
            pstmt.setString(3, usuari.getEdat());

            pstmt.executeUpdate();
        } catch (SQLException | ConnexioInvalidaException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error al insertar l'usuari: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
        }
    }
    public Model.Usuari llegirUsuari(String id) {
        String sql = "SELECT * FROM Usuaris WHERE id = ?";
        Model.Usuari usuari = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuari = new Model.Usuari();
                usuari.setId(rs.getString("id"));
                usuari.setNom(rs.getString("nom"));
                usuari.setCorreuElectronic(rs.getString("correu_electronic"));
                usuari.setEdat(rs.getString("edat"));
            }
        } catch (SQLException | ConnexioInvalidaException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error al llegir l'usuari: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
        }

        return usuari;
    }

    public void actualitzarUsuari(Model.Usuari usuari) {
        String sql = "UPDATE Usuaris SET nom = ?, correu_electronic = ?, edat = ? WHERE id = ?";

        // Validar les dades d'entrada
        try {
            if (!usuari.getNom().matches("[a-zA-Z ]+")) {
                throw new DadesInvalidesException("El nom nomes pot contindre lletres i espais");
            }
            if (!usuari.getCorreuElectronic().contains("@")) {
                throw new DadesInvalidesException("El correu electrónic ha de contindre un '@'");
            }
            try {
                Integer.parseInt(usuari.getEdat());
            } catch (NumberFormatException e) {
                throw new DadesInvalidesException("La edat ha de ser un número enter");
            }
        } catch (DadesInvalidesException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
            return;
        }

        // Si les dades són vàlides, procedeix amb l'actualització
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuari.getNom());
            pstmt.setString(2, usuari.getCorreuElectronic());
            pstmt.setString(3, usuari.getEdat());
            pstmt.setString(4, usuari.getId());

            pstmt.executeUpdate();
        } catch (SQLException | ConnexioInvalidaException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error al modificar l'usuari: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
        }
    }

    public void eliminarUsuari(String id)   {
        while (!UsuariExists(id)) {
            JOptionPane.showMessageDialog(null, "Aquest ID no existeix!", "Error", JOptionPane.ERROR_MESSAGE);
            id = JOptionPane.showInputDialog(null, "Introdueix el ID del usuari a eliminar", "Eliminar usuari", JOptionPane.QUESTION_MESSAGE);
            if ((id)==null){
                break;
            }
        }

        String sql = "DELETE FROM Usuaris WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);

            pstmt.executeUpdate();
        } catch (SQLException | ConnexioInvalidaException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error a l'esborrar l'usuari: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
        }}


    private boolean UsuariExists(String id) {
        String sql = "SELECT * FROM Usuaris WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException | ConnexioInvalidaException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error al verificar l'usuari: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
        }

        return false;
    }
    public List<Model.Usuari> obtenirUsuaris() {
        String sql = "SELECT * FROM Usuaris";
        List<Model.Usuari> usuaris = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Model.Usuari usuari = new Model.Usuari();
                usuari.setId(rs.getString("id"));
                usuari.setNom(rs.getString("nom"));
                usuari.setCorreuElectronic(rs.getString("correu_electronic"));
                usuari.setEdat(rs.getString("edat"));
                usuaris.add(usuari);
            }
        } catch (SQLException | ConnexioInvalidaException e) {
            System.out.println(e.getMessage());
        }

        return usuaris;
    }

}
