package rsam.utn2017.dam.agenda;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rsam.utn2017.dam.agenda.dal.DAO;
import rsam.utn2017.dam.agenda.model.Guardia;
import rsam.utn2017.dam.agenda.model.Lugar;

public class MainActivity extends AppCompatActivity implements CronogramaTabFragment.OnFragmentInteractionListener, NoticiasTabFragment.OnFragmentInteractionListener, GuardiaTabFragment.OnListFragmentInteractionListener {
public static int NUEVA_GUARDIA = 332;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private String tipousuario;
    private DAO dao;
    private ArrayList<Guardia> listaGuardias;
    private ArrayList<Lugar> lugares = new ArrayList<>();
    private GuardiaTabFragment guardiaFragment;
    public static int EDITAR_GUARDIA = 821;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guardiaFragment = new GuardiaTabFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        dao = new DAO();
        listaGuardias = new ArrayList<>();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevaGuardia = new Intent(MainActivity.this,NuevaGuardia.class);
                startActivityForResult(nuevaGuardia,NUEVA_GUARDIA);
            }
        });
        fab.hide();


        Runnable runnable = new UpdateDataSetRunnable("");


        Thread t = new Thread(runnable);
        t.start();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                final ArrayList<Lugar> _lugares  = dao.lugares();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lugares.clear();
                        lugares.addAll(_lugares);
                    }
                });
            }
        };

        Thread getLugares = new Thread(r);
        getLugares.start();

        Intent i = getIntent();
        tipousuario = i.getStringExtra("USR");

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 2:
                        if(tipousuario.equals("INSTRUCTOR"))
                            fab.show();
                        else
                            fab.hide();
                        break;
                    default:
                        fab.hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });







    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        if(resultCode == RESULT_OK){
            String opResult = "";
            Guardia g = (Guardia) data.getParcelableExtra("GUARDIA");
            if(requestCode == NUEVA_GUARDIA){
                dao.crear(g);

                opResult = "Guardia creada";
            }
            else if (requestCode == EDITAR_GUARDIA) {
                //
                String op = data.getStringExtra("OPERACION");
                if(op.equals("EDICION")){
                    dao.actualizar(g);
                    opResult = "Guardia modificada";
                }
                else{

                    //Eliminar reclamo
                    dao.borrar(g);
                    opResult = "Guardia eliminada";
            }

            }

            Runnable runnable = new UpdateDataSetRunnable(opResult);


            Thread t = new Thread(runnable);
            t.start();
        }
    }


    private class UpdateDataSetRunnable implements Runnable{

        private String _msg;
        UpdateDataSetRunnable(String msg){
            _msg = msg;
        }
        @Override
        public void run() {
            List<Guardia> _guardias = dao.guardias(true);
            listaGuardias.clear();
            listaGuardias.addAll(_guardias);
            runOnUiThread(new Runnable() {
                public void run() {

                    //adapter.notifyDataSetChanged();
                    guardiaFragment.updateDataSet(listaGuardias);
                    if(!_msg.isEmpty())
                        Toast.makeText(MainActivity.this,_msg,Toast.LENGTH_LONG).show();

                }
            });
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this,MapsActivity.class);
        i.putParcelableArrayListExtra("lugares",lugares);
        startActivity(i);

        //}
        return true;
        //return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(){}

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch(position){
                case 0:
                    return new CronogramaTabFragment();
                case 1:
                    return new NoticiasTabFragment();
                default:
                    return guardiaFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Cronograma";
                case 1:
                    return "Noticias";
                case 2:
                    return "Guardias";
            }
            return null;
        }
    }
}
