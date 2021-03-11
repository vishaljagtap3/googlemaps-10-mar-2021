package in.bitcode.googlemapsdemo;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class SVPAct extends FragmentActivity {

    SupportStreetViewPanoramaFragment svpFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Geocoder gc = new Geocoder(this, Locale.getDefault());
        try {
            //List<Address> addresses = gc.getFromLocation(18.5204, 73.8567, 50);
            List<Address> addresses = gc.getFromLocationName("Hotels in pune", 50);
            for(Address a: addresses) {
                mt(a.getAddressLine(0));
                if(a.getMaxAddressLineIndex() >= 1) {
                    mt(a.getAddressLine(1));
                }
                //mt(a.getPhone());
                mt(a.getCountryName());
                mt("-------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.svp_act);
        svpFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.svpFragment);

        svpFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama svp) {

                        svp.setUserNavigationEnabled(true);
                        svp.setPanningGesturesEnabled(true);
                        svp.setZoomGesturesEnabled(true);
                        svp.setPosition(new LatLng(-33.87365, 151.20689));
                        //svp.setPosition("Shanivarwada Pune India");

                    }
                }
        );
    }

    void mt(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Log.e("tag", text);
    }

}
