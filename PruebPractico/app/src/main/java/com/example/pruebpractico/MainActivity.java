package com.example.pruebpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ComunicaMenu{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //Ahora ya sabemos que boton ha sido pulsado, lo ue tenemos que hacer es actualizar el panel
    @Override
    public void menu(int botonUndido) {

        //Para mandarlo a la actividad del panel
        Intent i = new Intent(this, PanelActivity.class);
        //Almacenar dentro del objeto la información que quiero pasar a la otra actividad
        i.putExtra("BOTON_PULSADO", botonUndido);

        //Comience la actividad
        startActivity(i);

    }
}
