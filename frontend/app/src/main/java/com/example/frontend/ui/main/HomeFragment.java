package com.example.frontend.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.frontend.PostActivity;
import com.example.frontend.R;
import com.example.frontend.RequestHttpURLConnection;
import com.example.frontend.databinding.FragmentHomeBinding;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener {

    private static final String LOG_TAG = "KAKAO_MAP_LOG";
    //    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    /**
     * KAKAO MAP 선언
     * */
    private MapView mapView;


    MapPoint currentMapPoint;

    private float mCurrentLng;
    private float mCurrentLat;
    private String strUserId;

    private MarkerEventListener eventListener = new MarkerEventListener();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        /**
         * KAKAO MAP 권한 설정
         * */

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }


        /**
         * KAKAO MAP 설정
         * */

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "현재위치 탐색",Toast.LENGTH_SHORT).show();
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading); //TrackingModeOnWithoutHeading

            }
        });

        mapView = new MapView(getActivity());
        mapView.setCurrentLocationEventListener(this);
        mapView.setMapViewEventListener(this);



        ViewGroup mapViewContainer = (ViewGroup) binding.mapView;
        mapViewContainer.addView(mapView);

        mapView.setZoomLevel(3, true); //맵 확대
        mapView.zoomIn(true);
        mapView.zoomOut(true);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapView.setPOIItemEventListener(eventListener);





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    //MapView.MapViewEventListener 구현
    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        //사용자가 지도를 길게 누른 경우 수행
        setMapMarker(mapView, mapPoint);

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
    //MapView.MapViewEventListener 구현끝



    public boolean checkLocationServicesStatus() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);;

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission(){
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. 이미 퍼미션을 가지고 있다면
            // 3.  위치 값을 가져올 수 있음
//            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading); //TrackingModeOnWithoutHeading


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getActivity(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    // 커스텀 말풍선 클래스
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText("클릭");
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }

    }

    //마커 클릭시
    class MarkerEventListener implements MapView.POIItemEventListener {
        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("선택하세요");

            builder.setItems(R.array.LAN, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int pos)
                {
                    String[] items = getResources().getStringArray(R.array.LAN);
                    Toast.makeText(getActivity(),items[pos],Toast.LENGTH_LONG).show();
                    int count = checkPostHistory();
                    // 각 버튼별로 수행할 일
                    if(pos == 0 && count == 0){
                        Intent intent = new Intent(getActivity(), PostActivity.class);
                        intent.putExtra("lat", mCurrentLat);
                        intent.putExtra("lon", mCurrentLng);
                        intent.putExtra("userId", strUserId);
                        startActivity(intent);
                    }
                    else if(pos == 0 && count == 1){
                        Toast.makeText(getActivity(), "하루에 한 번만 등록 가능!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(pos==1){
                        mapView.removePOIItem(mapPOIItem);
                    }
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

        }

    }

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }



    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
        currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
        //이 좌표로 지도 중심 이동
        mapView.setMapCenterPoint(currentMapPoint, true);
        //최초 1회만 현재위치 받기 위함
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff); //TrackingModeOnWithoutHeading
        //전역변수로 현재 좌표 저장
        mCurrentLat = (float)mapPointGeo.latitude;
        mCurrentLng = (float)mapPointGeo.longitude;
        Log.d(LOG_TAG, "현재위치 => " + mCurrentLat + "  " + mCurrentLng);

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }


    public void setMapMarker(MapView mapView, MapPoint mapPoint) {
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재 위치");
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomImageResourceId(R.drawable.custom_marker_red);
        marker.setCustomImageAutoscale(true);
        marker.setCustomImageAnchor(0.5f, 1.0f);
        marker.setDraggable(true);
        mapView.addPOIItem(marker);

    }

    //글 기록 확인
    public int checkPostHistory(){
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String getTime = sformat.format(now);
        String rtnStr="";
        int count = 0;
        //REST API 주소
        String url = "http://localhost:8080/api/history";
        //String url = "http://본인IP주소:8080/api/history";

        try{
            String jsonString = new JSONObject()
                    .put("userId", strUserId)
                    .put("publishDate", getTime)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            rtnStr = networkTask.execute().get();

            //리스트 길이 확인
            count = Integer.parseInt(rtnStr);


        }catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }
}