package com.example.reto2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.example.reto2.model.PlayList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private ArrayList<String> listaDePlayList;
    private Context context;
    //private ArrayList<Cancion> canciones


    public Adaptador(ArrayList<String> lista, Context context) {
        this.listaDePlayList = lista;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaDePlayList.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDePlayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item = (String) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item,null);

        ImageView foto = convertView.findViewById(R.id.imgplaylist);
        TextView titulo = convertView.findViewById(R.id.nombretitulo);
        TextView usuario = convertView.findViewById(R.id.nombreusuarioartista);
        TextView cantidad = convertView.findViewById(R.id.numeroitemslanzamiento);


        titulo.setText(item.split(" - ")[0]);
        usuario.setText(item.split(" - ")[5]);
        cantidad.setText("# Canciones: "+item.split(" - ")[1]);
        //Log.e("SSSS",item.split("-")[2]);
        Picasso.get().load(item.split(" - ")[2]).into(foto);

        //"https://api.deezer.com/playlist/757807/image"

        return convertView;
    }
}
