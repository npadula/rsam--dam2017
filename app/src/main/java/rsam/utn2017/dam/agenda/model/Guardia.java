package rsam.utn2017.dam.agenda.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by npadula on 16/1/2018.
 */

public class Guardia implements Serializable {

        private ArrayList<Usuario> Equipo;
        private Date Fecha;


    public String getTextoEquipo() {
        String res = "";
        for(int i = 0;i<Equipo.size();i++){
            res = res + '\n' +  Equipo.get(i).toString();
        }

        return res;
    }
    public Guardia(){
        setEquipo(new ArrayList<Usuario>());
    }

    public void addUsuario(Usuario usuario) {
        Equipo.add(usuario);
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public ArrayList<Usuario> getEquipo() {
        return Equipo;
    }

    public void setEquipo(ArrayList<Usuario> equipo) {
        Equipo = equipo;
    }

    public void resetEquipo() {
        Equipo = new ArrayList<>();
    }
}
