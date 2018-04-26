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
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

import singkorea.singkorea.com.singkorea.R;

/**
 * This shows how to use setPadding to allow overlays that obscure part of the map without
 * obscuring the map UI or copyright notices.
 */
public class RegionAddressActivity extends AppCompatActivity implements
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private GoogleMap mMap;
    private TextView mMessageView;

    /** Keep track of current values for padding, so we can animate from them. */
    int currentLeft = 150;
    int currentTop = 0;
    int currentRight = 0;
    int currentBottom = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_address);
        mMessageView = findViewById(R.id.message_text);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        new OnMapAndViewReadyListener(mapFragment, this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.28333, 103.85), 14));
        //mMap.setPadding(currentLeft, currentTop, currentRight, currentBottom);

        // Add a camera idle listener.
        mMap.setOnCameraIdleListener(() -> {
            mMessageView.setText(
//                    "CameraChangeListener: " + mMap.getCameraPosition() + "\n"+
                            getAddress(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude));//mMap.getCameraPosition().target.latitude
        });
    }

    /** 위도와 경도 기반으로 주소를 리턴하는 메서드*/
    public String getAddress(double latitude, double longitude){

        String address = null;



        //위치정보를 활용하기 위한 구글 API 객체

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());



        //주소 목록을 담기 위한 HashMap

        List<Address> list = null;

        try{

            list = geocoder.getFromLocation(latitude, longitude, 1);

        } catch(Exception e){

            e.printStackTrace();

        }

        if(list == null){

            Log.e("getAddress", "주소 데이터 얻기 실패");

            return null;

        }

        if(list.size() > 0){

            Address addr = list.get(0);

            address = (addr.getLocality() == null ? "" : addr.getLocality() + " ")
                    + (addr.getThoroughfare() == null ? "" : addr.getThoroughfare() + " ")
                    + (addr.getFeatureName() == null ? "" : addr.getFeatureName() + " ")
                    + " (" + (addr.getPostalCode() == null ? "" : addr.getPostalCode() + ", ") + addr.getCountryName() + ")";

        }

        return address;

    }

    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.btn_select:
                double latitude = mMap.getCameraPosition().target.latitude;
                double longitude = mMap.getCameraPosition().target.longitude;
                String address = getAddress(latitude, longitude);
                Intent intent = new Intent();
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("address", address);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }


}
