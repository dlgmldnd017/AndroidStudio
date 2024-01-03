package com.example.samplemylocationwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

public class MyLocationProvider extends AppWidgetProvider {
    public static double ycoord = 0.0D;
    public static double xcoord = 0.0D;

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int size = appWidgetIds.length;

        for (int i = 0; i < size; i++) {
            int appWidgetId = appWidgetIds[i];

            String uri = "geo:"+ ycoord + "," + xcoord + "?z=10";
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            //PendingIntent를 다른 응용 프로그램에 제공하면 다른 응용 프로그램이 자신인 것처럼 지정한 작업
            // (동일한 권한 및 ID를 사용)을 수행할 수 있는 권한을 부여하는 것입니다.

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mylocation);
            views.setOnClickPendingIntent(R.id.txtInfo, pendingIntent);
            // 텍스트뷰를 클릭했을 경우, pedingIntent를 실행하도록 설정

            appWidgetManager.updateAppWidget(appWidgetId, views);
            // 업데이트하면 텍스트뷰의 클릭 이벤트를 처리하기 위한 인텐트가 설정됨.
        }
        context.startService(new Intent(context,GPSLocationService.class));
    }


    public static class GPSLocationService extends Service {
        public static final String TAG = "GPSLocationService";

        private LocationManager manager = null;

        private LocationListener listener = new LocationListener() {

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }

            public void onLocationChanged(Location location) {
                updateCoordinates(location.getLatitude(), location.getLongitude());
                stopSelf();
            }
        };

        public IBinder onBind(Intent intent) {
            return null;
        }

        public void onCreate() {
            super.onCreate();
            manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        }

        public int onStartCommand(Intent intent, int flags, int startId) {
            startListening();
            return super.onStartCommand(intent, flags, startId);
        }

        public void onDestroy() {
            stopListening();
            super.onDestroy();
        }

        private void startListening() {
            final Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_HIGH);

            final String bestProvider = manager.getBestProvider(criteria, true);

            try {
                if (bestProvider != null && bestProvider.length() > 0) {
                    manager.requestLocationUpdates(bestProvider, 500, 10, listener);
                } else {
                    final List<String> providers = manager.getProviders(true);

                    for (final String provider : providers) {
                        manager.requestLocationUpdates(provider, 500, 10, listener);
                    }
                }
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }

        private void stopListening() {
            try {
                if (manager != null && listener != null) {
                    manager.removeUpdates(listener);
                }

                manager = null;
            } catch (final Exception ex) {

            }
        }

        private void updateCoordinates(double latitude, double longitude) {
            Geocoder coder = new Geocoder(this);
            List<Address> addresses = null;
            String info = "";

            Log.d("llll", "실행이 되나요?");
            info = "[내 위치] " + latitude + ", " + longitude
                    + "\n터치하면 지도로 볼 수 있습니다.";

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.mylocation);
            views.setTextViewText(R.id.txtInfo, info);

            ComponentName thisWidget = new ComponentName(this, MyLocationProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, views);
            xcoord = longitude;
            ycoord = latitude;
        }
    }
}
