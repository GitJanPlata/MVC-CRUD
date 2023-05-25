package MVC.model;

import java.sql.Connection;
import java.util.List;

public interface UsuariDAOInterface<T> {

        Connection getConnection() throws ConnexioInvalidaException;

        void insertarUsuari(T usuari) throws DadesInvalidesException;
        T llegirUsuari(String id)throws DadesInvalidesException;
        void actualitzarUsuari(T usuari) throws DadesInvalidesException;
        void eliminarUsuari(String id) throws DadesInvalidesException;
        List<T> obtenirUsuaris()throws DadesInvalidesException;
}
