package rsam.utn2017.dam.agenda.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by npadula on 22/1/2018.
 */

public class Lugar implements Parcelable{
    private LatLng ubicacion;

    public Lugar(double lat, double lng){
        ubicacion = new LatLng(lat,lng);
    }

    public Lugar(Parcel parcel){
        Double lat = parcel.readDouble();
        Double lng = parcel.readDouble();
        ubicacion = new LatLng(lat,lng);
    }

    public static final Creator<Lugar> CREATOR = new Creator<Lugar>() {
        @Override
        public Lugar createFromParcel(Parcel in) {
            return new Lugar(in);
        }

        @Override
        public Lugar[] newArray(int size) {
            return new Lugar[size];
        }
    };

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public String toJSON(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("lat",ubicacion.latitude);
            obj.put("lng",ubicacion.longitude);

            return obj.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(ubicacion.latitude);
        parcel.writeDouble(ubicacion.longitude);
    }
}
