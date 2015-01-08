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
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class preferencia extends ListActivity {
	
	
	String valorPreferencia, n;
	

	private int tipo = 0;
	
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> horariosList;

	private static String url_Lista_tipo_comidas = servidor.ip() + servidor.ruta2()+"ListaPreferenciasComidas.php";
	private static String url_Lista_tipo_distancia = servidor.ip() + servidor.ruta2()+"ListaPreferenciasDistancia.php";
	private static String url_Lista_tipo_precio = servidor.ip() + servidor.ruta2()+"ListaPreferenciasPrecio.php";
	
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	public static final String TAG_Preferencia = "Preferencia_tipo";
	public static final String TAG_PREFERENCIA_TIPO_NOMBRE = "Preferencia_tipo_nombre";
	public static final String TAG_PREFERENCIA_TIPO_VALOR = "Preferencia_tipo_valor";

	
	
	LinearLayout[] Linear = new LinearLayout[3];
	

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
		
		// Hashmap for ListView
		horariosList = new ArrayList<HashMap<String, String>>();
		
		
		
		Linear[0] = (LinearLayout) findViewById(R.id.linearLayoutPreferencias1);
		Linear[1] = (LinearLayout) findViewById(R.id.linearLayoutPreferencias2);
		Linear[2] = (LinearLayout) findViewById(R.id.linearLayoutPreferencias3);
		
		
		
		
		

		// Get listview
		for(int i = 0; i<3; i++){
		ListView lv = (ListView)Linear[i].getChildAt(1);
        
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				HashMap<String, Object> elemento = (HashMap<String, Object>)getListAdapter().getItem(position);
				RelativeLayout textView = (RelativeLayout)getListAdapter().getView(position, view, parent);


				n = (String)elemento.get(TAG_PREFERENCIA_TIPO_VALOR);
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(TAG_PREFERENCIA_TIPO_VALOR, n);
				map.put(UserData.TAG_TIPO, tipo);
					
				((TextView)view.findViewById(R.id.nombrePlato)).setTextColor(Color.BLACK);

				}
		});
	}
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
		public void llenarLista(String url, int index){
			// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);
		
			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					horariosl[index] = json.getJSONArray(TAG_Preferencia);

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
		llenarLista(url_Lista_tipo_comidas, 0);
		llenarLista(url_Lista_tipo_distancia, 1);
		llenarLista(url_Lista_tipo_precio, 2);
			

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
					CopyOfWeatherDataListAdapter[] adapters = new CopyOfWeatherDataListAdapter[3];
					
					for(int j = 0; j<3; j++){
						horariosList = new ArrayList<>();

						for (int i = 0; i < horariosl[j].length(); i++) {
							JSONObject c = null;
							try {
								c = horariosl[j].getJSONObject(i);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								valorPreferencia = c.getString(TAG_PREFERENCIA_TIPO_VALOR);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							//	map.put(TAG_FECHA, fecha);
							map.put(TAG_PREFERENCIA_TIPO_VALOR, valorPreferencia);

							// adding HashList to ArrayList
							horariosList.add(map);
						}
						
						
						adapters[j] = new CopyOfWeatherDataListAdapter(
								 preferencia.this, horariosList, R.layout.lista_itempreferencias,
									new String[] { TAG_PREFERENCIA_TIPO_VALOR }, new int[] {R.id.itempreferencia}, "platos2");
						((ListView)Linear[j].getChildAt(1)).setAdapter(adapters[j]);

					}
				}
			});

		}

	}

	

}
