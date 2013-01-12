package com.example.valenparty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class Facebook extends FragmentActivity{

	
	private MainFragment mainFragment;
	private static final String TAG = "MainFragment";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
        }
        
        
     // start Facebook Login
        Session.openActiveSession(this, true, new Session.StatusCallback() {

          // callback when session changes state
          @Override
          public void call(Session session, SessionState state, Exception exception) {
            if (session.isOpened()) {
              // make request to the /me API
              Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                // callback after Graph API response with user object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                  if (user != null) {
                	  //TextView welcome = (TextView) findViewById(R.id.textView1);
                	  //welcome.setText(R.string.logueado_face + user.getName() + "!");            	  
                	  Toast.makeText(getApplicationContext(),getString(R.string.logueado_face) + " " + user.getName() + "!", Toast.LENGTH_LONG).show();
                  }
                  else
                  {
                	 //TextView welcome = (TextView) findViewById(R.id.textView1);
                     //welcome.setText(R.string.no_logueado_face);
                     Toast.makeText(getApplicationContext(), getString(R.string.no_logueado_face) + "!", Toast.LENGTH_LONG).show();
                  }
                }
              });
            }
          }
        });
        
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.valenparty", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        
        setContentView(R.layout.facebook_activity);
    }


    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}
