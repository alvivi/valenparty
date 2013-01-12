package com.example.valenparty;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;



public class gestor_amigos extends SherlockActivity implements OnClickListener{
	private Button mBtnContacts;
	private final int PICK = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gestor_amigos);
		mBtnContacts = (Button) findViewById(R.id.xBtnContacts);
		mBtnContacts.setOnClickListener(this);
	}

	@TargetApi(5)
	public void onClick(View v) {
		// Opening Contacts Window as a Window
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		// calling OnActivityResult with intenet And Some conatct for Identifie
		startActivityForResult(intent, PICK);
	}

	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	  super.onActivityResult(reqCode, resultCode, data);
	  if (resultCode == Activity.RESULT_OK){
	     //Uri donde estan los datos de los contactos.
		 Uri contactData = data.getData();
		 String id = contactData.getLastPathSegment();
		 Log.d("Contactos",contactData.toString());
		 Uri URI = ContactsContract.Data.CONTENT_URI;
		 Log.d("Contactos",URI.toString());
		 Log.d("Contactos","Paso 1");
		 //abrimos la base de datos
		 Myhelper BD = new Myhelper(this, "DBUsuarios");
		 SQLiteDatabase db = BD.getWritableDatabase();
		// db.execSQL("DROP TABLE IF EXISTS mytable");
		// db.execSQL("CREATE TABLE mytable (numero TEXT PRIMARY KEY, nombre TEXT)");
	   
		 //solo quiero estos campos
		 String[] projection = new String[]{
	             Data.DISPLAY_NAME,                
	             Phone.NUMBER,
		 };	
		 
		 String where = Phone.CONTACT_ID + "=?";
		 //consulta en la que obtenemos los datos.
	 	 Cursor cursor = getContentResolver().query(Phone.CONTENT_URI, projection , where, new String[]{id}, null);
	 	 Log.d("Contactos","Paso 2");
	   	 cursor.moveToFirst();
		
	   	 //Buscamos la columna donde esta el nombre, obtenemos el nombre y lo imprimimos.
	   	 //Buscamos la columna donde esta el numero, obtenemos el numero y lo imprimimos.
	 
	   	 int colIdx = cursor.getColumnIndex(Data.DISPLAY_NAME);
	   	 String  nombre = cursor.getString(colIdx);
		 Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
		 
		 colIdx = cursor.getColumnIndex(Phone.NUMBER);
		 String numero = cursor.getString(colIdx);
		 Toast.makeText(this, numero, Toast.LENGTH_SHORT).show();
		 
		 String insert = "INSERT INTO mytable (numero, nombre) " + "VALUES ('" + numero +"', '" + nombre +"')";
		 db.execSQL(insert);
		 Log.d("Contactos","Paso 4");
		 /*
		 while(cursor.moveToNext()){
	   		 
	   		 colIdx = cursor.getColumnIndex(Data.DISPLAY_NAME);
			 nombre = cursor.getString(colIdx);
			 Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
			 colIdx = cursor.getColumnIndex(Phone.NUMBER);
			 numero = cursor.getString(colIdx);
			 Toast.makeText(this, numero, Toast.LENGTH_SHORT).show();
			 insert = "INSERT INTO mytable (numero, nombre) " + "VALUES ('" + numero +"', '" + nombre +"')";
			 db.execSQL(insert);
    	 }
	   	 	*/		 
		 
		 
		 
		 // Hacemos la consulta para recuperar nuestros datos
		 String consulta = "select * from mytable";
			
		 // Usamos un cursor para recorrer la consulta
		 Cursor c = db.rawQuery(consulta,null);
		
		 Toast.makeText(this, "leyendo Base Datos", Toast.LENGTH_SHORT).show();
		 
		 while(c.moveToNext()){
				String num = c.getString(0);
				String nom = c.getString(1);
				Toast.makeText(this, nom, Toast.LENGTH_SHORT).show();	
				Toast.makeText(this, num, Toast.LENGTH_SHORT).show();  
			}
		 db.close();  	

					
	  }		
	}

}
