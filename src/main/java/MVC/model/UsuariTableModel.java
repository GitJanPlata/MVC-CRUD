package MVC.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsuariTableModel extends AbstractTableModel {
    //Model de la taula que ha de mostrar java definint la seva estructura i característiques.
    private List<Model.Usuari> usuaris;
    private String[] columnNames = {"ID", "Nom", "Correu Electronic", "Edat"};

    public UsuariTableModel(List<Model.Usuari> usuaris) {
        this.usuaris = usuaris;
    }

    @Override
    public int getRowCount() {
        return usuaris.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Model.Usuari usuari = usuaris.get(rowIndex);
        switch (columnIndex) {
            case 0: return usuari.getId();
            case 1: return usuari.getNom();
            case 2: return usuari.getCorreuElectronic();
            case 3: return usuari.getEdat();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}