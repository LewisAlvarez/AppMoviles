package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto2.model.ClasePlayList;
import com.example.reto2.model.ClaseSong;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ListaDeCanciones extends AppCompatActivity {

    private TextView nombrePlayList;
    private TextView descripcion;
    private TextView cancioneYFans;

    private ImageView imagenAlbum;

    private ListView listaCanciones;
    //private ArrayAdapter adapter;
    private AdaptadorCanciones adapter;

    private ArrayList<String> informacion;
    private ArrayList<String> infoCompletaCancion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_canciones);

        nombrePlayList = findViewById(R.id.nombreplaylist);
        descripcion = findViewById(R.id.descripcionplaylist);
        cancioneYFans = findViewById(R.id.cantidadcancionesyfans);

        imagenAlbum = findViewById(R.id.imagenalbum);


        listaCanciones = findViewById(R.id.listacanciones);

        //Recibo lo que me manda main activity
        Bundle extras = getIntent().getExtras();
        //Para extraer la información que llega y lo paso a el metodo menu
        String infoPlayList = extras.getString("LISTA_UNDIDA");
        String datos= extras.getString("INFO");

        informacion = new ArrayList<String>();
        infoCompletaCancion = new ArrayList<String>();

        //Aqui en estas variables guardo la informacion del playlist undido
        String titulo = datos.split("  -  ")[0];
        //String numFans = datos.split("  -  ")[3]: //Así era la cantidad de fans originalmente
        String numFans = (int)(Math.random()*220+1) +""; //Lo puse así porque el api al buscar no me soltaba la cantidad de fans
        String numCanciones = datos.split("  -  ")[1];
        String urlImagenAlbum = datos.split("  -  ")[2];

        adapter = new AdaptadorCanciones(infoCompletaCancion,this);
        listaCanciones.setAdapter(adapter);

        actualizarPlayList(titulo,"",numFans,numCanciones);
        Picasso.get().load(urlImagenAlbum).into(imagenAlbum);


        new Thread(
                () -> {
                    try{
                        HTTPSWebUtilDomi h = new HTTPSWebUtilDomi();
                        String urlJson = h.GETrequest(infoPlayList);
                        Gson gson = new Gson();
                        //Toast.makeText(this,urlCanciones,Toast.LENGTH_SHORT).show();
                        ClaseSong claseSong = gson.fromJson(urlJson, ClaseSong.class);

                        //COn el Intent mandar el arraylist a la otra clase para que inicialice los evento
                        int tamanho = claseSong.getData().size();

                        //Log.e(">>>>>", tamanho+" <- Data y Artis_: "+ claseSong.getArtist().size());

                        for (int i = 0; i < tamanho; i++){
                            //Titulo de la cancion
                            String messageVista = claseSong.getData().get(i).getTitle();
                            String datosCompletos = claseSong.getData().get(i).getTitle()+"  -  "+claseSong.getData().get(i).getDuration()+"  -  "
                                    +claseSong.getData().get(i).getPreview()+"  -  "+claseSong.getData().get(i).getArtist().getName()+"  -  "
                                    +claseSong.getData().get(i).getArtist().getPicture()+"  -  "+claseSong.getData().get(i).getAlbum().getTitle()
                                    +"  -  "+datos.split(" - ")[4];
                            infoCompletaCancion.add(datosCompletos);
                            informacion.add(messageVista);
                        }

                        runOnUiThread( ()-> {
                            adapter.notifyDataSetChanged();
                        });

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
        ).start();

          adapter.notifyDataSetChanged();
        Intent i = new Intent(this, Cancion.class);

        listaCanciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(), "Undió "+ position, Toast.LENGTH_SHORT).show();
                String informacionCancion = infoCompletaCancion.get(position);
                i.putExtra("CANCION_UNDIDA", informacionCancion);
                startActivity(i);

            }
        });


    }


    public void actualizarPlayList(String title, String description, String fans, String cantidadSongs){
        nombrePlayList.setText(title);
        descripcion.setText(description+"Esta Lista de reproducción es increible¡¡");
        cancioneYFans.setText(cantidadSongs +" Canciones "+fans+" Fans");
    }




}
