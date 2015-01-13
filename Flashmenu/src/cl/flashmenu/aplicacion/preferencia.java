package cl.flashmenu.aplicacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import restaurant.infoRestaurantes;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import cliente.crearCliente;
import cliente.inicioSesionCliente;
import cliente.perfilCliente;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;


public class preferencia extends ListActivity {

	/////////////////////////
	String valorPreferencia, n;
	
	JSONParser jsonParser = new JSONParser();

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	
	// JSONArray
		JSONArray preferencia = null;

	HashMap<String, Object> extract;
	ArrayList<HashMap<String, String>> horariosList;

	private static String url_Lista_tipo = servidor.ip() + servidor.ruta2()+"ListaPreferencias.php";
	private static String url_guardar_perfil = servidor.ip() + servidor.ruta2()+"guardarPerfil.php";



	LinearLayout[] Linear = new LinearLayout[3];

	ArrayList<HashMap<String, Object>> DatosPlatos;
	// JSONArray
	JSONArray[] horariosl = new JSONArray[3];


	// Progress Dialog
	private ProgressDialog pDialog;

	ArrayList<String> nombreArrayList = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencia);


		new LoadAllpreferencia().execute();

		//DatosPorDefecto2();

		// Hashmap for ListView
		horariosList = new ArrayList<HashMap<String, String>>();

		Linear[0] = (LinearLayout) findViewById(R.id.linearLayoutPreferencias1);
//		Linear[1] = (LinearLayout) findViewById(R.id.linearLayoutPreferencias2);
//		Linear[2] = (LinearLayout) findViewById(R.id.linearLayoutPreferencias3);

		DatosPlatos = new ArrayList<HashMap<String, Object>>();

		// Get listview
		//for(int i = 0; i<3; i++){
		int i = 0;
		ListView lv = (ListView)Linear[i].getChildAt(1);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				HashMap<String, Object> elemento = (HashMap<String, Object>)((ListView)Linear[0].getChildAt(1)).getAdapter().getItem(position);
				RelativeLayout textView = (RelativeLayout)((ListView)Linear[0].getChildAt(1)).getAdapter().getView(position, view, parent);

				n = (String)elemento.get(UserData.TAG_PREFERENCIAS_TIPO_VALOR);

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(UserData.TAG_PREFERENCIAS_TIPO_VALOR, n);
				// adding HashList to ArrayList

			//	System.out.println("maaaapppp"+ User)
					UserData.lista2.add(map);
			}

		});
		//}
	}


	/**
	 * Background Async Task to Load all fechas by making HTTP Request
	 * */
	class LoadAllpreferencia extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(preferencia.this);
			pDialog.setMessage("Cargando. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All platos from url
		 * */
		public void llenarLista(String url, int index, String buscar){
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("buscar", buscar));

			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {

					horariosl[index] = json.getJSONArray(UserData.TAG_preferencia_tipo);

				} else {

					Intent i = new Intent(getApplicationContext(), perfilCliente.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		
		protected String doInBackground(String... args) {

			// getting JSON string from URL
			llenarLista(url_Lista_tipo, 0, "Comidas");
//			llenarLista(url_Lista_tipo, 1, "Distancia");
//			llenarLista(url_Lista_tipo, 2, "Precio");

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					SimpleAdapter[] adapters = new SimpleAdapter[3];

				//	for(int j = 0; j<3; j++){
						horariosList = new ArrayList<>();

						for (int i = 0; i < horariosl[0].length(); i++) {
							JSONObject c = null;
							try {
								c = horariosl[0].getJSONObject(i);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								valorPreferencia = c.getString(UserData.TAG_PREFERENCIAS_TIPO_VALOR);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							//	map.put(TAG_FECHA, fecha);
							map.put(UserData.TAG_PREFERENCIAS_TIPO_VALOR, valorPreferencia);

							// adding HashList to ArrayList
							horariosList.add(map);
						}

						adapters[0] = new SimpleAdapter(
								preferencia.this, horariosList, R.layout.lista_itempreferencias,
								new String[] { UserData.TAG_PREFERENCIAS_TIPO_VALOR }, new int[] {R.id.itempreferencia});
						((ListView)Linear[0].getChildAt(1)).setAdapter(adapters[0]);
					//}
				}
			});
		}
	}
	
	
	
	
	/**
	 * Background Async Task to Create new cliente
	 * */
	class guardarPerfil extends AsyncTask<String, String, String> {
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(preferencia.this);
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
			JSONObject json = jParser.makeHttpRequest(url_guardar_perfil, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All restaurantes: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {

					preferencia = json.getJSONArray(UserData.TAG_restaurant);

					for (int i = 0; i < preferencia.length(); i++) {
						JSONObject c = preferencia.getJSONObject(i);


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




			}
				
			
			}
			
			
	
		}



}
