package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.player.TrackPlayer;
import com.squareup.picasso.Picasso;

public class Cancion extends AppCompatActivity {

    private TextView nombreCancion;
    private TextView artistaCancion;
    private TextView albumCancion;
    private TextView duracionCancion;

    private Button btnEscucharCancion;
    private ImageView imagenCancion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancion);


        nombreCancion = findViewById(R.id.nombrecancion);
        artistaCancion = findViewById(R.id.artistacancion);
        albumCancion = findViewById(R.id.albumcancion);
        duracionCancion = findViewById(R.id.duracioncancion);

        imagenCancion = findViewById(R.id.imagencancion);
        btnEscucharCancion = findViewById(R.id.escuchar);


        //Deezer
        //TrackPlayer trackPlayer = new TrackPlayer(application, deezerConnect, new WifiAndMobileNetworkStateChecker());

        //Adquiero la información proveniente de la lista de canciones
        Bundle extras = getIntent().getExtras();
        //Para extraer la información que llega y lo paso a el metodo menu
        String infoPlayList = extras.getString("CANCION_UNDIDA");

        String [] infoSeparada = infoPlayList.split("  -  ");

        String nameCancion = infoSeparada[0];
        String artista = infoSeparada[3];
        String album = infoSeparada[5];
        String urlPictureArtist = infoSeparada[4];
        String duracion = infoSeparada[1];
        String reproducirCorto = infoSeparada[2];


        String time = segundoAMinuto(duracion);
     //   /*

        nombreCancion.setText(nameCancion);
        artistaCancion.setText(artista);
        duracionCancion.setText(time);
        albumCancion.setText(album);
 // */
        Picasso.get().load(urlPictureArtist).into(imagenCancion);

        btnEscucharCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri url = Uri.parse(reproducirCorto);
                Intent i = new Intent(Intent.ACTION_VIEW, url);
                startActivity(i);
                //finish();

            }
        });

    }


    public String segundoAMinuto(String segundos){

        double unSegundo = 0.0166667;
        String minutos = "";

        //try{
            int dato = Integer.parseInt(segundos);
            double multi = dato*unSegundo;
            String d = multi+"";
            int min = (int) multi;
            String seg = d.charAt(2) + "" + d.charAt(3) ;

            minutos = "Duración "+min+":"+seg;

       // }catch(Exception e){
       //     e.printStackTrace();
       // }

        return minutos;
    }



}
