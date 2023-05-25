package MVC.model;

import java.util.HashMap;
import java.util.Map;

public class DadesInvalidesException extends Exception {
    public DadesInvalidesException(String missatges) {
        super(missatges);
    }

}

class ConnexioInvalidaException extends Exception {
    public ConnexioInvalidaException(String missatges) {
        super(missatges);
    }

}