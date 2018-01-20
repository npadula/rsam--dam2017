package rsam.utn2017.dam.agenda.dal;

/**
 * Created by npadula on 18/1/2018.
 */

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Guard;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RunnableFuture;

import rsam.utn2017.dam.agenda.model.Guardia;
import rsam.utn2017.dam.agenda.model.Usuario;


/**
 * Created by mdominguez on 26/10/17.
 */

public class DAO  {

    private ArrayList<Guardia> listaGuardias = null;
    public ArrayList<Usuario> usuarios = null;
    private String server;
    private MyGenericHTTPClient cliente;

    public DAO(){
        //para emulador
        server="http://10.0.2.2:3000";

        //para celular
        //server="http://192.168.42.29:3000";
        //server = "http://192.168.0.107:3000"; //(nico)
        cliente = new MyGenericHTTPClient(server);
    }

    public DAO(String server){
        this.server=server;
        cliente=new MyGenericHTTPClient(server);
    }



   /* public List<Estado> estados() {
        if(tiposEstados!=null && tiposEstados.size()>0) return this.tiposEstados;
        else{
            String estadosJSON = cliente.getAll("estado");
            try {
                JSONArray arr = new JSONArray(estadosJSON);
                for(int i=0;i<arr.length();i++){
                    JSONObject unaFila = arr.getJSONObject(i);
                    tiposEstados.add(new Estado(unaFila.getInt("id"),unaFila.getString("tipo")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tiposEstados;
    }*/


 public ArrayList<Usuario> usuarios(){
     if(usuarios!=null && usuarios.size()>0) return this.usuarios;

     else{
         usuarios = new ArrayList<>();
         String estadosJSON = cliente.getAll("usuarios");
         try {
             JSONArray arr = new JSONArray(estadosJSON);
             for(int i=0;i<arr.length();i++){
                 JSONObject unaFila = arr.getJSONObject(i);
                 usuarios.add(new Usuario(unaFila.getInt("id"),unaFila.getString("dni"),unaFila.getString("nombre")));
             }
         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
     return usuarios;
 }


    public List<Guardia> guardias(Boolean reload) {

        if(listaGuardias!=null && !reload) return this.listaGuardias;

        listaGuardias = new ArrayList<>();
        String guardiasJSON = cliente.getAll("guardias");

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        try {
            JSONArray arr = new JSONArray(guardiasJSON);
            for(int i=0;i<arr.length();i++){
                JSONObject unaFila = arr.getJSONObject(i);
                Guardia guardia = new Guardia();
                guardia.setId(unaFila.getInt("id"));

                String str = unaFila.getString("fecha");
                Date f;
                try{
                    f = sdf.parse(str);
                }
                catch (Exception ex){
                    f = new Date();
                }

                guardia.setFecha(f);


                JSONArray equipo = unaFila.getJSONArray("equipo");
                guardia.setEquipo(new ArrayList<Usuario>());
                for(int j = 0;j<equipo.length();j++){
                    JSONObject obj2 = equipo.getJSONObject(j);
                    Integer uID = obj2.getInt("uID");
                    Usuario u = getUsuarioByID(uID);
                    guardia.addUsuario(u);

                }


                listaGuardias.add(guardia);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaGuardias;
    }

    public Usuario getUsuarioByID(Integer uID) {
        Usuario objResult = null;

        if(this.usuarios!=null){
            for(Usuario e:usuarios){
                if(e.getId()==uID) return e;
            }
        }else{
            String estadoJSON = cliente.getById("usuarios",uID);
            try {
                JSONObject unaFila = new JSONObject(estadoJSON);
                objResult = new Usuario(unaFila.getInt("id"),unaFila.getString("dni"),unaFila.getString("nombre"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return objResult;
    }


    public Guardia getGuardiaById(Integer id){
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Guardia guardia =new Guardia();
        if(this.listaGuardias!=null){
            for(Guardia e:listaGuardias){
                if(e.getId()==id) return e;
            }
        }else{
            String estadoJSON = cliente.getById("guardias",id);
            try {
                JSONObject unaFila = new JSONObject(estadoJSON);
                guardia.setId(unaFila.getInt("id"));

                String str = unaFila.getString("fecha");
                Date f;
                try{
                    f = sdf.parse(str);
                }
                catch (Exception ex){
                    f = new Date();
                }


                JSONArray equipo = unaFila.getJSONArray("equipo");
                guardia.setEquipo(new ArrayList<Usuario>());
                for(int j = 0;j<equipo.length();j++){
                    JSONObject obj2 = equipo.getJSONObject(j);
                    Integer uID = obj2.getInt("uID");
                    Usuario u = getUsuarioByID(uID);
                    guardia.addUsuario(u);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return guardia;
    }



    public void crear(final Guardia g) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Integer newID = guardias(false).size() + 1;
                g.setId(newID);
                cliente.post("guardias",g.toJSON());
            }
        };

        Thread t = new Thread(r);
        t.start();

    }


    public void actualizar(Guardia g) {
        cliente.put("guardias/" + g.getId().toString(),g.toJSON());
    }


    public void borrar(Guardia g) {
        cliente.delete("guardias",g.getId());
    }
}