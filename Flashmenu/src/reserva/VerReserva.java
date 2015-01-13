package reserva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mesas.horario;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.flashmenu.aplicacion.JSONParser;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.UserData;
import cl.flashmenu.aplicacion.servidor;
import cliente.perfilCliente;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class VerReserva extends ListActivity{

	// Progress Dialog
	private ProgressDialog pDialog;



	String idd;

	String h, f, i;

	

	TextView perfil, cerrar, perfilUsuario, titulo;

	
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> PlatosList;

	private static String url_Lista_platos = servidor.ip() + servidor.ruta2()+"verreserva.php";

	// JSON Node names
//	
//	private static final String TAG_NOMBREREST = "Rest_nombre";
//	private static final String TAG_DIRECCIONREST = "Rest_direccion";
//
//	private static final String TAG_restaurant = "restaurant";

	JSONArray j1 = null;




	// JSONArray
	JSONArray platosl = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista2); 

		

		perfilUsuario = (TextView) findViewById(R.id.nombreClienteLISTA2);
		perfilUsuario.setText(UserData.Cliente_email);



		new LoadAllplatos().execute();
		
		titulo = (TextView) findViewById(R.id.titulolista2);
		titulo.setText("Mis reservas");


		perfil = (TextView) findViewById(R.id.perfilInfoRest2);
		perfil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
				
				startActivity(i);

				//finish();
			}
		});

		

		// Hashmap for ListView
		PlatosList = new ArrayList<HashMap<String, String>>();



		// Get listview
		ListView lv = getListView();


		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//
//				f = ((TextView) view.findViewById(R.id.text_fecha)).getText().toString();
//				h = ((TextView) view.findViewById(R.id.text_hota)).getText().toString();
				i = ((TextView) view.findViewById(R.id.text_id)).getText().toString();
//

				Intent in = new Intent(getApplicationContext(),	perfilReserva.class);
				in.putExtra("i", i);
				startActivityForResult(in, 100);

			}
		});

	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

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
			pDialog = new ProgressDialog(VerReserva.this);
			pDialog.setMessage("Cargando platos. Please wait...");
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

			params.add(new BasicNameValuePair("buscar", UserData.idCliente));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_Lista_platos, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {

					platosl = json.getJSONArray(UserData.TAG_reserva);
//					platosl = json.getJSONArray(TAG_restaurant);


					for (int i = 0; i < platosl.length(); i++) {
						JSONObject c = platosl.getJSONObject(i);

						idd = c.getString(UserData.TAG_ID_RESERVA);
						UserData.VReserva_fecha = c.getString(UserData.TAG_FECHA_RESERVA);
						UserData.VReserva_hora = c.getString(UserData.TAG_HORA_RESERVA);
//						nombre_rest = c.getString(TAG_NOMBREREST);
//						direccion_rest = c.getString(TAG_DIRECCIONREST);



						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						map.put(UserData.TAG_ID_RESERVA, idd);
						map.put(UserData.TAG_FECHA_RESERVA, UserData.VReserva_fecha);
						map.put(UserData.TAG_HORA_RESERVA, UserData.VReserva_hora);
//						map.put(TAG_NOMBREREST, nombre_rest);
//						map.put(TAG_DIRECCIONREST, direccion_rest);


						// adding HashList to ArrayList
						PlatosList.add(map);
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
							VerReserva.this, PlatosList, R.layout.lista_itemreserva,
							new String[] { UserData.TAG_ID_RESERVA, UserData.TAG_FECHA_RESERVA, UserData.TAG_HORA_RESERVA}, new int[] {R.id.text_id, R.id.text_fecha, R.id.text_hota});


					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
	
	
	
	


}