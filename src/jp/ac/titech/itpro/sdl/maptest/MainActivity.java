package jp.ac.titech.itpro.sdl.maptest;

import java.util.Locale;

import jp.ac.titech.itpro.sdl.maptest.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener {
    private final static String TAG = "MainActivity";

    // W8E bldg, Ookayama Campus, Tokyo Institute of Technology
    private final static LatLng INITIAL_LOCATION = new LatLng(35.6049, 139.6826);
    private final static float INITIAL_ZOOM_LEVEL = 17;

    private GoogleMap map;
    private int nextMarkerNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mf.getMap();
        map.setOnMarkerClickListener(this);
        map.setOnMarkerDragListener(this);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(INITIAL_LOCATION, INITIAL_ZOOM_LEVEL));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        map.setMyLocationEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
        map.setMyLocationEnabled(false);
    }

    // GoogleMap.OnMarkerClickListener

    public boolean onMarkerClick(Marker marker) {
        Log.i(TAG, "onMarkerClick");
        showMarker(marker);
        return true;
    }

    // GoogleMap.OnMarkerDragListener

    public void onMarkerDrag(Marker marker) {
        Log.i(TAG, "onMarkerDrag");
    }

    public void onMarkerDragStart(Marker marker) {
        Log.i(TAG, "onMarkerDragStart");
    }

    public void onMarkerDragEnd(Marker marker) {
        Log.i(TAG, "onMarkerDragEnd");
        showMarker(marker);
    }

    // Buttons

    public void onClickMarkButton(View view) {
        MarkerOptions mo = new MarkerOptions();
        mo.position(map.getCameraPosition().target);
        mo.draggable(true);
        mo.title(Integer.toString(nextMarkerNum++));
        showMarker(map.addMarker(mo));
    }

    public void onClickClearButton(View view) {
        map.clear();
    }

    private void showMarker(Marker marker) {
        LatLng pos = marker.getPosition();
        String msg = String.format(Locale.getDefault(), "Marker %s (%.3f, %.3f)",
                marker.getTitle(), pos.latitude, pos.longitude);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
