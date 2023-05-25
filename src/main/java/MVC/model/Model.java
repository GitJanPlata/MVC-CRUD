package MVC.model;

    public class Model{
        //CRUD dels objectes usuari que s'inseriran a la taula de la BD.
        public static class Usuari {
            private String id;
            private String nom;
            private String correuElectronic;
            private String Edat;


            public String getEdat() {
                return Edat;
            }
            public String getId() {
                return id;
            }
            public String getNom() {
                return nom;
            }
            public String getCorreuElectronic() {
                return correuElectronic;
            }
            public void setEdat(String edat) {
                Edat = edat;
            }
            public void setId(String id) {
                this.id = id;
            }
            public void setNom(String nom) {
                this.nom = nom;
            }
            public void setCorreuElectronic(String correuElectronic) {
                this.correuElectronic = correuElectronic;
            }


        }

    }
