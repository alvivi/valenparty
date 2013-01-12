package com.example.valenparty;

import java.util.Date;

import oauth.signpost.OAuth;
import com.actionbarsherlock.app.SherlockActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;





public class RedesSociales extends SherlockActivity {
	
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
    	
         //CLICK EN EL BOTON DE TWITTER DE LA INTERFAZ INICIAL
    	private void click (){
    	
    	
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

