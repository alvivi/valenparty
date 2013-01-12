package com.example.valenparty;





import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

//import android.view.Menu;
//import android.view.MenuItem;


import android.view.View;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends SherlockActivity {

    Button btnShowLocation;
    ImageButton botongestionamigos;
    
    // clase GPSTracker 
    GPSTracker gps;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
        botongestionamigos = (ImageButton) findViewById(R.id.imageButton3);
        
        // Evento boton gestor contactos
        botongestionamigos.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
            	startActivity(new Intent(MainActivity.this, gestor_amigos.class));
            }
        });
        
        
      //botÃ³n para compartir en redes sociales
        final Button redSocial = (Button) findViewById(R.id.Button01);
   	    redSocial.setOnClickListener(new View.OnClickListener() {
 			
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				Intent rs = new Intent(MainActivity.this, RedesSociales.class);
 				startActivity(rs);
 			}			
 		});

   	    
   	    
        /* PRUEBA DE USO DE LA CLASE GPS 
        btnShowLocation = (Button) findViewById(R.id.button1);
        
        // Evento boton mostrar_ubicacion
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // creamos un objeto de la clase
                gps = new GPSTracker(MainActivity.this);
 
                // comprobamos si el GPS esta activado
                if(gps.canGetLocation()){
 
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
 
                    
                    Toast.makeText(getApplicationContext(), "Tu posici�n es - \nLatitud: " + latitude + "\nLongitud: " + longitude, Toast.LENGTH_LONG).show();
                }else{

                    gps.showSettingsAlert();
                }
 
            }
        });
        /* FIN DE LA PRUEBA DE USO DE LA CLASE GPS */
    

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    	MenuInflater inflater = getSupportMenuInflater();
    	inflater.inflate(R.menu.activity_main, menu);
       // getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }
    
    
    
    //FUNCI�N PARA DETECTAR SI HAY CONEXI�N A INTERNET
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}

		return false;
	}
	
	
	
    //LANZAMOS LA VENTANA MAPS
    public void launchMostrarMapas(View view) { 
    	startActivity(new Intent(this, MapsActivity.class));
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	int itemId = item.getItemId();
		if (itemId == R.id.itemmenumaps) {
			//if (isOnline()){ //si hay conexi�n a internet lo mostramos, sin� no
			launchMostrarMapas(null);
  	//}else{
  	//	Toast.makeText(getApplicationContext(), "Debes estar conectado a Internet para acceder a esta funci�n", Toast.LENGTH_LONG).show();
  	//}
		} else if (itemId == R.id.menu_settings) {
		} else if (itemId == R.id.creditos_settings) {
			startActivity(new Intent(MainActivity.this, CreditosActivity.class));
		} else {
		}
		return false;
    }
    
    
    

    /*
		    <!--  #boton para lanzar la activity MapsActivity
		    <Button
		        android:id="@+id/button2"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:layout_alignParentBottom="true"
		        android:layout_centerHorizontal="true"
		        android:layout_marginBottom="49dp"
		        android:onClick="@string/launchMostrarMapas"
		        android:text="@string/showmaps" />
			-->

     */
    
    

    
}


//PPPPAAAAuuuuuuuuuuu

