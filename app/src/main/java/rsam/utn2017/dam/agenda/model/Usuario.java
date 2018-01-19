package rsam.utn2017.dam.agenda.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by npadula on 16/1/2018.
 */

public class Usuario implements Serializable, Parcelable {
    private String nombre;
    private String dni;
    private Integer id;

    public Usuario(Integer id, String dni, String nombre){
        this.id = id;
        this.setNombre(nombre);
        this.dni = dni;

    }

    public Usuario(Parcel parcel){
        id = parcel.readInt();
        dni = parcel.readString();
        nombre = parcel.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.dni);
        parcel.writeString(this.nombre);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final Creator CREATOR = new Creator() {
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };



    public String toJSON(){
        JSONObject obj = new JSONObject();

        try {
            obj.put("id",this.getId());
            obj.put("dni",this.dni);
            obj.put("nombre",this.nombre);


            return obj.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();

        try {
            obj.put("id",this.getId());
            obj.put("dni",this.dni);
            obj.put("nombre",this.nombre);


            return obj;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
