package com.example.pruebpractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import android.app.Fragment;
//import android.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class PanelActivity extends AppCompatActivity implements ComunicaMenu{


    //Array
     Fragment[] losFragments;
     MapsFragment f;
     //FragmentActivity fragmentActivityMapa;

    private TextView puntaje;

    private MapsFragment mapsFragment;
    private CuestionarioFragment cuestionarioFragment;
    private TiendaFragment tiendaFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        //Inicializo el array de fragmentos, y en cada posición inicializo un fragmento especifico
        //losFragments = new Fragment[3];
         //f = new MapsFragment();
        //losFragments[0] = new MapsFragment();
        //losFragments[0] = new MapaFragment();
        //fragmentActivityMapa = new MapaFragment();
        //losFragments[1] = new CuestionarioFragment();
        //losFragments[2] = new TiendaFragment();

        mapsFragment = new MapsFragment();
        cuestionarioFragment = new CuestionarioFragment();
        tiendaFragment = new TiendaFragment();

        puntaje = findViewById(R.id.puntaje);
        //Para obtener la informacion que me viene de activity main
 //       Bundle extras = getIntent().getExtras();

        //Para extraer la información que llega y lo paso a el metodo menu
//        menu(extras.getInt("BOTON_PULSADO"));

    }

    @Override
    public void menu(int botonUndido) {

        //Gestionador de fragments
        FragmentManager manejadorFragments = getSupportFragmentManager();

        //Conseguir la transsaccion
        FragmentTransaction transaccionFragments = manejadorFragments.beginTransaction();

        if (botonUndido == 0) {
            transaccionFragments.replace(R.id.panelcentro, mapsFragment);
            transaccionFragments.commit();
        }

        //Verifico qie este en el area
        if (botonUndido == 1) {
            if (mapsFragment.getEstaEnArea()) {
                transaccionFragments.replace(R.id.panelcentro, cuestionarioFragment);
                transaccionFragments.commit();
                //puntaje.setText("Puntos: "+cuestionarioFragment.getPuntos());
            }else{
                Toast.makeText(this,"No está en el área del M o Auditorio Manuelita para acceder",Toast.LENGTH_SHORT).show();
           }
        }


        if (botonUndido == 2) {
            if(mapsFragment.getEstaEnBiblioteca()) {
                transaccionFragments.replace(R.id.panelcentro, tiendaFragment);
                transaccionFragments.commit();
                String[] num = puntaje.getText().toString().split(":");
                tiendaFragment.setPuntajeTienda(num[1]);

                //tiendaFragment.setPuntajeTienda(puntaje.getText().toString());
            }else{
                Toast.makeText(this,"No está en Biblioteca para acceder a la Tienda",Toast.LENGTH_SHORT).show();
            }
        }




    }

    @Override
    protected void onPause() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("texto",puntaje.getText().toString());
        editor.apply();

        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        String texto = PreferenceManager.getDefaultSharedPreferences(this).getString("texto", "0");
        puntaje.setText(texto);

    }
}
