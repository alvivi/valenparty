package com.example.valenparty;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.android.gms.maps.model.Marker;



public class listaAmigos {
	List<Amigo> lista = new ArrayList<Amigo>();
 
 	 
	 public int tamanyo(){
		 return lista.size();
	 }
	 
	 public void vaciar(){
		 lista.clear();
	 }
	 
	 public boolean anyadir_Amigo(Amigo aux){
		 lista.add(aux);
		 return true;
	 }
	 
	 public boolean borrar_Amigo(Amigo aux){
		 return lista.remove(aux);
	 }
	 
	 
	 public boolean ordenar_amigos(){
		 //por ahora no hace falta
		 return true;
	 }
	 
	 
	 public Amigo leerAmigo(int indice){
		 if ((indice>=lista.size()) || indice<0){
			 Log.d("Array pos fuera de los rangos","Se ha accedido a la pos:" + (indice+1) + " y el array solo tiene " +lista.size() + " elementos");
		 }else{
			 return lista.get(indice);
		 }
		 return null;
	 }
	 
	 public boolean establecerMarkerApos(Marker marca, Amigo amigo){
	 
		//Actualizamos el marcador del amigo
		amigo.actualizaMarker(marca);
		 
		//Lo sustituimos en la lista por el que había antes.
		lista.set(lista.indexOf(amigo), amigo);
			 
		return true;

	 }

	public Amigo extraeAmigoMarkerId(String id) {
		// TODO Auto-generated method stub
		int i=0;
		for(i=0;i<lista.size();i++){
			if (lista.get(i).getMarca().getId().equals(id)) return lista.get(i);
				
				
		}
		return null;
	}
}
