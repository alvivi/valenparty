
package com.example.valenparty;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Myhelper extends SQLiteOpenHelper {
	
	public Myhelper(Context context,String nombre) {
		super(context, "myfile.db", null, 1);
	}

	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE mytable (numero TEXT PRIMARY KEY, nombre TEXT, latitud TEXT, longitud TEXT, estado TEXT)");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	
	
	
	}
}