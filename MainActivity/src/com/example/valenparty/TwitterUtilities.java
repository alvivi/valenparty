package com.example.valenparty;

import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import android.content.SharedPreferences;
import android.provider.SyncStateContract.Constants;

public class TwitterUtilities {
	
	public static boolean isAuthenticated(SharedPreferences Myprefs) {	
		String token = Myprefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = Myprefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
		AccessToken a = new AccessToken(token,secret);
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constantes.CONSUMER_KEY, Constantes.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);	
	
			try {
				twitter.getAccountSettings();
				return true;
				} catch (TwitterException e) {
				return false;
			}
	}

	
	public static void sendTweet(SharedPreferences Myprefs,String msg) throws Exception {
		String token = Myprefs.getString(OAuth.OAUTH_TOKEN, "1026822211-YBhRshFAC78qPoD6k7nFLZGZbeMHXf33yw06Wbz");
		String secret = Myprefs.getString(OAuth.OAUTH_TOKEN_SECRET, "FsrwrktP3ZVj1rR5BnQ78X4Y8mmi4QmbBhyeXvb89dg");

		AccessToken a = new AccessToken(token,secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constantes.CONSUMER_KEY, Constantes.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);
        twitter.updateStatus(msg);
	}	
	
	

}
