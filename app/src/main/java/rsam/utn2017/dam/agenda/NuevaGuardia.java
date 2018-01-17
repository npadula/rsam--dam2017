package rsam.utn2017.dam.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import rsam.utn2017.dam.agenda.model.Guardia;
import rsam.utn2017.dam.agenda.model.Usuario;
import rsam.utn2017.dam.agenda.model.Utils;

public class NuevaGuardia extends AppCompatActivity {

    private Guardia guardia;
    private TextView txtEquipo;
    private Button btnCrearGuardia;
    private Button btnReiniciar;
    private ListView miLista;
    private Button btnAgregar;
    private Usuario[] elementos;
    private ArrayAdapter<Usuario> miAdaptador;
    private Usuario usuarioSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_guardia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        guardia = new Guardia();
        txtEquipo = (TextView) findViewById(R.id.txtEquipo);
        btnCrearGuardia = (Button) findViewById(R.id.btnCrearGuardia);
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);


        miLista = (ListView) findViewById(R.id.lista);
        btnAgregar = (Button)findViewById(R.id.btnAgregar);

        Utils utils = new Utils();
        utils.iniciarListas();

        elementos= utils.getListaPersonas();
        ArrayList<Usuario> lst = new ArrayList<Usuario>(Arrays.asList(elementos));

        miAdaptador= new ArrayAdapter<Usuario>(
                this,
                android.R.layout.simple_list_item_single_choice,
                lst);


        miLista.setAdapter( miAdaptador );



        btnCrearGuardia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Persistir via REST y volver a pantalla de guardias
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciarGuardia();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usuarioSeleccionado != null){

                    guardia.addUsuario(usuarioSeleccionado);

                    txtEquipo.setText(guardia.getTextoEquipo());

                }

            }
        });

        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                usuarioSeleccionado = (Usuario) miLista.getItemAtPosition(i);
                String itemText = usuarioSeleccionado.toString();

            }
        });







    }

    private void reiniciarGuardia() {
    }

    private void clearListViewSelection(){
        usuarioSeleccionado = null;
        miLista.clearChoices();
        miAdaptador.notifyDataSetChanged();
    }
    private void resetAdapterDataSet(Usuario[] newDataSet) {

        miAdaptador.clear();
        clearListViewSelection();
        ArrayList<Usuario> lst = new ArrayList<Usuario>(Arrays.asList(newDataSet));
        miAdaptador.addAll(newDataSet);
        miAdaptador.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("GUARDIA",guardia);
        outState.putString("EQUIPO_TEXT",txtEquipo.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle previousState){
        super.onRestoreInstanceState(previousState);

        guardia= (Guardia) previousState.getSerializable("GUARDIA");
        txtEquipo.setText(previousState.getString("EQUIPO_TEXT"));

    }

}
