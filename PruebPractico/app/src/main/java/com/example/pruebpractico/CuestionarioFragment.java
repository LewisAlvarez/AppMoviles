package com.example.pruebpractico;


//import android.app.Fragment;
import android.os.Bundle;

//import android.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

//import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CuestionarioFragment extends Fragment {


    public final static  int SUMA = 1;
    public final static  int RESTA = 2;
    public final static  int MULTIPLICACION = 3;
    public final static  int DIVISION = 4;


    private TextView pregunta;
    private EditText respuesta;
    private Button btnAceptar;
    private TextView puntaje;
    private TextView puntajeParaTienda;

    int primerNumero;
    int segundoNumero;
    int oper;

    private int puntos;

    //PanelActivity panelActivity;


    public CuestionarioFragment() {
        // Required empty public constructor

        //puntos = 0;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_cuestionario2, container, false);



        pregunta = (TextView) view.findViewById(R.id.pregunta);
        respuesta = (EditText) view.findViewById(R.id.respuesta);
        btnAceptar = (Button) view.findViewById(R.id.aceptarrespuesta);
        //panelActivity = new PanelActivity();

        generarPregunta();

        puntaje = (TextView) getActivity().findViewById(R.id.puntaje);

        try {
            Toast.makeText(getContext(), puntaje.getText().toString().split(":")[1]+" Esto imprime en persistencia",Toast.LENGTH_SHORT ).show();
            puntos = Integer.parseInt(puntaje.getText().toString().split(":")[1]);
        }catch (Exception e){
            e.printStackTrace();
        }

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean respuestaBien = respuestaCorrecta();
                if (respuestaBien){
                    //Aqui deberia de aumentar los puntos y
                    //generar otra pregunta
                    //Decirle a activity panel que actualice el puntaje
                    puntos = puntos+1;
                    puntaje.setText("Puntos:"+puntos);
                    Toast.makeText(getContext(),"Correcto¡¡¡",Toast.LENGTH_SHORT).show();

                }else{
                    puntos = puntos-1;
                    puntaje.setText("Puntos:"+puntos);
                    Toast.makeText(getContext(),"Incorrecto :/",Toast.LENGTH_SHORT).show();
                }

                //Limpiar respuesta, generar otra pregunta
                respuesta.setText("");
                generarPregunta();
            }
        });

        return view;
    }

    //Este método hace una pregunta aleatoria  y lo cambia en el text de la pregunta
    public void generarPregunta(){

        int n = 10;
        int num1 =  (int) (Math.random() * n) + 1;
        int num2 =  (int) (Math.random() * n) + 1;

        int m = 4;
        int operador = (int) (Math.random() * m) + 1;

        primerNumero = num1;
        segundoNumero = num2;
        oper = operador;

        String laPregunta = "";

        switch (operador){
            case SUMA:
                laPregunta = num1 + " + " + num2;
                pregunta.setText(laPregunta);
                break;

            case RESTA:
                laPregunta = num1 + " - " + num2;
                pregunta.setText(laPregunta);
                break;

            case MULTIPLICACION:
                laPregunta = num1 + " * " + num2;
                pregunta.setText(laPregunta);
                break;

            case DIVISION:
                laPregunta = num1 + " / " + num2;
                pregunta.setText(laPregunta);
                break;
        }
    }


    //Este metodo me verifica si la respuesta fue correcta o no
    public boolean respuestaCorrecta(){
        boolean correcta = false;

        String respuestaUsuario = respuesta.getText().toString();

        int valor = 0;

        try{
            valor = Integer.parseInt(respuestaUsuario);
            correcta = correcto(valor);
        }catch (Exception e){
            e.printStackTrace();
        }

        return correcta;
    }

    public boolean correcto(int valor){
        boolean c = false;

        if (oper == 1){
            if ((primerNumero+segundoNumero)==valor){
                c = true;
            }
        }else if (oper == 2){
            if ((primerNumero-segundoNumero)==valor){
                c = true;
            }
        }else if (oper == 3){
            if ((primerNumero*segundoNumero)==valor){
                c = true;
            }
        }else if (oper == 4){
            if ((primerNumero/segundoNumero)==valor){
                c = true;
            }
        }

        return c;
    }

    public int getPuntos(){
        return puntos;
    }


    public void actualizarPuntos(int s) {
        puntos = s;
    }
}
