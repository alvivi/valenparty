package com.example.valenparty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas; //Para dibujar sobre una capa
import android.graphics.Color;  //Colores predefinidos
import android.graphics.Paint;  //Para dibujar
import android.graphics.Point;  //un punto en la matriz de pixeles
import android.util.Log;
import android.widget.Toast;


import com.google.android.maps.GeoPoint;   //Estructura de Coordenadas lat. y long.
import com.google.android.maps.MapView;    //El mapa que se muestra
import com.google.android.maps.Overlay;    //La capa donde se ha de pintar
import com.google.android.maps.Projection; //Para hacer la conversion entre pixeles y coordenadas



public class MyInfoExtraMaps extends Overlay  {
    public GeoPoint puntoX = null;
    
 
    public MyInfoExtraMaps(GeoPoint mipunto) {
    	puntoX= new GeoPoint(mipunto.getLatitudeE6(), mipunto.getLongitudeE6());
		
	}

    
    //Con esta funci�n pintaremos a todos los amigos
	@Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow)
    {
		int i;
		Log.d("DEBUG*", "num elementos: " + MapsActivity.listaAmigos.size());
		//Iterator iter = MapsActivity.listaAmigos.iterator();
		
		
		
		for(i=0;i<MapsActivity.listaAmigos.size();i++){
			//iter.next();
			Projection projection = mapView.getProjection();
			
	        
	        Log.d("DEBUG*", MapsActivity.listaAmigos.get(i).getNombrePublico()+"LATITUD: "+ puntoX.toString() + "\tLONGITUD: "+ puntoX.toString());
	               
	        if (shadow == false)
	        {
	            Point centro = new Point();
	           
	            projection.toPixels(MapsActivity.listaAmigos.get(i).getLocAmigo(), centro);
	 
	            //Definimos el pincel de dibujo
	            Paint p = new Paint();
	            p.setColor(Color.BLUE);
	            //p.setTextScaleX(1);
	 
	            //C�rculo y Texto
	            //canvas.drawCircle(centro.x, centro.y, 10, p);
	            canvas.drawText(MapsActivity.listaAmigos.get(i).getNombrePublico(), centro.x+5, centro.y+45, p);
	            
	            
	          //Carita
	            //if(MapsActivity.listaAmigos.get(i).getFotoAmigo()==null){
		            Bitmap bm = BitmapFactory.decodeResource(
		                    mapView.getResources(),
		                    R.drawable.amigo);
		             
		            canvas.drawBitmap(bm, centro.x ,//- bm.getWidth()
		                    centro.y, p);// - bm.getHeight()
	            //}
	            //else{
	            	//pintamos la imagen del amigo
	            //}
	        }
		}
           
    }
	
	
	
	@Override
	public boolean onTap(GeoPoint point, MapView mapView) 
	{
		int i;
		Context contexto = mapView.getContext();
		for(i=0;i<MapsActivity.listaAmigos.size();i++){
			Log.d("DEBUGGER","Pulsacion en pantalla");
			
			
			
			
			if (point.hashCode() == MapsActivity.listaAmigos.get(i).getLocAmigo().hashCode()){
				
				Log.d("DEBUGGER","PASAMOS DENTRO");
				Toast toast = Toast.makeText(contexto, MapsActivity.listaAmigos.get(i).getNick().toUpperCase() 
						+ "\n" + "Nmbre: " + MapsActivity.listaAmigos.get(i).getNombrePublico() 
						+ "\n" + "IP: " + MapsActivity.listaAmigos.get(i).getIp() 
						+ "\n" + "Extra: " + MapsActivity.listaAmigos.get(i).getSexo()
						+ "\n" +"Estado: "+ "Desconectado", Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		
		return true;
	}


}




/* ANTIGUA CLASE:
 * 
 * 
 * 
 * 
 * package com.example.valenparty;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas; //Para dibujar sobre una capa
import android.graphics.Color;  //Colores predefinidos
import android.graphics.Paint;  //Para dibujar
import android.graphics.Point;  //un punto en la matriz de pixeles
import android.util.Log;
import android.widget.Toast;


import com.google.android.maps.GeoPoint;   //Estructura de Coordenadas lat. y long.
import com.google.android.maps.MapView;    //El mapa que se muestra
import com.google.android.maps.Overlay;    //La capa donde se ha de pintar
import com.google.android.maps.Projection; //Para hacer la conversion entre pixeles y coordenadas



public class MyInfoExtraMaps extends Overlay  {
    public GeoPoint puntoX = null;
    
 
    public MyInfoExtraMaps(GeoPoint mipunto) {
    	puntoX= new GeoPoint(mipunto.getLatitudeE6(), mipunto.getLongitudeE6());
		
	}

    
    //Con esta funci�n pintaremos a todos los amigos
	@Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow)
    {
        Projection projection = mapView.getProjection();
        
        Log.d("DEBUG*", "LATITUD: "+ puntoX.toString() + "\tLONGITUD: "+ puntoX.toString());
        
        //Toast.makeText(getBaseContext(R.layout.maps_activity), "Tu posici�n es - \nLatitud: " + (int) (MapsActivity.mipunto.getLatitudeE6()) + "\nLongitud: " + (int) (MapsActivity.mipunto.getLongitudeE6()), Toast.LENGTH_LONG).show();
        //imprimir por salida de error
        
        
        if (shadow == false)
        {
            Point centro = new Point();
           
            projection.toPixels(puntoX, centro);
 
            //Definimos el pincel de dibujo
            Paint p = new Paint();
            p.setColor(Color.BLUE);
 
            //C�rculo y Texto
            //canvas.drawCircle(centro.x, centro.y, 10, p);
            canvas.drawText("Yo", centro.x+5, centro.y+45, p);
            
            
          //Carita
            if(null==null){
	            Bitmap bm = BitmapFactory.decodeResource(
	                    mapView.getResources(),
	                    R.drawable.amigo);
	             
	            canvas.drawBitmap(bm, centro.x ,//- bm.getWidth()
	                    centro.y, p);// - bm.getHeight()
            }
            else{
            	//pintamos la imagen del amigo
            }
        }
           
    }
	
	
	
	@Override
	public boolean onTap(GeoPoint point, MapView mapView) 
	{
		Context contexto = mapView.getContext();
		Toast toast = Toast.makeText(contexto, "Amigo", Toast.LENGTH_SHORT);
		toast.show();
		
		return true;
	}
}
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */

