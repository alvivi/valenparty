package com.example.valenparty;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.model.Marker;
import com.google.android.maps.GeoPoint;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class gestor_amigos extends SherlockActivity{

	private final int PICK = 1;
	private ListView mContactList;
	private listaAmigos lista_amigos = null;
	Myhelper BD;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gestor_amigos);
		
		mContactList = (ListView) findViewById(R.id.listView1);
		generarListaContactos();
		registerForContextMenu(mContactList);

	}

	private void generarListaContactos() {
      
		lista_amigos = new listaAmigos();
		BD = new Myhelper(this, "DBUsuarios");
    	SQLiteDatabase db = BD.getWritableDatabase();
 
    	// Hacemos la consulta para recuperar nuestros datos
    	String consulta = "select * from mytable";
    	
    	// Usamos un cursor para recorrer la consulta
    	Cursor c = db.rawQuery(consulta,null);
    	ArrayList<String> lista = new ArrayList<String>();
    	while(c.moveToNext()){
    		String num = c.getString(1);
    		String nom = c.getString(2);
    		lista.add(nom + " " + num);
    		
    		Amigo amigo = new Amigo(nom, num, new GeoPoint(0,0),null, null, null, null);
    		lista_amigos.anyadir_Amigo(amigo);
    	}
    	db.close(); 

		 
//       String strings[] = new String[100];
//       for (int i = 0; i < strings.length; i++)
//    	   strings[i] = new String("Elemento "+i);     
   
      
        mContactList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista));


    }

	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	  super.onActivityResult(reqCode, resultCode, data);
	  if (resultCode == Activity.RESULT_OK){
	     //Uri del contacto que hemos elegido
		 Uri contactData = data.getData();
		 //Sacamos el id del contacto
		 String id = contactData.getLastPathSegment();
	   
		 //solo quiero estos campos
		 String[] projection = new String[]{
	             Data.DISPLAY_NAME,                
	             Phone.NUMBER,
		 };	
		 
		 String where = Phone.CONTACT_ID + "=?";
		 //Usamos un cursor para recuperar los datos del contacto
		 
		 
	 	 Cursor cursor = getContentResolver().query(Phone.CONTENT_URI, projection , where, new String[]{id}, null);
	   	 cursor.moveToFirst();
		
	   	 //Recuperamos los datos del contacto y los insertamos en la base de datos
	 
	   	 int colIdx = cursor.getColumnIndex(Data.DISPLAY_NAME);
	   	 String  nombre = cursor.getString(colIdx);
		 
		 colIdx = cursor.getColumnIndex(Phone.NUMBER);
		 String numero = cursor.getString(colIdx);
		 cursor.close();
		 //abrimos la base de datos
		 BD = new Myhelper(this, "DBUsuarios");
		 SQLiteDatabase db = BD.getWritableDatabase();
		 
		 String insert = "INSERT INTO mytable (numero, nombre, latitud, longitud, estado) " + "VALUES ('" + numero +"', '" + nombre +"', '0', '0', 'POR_CONFIRMAR')";
		 try {
			db.execSQL(insert);
		} catch (SQLException e) {
			Log.d("Gestor","Intentamos a�adir un duplicado");
		}
		 //Cerramos la base de datos
		 db.close();
		 generarListaContactos();
	  }		
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getSupportMenuInflater();
    	inflater.inflate(R.menu.menu_amigos, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	int itemId = item.getItemId();
		if (itemId == R.id.anyadir_amigo) {
			// Opening Contacts Window as a Window
			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			// calling OnActivityResult with intent And Some contact for Identifier
			startActivityForResult(intent, PICK);
			return true;			
		}
		return false;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        android.view.MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        menu.setHeaderTitle(mContactList.getAdapter().getItem(info.position).toString());

        inflater.inflate(R.menu.menu_contextual_amigos, menu);
    }
    public boolean onContextItemSelected(android.view.MenuItem item) {

        AdapterContextMenuInfo info =
            (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.Contextual1:
                consultarPosicion(info.position);
                return true;
            case R.id.Contextual2:
                llamadaTelefonica(info.position);
                return true;
            case R.id.Contextual3:
                borrarAmigo(info.position);
                return true;
            default:
                return super.onContextItemSelected((android.view.MenuItem) item);
        }
    }
    private void consultarPosicion(int id){
    	
    }
    private void llamadaTelefonica(int id){
    	Amigo amigo = lista_amigos.leerAmigo(id);
    	Intent myIntent = null;
    	myIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + amigo.getTelefono()));
		startActivity(myIntent);
    }
    private void borrarAmigo(int id){
    	Log.d("Gestor","Borrando Amigo numero " + id);
    	Amigo aux = lista_amigos.leerAmigo(id);
    	String numero = aux.getTelefono();
    	lista_amigos.borrar_Amigo(aux);
    	BD = new Myhelper(this, "DBUsuarios");
    	SQLiteDatabase db = BD.getWritableDatabase();

    	db.delete("mytable", "numero = ?", new String[] { numero });
    	db.close();
    	
    	generarListaContactos();
    }
}
