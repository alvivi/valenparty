package com.example.valenparty;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MapsActivityV2 extends android.support.v4.app.FragmentActivity  {

	/* NOTA:
		Los índices (i) que aparezcan en estas variables:
		@spinnerAmigos[i]
		@lista.leerAmigo(i)
		@lmarker[i]
		Corresponden a distintas referencias usuario i
		
		
		* Sugerencia: Se podría incluir un marcador (Marker) dentro de la estructura de amigo
		que se dejaría a null y se rellenaría en la fase de dibujado de marcadores.
		
		* Uso de C2DM de Google para hacer las consultas a servidor eficientes.
	*/
	
	
	//Preferencias
	int vista = 0;
	int mivista = 0;
	int miangulo = 0;
	int mi_inclinacion = 0;
	
	
	
	
	
	
	
	//Valor de la vista mostrada, para incluir en preferences
	
	
	int i=0;
	

	
	//Lista de los amigos online que podemos mostrar
    listaAmigos lista = new listaAmigos();
    
    //Mapa de referencia donde mostramos todo
	GoogleMap mapa = null;
	
			
	
	
    public Dialog crearDialogoSeleccion(Marker marca)
    {
        //final String[] items = {"Llamar", "Enviar mensaje", "Obtener ruta hasta amigo"};
		final String[] items = {"Llamar", "Enviar mensaje", "Compartir"};
     
		//Accedemos a la información del amigo al que se le ha clicado el Balloon
		
		final Amigo elegido = lista.extraeAmigoMarkerId(marca.getId());
		
		
		
		
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivityV2.this);
     
        builder.setTitle("¿Qué quieres hacer con " +  elegido.getNombrePublico() + "?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
        	
        	
        	
        	
        	@Override
            public void onClick(DialogInterface dialog, int item) {
        		
        		Uri uri;
        		Intent mintent = null;
        		File file;
        		      		
        		
				switch (item) {
				case 0:
					mintent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+elegido.getTelefono()));
					startActivity(mintent);
					break;

				case 1:
					//file =new File("/sdcard/crash.txt");
					
					//uri = Uri.fromFile(file); 
					//mintent.setType("text");
					//mintent.putExtra("android.intent.extra.STREAM", uri);
					//startActivity(Intent.createChooser(mintent, getString(0x7f04000e))); // send_whatsapp
					//quality_selected = false;
					
					uri = Uri.parse("smsto:"+elegido.getTelefono());
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					//it.putExtra("sms_body", "Valenparty dice:");
					startActivity(it);
					
					break;

				case 2:
					
					file = new File("/sdcard/data.dat");
					uri = Uri.fromFile(file); 
					
					Intent sendIntent = new Intent(Intent.ACTION_SEND);
					sendIntent.setType("audio/*");
					sendIntent.putExtra(Intent.EXTRA_SUBJECT, "SpyTools: AudioRecord");
					sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + uri));
 
					startActivity(Intent.createChooser(sendIntent, "Compartir"));
					break;
					
				default:
					break;
				}
        		
                Log.i("Dialogos", "Opción elegida: " + items[item]);
            }
        });
     
        return builder.show();
    }
	
	
    
    
    public Dialog crearDialogMiUbicacion(Marker marca)
    {
        
		final String[] items = {"Llamar a un taxi", "Llamar a emergencias", "Llamar a Chuck Norris"};
     
		//Accedemos a la información del amigo al que se le ha clicado el Balloon
		
		final Amigo elegido = lista.extraeAmigoMarkerId(marca.getId());
		
		
		
		
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivityV2.this);
     
        builder.setTitle("Acciones relativas a " +  elegido.getNombrePublico() + ".");
        builder.setItems(items, new DialogInterface.OnClickListener() {
        	
        	
        	
        	
        	@Override
            public void onClick(DialogInterface dialog, int item) {
        		
        		Uri uri;
        		Intent mintent = null;
        		File file;
        		      		
        		
				switch (item) {
				case 0:
					mintent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:963571313"));
					startActivity(mintent);
					break;

					
				case 1:
					mintent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:112"));
					startActivity(mintent);
					break;
					
					

				case 2:
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=JLO1YIWQuXE")));

					break;

					
				default:
					break;
				}
        		
                Log.i("Dialogos", "Opción elegida: " + items[item]);
            }
        });
     
        return builder.show();
    }
    
    
    
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maps_activity_v2);
	    
		mapa = ((SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map)).getMap();    
	    


	    
	    	
	    // 		Mostramos la vista Satelite dependiendo de la configuracion puesta
		//
		//	        if (vista==0){
		//	        	mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		//	        }else if (vista==1){
		//	        	mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//	        }else if (vista==2){
		//	        	mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		//	        }else if (vista==3){
		//				mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		//			}
	    	
	    	
	    GPSTracker gps=(new GPSTracker(MapsActivityV2.this){
	    
	    @Override
	    public void onLocationChanged(Location location) {
	        Toast.makeText(
	                MapsActivityV2.this,
	                "Hay cambios:\n" +
	                location.getProvider(),
	                Toast.LENGTH_SHORT).show();
	        
	        
	        Log.d("Actualizacion posicion", "Hay cambio. Posicion=(" + location.getLatitude() + ","+ location.getLongitude() + ")");
	        
	        
	        //Borramos nuestro anterior marker y lo actualizamos con la nueva posición
	        
	        
	        //Enviamos al servidor nuestra nueva posición.
	    }
	    });
	    
	    startService(new Intent(this,GPSTracker.class));
	    
	    
	    gps.getLocation();
	    
	    
	    //1º- leemos de la BD rellenando la lista de amigos con un while
	    //lista.anyadir_Amigo(new Amigo("nombre_amigo", "nick", new GeoPoint (39119540,-452542),null,null,"nenaza" ));
	    
	    //Supongamos que hemos leido estos amigos:
        
        Amigo yo = new Amigo("Mi ubicación", "659369596",new GeoPoint((int) (gps.latitude*1E6),(int) (gps.longitude*1E6)),null,"82.16.95.1","\n - Muy macho", null );
        Amigo Gustavo = new Amigo("Gustavo", "687498857", new GeoPoint (39119540,-452542),null,"82.16.95.1","\n - Un poco nenaza", null );
        Amigo Pau = new Amigo("Pau", "680707225",new GeoPoint (39510444,-318405),null,"82.16.95.1","\n - Muy nenaza", null );
        Amigo X = new Amigo("El torero", "680123545",new GeoPoint (39466753,-376141),null,"82.16.95.1","\n - Un Rarillo", null );
        Amigo v = new Amigo("VVV", "680123545",new GeoPoint (39416753,-376541),null,"82.16.95.1","\n - Un Rarillo", null );
        Amigo c = new Amigo("cCc", "680123545",new GeoPoint (39436753,-376941),null,"82.16.95.1","\n - Un Rarillo", null );
        
        
        //se añaden a la lista
        lista.anyadir_Amigo(yo);
        lista.anyadir_Amigo(Pau);
        lista.anyadir_Amigo(Gustavo);
        lista.anyadir_Amigo(X);
        lista.anyadir_Amigo(v);
        lista.anyadir_Amigo(c);
        
        //Rellenamos el spinner (para que se pueda seleccionar cual ver)
        String[] spinnerAmigos=new String[lista.tamanyo()+1];
        
		//Obtenemos un array con todas las personas que mostraremos en el spiner
        for(i=0;i<lista.tamanyo();i++){
        	spinnerAmigos[i]=lista.leerAmigo(i).getNombrePublico();        	
        }
        
        //Añadimos la opción de ver a todos los amigos al mismo tiempo.
        spinnerAmigos[lista.tamanyo()]="VER TODOS";
  
	    
        //Tomamos el identificador de la vista del spinner
	    Spinner s = (Spinner) findViewById(R.id.selec_amigos);
	    
	    //Le asociamos los strings necesarios
		ArrayAdapter adapter = new ArrayAdapter(this,
	    android.R.layout.simple_spinner_item, spinnerAmigos);
	    s.setAdapter(adapter);
	    
	    
	    //Le damos funcionalidad al spinner
	    s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
	    {
	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
	        {
	        	desplazarHastaAmigo(position);
	            //Log.d("PULSA SPINNER","Has pulsado " + position);
	        }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

	    });
	    
	    
	    
	    
        //CARGAMOS UNA PRIMERA VISTA QUE NOS UBICA A NOSOTROS------------------------
    	//LatLng ubicacion = new LatLng(gps.getLatitude(), gps.getLongitude());
	    
        //PARA QUE MARQUE NUESTRO PUNTO Y LA PRECISIÓN DE LA MEDIDA (sale el area de precisión)
        mapa.setMyLocationEnabled(true);
	    
	    LatLng ubicacion = new LatLng((float)(lista.leerAmigo(0).getLocAmigo().getLatitudeE6())/1E6, (float)(lista.leerAmigo(0).getLocAmigo().getLongitudeE6())/1E6);
    
        CameraPosition camPos = new CameraPosition.Builder()
                .target(ubicacion)   //Centramos el mapa en nuestra ubicacion
                .zoom(19)         //Establecemos el zoom en 19
                .bearing(0)      //Establecemos la orientación con el noreste arriba
                .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
                .build();
         
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
        
            
         
        mapa.animateCamera(camUpd);
        
        
        //Ejemplos de marcadores: https://developers.google.com/maps/documentation/android/marker#customize_a_marker
        
        //Marca que añadimos a mi posición referenciandola al amigo de la lista 0 (que somos nosotros)
        Marker miMarca;
        
        miMarca = mapa.addMarker(
        		new MarkerOptions()
	        	.position(new LatLng(gps.getLatitude(), gps.getLongitude()))
	        	.title("Mi ubicación")
	        	//Ponemos una carita donde estamos nosotros //(R.drawable.amigo))
	        	//.icon(BitmapDescriptorFactory.fromResource(R.drawable.fiesta2))
	        	.icon(BitmapDescriptorFactory.fromResource(R.drawable.amigo))
	        	.snippet("Clic para ver opciones")
	        	);
	        
        //Actualizamos el Marker de esta persona ahora que ya lo tenemos (0 somos nosotros).
        lista.establecerMarkerApos(miMarca, lista.leerAmigo(0));
        
        
        
        
        //Dibujamos en el mapa a todos nuestros amigos
        SituarAmigosEnMapa();
        
        
        
        
        mapa.setTrafficEnabled(true);
        //-------------------------------------FIN DE LA VISTA QUE NOS UBICA A NOSOTROS

        
        
//        mapa.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
//
//			@Override
//			public boolean onMarkerClick(Marker marker) {
//				// TODO Auto-generated method stub
//				
//				Si se toca dentro de una de las zonas de valencia que cuentan estadísticas, se podrían mostrar éstas.
//        
//        
//				return false;
//			}
//        	
//        });
        
        
        

        mapa.setOnInfoWindowClickListener(
        		  new OnInfoWindowClickListener(){
        		    public void onInfoWindowClick(Marker marker){
						//Intent nextScreen = new Intent(MapsActivityV2.this,MapsActivity.class);
        		        //nextScreen.putExtra("userId", "" + userId);
        		        //nextScreen.putExtra("eventId", "" + eventId);
						//
        		        //startActivityForResult(nextScreen, 0);
        		    	

        		    	if (marker.getId().equals(lista.leerAmigo(0).getMarca().getId())){
        		    		crearDialogMiUbicacion(marker);
        		    	}else{
        		    		crearDialogoSeleccion(marker);
        		    	}
        		    	
 
        		    	
        		    }
        		  }
        		);
        
        
        
        
        mapa.setOnMarkerClickListener(new OnMarkerClickListener() {
        	
            public boolean onMarkerClick(Marker marker) {
            	/*
                Toast.makeText(
                    MapsActivityV2.this,
                    "Marcador pulsado:\n" +
                    marker.getTitle(),
                    Toast.LENGTH_SHORT).show();
                    */
                
                //ACCIONES DISPONIBLES DEL MARCADOR PULSADO
                //Hacer zoom hasta un nivel determinado cuando se toca el marcador
                
                
                
                return false;
            }
            
            
            
        });
	        

	        
	        //Cuando hacemos click en el mapa
			mapa.setOnMapClickListener(new OnMapClickListener() {
				public void onMapClick(LatLng point) {
					com.google.android.gms.maps.Projection proj = mapa.getProjection();
					Point coord = proj.toScreenLocation(point);
					
					/*Toast.makeText(
							MapsActivityV2.this, 
							"Has hecho click en el mapa",
							//"Click\n" + 
							//"Lat: " + point.latitude + "\n" +
							//"Lng: " + point.longitude + "\n" +
							//"X: " + coord.x + " - Y: " + coord.y,
							Toast.LENGTH_SHORT).show();*/
				}
			});
			
			
			//Cuando realizamos la pulsación larga
			mapa.setOnMapLongClickListener(new OnMapLongClickListener() {
				public void onMapLongClick(LatLng point) {
					com.google.android.gms.maps.Projection proj = mapa.getProjection();
					Point coord = proj.toScreenLocation(point);
					
					
					//Mostrar un AlertDialog() preguntando "¿Quieres enviar a tus amigos esta posición como la tuya durante los próximos 30 minutos?"
					
				    //ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
				    //pb.setActivated(false);
					
					
					
					
					Toast.makeText(
							MapsActivityV2.this, 
							"Click Largo\n" + 
							"Lat: " + point.latitude + "\n" +
							"Lng: " + point.longitude + "\n" +
							"X: " + coord.x + " - Y: " + coord.y,
							Toast.LENGTH_SHORT).show();
				}
			});
	        
	       ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
	       //pb.d;
			
   	}
   
    
    
    
    /*
     * @descrip
     * @namigo: indica la posición del amigo que se ha leído en el spinner que coincide
     * con la posición que se ha introducido en la lista de amigos.
     * 
     * SALVEDADES:
     * La primera posición del spinner somos siempre nosotros,
     * La última posición siempre es mostrar a todos los amigos en el mapa, Centrándome a MI
     */
    
    //Mostramos distintas formas de acercarse al amigo 
    public void desplazarHastaAmigo(int namigo){
    	if (namigo == 0){
    		Log.d("MUESTRA_YO", "nos mostramos nosotros");
	    	LatLng ubicacion = new LatLng((float)(lista.leerAmigo(0).getLocAmigo().getLatitudeE6())/1E6, (float)(lista.leerAmigo(0).getLocAmigo().getLongitudeE6())/1E6);
	        
	        CameraPosition camPos = new CameraPosition.Builder()
	                .target(ubicacion)   //Centramos el mapa en nuestra ubicacion
	                .zoom(18)         //Establecemos el zoom en 19
	                .bearing(0)      //Establecemos la orientación con el noreste arriba
	                .tilt(30)         //Bajamos el punto de vista de la cámara 70 grados
	                .build();
	         
	        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
    		
	        mapa.animateCamera(camUpd);	
	        
	        
    	}else if(namigo == lista.tamanyo()){
    		//Perspectiva que recoja toda valencia centrada en nosotros
    		Log.d("MUESTRA_TODOS", "mostramos a todos");
    		
    		LatLng ubicacion = new LatLng((float)(lista.leerAmigo(0).getLocAmigo().getLatitudeE6())/1E6, (float)(lista.leerAmigo(0).getLocAmigo().getLongitudeE6())/1E6);
	        
	        CameraPosition camPos = new CameraPosition.Builder()
	                .target(ubicacion)   //Centramos el mapa en nuestra ubicacion
	                .zoom(11)         //Establecemos el zoom en 19
	                .bearing(0)      //Establecemos la orientación con el noreste arriba
	                .tilt(80)         //Bajamos el punto de vista de la cámara 80 grados
	                .build();
	         
	        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
	                 
	        mapa.animateCamera(camUpd);	
    		
    		
    		
    		
    	}else{
	    	LatLng ubicacion = new LatLng((float)(lista.leerAmigo(namigo).getLocAmigo().getLatitudeE6())/1E6, (float)(lista.leerAmigo(namigo).getLocAmigo().getLongitudeE6())/1E6);
	        
	        CameraPosition camPos = new CameraPosition.Builder()
	                .target(ubicacion)   //Centramos el mapa en nuestra ubicacion
	                .zoom(19)         //Establecemos el zoom en 19
	                .bearing(0)      //Establecemos la orientación con el noreste arriba
	                .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
	                .build();
	         
	        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
	        
	            
	         
	        mapa.animateCamera(camUpd);
	        
	        //PARA QUE MARQUE NUESTRO PUNTO Y LA PRECISIÓN DE LA MEDIDA (sale el area de precisión)
/*	        mapa.setMyLocationEnabled(true);
	        
	        
	        
	        
	        
	        //Ejemplos de marcadores: https://developers.google.com/maps/documentation/android/marker#customize_a_marker
	        mapa.addMarker(new MarkerOptions()
	        .position(new LatLng((float)(lista.leerAmigo(namigo).getLocAmigo().getLatitudeE6())/1E6, (float)(lista.leerAmigo(namigo).getLocAmigo().getLongitudeE6())/1E6))
	        .title(lista.leerAmigo(namigo).getNombrePublico())
	        //Ponemos una carita donde estamos nosotros
	        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.amigo))
	        .snippet(lista.leerAmigo(namigo).getNick() + "\n" +
	        		lista.leerAmigo(namigo).getIp() +  "\n" + "Online" +
	        		lista.leerAmigo(namigo).getSexo()));
        
	        
	        mapa.setTrafficEnabled(true);*/
    	}
    }
    
    
    
    
    //Ejemplos de marcadores: https://developers.google.com/maps/documentation/android/marker#customize_a_marker
    
    public void SituarAmigosEnMapa(){
    	
    	int namigo;
    	Marker miMarca;
    	
    	//empezamos por el 1 porque el 0 somos nosotros y ya estamos dibujados
    	for(namigo=1;namigo<lista.tamanyo();namigo++){
    		
            miMarca = mapa.addMarker(new MarkerOptions()
	        	.position(new LatLng((float)(lista.leerAmigo(namigo).getLocAmigo().getLatitudeE6())/1E6, (float)(lista.leerAmigo(namigo).getLocAmigo().getLongitudeE6())/1E6))
	        	.title(lista.leerAmigo(namigo).getNombrePublico())
	        	//Ponemos una carita donde estamos nosotros
	        	//.icon(BitmapDescriptorFactory.fromResource(R.drawable.amigo))
	        	.snippet(lista.leerAmigo(namigo).getTelefono() + "\n" +
	        		lista.leerAmigo(namigo).getIp() +  "\n" + "Online" +
	        		lista.leerAmigo(namigo).getSexo()));
            
            //Actualizamos el Marker de esta persona.
            lista.establecerMarkerApos(miMarca, lista.leerAmigo(namigo));
            
            
    	}
    }
    
    
    
 
    
    
    
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_activity_v2, menu);
        return true;
    }
	
 
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	int itemId = item.getItemId();
		if (itemId == R.id.vista_satelite2) {
			//if (item.isChecked()){
			//item.setChecked(false);
				//cambiamos la variable global que regula esto
				vista = 0;
				mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			
		}else if (itemId == R.id.vistanormal) {
				//if (item.isChecked()){
			//item.setChecked(false);
					//cambiamos la variable global que regula esto
					vista = 1;
					mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				
		}else if (itemId == R.id.vistahibrida) {
			//if (item.isChecked()){
				//item.setChecked(false);
				//cambiamos la variable global que regula esto
				vista = 2; 
				mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			
		}else {
			
		}
    	
//        if (vista==1){
//        	mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        }else if (vista==0){
//        	mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        }else if (vista==2){
//        	mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        }
    	
		return false;
    }
	
	

    
    private void saveData() {
		// TODO Auto-generated method stub
    	SharedPreferences preferences = getSharedPreferences("Valenparty", Context.MODE_PRIVATE); 
    	//EditText text = (EditText) findViewById(R.id.myEditText); 
    	Editor editor = preferences.edit(); 
    	//editor.putInt("numberQuestion", numberQuestion);
    	//editor.putInt("comodin_phone", comodin_phone);
    	//editor.putInt("comodin_fifty", comodin_fifty);
    	//editor.putInt("comodin_audience", comodin_audience);
    	editor.commit();
	}
    
    
    private void restoreData() {
		// TODO Auto-generated method stub
    	SharedPreferences preferences = getSharedPreferences("Valenparty", Context.MODE_PRIVATE); 
    	//numberQuestion=preferences.getInt("numberQuestion", 0); //1 es el flag por defecto
    	//comodin_phone=preferences.getInt("comodin_phone", 1); 
    	//comodin_audience=preferences.getInt("comodin_audience", 1);
    	//comodin_fifty=preferences.getInt("comodin_fifty", 1);
    	
	}
    
    
    
//	//FUNCI�N QUE COMPRUEBA SI HAY INTERNET
//	public boolean isOnline() {
//		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo netInfo = cm.getActiveNetworkInfo();
//
//		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
//			return true;
//		}
//
//		return false;
//	}
//    
//    
//    
    

  
    
    
    
}