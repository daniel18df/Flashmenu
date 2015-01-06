package mesas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.flashmenu.aplicacion.JSONParser;
import cl.flashmenu.aplicacion.Paypal;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.servidor;
import cliente.crearReserva;
import cliente.perfilCliente;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class verMesas  extends ListActivity {




	//botones
	Button btnHaciaMesas;
	TextView perfil, cerrar, perfilTitulo;

	//desde intent
	String idRest, usuario, mailRest, direccionRest, idCliente, Cliente_email, hora, fecha;

	//variables para obtener json y radiobutton
	String numero, descripcion, cantidad_personas, n, d, c;

	//variables para obtener json y radiobutton
	String m;
	TextView m1, titulo;
	ImageView imagen;


	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> horariosList;

	private static String url_Lista_horario = servidor.ip() + servidor.ruta2()+"verMesas.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_mesa = "mesa";
	private static final String TAG_NRO = "Mesa_nro";
	private static final String TAG_DESCRIPCION = "Mesa_descripcion";
	private static final String TAG_CANTIDAD = "Mesa_cantPersonas";




	// JSONArray
	JSONArray horariosl = null;

	// Progress Dialog
	private ProgressDialog pDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista2);


		//RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			idRest  = extras.getString("idRest");//
			usuario  = extras.getString("usuario");//
			mailRest  = extras.getString("mailRest");//
			direccionRest  = extras.getString("direccionRest");//
			idCliente = extras.getString("idCliente");
			Cliente_email = extras.getString("Cliente_email");
			hora = extras.getString("hora");
			fecha = extras.getString("fecha");


		}else{
			idRest="error";
			usuario="error";
			mailRest="error";
			direccionRest="error";
			idCliente = "error";
			Cliente_email = "error";
			hora = "error";
			fecha = "error";
		}///
		new LoadAllplatos().execute();
		Toast.makeText(getApplicationContext(), "id cli: "+idCliente, Toast.LENGTH_LONG).show();


		titulo = (TextView) findViewById(R.id.titulolista2);
		titulo.setText("Mesas");

		imagen = (ImageView) findViewById(R.id.imagen2);
		imagen.setImageResource(R.drawable.mesas);

		perfilTitulo = (TextView) findViewById(R.id.nombreClienteLISTA2);
		perfilTitulo.setText(usuario);
		perfil = (TextView) findViewById(R.id.perfilInfoRest2);
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



		// Hashmap for ListView
		horariosList = new ArrayList<HashMap<String, String>>();


		// Get listview
		ListView lv = getListView();


		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {


				m1 = ((TextView) view.findViewById(R.id.numero_mesa));
				m =	m1.getText().toString();



				Intent i = new Intent(getApplicationContext(), Paypal.class);
				i.putExtra("hora", hora);
				i.putExtra("idRest", idRest);
				i.putExtra("usuario", usuario);
				i.putExtra("mailRest", mailRest);
				i.putExtra("direccionRest", direccionRest);
				i.putExtra("idCliente",idCliente);
				i.putExtra("Cliente_email",Cliente_email);
				i.putExtra("fecha",fecha);
				i.putExtra("mesa",m);
				startActivity(i);

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
			pDialog = new ProgressDialog(verMesas.this);
			pDialog.setMessage("Cargando mesas. Please wait...");
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

			params.add(new BasicNameValuePair("buscar", idRest));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_Lista_horario, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					horariosl = json.getJSONArray(TAG_mesa);


					for (int i = 0; i < horariosl.length(); i++) {
						JSONObject c = horariosl.getJSONObject(i);


						numero = c.getString(TAG_NRO);
						descripcion = c.getString(TAG_DESCRIPCION);
						cantidad_personas = c.getString(TAG_CANTIDAD);



						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();


						map.put(TAG_NRO, numero);
						map.put(TAG_DESCRIPCION, descripcion);
						map.put(TAG_CANTIDAD, cantidad_personas);


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
							verMesas.this, horariosList, R.layout.lista_itemmesas,
							new String[] { TAG_NRO, TAG_DESCRIPCION, TAG_CANTIDAD }, new int[] {R.id.numero_mesa, R.id.descripcion_mesa, R.id.cantidad_personas});


					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}



}

