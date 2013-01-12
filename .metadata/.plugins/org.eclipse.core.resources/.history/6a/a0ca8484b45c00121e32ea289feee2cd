package com.example.valenparty;


import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MapsActivity extends MapActivity{
	
	private MapView mapa;
	private MapController controlMapa;
	private List<Overlay> mapOverlays;
	
	private boolean vistaSatelite = false;
	
	protected static GeoPoint mipunto = null;
	private boolean desplazaAnimado = false;
	public static List<Amigo> listaAmigos = new ArrayList<Amigo>();
	
	Button btnCentrar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_activity);
		
		//MANIPULANDO EL MAPA
		mapa = (MapView) findViewById(R.id.mapa); // Referencia al control del mapa
		mapa.displayZoomControls(true); 
		mapa.setBuiltInZoomControls(true); // Mostrar controles de zoom psobre el mapa
		controlMapa = mapa.getController(); 
        //Mostramos la vista Satelite dependiendo de la configuracion puesta
        if (!vistaSatelite)
        	mapa.setSatellite(false);
        else
        	mapa.setSatellite(false);
 

        
		btnCentrar = (Button) findViewById(R.id.buscameB);
		
		btnCentrar.setOnClickListener(new OnClickListener() {
			
		    public void onClick(View arg0) {
		        //Double latitud = 39.18*1E6;
		        //Double longitud = -0.34*1E6;
		        
		        GPSTracker gps;
	            gps = new GPSTracker(MapsActivity.this);
	               
	                // comprobamos si el GPS esta activado
	                if(gps.canGetLocation()){
	 
	                	double latitud = gps.getLatitude();
	                	double longitud = gps.getLongitude();

                	
	    		        mipunto = new GeoPoint((int) (latitud*1000000), (int) (longitud*1000000));
	    		        
	    		       if (!desplazaAnimado){
	    		    	   controlMapa.setCenter(mipunto);
	    		    	   controlMapa.setZoom(19);
	    		       }else{
	    		    	    controlMapa.animateTo(mipunto);
    			            int zoomActual = mapa.getZoomLevel();
    			     
    			            for(int i=zoomActual; i<19; i++)
    			                controlMapa.zoomIn();
	    		       }
    			        
  			        

    			        Toast.makeText(getApplicationContext(), "Tu posici�n es - \nLatitud: " + (int) (latitud*1000000) + "\nLongitud: " + (int) (longitud*1000000), Toast.LENGTH_LONG).show();

	                }else{

	                    gps.showSettingsAlert();
	                }
	                
	                
	                /*
	                 * COMPLETAMOS LA LISTA DE AMIGOS
	                 * http://maps.google.es/?ll=39.11954,-0.452542&spn=0.010088,0.01929&t=h&z=16
	                 * http://maps.google.es/?ll=39.510444,-0.318405&spn=0.002508,0.004823&t=h&z=18
	                 * */
	                
	                GeoPoint caGus = new GeoPoint (39119540,-452542);
	                GeoPoint caPau = new GeoPoint (39510444,-318405);
	                
	                Amigo yo = new Amigo("YO", "mipegir",mipunto,null,null,"muy Hombre" );
	                Amigo Gustavo = new Amigo("Gustavo", "guslandu",caGus,null,null,"un poco nenaza" );
	                Amigo Pau = new Amigo("Pau", "pamullo",caPau,null,null,"muy nenaza" );
	                
	                listaAmigos.add(yo);
	                listaAmigos.add(Gustavo);
	                listaAmigos.add(Pau);
	                
	                
	                
	                
	                /*
	                 * MOSTRAR CAPAS
	                 * 
	                 * 
	                 */
	                
	              //A�adimos la capa de marcadores
	                List<Overlay> capas = mapa.getOverlays();
	                MyInfoExtraMaps minfo = new MyInfoExtraMaps(mipunto);
	                capas.add(minfo);
	                mapa.postInvalidate();
	                
	                
	                /**
	                 *  FIN MOSTRAR CAPAS
	                 * 
	                 * */
		
		    }
		});

	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_activity, menu);
        return true;
    }
	

	
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	int itemId = item.getItemId();
		if (itemId == R.id.vista_satelite) {
			if (item.isChecked()){
				item.setChecked(false);
				//cambiamos la variable global que regula esto
				vistaSatelite = false;
				
			}else{
				item.setChecked(true);
				//cambiamos la variable global que regula esto
				vistaSatelite = true;
			}
		} else if (itemId == R.id.anim_activ) {
			if (item.isChecked()){
				item.setChecked(false);
				//cambiamos la variable global que regula esto
				desplazaAnimado = false;
			}else{
				item.setChecked(true);
				//cambiamos la variable global que regula esto
				desplazaAnimado = true;
			}
		} else {
		}
    	
        if (!vistaSatelite){
        	mapa.setSatellite(false);
        }else{
        	mapa.setSatellite(true);
        }
    	
		return false;
    }
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	//FUNCI�N QUE COMPRUEBA SI HAY INTERNET
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}

		return false;
	}

	



}
