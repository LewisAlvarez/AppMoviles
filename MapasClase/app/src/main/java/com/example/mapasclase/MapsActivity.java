package com.example.mapasclase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    //private ImageView icesi;

    //private Polygon icesiArea;

    private Marker miUbicacion;

    private List<Address>direccionesMarcadas;
    //Boton agregar
    private Button btn_agregar;

    private TextView distanciaTV;

    private EditText nombreMarcador;

   // private Marker[] fifo;
    private Geocoder geocoder;

    //Nuevo
    //Marcador del arraylist
    private Marker marcadorSiguiente;
    private ArrayList<Marker> listaMarcadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //------------ICESI----------------------
        //icesi = findViewById(R.id.icesi);

        nombreMarcador = findViewById(R.id.nombre_marcador);
        nombreMarcador.setEnabled(false);

       // fifo = new Marker[2];
       // marcadorSiguiente = new Marker();
        listaMarcadores = new ArrayList<Marker>();

        distanciaTV = findViewById(R.id.distanciaTV);

        btn_agregar = findViewById(R.id.btn_agregar);


        geocoder = new Geocoder(this, Locale.getDefault());

        //
        //Pedir permiso para usar ubicacion

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        }, 11);



        //


        //concurrencia();
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        //SUPIZ 3.343050,-76.530852
        //SUPDER 3.343093,-76.529160
        //INFIZQ 3.339815,-76.531240
        //INFDER 3.339922,-76.529257

       // icesiArea = mMap.addPolygon(new PolygonOptions().add(
       //         new LatLng(3.343050,-76.530852),
       //         new LatLng(3.343093,-76.529160),
       //         new LatLng(3.339922,-76.529257),
       //         new LatLng(3.339815,-76.531240)
        // ));

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(3.343050,-76.530852);

        miUbicacion =  mMap.addMarker(new MarkerOptions().position(sydney).title("Ubicacion Actual"));
        //miUbicacion = mMap.addMarker(new MarkerOptions().title("Ubicación Actual"));
        miUbicacion.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ubicacionactual));



        //if (miUbicacion.isInfoWindowShown()){

       // }


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));


        //Pedir que el sensor comience a medir
        //Manager para ubicarnos.
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,0,this);



        //mostrarMasCercano();



    }

    //LOCATION UPDATES

    @Override
    public void onLocationChanged(Location location) {


        LatLng coord = new LatLng(location.getLatitude(),location.getLongitude());
        miUbicacion.setPosition(coord);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coord,15));


        String cercano = "";

        Marker mark = alguienTocoMarcador();
        if (mark != null) {
            obtenerDistanciaAMarker();
        }else{
           cercano = mostrarMasCercano();
            //obtenerDistanciaAMarker();
        }

        try {
            direccionesMarcadas = geocoder.getFromLocation(miUbicacion.getPosition().latitude, miUbicacion.getPosition().longitude, 1);
            String address = direccionesMarcadas.get(0).getAddressLine(0).split(",")[0];
            miUbicacion.setSnippet(address);
        }catch (Exception e){
            e.printStackTrace();
        }


        //Aquí verifico que la persona ya esté en el lugar
        //Con una distancia de 80 metros
        try {
            double diferencia = Double.parseDouble(cercano.split("-")[1]);
            if (diferencia < 100.0) {
                distanciaTV.setText("Usted está en: " + cercano.split("-")[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    //Long click instancias de click
    //Con el tacto con la pantalla haga tato
    @Override
    public void onMapLongClick(LatLng latLng) {

      Marker k = mMap.addMarker(new MarkerOptions().position(latLng));
      
      nombreMarcador.setEnabled(true);

        marcadorSiguiente = k;

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                
                String nombre = nombreMarcador.getText().toString();
                marcadorSiguiente.setTitle(nombre);
                listaMarcadores.add(marcadorSiguiente);

                nombreMarcador.setText("");
                nombreMarcador.setEnabled(false);

                Toast.makeText(MapsActivity.this,nombreMarcador.getText().toString() + " Agregado con éxito¡", Toast.LENGTH_SHORT).show();


            }
        });

     // }

    }

//Obtengo la distancia  de mi ubicación actual, a el marcador seleccionado.
    public void obtenerDistanciaAMarker(){

      //  String distancia = "";

        Marker mark = alguienTocoMarcador();

        if (mark != null) {

            double distanciaPunto = distanciaMarcadorPosicionActual(mark);

            //DecimalFormat formato = new DecimalFormat("#####.##");
           // formato.format(distanciaPunto);
            String dist = distanciaPunto +"";
            //distanciaTV.setText("Distancias: " + dist.toString().substring(0,6) + " metros de distancia a:" + mark.getTitle().toString());
            try {
                direccionesMarcadas = geocoder.getFromLocation(mark.getPosition().latitude, mark.getPosition().longitude, 1);
                String address = direccionesMarcadas.get(0).getAddressLine(0).split(",")[0];
                mark.setSnippet(" Usted se encuentra a: " + dist.toString().substring(0, 6) + " Metros de distancia"+ ".\'"+address);
            }catch (Exception e){
                e.printStackTrace();
            }

            //Address a = new Address();
           // mark.setSnippet(distanciaPunto + " jejejejeje");
        }

    }

    public Marker alguienTocoMarcador(){
        boolean tocoMarcador = false;
        Marker marker = null;

        for (int i = 0; i < listaMarcadores.size(); i++){
            if (listaMarcadores.get(i) != null){
                if (listaMarcadores.get(i).isInfoWindowShown()){
                    tocoMarcador = true;
                    marker = listaMarcadores.get(i);
                }
            }
        }

        return marker;
    }

    public String mostrarMasCercano(){


        HashMap<Double,Marker> distanciasGenerales = new HashMap<Double,Marker>();
        ArrayList<Double> valores = new ArrayList<>();

        for (int i = 0; i < listaMarcadores.size(); i++) {
            if (listaMarcadores.get(i) != null) {
                double distancia = distanciaMarcadorPosicionActual(listaMarcadores.get(i));
                valores.add(distancia);
                distanciasGenerales.put(distancia, listaMarcadores.get(i));
            }
        }


        double maximo = Double.MAX_VALUE;


        for (int i = 0; i < distanciasGenerales.size(); i++){
              double dist = valores.get(i);
              if (dist < maximo){
                  maximo = dist;
              }
        }

        //En la variable maximo va a estar la distancia del objeto mas proximo
        Marker mark = distanciasGenerales.get(maximo);
        //DecimalFormat formato = new DecimalFormat("#####.##");
        //formato.format(maximo);

        String dist = maximo+"";
        String laDist = dist.toString().substring(0,6);

        distanciaTV.setText("Estás más cercano a: " + mark.getTitle().toString() + " con distancia de: " + laDist + " metros");


        String info = mark.getTitle().toString()+"-"+maximo;

        return info;

    }

    public double distanciaMarcadorPosicionActual(Marker m){


        double latDiff = miUbicacion.getPosition().latitude - m.getPosition().latitude;
        double longDiff = miUbicacion.getPosition().longitude - m.getPosition().longitude;

        double distanciaPunto = Math.sqrt(Math.pow(latDiff, 2) + Math.pow(longDiff, 2));
        double distancia =  distanciaPunto * 111.12 * 1000;

        //distancia = distanciaPunto;

        return distancia;
    }


    //salvaguardar de forma rapida
    @Override
    protected void onPause() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("texto", distanciaTV.getText().toString());
        editor.apply();

        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();

        String texto = PreferenceManager.getDefaultSharedPreferences(this).getString("texto", "0");
        distanciaTV.setText(texto);

    }
}
