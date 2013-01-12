package com.example.valenparty;


/**
 *  
 *
 * AQUÍ ESTÁN LAS CLAVES PROPORCIONADAS POR TWITTER PARA LA AUTORIZACIÓN
 *
 */

public class Constantes {

	public static final String CONSUMER_KEY = "lxuktL9edmtClpJFKxyVMA";
	public static final String CONSUMER_SECRET= "UCpLSzg6WheYxBxNa0zMj2XlJnE5FBEFcgRsVNQ";
 
	public static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
 
	final public static String	CALLBACK_SCHEME = "x-oauthflow-twitter";
	final public static String	CALLBACK_URL = CALLBACK_SCHEME + "://callback";
	
}
