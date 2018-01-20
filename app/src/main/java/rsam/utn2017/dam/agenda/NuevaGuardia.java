package rsam.utn2017.dam.agenda;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.RunnableFuture;

import rsam.utn2017.dam.agenda.dal.DAO;
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
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayAdapter<Usuario> miAdaptador;
    private Usuario usuarioSeleccionado;
    private Utils utils;
    private EditText fecha;
    private Calendar myCalendar;
    private int req;
    private DAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_guardia);
        dao = new DAO();
        Intent i = getIntent();
        req = i.getIntExtra("REQUEST",-1);

        guardia = new Guardia();
        txtEquipo = (TextView) findViewById(R.id.txtEquipo);
        btnCrearGuardia = (Button) findViewById(R.id.btnCrearGuardia);
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);


        miLista = (ListView) findViewById(R.id.lista);
        btnAgregar = (Button)findViewById(R.id.btnAgregar);





        miAdaptador= new ArrayAdapter<Usuario>(
                this,
                android.R.layout.simple_list_item_single_choice,
                usuarios);


        miLista.setAdapter( miAdaptador );

        reiniciarGuardia();
        fecha = (EditText) findViewById(R.id.fecha);



        if(req == MainActivity.EDITAR_GUARDIA){
            Guardia g = (Guardia)i.getParcelableExtra("GUARDIA");
            guardia = g;
            txtEquipo.setText(g.getTextoEquipo());


        }

        btnCrearGuardia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op = "";
                if(req != MainActivity.EDITAR_GUARDIA){

                    //guardia.setId( dao.guardias().get(dao.guardias().size() - 1).getId() + 1);
                    op = "NUEVO";
                }
                else {
                    op = "EDICION";
                }

                Intent resultado = getIntent();
                resultado.putExtra("GUARDIA",(Parcelable) guardia);
                resultado.putExtra("OPERACION",op);
                resultado.putExtra("RESULTADO","OK" );
                setResult(RESULT_OK, resultado);
                finish();


            }
        });

        setPicker();

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

                    ArrayList<Usuario> newDataSet = new ArrayList<Usuario>(usuarios);
                    newDataSet.remove(usuarioSeleccionado);
                    usuarios = newDataSet;
                    resetAdapterDataSet(usuarios);


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

    private void setPicker(){
       myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setFechaGuardia();
            }

        };

        fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NuevaGuardia.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setFechaGuardia() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fecha.setText(sdf.format(myCalendar.getTime()));

        guardia.setFecha(myCalendar.getTime());
    }


    private void reiniciarGuardia() {
        guardia.resetEquipo();
        guardia.setFecha(new Date());
        txtEquipo.setText("");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                usuarios = dao.usuarios();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resetAdapterDataSet(usuarios);
                    }
                });
            }
        };

        Thread t = new Thread(r);
        t.start();

    }

    private void clearListViewSelection(){
        usuarioSeleccionado = null;
        miLista.clearChoices();
        miAdaptador.notifyDataSetChanged();
    }
    private void resetAdapterDataSet(ArrayList<Usuario> newDataSet) {

        miAdaptador.clear();
        clearListViewSelection();
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
