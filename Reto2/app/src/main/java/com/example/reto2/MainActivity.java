package com.example.reto2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reto2.model.ClasePlayList;
import com.example.reto2.model.ClaseSong;
import com.example.reto2.model.PlayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String SEARCH = "https://api.deezer.com/search/playlist?q=";

    private ArrayList<String> playList;

    private ListView listaDeListaDeReproduccion;
    //private ArrayAdapter adapter;
    //Utilizo mi adaptador
    private Adaptador adapter;

    private EditText editText;
    private ImageButton button;
    //private ArrayList<Playlist> playlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.buscar);
        button = findViewById(R.id.lupa);

        playList = new ArrayList<String>();

        listaDeListaDeReproduccion = findViewById(R.id.listasdereproduccion);
        adapter = new Adaptador(playList,MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String parametro = editText.getText().toString();
                ArrayList<String> laLista = new ArrayList<>();

                if (!parametro.isEmpty()){

                    String ruta = SEARCH + parametro;

                    new Thread(
                            () -> {
                                try {
                                    HTTPSWebUtilDomi h = new HTTPSWebUtilDomi();
                                    String json = h.GETrequest(ruta+"");
                                    Gson gson = new Gson();

                                    ClasePlayList clasePlayList= gson.fromJson(json, ClasePlayList.class);


                                    int tam = clasePlayList.getData().size();
                                    for (int i = 0; i < tam; i++) {

                                        String messagePantalla = clasePlayList.getData().get(i).getTitle() + " - " + clasePlayList.getData().get(i).getNb_tracks();
                                        String urlPicture = clasePlayList.getData().get(i).getPicture();
                                        String infoCompleta = messagePantalla + " - " + urlPicture + " - " + clasePlayList.getData().get(i).getFans() + " - " + clasePlayList.getData().get(i).getTracklist()
                                                + " - " + clasePlayList.getData().get(i).getUser().getName() + " - " + clasePlayList.getData().get(i).getCreation_date();
                                        playList.add(infoCompleta);


                                    }

                                    runOnUiThread( ()-> {
                                        listaDeListaDeReproduccion.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    });

                                }catch(IOException e){
                                    e.printStackTrace();
                                }


                            }
                    ).start();

                }else{
                    Toast.makeText(MainActivity.this,"Valon Ingresado Inválido",Toast.LENGTH_SHORT).show();
                }

                editText.setText("");
                agregarPlaylist(playList);
//              adapter.notifyDataSetChanged();

            }

        });

    }


//Agrega la informacion organizada y en el formato al listview


    public void agregarPlaylist(ArrayList<String>informacion){

            adapter.notifyDataSetChanged();

          //Log.e(">>>>>", "hay "+playlist.size());

            Intent i = new Intent(this, ListaDeCanciones.class);


            listaDeListaDeReproduccion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(view.getContext(), "Undió "+ position, Toast.LENGTH_SHORT).show();

                    //URLJon no solo me va a mandar la url de la lista undida, sino también, lel nombre del play list y el numero de fans

                    String urlJsonUndido = playList.get(position).split(" - ")[4];
                    String infoPlayList = playList.get(position).split(" - ")[0]+"  -  "+playList.get(position).split(" - ")[1]+
                            "  -  "+playList.get(position).split(" - ")[2]+"  -  "+playList.get(position).split(" - ")[3]+"  -  "+playList.get(position).split(" - ")[6];
                    i.putExtra("LISTA_UNDIDA", urlJsonUndido);
                    i.putExtra("INFO", infoPlayList);
                    startActivity(i);
                    //finish();

                }
            });

    }


}
