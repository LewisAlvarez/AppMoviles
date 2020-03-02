package com.example.pruebpractico;


//import android.app.Fragment;
import android.os.Bundle;

//import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static com.example.pruebpractico.R.layout.fragment_tienda;


/**
 * A simple {@link Fragment} subclass.
 */
public class TiendaFragment extends Fragment  {


    private ListView listView;
    private Button btnComprar;

    private ArrayAdapter adapter;
    private TextView puntajeParaTienda;
    private TextView punt;


    private  final static int CANTIDAD_PRODUCTOS = 5;
    private int[] puntosPorProducto;
   private String puntaje;
    private int devueltaUsuario;
    private boolean hayDevuelta;


    public TiendaFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(fragment_tienda, container, false);

        listView = (ListView)view.findViewById(R.id.list_view);
       // btnComprar = (Button)view.findViewById(R.id.comprar);
        //puntajeParaTienda = (EditText) view.findViewById(R.id.puntajee);
        //puntaje.setText(getActivity().findViewById(R.id.puntaje));
        puntajeParaTienda = view.findViewById(R.id.ptienda);

        puntajeParaTienda.setText(puntaje);
        punt = (TextView) getActivity().findViewById(R.id.puntaje);


        final ArrayList<String> productos = new ArrayList<String>();

        productos.add("Lapicero icesi         4 puntos");
        productos.add("Cuaderno               5 puntos");
        productos.add("Libreta icesi          6 puntos");
        productos.add("Camisa icesi           7 puntos");
        productos.add("Saco icesi            10 puntos");

        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, productos);
        puntosPorProducto = new int[CANTIDAD_PRODUCTOS];
        puntosPorProducto[0] = 4;
        puntosPorProducto[1] = 5;
        puntosPorProducto[2] = 6;
        puntosPorProducto[3] = 7;
        puntosPorProducto[4] = 10;

        listView.setAdapter(adapter);

                        //Ahora ponemos el listenner para que este escuchando la lista
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Posicion , me indica cual objeto de la lista fue undido

                                try {
                                    Toast.makeText(getContext(),productos.get(position).toString() +"-----"+puntajeParaTienda.getText().toString()+"---y" ,Toast.LENGTH_SHORT).show();
                                    int puntos = Integer.parseInt(puntajeParaTienda.getText().toString());
                                    //Toast.makeText(getContext(),puntos+"---" ,Toast.LENGTH_SHORT).show();
                                    if (puntos >= puntosPorProducto[position]){
                                        int devuelta = puntos - puntosPorProducto[position];
                                        hayDevuelta = true;
                                        punt.setText("Puntos:"+devuelta);
                                        devueltaUsuario = devuelta;
                                        puntajeParaTienda.setText(""+ devuelta);
                                        Toast.makeText(getContext(), "Has comprado: "+productos.get(position).toString(),Toast.LENGTH_SHORT).show();
                                    }else{
                        Toast.makeText(getContext(), "Tus puntos no alcanzan para el producto",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                                    Toast.makeText(getContext(), "Entró al catch",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        //puntajeParaTienda.setText("80");
    }


    public void setPuntajeTienda(String mensaje){
        puntaje = mensaje;
    }
    public  boolean getHayDevuelta(){
        return hayDevuelta;
    }

    public int getDevuelta(){
        return devueltaUsuario;
    }

}
