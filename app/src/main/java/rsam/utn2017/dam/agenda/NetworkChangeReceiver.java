package rsam.utn2017.dam.agenda;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by npadula on 23/1/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    public NetworkChangeReceiver(){}
    @Override
    public void onReceive(final Context context, final Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) { // not connected to the internet
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.checkbox_on_background)
                            .setContentTitle("Conexión perdida")
                            .setTicker("Alerta!")
                            .setVibrate(new long[] {100, 250, 100, 500})
                            .setContentText("La aplicación requiere de conexión a internet para funcionar correctamente");
            nm.notify(1, mBuilder.build());
        }
    }
}
