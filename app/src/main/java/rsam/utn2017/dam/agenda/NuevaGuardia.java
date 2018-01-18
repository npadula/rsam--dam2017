package rsam.utn2017.dam.agenda;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
    private Utils utils;
    private EditText fecha;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_guardia);


        guardia = new Guardia();
        txtEquipo = (TextView) findViewById(R.id.txtEquipo);
        btnCrearGuardia = (Button) findViewById(R.id.btnCrearGuardia);
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);


        miLista = (ListView) findViewById(R.id.lista);
        btnAgregar = (Button)findViewById(R.id.btnAgregar);

        utils = new Utils();
        utils.iniciarListas();

        elementos= utils.getListaPersonas();
        ArrayList<Usuario> lst = new ArrayList<Usuario>(Arrays.asList(elementos));

        miAdaptador= new ArrayAdapter<Usuario>(
                this,
                android.R.layout.simple_list_item_single_choice,
                lst);


        miLista.setAdapter( miAdaptador );


        fecha = (EditText) findViewById(R.id.fecha);


        btnCrearGuardia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Persistir via REST y volver a pantalla de guardias


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

                    ArrayList<Usuario> newDataSet = new ArrayList<Usuario>(Arrays.asList(elementos));
                    newDataSet.remove(usuarioSeleccionado);
                    elementos = newDataSet.toArray(new Usuario[0]);
                    resetAdapterDataSet(elementos);


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
        elementos = utils.getListaPersonas();
        resetAdapterDataSet(elementos);
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
