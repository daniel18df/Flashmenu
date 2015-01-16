package cl.flashmenu.aplicacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import restaurant.infoRestaurantes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import carta.listaPlatos2;
import cliente.perfilCliente;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class verMapa extends FragmentActivity implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener{


	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, Object>> PlatosList;
	ArrayList<HashMap<String, Object>> DatosPlatos;
	DecimalFormat fmt;
	private static String url_Lista_platos = servidor.ip() + servidor.ruta2()+"getRestaurantes.php";

	JSONArray j1 = null;
	private int tipo = 0;

	String preferencias_tipo; 

	// JSONArray
	JSONArray platosl = null;

	HashMap<String, Object> extract;
	TextView perfilUsuario, perfil;
	Button versugerencias, versugerenciasmenu;
	String usuario, idRest;


	//Map Variables
	private SupportMapFragment mapFragment;
	private GoogleMap mMap;
	private double mLat, mLng;
	private LocationClient mLocationClient;

	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 900;


	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}



	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vermapa);



		//UserData.initall();
		mLocationClient = new LocationClient(this, this, this);


		//We create a map
		mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		mMap = mapFragment.getMap();
		//and enable its location
		mMap.setMyLocationEnabled(true);



		perfilUsuario = (TextView) findViewById(R.id.nombreClienteMapa);
		perfilUsuario.setText(UserData.Cliente_email);


		perfil = (TextView) findViewById(R.id.perfilInfoRestMapa);
		perfil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
				startActivity(i);
				//finish();
			}
		});
		new LoadAllplatos().execute();



		versugerencias = (Button) findViewById(R.id.verSugerencia);
		versugerencias.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), verSugerencias.class);
				startActivity(i);

				//finish();
			}
		});
		
		versugerenciasmenu = (Button) findViewById(R.id.verSugerenciamenu);
		versugerenciasmenu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), verSugerenciasMenu.class);
				startActivity(i);

				//finish();
			}
		});

	}
	//oncreate


	/**
	 * Background Async Task to Load all platos by making HTTP Request
	 * */
	class LoadAllplatos extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(verMapa.this);
			pDialog.setMessage("Cargando platos. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All platos from url
		 * */
		@SuppressWarnings("unchecked")
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_Lista_platos, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All restaurantes: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {

					platosl = json.getJSONArray(UserData.TAG_restaurant);

					for (int i = 0; i < platosl.length(); i++) {
						JSONObject c = platosl.getJSONObject(i);


						UserData.idRestaurant= c.getString(UserData.TAG_ID_REST);
						UserData.Rest_nombre= c.getString(UserData.TAG_NOMBRE_REST);
						UserData.Rest_tipo= c.getString(UserData.TAG_TIPO_REST);
						UserData.Rest_descripcion= c.getString(UserData.TAG_DESCRIPCION_REST);
						UserData.Rest_email= c.getString(UserData.TAG_EMAIL_REST);
						UserData.Rest_direccion= c.getString(UserData.TAG_DIRECCION_REST);
						UserData.Rest_lat= c.getString(UserData.TAG_LAT_REST);
						UserData.Rest_long= c.getString(UserData.TAG_LONG_REST);


						// creating new HashMap
						HashMap<String, Object> map = new HashMap<String, Object>();
						//
						//
						map.put(UserData.TAG_ID_REST, UserData.idRestaurant);
						map.put(UserData.TAG_NOMBRE_REST, UserData.Rest_nombre);
						map.put(UserData.TAG_TIPO_REST, UserData.Rest_tipo);
						map.put(UserData.TAG_DESCRIPCION_REST, UserData.Rest_descripcion);
						map.put(UserData.TAG_EMAIL_REST, UserData.Rest_email);
						map.put(UserData.TAG_DIRECCION_REST, UserData.Rest_direccion);
						map.put(UserData.TAG_LAT_REST, UserData.Rest_lat);
						map.put(UserData.TAG_LONG_REST, UserData.Rest_long);


						//						// adding HashList to ArrayList
						UserData.lista_restaurantes.add(map);



					}
				} else {

					Intent i = new Intent(getApplicationContext(), perfilCliente.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

			System.out.println("lista rest: " + UserData.lista_restaurantes);


			extract = (HashMap<String, Object>)UserData.lista_restaurantes.get(0);
			for(int i=0;i<UserData.lista_restaurantes.size();i++){
				extract = (HashMap<String, Object>)UserData.lista_restaurantes.get(i);


				mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble((String)(extract.get(UserData.TAG_LAT_REST))), Double.parseDouble((String)(extract.get(UserData.TAG_LONG_REST)))))
						.title(((String)extract.get(UserData.TAG_NOMBRE_REST))).snippet("Vuelva a presionar el marker para mas informacion!"));

				
						mMap.setOnMarkerClickListener(new OnMarkerClickListener(){

				@Override
				public boolean onMarkerClick(Marker arg0) {

					mMap.setOnMarkerClickListener(new OnMarkerClickListener(){

						@Override
						public boolean onMarkerClick(Marker arg0) {

								
								Intent in = new Intent(getApplicationContext(), infoRestaurantes.class);
								UserData.Rest_nombre = arg0.getTitle();
								startActivity(in);
										
							
							
							return false;
						}
					});

					return false;
				}
			});
				
			
			}
			
			
	
		}
	}






	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		if(isGooglePlayServicesAvailable()){
			mLocationClient.connect();
		}

	}


	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();

		super.onStop();
	}


	@Override
	protected void onActivityResult(
			int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case CONNECTION_FAILURE_RESOLUTION_REQUEST:


			switch (resultCode) {
			case Activity.RESULT_OK:
				mLocationClient.connect();
				break;
			}

		}
	}

	private boolean isGooglePlayServicesAvailable() {
		// Check that Google Play services is available
		int resultCode =  GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			return true;
		} else {
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog( resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				// Create a new DialogFragment for the error dialog
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(errorDialog);
				errorFragment.show(getSupportFragmentManager(), "Location Updates");
			}

			return false;
		}
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		Location location = mLocationClient.getLastLocation();
		mLat = location.getLatitude();
		mLng = location.getLongitude();
		LatLng latLng = new LatLng(mLat, mLng);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
		mMap.animateCamera(cameraUpdate);
	}

	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}



	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {


		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(
						this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);


			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(), "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
		}
	}


}
