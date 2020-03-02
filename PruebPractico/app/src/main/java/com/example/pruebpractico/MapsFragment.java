package com.example.pruebpractico;


//import android.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
//    private Geocoder geocoder;
    private Marker miUbicacion;

    private ImageView imagenPregunta;

    private boolean estaEnArea;

    private boolean estaEnBiblio;
    //Areas de la universidad
    private Polygon bibliotecaIcesiArea;
    private Polygon edificioMIcesiArea;
    private Polygon auditorioArea;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);

        //geocoder = new Geocoder(this, Locale.getDefault());

        imagenPregunta = (ImageView) getActivity().findViewById(R.id.icesipregunta);
        //
        //Pedir permiso para usar ubicacion

        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        }, 11);

        return root;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(3.343050,-76.5308521);

        miUbicacion = mMap.addMarker(new MarkerOptions().position(sydney).title("Ubicacion Actual"));
        //miUbicacion = mMap.addMarker(new MarkerOptions().title("Ubicación Actual"));
        miUbicacion.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ubicacionactual));

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));


        //Pedir que el sensor comience a medir
        //Manager para ubicarnos.
        try {
            LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,0,this);
        }catch (Exception e){
            e.printStackTrace();
        }

        //----------------------
        //Maejo de las areas

        bibliotecaIcesiArea = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.341867, -76.530104),
                new LatLng(3.341687, -76.530111),
                new LatLng(3.341690, -76.529816),
                new LatLng(3.341847, -76.529811)

        ));

        edificioMIcesiArea = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.342488, -76.530467),
                new LatLng(3.342436, -76.530079),
                new LatLng(3.342318, -76.530084),
                new LatLng(3.342342, -76.530451)

        ));

        auditorioArea = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(3.342715, -76.529867),
                new LatLng(3.342771, -76.529739 ),
                new LatLng(3.342731, -76.529582 ),
                new LatLng(3.342546, -76.529487),
                new LatLng(3.342391, -76.529540),
                new LatLng( 3.342343, -76.529717),
                new LatLng(3.342396, -76.529857),
                new LatLng(3.342535, -76.529927)

        ));


        //---------------------------
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng coord = new LatLng(location.getLatitude(),location.getLongitude());
        miUbicacion.setPosition(coord);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coord,15));

        //Ahora verifico si me encuentro en una determinada area

        boolean estaEnBiblioteca = PolyUtil.containsLocation(coord, bibliotecaIcesiArea.getPoints(), true);
        boolean estaEnElM = PolyUtil.containsLocation(coord, edificioMIcesiArea.getPoints(), true);
        boolean estaEnElManuelita = PolyUtil.containsLocation(coord, auditorioArea.getPoints(), true);

        if (estaEnElM || estaEnElManuelita){
            //Aqui deberia de estar zona reactiva
            //Muestro una imagen de pregunta para que la persona vaya a el fragment de preguntas
           // imagenPregunta.setVisibility(View.VISIBLE);
            estaEnArea = true;
            //Toast.makeText(getContext(), "Estas en zona de pregunta, Ve a resolver una",Toast.LENGTH_LONG).show();

        } else if (estaEnBiblioteca){
            estaEnBiblio = true;
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean getEstaEnArea(){
        return estaEnArea;
    }
    public boolean getEstaEnBiblioteca(){
        return estaEnBiblio;
    }

}
