package rsam.utn2017.dam.agenda.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by npadula on 16/1/2018.
 */

public class Guardia implements Serializable, Parcelable {

        private ArrayList<Usuario> Equipo;
        private Date Fecha;
        private Integer id = 0;


    public String getTextoEquipo() {
        String res = "";
        for(int i = 0;i<Equipo.size();i++){
            res = res + '\n' +  Equipo.get(i).toString();
        }

        return res;
    }

    public String getMockText(){
        return "texto para ocupar espacio";
    }

    public Guardia(Parcel in){
        id = in.readInt();
        Fecha = (Date)in.readSerializable();
        Equipo = in.readArrayList(Usuario.class.getClassLoader());
    }
    public Guardia(){
        setEquipo(new ArrayList<Usuario>());
    }
    public Guardia(int id){
        setEquipo(new ArrayList<Usuario>());
        this.id = id;
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


    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeSerializable(Fecha);
        parcel.writeList(Equipo);
    }

    public static final Creator CREATOR = new Creator() {
        public Guardia createFromParcel(Parcel in) {
            return new Guardia(in);
        }

        public Guardia[] newArray(int size) {
            return new Guardia[size];
        }
    };


    public String toJSON(){
        JSONObject obj = new JSONObject();
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        try {
            obj.put("id",this.getId());
            obj.put("fecha",sdf.format(getFecha()));
            JSONArray equipo = new JSONArray();

            for(int i =0;i<Equipo.size();i++){
                Usuario u = Equipo.get(i);
                JSONObject obj2 = new JSONObject();
                obj2.put("uID",u.getId());
                equipo.put(obj2);


            }

            obj.put("equipo",equipo);


            return obj.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
