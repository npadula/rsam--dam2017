package rsam.utn2017.dam.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import rsam.utn2017.dam.agenda.dal.DAO;
import rsam.utn2017.dam.agenda.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    Button btnResidente;
    Button btnInstructor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final Intent i = new Intent(LoginActivity.this,MainActivity.class);
        btnResidente = (Button) findViewById(R.id.btnResidente);
        btnInstructor = (Button) findViewById(R.id.btnInstructor);


        btnResidente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                FirebaseMessaging.getInstance().subscribeToTopic("residentes");
                i.putExtra("USR", "RESIDENTE");
                startActivity(i);


            }
        });



        btnInstructor.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //FirebaseMessaging.getInstance().unsubscribeFromTopic("residentes");
                i.putExtra("USR", "INSTRUCTOR");
                startActivity(i);
            }
        });

    }
}
