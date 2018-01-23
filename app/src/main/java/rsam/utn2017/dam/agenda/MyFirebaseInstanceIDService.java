package rsam.utn2017.dam.agenda;

import android.app.Service;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by npadula on 22/1/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Refreshed token: SERVICIO CREADO!!!!");

    }

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        saveTokenToPrefs(refreshedToken);
    }

    private void saveTokenToPrefs(String _token)
    {
        // Access Shared Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // Save to SharedPreferences
        editor.putString("registration_id", _token);
        editor.apply();
    }

    private String getTokenFromPrefs()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }
}
