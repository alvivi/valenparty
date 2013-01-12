package com.example.valenparty;

import java.util.Date;

import oauth.signpost.OAuth;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;



public class RedesSociales extends Activity {
	
	private SharedPreferences Myprefs;
	private final Handler myTwitterHandler = new Handler();
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes_sociales);
        
        Myprefs = PreferenceManager.getDefaultSharedPreferences(this);
        
    	ImageButton face = (ImageButton) findViewById(R.id.imageButton2);
    	face.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			Intent faceb = new Intent(getApplicationContext(), Facebook.class);
    			startActivity(faceb);
    		}
    	
        });
        
    	ImageButton tweet = (ImageButton) findViewById(R.id.imageButton1);
		 tweet.setOnClickListener(new View.OnClickListener() {
		       public void onClick(View v) {
		       	   task task = new task();	
		      	    task.execute();
		        }
		            
		  });
        

        
        //LIMPIAMOS CREDENCIALES
        final Button clearCredentials = (Button) findViewById(R.id.button1);
        clearCredentials.setOnClickListener(new View.OnClickListener() {
		       public void onClick(View v) {
		    	   clearCredentials() ;
		        }
       
        });
        
    }
    
    
    //CON ESTE CODIGO SE ACTIVA EL MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_redes_sociales, menu);
        return true;
    }

	@Override
	//METODOS PARA PASAR A LA PANTALLA CREDITOS DESDE EL MENU
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()){
		case R.id.menu_settings:
			clearCredentials();
			break;
		}
		return true;
	}
	
	

//FIN DE CODIGO PARA PASAR A PANTALLA CREDITOS DESDE EL MENU
	
	
    private void clickFacebook(){
    	ImageButton face = (ImageButton) findViewById(R.id.imageButton2);
    	face.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			startActivity(new Intent(RedesSociales.this, Facebook.class));
    		}
    	
        });
    }
    
    
    private class task extends AsyncTask<Void, Integer, Boolean>{
    	@Override
    	protected Boolean doInBackground(Void... arg0) {
    		if (TwitterUtilities.isAuthenticated(Myprefs)) {
				    sendTweet();
				    } else {
				    	Intent i = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
				    	i.putExtra("tweet_msg",getTweetMsg());
				    	startActivity(i);
				    }
    		
    		return true;
    		
    	}
    }
    	
     
    	//MENSAJE PREDEFINIDO DEL TWEET
    	private String getTweetMsg() {
    		return getString(R.string.mensaje_twitter) + new Date();
    	}
    	
    	   
    	//ENVIA EL TWEET
    	public void sendTweet(){
    		Thread t = new Thread() {
    		public void run() {

    			try {
    				TwitterUtilities.sendTweet(Myprefs,getTweetMsg());
    				myTwitterHandler.post(mUpdateTwitterNotification);
    				} catch (Exception ex) {
    					ex.printStackTrace();
    				}
    			}

    		};
    		t.start();
    	}
    	
    	
    	//NOTIFICA QUE HA ENVIADO TWEET
    	private final Handler mTwitterHandler = new Handler();
    	 
    	   final Runnable mUpdateTwitterNotification = new Runnable() {
    	       public void run() {
    	       	Toast.makeText(getBaseContext(), getString(R.string.mensaje_twitter_notify), Toast.LENGTH_LONG).show();
    	       }
    	   };
        
    	   
    	//LIMPIAMOS LAS CREDENCIALES PARA LA PROXIMA VEZ
    	private void clearCredentials() {
    		SharedPreferences Myprefs = PreferenceManager.getDefaultSharedPreferences(this);
    		final Editor edit = Myprefs.edit();
    		edit.remove(OAuth.OAUTH_TOKEN);
    		edit.remove(OAuth.OAUTH_TOKEN_SECRET);
    		edit.commit();
    	}
    	
    	
    
}

