package com.example.valenparty;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrepareRequestTokenActivity extends Activity {
	final String TAG = getClass().getName();
	private OAuthConsumer consumer;
    private OAuthProvider provider;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
	   		this.consumer = new CommonsHttpOAuthConsumer(Constantes.CONSUMER_KEY, Constantes.CONSUMER_SECRET);
	   	    this.provider = new CommonsHttpOAuthProvider(Constantes.REQUEST_URL,Constantes.ACCESS_URL,Constantes.AUTHORIZE_URL);
			} catch (Exception e) {
	   		Log.e(TAG, "Error creating consumer / provider",e);
			}
	 
	       Log.i(TAG, "Starting task to retrieve request token.");
	       new OAuthRequestTokenTask(this,consumer,provider).execute();
	}
	

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		SharedPreferences Myprefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(Constantes.CALLBACK_SCHEME)) {
			Log.i(TAG, "Callback received : " + uri);
			Log.i(TAG, "Retrieving Access Token");
			new RetrieveAccessTokenTask(this,consumer,provider,Myprefs).execute(uri);
			finish();	
		}
	}
		
		

	
	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

		private Context	context;
		private OAuthProvider provider;
		private OAuthConsumer consumer;
		private SharedPreferences Myprefs;

		public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,OAuthProvider provider, SharedPreferences Myprefs) {
			this.context = context;
			this.consumer = consumer;
			this.provider = provider;
			this.Myprefs = Myprefs;
		}

			@Override
			protected Void doInBackground(Uri...params) {
				final Uri uri = params[0];
				final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

					try {
						provider.retrieveAccessToken(consumer, oauth_verifier);

						final Editor edit = Myprefs.edit();
						edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
						edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
						edit.commit();

						String token = Myprefs.getString(OAuth.OAUTH_TOKEN, "");
						String secret = Myprefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

						consumer.setTokenWithSecret(token, secret);
						context.startActivity(new Intent(context, MainActivity.class));

						executeAfterAccessTokenRetrieval();

						Log.i(TAG, "OAuth - Access Token Retrieved");

						} catch (Exception e) {
							Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
						}

					return null;
			}


	private void executeAfterAccessTokenRetrieval() {
		String msg = getIntent().getExtras().getString("tweet_msg");
			try {
				TwitterUtilities.sendTweet(Myprefs, msg);
				} catch (Exception e) {
					Log.e(TAG, "OAuth - Error sending to Twitter", e);
				}
			}
	}	
	
	
}
