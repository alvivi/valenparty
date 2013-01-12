package com.example.valenparty;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.Marker;
import com.google.android.maps.GeoPoint;

public class Amigo {
	
	String NombrePublico;
	String telefono;
	GeoPoint locAmigo;
	Bitmap fotoAmigo;
	String ip;
	String sexo;
	Marker marca;
	
	Amigo(String NombrePub, String telef, GeoPoint locAmig, Bitmap foto, String ipp, String sexxo, Marker marc){ //CONSTRUCTOR
		NombrePublico=NombrePub;
		telefono = telef; //es necesario hacer el new?
		locAmigo = new GeoPoint(locAmig.getLatitudeE6(),locAmig.getLongitudeE6());
		fotoAmigo = foto;
		ip=ipp;
		sexo=sexxo;
		marca= marc;
	}

	public String getNombrePublico() {
		return NombrePublico;
	}

	public void setNombrePublico(String nombrePublico) {
		NombrePublico = nombrePublico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telef) {
		this.telefono = telef;
	}

	public GeoPoint getLocAmigo() {
		return locAmigo;
	}

	public void setLocAmigo(GeoPoint locAmigo) {
		this.locAmigo = locAmigo;
	}

	public Bitmap getFotoAmigo() {
		return fotoAmigo;
	}

	public void setFotoAmigo(Bitmap fotoAmigo) {
		this.fotoAmigo = fotoAmigo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	
//	public void setMarca(Marker marc){
//		this.marca=marc;
//	}
	
	public Marker getMarca() {
		return marca;
	}
	
	public void actualizaMarker(Marker marc){
		marca=marc;
		
	}
	
}
