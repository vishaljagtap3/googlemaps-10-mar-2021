package in.bitcode.googlemapsdemo;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker pune, mumbai;

    ArrayList<Marker> markers;
    Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        markers = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //mapFragment.getMapAsync(new MyOnMapReadyCallback());
        mapFragment.getMapAsync(this);
    }

    /*class MyOnMapReadyCallback implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {

        }
    }*/


    @SuppressLint("NewApi")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mapSettings();
        addMarkers();
        addListeners();

        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());

        drawShapes();

    }


    void drawShapes() {

        circle = mMap.addCircle(
                    new CircleOptions()
                        .center(pune.getPosition())
                        .radius(5000)
                        .fillColor(Color.argb(90, 255, 0, 0))
                        .strokeColor(Color.RED)
                        .clickable(true)
                        .zIndex(20)
        );

        Polygon polygon = mMap.addPolygon(
                new PolygonOptions()
                .add( new LatLng(28.7041, 77.1025))
                        .add( new LatLng(26.9124, 75.7873))
                        .add( new LatLng(22.7196, 75.8577))
                        .add( new LatLng(28.6856, 79.3452))
                        .add(new LatLng(22.5726, 88.3639))
        );

    }


    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {

            View view = getLayoutInflater().inflate(R.layout.infowindow, null);
            ImageView img = view.findViewById(R.id.img);
            TextView txt = view.findViewById(R.id.txt);

            img.setImageResource(R.drawable.flag);
            txt.setText(marker.getTitle());

            return view;
        }
    }


    private void addListeners() {
        mMap.setOnMapClickListener(new MyOnMapClickListener());
        mMap.setOnMarkerClickListener(new MyMarkerClickListener());
        mMap.setOnInfoWindowClickListener(new MyInfoWindowClickListener());
        mMap.setOnMarkerDragListener(new MyMarkerDragListener());

        mMap.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
            @Override
            public void onInfoWindowClose(Marker marker) {

            }
        });
    }

    class MyMarkerDragListener implements GoogleMap.OnMarkerDragListener {
        @Override
        public void onMarkerDragStart(Marker marker) {
            mt("Drag start: " + marker.getTitle());
        }

        @Override
        public void onMarkerDrag(Marker marker) {
            mt("Drag: " + marker.getTitle());
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            mt("Drag end: " + marker.getTitle());
        }
    }


    class MyInfoWindowClickListener implements GoogleMap.OnInfoWindowClickListener {
        @Override
        public void onInfoWindowClick(Marker marker) {
            mt("Info window: " + marker.getTitle());
        }
    }

    class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {
        @Override
        public boolean onMarkerClick(Marker marker) {
            mt("Marker Click: " + marker.getTitle());
            return false;
        }
    }

    void mt(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Log.e("tag", text);
    }

    class MyOnMapClickListener implements GoogleMap.OnMapClickListener {
        @Override
        public void onMapClick(LatLng latLng) {
            markers.add(
                    mMap.addMarker(
                            new MarkerOptions()
                                    .title("BitCode")
                                    .position(latLng)
                    )
            );


        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void addMarkers() {


        pune = mMap.addMarker(
                new MarkerOptions()
                        .title("Pune")
                        .snippet("This is Pune")
                        .flat(true)
                        .draggable(true)
                        .position(new LatLng(18.5204, 73.8567))
                        .anchor(0.5f, 0.5f)
                        .visible(true)
                        .rotation(45)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        );


        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
        bitmap.setWidth(50);
        bitmap.setHeight(80);*/


        mumbai = mMap.addMarker(
                new MarkerOptions()
                        .title("Mumbai")
                        .snippet("This is Mumbai")
                        .position(new LatLng(19.0760, 72.8777))
                        .draggable(true)
                        /*.icon(
                                BitmapDescriptorFactory.fromBitmap(bitmap)
                        )*/
        );
    }

    @SuppressLint("MissingPermission")
    private void mapSettings() {

        mMap.setMyLocationEnabled(true);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setScrollGesturesEnabledDuringRotateOrZoom(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setCompassEnabled(true);
        //uiSettings.setAllGesturesEnabled(true);


        mMap.setIndoorEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setBuildingsEnabled(true);
        mMap.setTrafficEnabled(true);

    }
}














