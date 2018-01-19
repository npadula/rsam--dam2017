package rsam.utn2017.dam.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rsam.utn2017.dam.agenda.dal.DAO;
import rsam.utn2017.dam.agenda.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    Button btnResidente;
    Button btnInstructor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Runnable r = new Runnable() {
            @Override
            public void run() {
                 DAO dao = new DAO();
                 final Usuario u = dao.getUsuarioByID(1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnInstructor.setText(u.getNombre());
                    }
                });
            }
        };

        Thread t = new Thread(r);
        t.start();


        final Intent i = new Intent(LoginActivity.this,MainActivity.class);
        btnResidente = (Button) findViewById(R.id.btnResidente);
        btnInstructor = (Button) findViewById(R.id.btnInstructor);


        btnResidente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                i.putExtra("USR", "RESIDENTE");
                startActivity(i);
            }
        });



        btnInstructor.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                i.putExtra("USR", "INSTRUCTOR");
                startActivity(i);
            }
        });

    }
}
