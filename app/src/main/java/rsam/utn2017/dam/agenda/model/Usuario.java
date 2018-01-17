package rsam.utn2017.dam.agenda.model;

import java.io.Serializable;

/**
 * Created by npadula on 16/1/2018.
 */

public class Usuario implements Serializable{
    private String nombre;
    private String dni;

    public Usuario(String nombre, String dni){
        this.setNombre(nombre);
        this.dni = dni;
    }

    public Usuario(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return this.nombre;
    }
}
