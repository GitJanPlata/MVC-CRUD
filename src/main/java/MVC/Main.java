package MVC;

import MVC.controller.Controlador;
import MVC.model.UsuariDAOImpl;
import MVC.view.View;


public class Main {
    public static void main(String[] args) {
        View vista = new View();
        UsuariDAOImpl model = new UsuariDAOImpl();
        new Controlador(vista, model);
    }
}

