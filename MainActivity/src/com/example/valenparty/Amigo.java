package com.example.valenparty;

import android.graphics.Bitmap;

import com.google.android.maps.GeoPoint;

public class Amigo {
	
	String NombrePublico;
	String nick;
	GeoPoint locAmigo;
	Bitmap fotoAmigo;
	String ip;
	String sexo;
	
	Amigo(String NombrePub, String nickk, GeoPoint locAmig, Bitmap foto, String ipp, String sexxo){ //CONSTRUCTOR
		NombrePublico=NombrePub;
		nick = nickk; //es necesario hacer el new?
		locAmigo = new GeoPoint(locAmig.getLatitudeE6(),locAmig.getLongitudeE6());
		fotoAmigo = foto;
		ip=ipp;
		sexo=sexxo;
	}

	public String getNombrePublico() {
		return NombrePublico;
	}

	public void setNombrePublico(String nombrePublico) {
		NombrePublico = nombrePublico;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
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
	
	
	
	
}
