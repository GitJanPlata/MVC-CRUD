package MVC.model;

import java.sql.Connection;
import java.util.List;

public interface UsuariDAOInterface {
        Connection getConnection();
        void insertarUsuari(Model.Usuari usuari) throws DadesInvalidesException;
        Model.Usuari llegirUsuari(String id);
        void actualitzarUsuari(Model.Usuari usuari) throws DadesInvalidesException;
        void eliminarUsuari(String id) throws DadesInvalidesException;
        List<Model.Usuari> obtenirUsuaris();
    }
