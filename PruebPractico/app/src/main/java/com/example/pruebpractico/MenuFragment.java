package com.example.pruebpractico;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    private final int[] BOTONES_MENU ={R.id.maps, R.id.pregunta, R.id.tienda};

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View miMenu = inflater.inflate(R.layout.fragment_menu, container, false);


        ImageButton botonesMenu;

        for(int i = 0; i < BOTONES_MENU.length; i++){

            botonesMenu = (ImageButton) miMenu.findViewById(BOTONES_MENU[i]);

            final int botonUndido = i;

            botonesMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Detectar en que actividad se encuentra el boton pulsado
                    //Y enviar eso al metodo de la interface para que lo ejecute

                    Activity actividadActual = getActivity();

                    ((ComunicaMenu)actividadActual).menu(botonUndido);

                }
            });

        }


        return miMenu;
    }

}
