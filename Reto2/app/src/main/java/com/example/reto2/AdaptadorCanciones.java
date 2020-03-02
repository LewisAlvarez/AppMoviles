package com.example.reto2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorCanciones extends BaseAdapter {

    private ArrayList<String> listaDeCanciones;
    private Context context;
    //private ArrayList<Cancion> canciones


    public AdaptadorCanciones(ArrayList<String> lista, Context context) {
        this.listaDeCanciones = lista;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaDeCanciones.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeCanciones.get(position);
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


        titulo.setText(item.split("  -  ")[0]);
        usuario.setText(item.split("  -  ")[3]);
        cantidad.setText(item.split("  -  ")[6]);

        Picasso.get().load(item.split("  -  ")[4]).into(foto);

        return convertView;
    }
}
