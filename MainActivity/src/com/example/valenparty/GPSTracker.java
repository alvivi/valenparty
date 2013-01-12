package com.example.valenparty;

 
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
 


/*******************************************************************************
 *  LÓGICA INTERNA DEL PROGRAMA (O EXPLICACIÓN DE LA JUGADA):
 * 
 * LA IDEA ES CREAR UNA CLASE QUE SE LANZA CUANDO ARRANCA EL PROGRAMA (O SE ACEPTA
 * EL RASTREO) Y SE MANTIENE EN SEGUNDO PLANO CON UNA TASA DE REFRESCO DE 20 SEGUNDOS
 * A 5 MINUTOS (CONFIGURABLE EN LA PANTALLA DE "AJUSTES"). 
 * 
 * CUANDO SE RECIBE CADA COORDENADA SE DEBE "TRATAR"
 * - REPRESENTANDOLA EN UN MAPA
 * - ENVIANDOLA POR INTERNET A UN WEBSERVICE
 * - GUARDANDOLA LOCALMENTE (PARA POSTERIORMENTE REPRESENTAR UN TRAYECTO)
 * 
 * A TENER EN CUENTA:
 * - LAS PETICIONES DE GEOPOSICIONAMIENTO SE REALIZARÁN MEDIANTE UNA TAREA ASÍNCRONA
 * 
 *******************************************************************************/




public class GPSTracker extends Service implements LocationListener {
	
	// LA CLASE QUE UTILIZAREMOS COMO SERVICIO DESDE OTRO ACTIVITY
    
	private final Context mContext;
 
    // flag for GPS status
    boolean isGPSEnabled = false;
 
    // flag for network status
    boolean isNetworkEnabled = false;
 
    // flag for GPS status
    boolean canGetLocation = false;
 
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
 
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
 
    // Declaring a Location Manager
    protected LocationManager locationManager;
 
    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }
 
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
 
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                
                // PRIMERO: if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
                // EN CASO CONTRARIO: get location from Network Provider
                else if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return location;
    }
 
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }
 
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
 
        // return latitude
        return latitude;
    }
 
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
 
        // return longitude
        return longitude;
    }
 
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
 
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
 
        // Setting Dialog Title
        alertDialog.setTitle("GPS no establecido");
 
        // Setting Dialog Message
        alertDialog.setMessage("Se recomiendo activar el GPS. Quieres ir al menú de configuraciones?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Mostrar el mensaje
        alertDialog.show();
    }
 

    public void onLocationChanged(Location location) {
    }
 
 
    public void onProviderDisabled(String provider) {
    }
 

    public void onProviderEnabled(String provider) {
    }
 
 
    public void onStatusChanged(String provider, int status, Bundle extras) {
    	//aquí meteremos todo lo referente al tracking
    	//es decir, cada vez que cambie la ubicación la registraremos.
    	
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
 
    
    
}



/*
 * 
 * EJEMPLO DE USO DE ESTA CLASE:
 * 
 */
/* 

Button btnShowLocation;

// clase GPSTracker 
GPSTracker gps;


@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    
    
    //PRUEBA DE USO DE LA CLASE GPS 
    btnShowLocation = (Button) findViewById(R.id.button1);
    
    // show location button click event
    btnShowLocation.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // creamos un objeto de la clase
            gps = new GPSTracker(MainActivity.this);

            // comprobamos si el GPS esta activado
            if(gps.canGetLocation()){

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{

                gps.showSettingsAlert();
            }

        }
    });
    // FIN DE LA PRUEBA DE USO DE LA CLASE GPS 
    */



