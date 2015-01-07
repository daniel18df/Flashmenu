package cl.flashmenu.aplicacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cliente.perfilCliente;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


public class preferencia extends ListActivity {
	
	
	String valorPreferencia;
	
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> horariosList;

	private static String url_Lista_tipo_comidas = servidor.ip() + servidor.ruta2()+"ListaPreferenciasComidas.php";
	private static String url_Lista_tipo_distancia = servidor.ip() + servidor.ruta2()+"ListaPreferenciasDistancia.php";
	private static String url_Lista_tipo_precio = servidor.ip() + servidor.ruta2()+"ListaPreferenciasPrecio.php";
	
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_Preferencia = "Preferencia_tipo";
	private static final String TAG_PREFERENCIA_TIPO_NOMBRE = "Preferencia_tipo_nombre";
	private static final String TAG_PREFERENCIA_TIPO_VALOR = "Preferencia_tipo_valor";




	// JSONArray
	JSONArray horariosl = null;

	// Progress Dialog
	private ProgressDialog pDialog;

	ArrayList<String> nombreArrayList = new ArrayList<String>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencia);
		

		new LoadAllpreferencia().execute();
		
		// Hashmap for ListView
		horariosList = new ArrayList<HashMap<String, String>>();

		// Get listview
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
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
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_Lista_tipo_comidas, "POST", params);
			

			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					horariosl = json.getJSONArray(TAG_Preferencia);

					for (int i = 0; i < horariosl.length(); i++) {
						JSONObject c = horariosl.getJSONObject(i);

						valorPreferencia = c.getString(TAG_PREFERENCIA_TIPO_VALOR);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						//	map.put(TAG_FECHA, fecha);
						map.put(TAG_PREFERENCIA_TIPO_VALOR, valorPreferencia);

						// adding HashList to ArrayList
						horariosList.add(map);
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

			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							preferencia.this, horariosList, R.layout.lista_itempreferencias,
							new String[] { TAG_PREFERENCIA_TIPO_VALOR }, new int[] {R.id.itempreferencia});

					ListAdapter adapter2 = new SimpleAdapter(
							preferencia.this, horariosList, R.layout.lista_itempreferencias,
							new String[] { TAG_PREFERENCIA_TIPO_VALOR }, new int[] {R.id.itempreferencia});
					
					ListAdapter adapter3 = new SimpleAdapter(
							preferencia.this, horariosList, R.layout.lista_itempreferencias,
							new String[] { TAG_PREFERENCIA_TIPO_VALOR }, new int[] {R.id.itempreferencia});

					// updating listview
					setListAdapter(adapter);
					setListAdapter(adapter2);
					setListAdapter(adapter3);
				}
			});

		}

	}

	

}
