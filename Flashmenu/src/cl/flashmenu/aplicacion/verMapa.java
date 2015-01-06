package cl.flashmenu.aplicacion;

import java.util.ArrayList;
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
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



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
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class verMapa extends FragmentActivity implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener{


	// General Variables

	TextView perfilUsuario, perfil;


	String usuario, idRest, idCliente, Cliente_email, ic, ce;
	//int idRest;

	private static final String TAG_SUCCESS = "success";

	JSONParser jParser = new JSONParser();

	////
	private static String url_all_inforest = servidor.ip() +servidor.ruta2() + "getCliente.php";
	JSONArray j1 = null;
	private static final String TAG_ID = "idCliente";
	private static final String TAG_EMAIL = "Cliente_email";
	private static final String TAG_cliente = "cliente";
	//////


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



	/////////////////
	/////////////////


	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vermapa);


		mLocationClient = new LocationClient(this, this, this);

		//We create a map
		mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		mMap = mapFragment.getMap();
		//and enable its location
		mMap.setMyLocationEnabled(true);
		new getID().execute();





		///

		//			RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			usuario  = extras.getString("usuario");//usuario


		}else{
			usuario="error";
		}///
		new getID().execute();

		perfilUsuario = (TextView) findViewById(R.id.nombreClienteMapa);
		perfilUsuario.setText(usuario);


		perfil = (TextView) findViewById(R.id.perfilInfoRestMapa);
		perfil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
				i.putExtra("usuario",usuario);
				i.putExtra("idCliente",idCliente);
				startActivity(i);

				//finish();
			}
		});


		mMap.addMarker(new MarkerOptions().position(new LatLng(-33.013779, -71.543099))
				.title("sushihome").snippet("Vuelva a presionar el marker para mas informacion!"));

		mMap.addMarker(new MarkerOptions().position(new LatLng(-33.013591, -71.542563))
				.title("MAIA").snippet("Vuelva a presionar el marker para mas informacion!"));

		mMap.addMarker(new MarkerOptions().position(new LatLng(-33.017302, -71.543780))
				.title("Sanito").snippet("Vuelva a presionar el marker para mas informacion!"));

		mMap.addMarker(new MarkerOptions().position(new LatLng(-33.012863, -71.548731))
				.title("El rancho del cordobes").snippet("Vuelva a presionar el marker para mas informacion!"));

		//		mMap.addMarker(new MarkerOptions().position(new LatLng(-33.012863, -71.548791))
		//				.title("Unab").snippet("Vuelva a presionar el marker para mas informacion!"));



		mMap.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker arg0) {



				//////////
				mMap.setOnMarkerClickListener(new OnMarkerClickListener(){

					@Override
					public boolean onMarkerClick(Marker arg0) {

						if(arg0.getTitle().equalsIgnoreCase("sushihome")){					
							Intent i = new Intent(getApplicationContext(), infoRestaurantes.class);
							idRest = "6";
							i.putExtra("idRest", idRest);
							i.putExtra("usuario",usuario);
							i.putExtra("idCliente",idCliente);
							i.putExtra("Cliente_email",Cliente_email);
							Toast.makeText(getApplicationContext(),"id cliente "+ idCliente, Toast.LENGTH_LONG).show();
							Toast.makeText(getApplicationContext(), "email: "+ Cliente_email, Toast.LENGTH_LONG).show();
							startActivity(i);
						}
						else if(arg0.getTitle().equalsIgnoreCase("El rancho del cordobes")){
							Intent i = new Intent(getApplicationContext(), infoRestaurantes.class);
							idRest = "7";
							i.putExtra("idRest", idRest);
							i.putExtra("usuario",usuario);
							i.putExtra("idCliente",idCliente);
							i.putExtra("Cliente_email",Cliente_email);
							Toast.makeText(getApplicationContext(), idRest, Toast.LENGTH_LONG).show();
							Toast.makeText(getApplicationContext(), idCliente, Toast.LENGTH_LONG).show();
							startActivity(i);
						}
						else if(arg0.getTitle().equalsIgnoreCase("MAIA")){
							Intent i = new Intent(getApplicationContext(), infoRestaurantes.class);
							idRest = "8";
							i.putExtra("idRest", idRest);
							i.putExtra("usuario",usuario);
							i.putExtra("idCliente",idCliente);
							i.putExtra("Cliente_email",Cliente_email);
							Toast.makeText(getApplicationContext(), idRest, Toast.LENGTH_LONG).show();
							startActivity(i);
						}
						else if(arg0.getTitle().equalsIgnoreCase("Sanito")){
							Intent i = new Intent(getApplicationContext(), infoRestaurantes.class);
							idRest = "9";
							i.putExtra("idRest", idRest);
							i.putExtra("usuario",usuario);
							i.putExtra("idCliente",idCliente);
							i.putExtra("Cliente_email",Cliente_email);
							Toast.makeText(getApplicationContext(), idRest, Toast.LENGTH_LONG).show();
							startActivity(i);
						}
						//						else if(arg0.getTitle().equalsIgnoreCase("unab")){
						//							Intent i = new Intent(getApplicationContext(), infoRestaurantes.class);
						//							idRest = "10";
						//							i.putExtra("idRest", idRest);
						//							i.putExtra("usuario",usuario);
						//							i.putExtra("idCliente",idCliente);
						//							i.putExtra("Cliente_email",Cliente_email);
						//							Toast.makeText(getApplicationContext(), idRest, Toast.LENGTH_LONG).show();
						//							startActivity(i);
						//						}




						return false;
					}
				});

				//////////

				return false;
			}
		});


	}
	//oncreate




	public class getID extends AsyncTask<String, String, String> {
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("buscar", usuario));

			JSONObject json = jParser.makeHttpRequest(url_all_inforest, "POST", params);

			Log.d("All : cliente", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					j1 = json.getJSONArray(TAG_cliente);

					for (int i = 0; i < j1.length(); i++) {
						JSONObject c = j1.getJSONObject(i);

						idCliente = c.getString(TAG_ID);
						Cliente_email = c.getString(TAG_EMAIL);

					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
	}
	//



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
