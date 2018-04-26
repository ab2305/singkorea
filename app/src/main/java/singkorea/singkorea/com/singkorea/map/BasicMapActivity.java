/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package singkorea.singkorea.com.singkorea.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import singkorea.singkorea.com.singkorea.R;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 */
public class BasicMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private double longitude;
    private double latitude;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        Intent intent = getIntent();
        if(intent != null) {
            title = intent.getStringExtra("title");
            latitude = intent.getDoubleExtra("latitude", 1.28333d);
            longitude = intent.getDoubleExtra("longitude", 103.85d);
        }

        mapFragment.getMapAsync(this);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.28333, 103.85), 10));
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(title)).showInfoWindow();

        final CameraPosition SING = new CameraPosition.Builder().target(new LatLng(latitude, longitude))
                .zoom(15.5f)
                .bearing(300)
                .tilt(50)
                .build();

        Handler handler = new Handler();
        handler.postDelayed(() -> map.animateCamera(CameraUpdateFactory.newCameraPosition(SING), null), 1000);
    }
}
