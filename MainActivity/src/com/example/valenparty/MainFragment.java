package com.example.valenparty;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class MainFragment extends Fragment {
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	private Button shareButton;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.facebook_activity, container, false);  
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.button1);
	    authButton.setFragment(this);
	    authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
	    shareButton = (Button) view.findViewById(R.id.shareButton);
	    
	    shareButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            //publishStory();  
	        	showDialog();
	        }
	    });
	    
	    if (savedInstanceState != null) {
	        pendingPublishReauthorization = savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);
	    }
	    return view;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	    	Log.i(TAG, "Logged in...");
	        shareButton.setVisibility(View.VISIBLE);
	        if (pendingPublishReauthorization && 
	            state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
	               pendingPublishReauthorization = false;
	               //publishStory();
	               showDialog();  
	        	}
	    	} else if (state.isClosed()) {
	    		Log.i(TAG, "Logged off...");
	    		shareButton.setVisibility(View.INVISIBLE);
	    }
	}
	
	private void showDialog(){
		
		AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
		 dialogo.setTitle(R.string.dialogo1);
		 dialogo.setMessage(R.string.diaglogo2);
		 
		 dialogo.setPositiveButton(R.string.dialogo3, new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				publishStory();
			}
		});
		 
		 
		dialogo.setNegativeButton(R.string.dialogo4, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				 
			     //finish();
			}
		});
		
		 AlertDialog alerta2 = dialogo.create();
		 alerta2.show();
	 
		
	}
	
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    uiHelper.onResume();
	}
	  

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		 super.onSaveInstanceState(outState);
		    outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
		    uiHelper.onSaveInstanceState(outState);
	}
	
	private void publishStory() {
	    Session session = Session.getActiveSession();

	    if (session != null){

	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, PERMISSIONS);
	            session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }

	        Bundle postParams = new Bundle();
	        postParams.putString("name", getString(R.string.nameappface));
	        postParams.putString("caption", getString(R.string.captionappface));
	        postParams.putString("description", getString(R.string.descriptionappface));
	        postParams.putString("link", "http://www.facebook.com/Valenparty");
	        postParams.putString("picture", "http://personales.alumno.upv.es/~jasangu1/partyicon.png");

	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	                JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                } catch (JSONException e) {
	                    Log.i(TAG, "JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(getActivity().getApplicationContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
	                } else {
	                    //Toast.makeText(getActivity().getApplicationContext(), postId, Toast.LENGTH_LONG).show();
	                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.comentariofacebook), Toast.LENGTH_LONG).show();
	                }
	            }
	        };

	        Request request = new Request(session, "me/feed", postParams, HttpMethod.POST, callback);
	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
	
}
