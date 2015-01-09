package cl.flashmenu.aplicacion;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.flashmenu.aplicacion.JSONParser;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.UserData;
import cl.flashmenu.aplicacion.servidor;
import cl.flashmenu.aplicacion.verMapa;
import cl.flashmenu.aplicacion.verMapa.getPreferencias;
import cliente.perfilCliente;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class verSugerencias extends ListActivity{

	// Progress Dialog
	private ProgressDialog pDialog;

	//recibidos por intent
	String usuario, idRest, mailRest, direccionRest, idCliente, Cliente_email;


	String idRestaurant, nombreP, descripcionP, precioP;

	String email_rest ,nombre_rest ,tipo_rest,direccion_rest; 

	String n, d, p, idd;

	TextView nn, dd, pp, totalEnCarta;

	TextView perfil, cerrar, perfilTitulo;
	ImageView imagen;
	RadioButton rb;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, Object>> PlatosList;
	ArrayList<HashMap<String, Object>> DatosPlatos;
	DecimalFormat fmt;
	private static String url_Lista_platos = servidor.ip() + servidor.ruta2()+"verSugerencias.php";
	private static String url_all_getRest = servidor.ip() + servidor.ruta2()+"redtaurantes.php";

	// JSON Node names
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_platos = "Producto";
	public static final String TAG_NOMBRE = "Producto_nombre";
	public static final String TAG_DESCRIPCION = "Producto_descripcion";
	public static final String TAG_PRECIO = "Producto_precio";
	//public static final String TAG_TIPO = "Producto_tipo";
	public static final String TAG_ID = "Restaurant_idRestaurant";
	public static final String TAG_SPINNER = "Restaurant_spinner";
	public static final String TAG_POSITION = "Position_Lista";
	public static final String TAG_CANTIDAD = "cantidad";




	private static final String TAG_IDREST = "idRestaurant";
	private static final String TAG_NOMBREREST = "Rest_nombre";
	private static final String TAG_TIPOREST = "Rest_tipo";
	private static final String TAG_DESCRIPCIONREST = "Rest_descripcion";
	private static final String TAG_EMAILREST = "Rest_email";
	private static final String TAG_DIRECCIONREST = "Rest_direccion";
	private static final String TAG_restaurant = "restaurant";


	JSONArray j1 = null;

	String preferencias_tipo; 
	TextView perfilUsuario;

	// JSONArray
	JSONArray platosl = null;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista2); 


		//RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			usuario  = extras.getString("usuario");
			idCliente = extras.getString("idCliente");
			Cliente_email = extras.getString("Cliente_email");

		}else{
			usuario="error";
			idCliente = "error";
			Cliente_email = "error";
		}///
		
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
		perfilTitulo = (TextView) findViewById(R.id.nombreClienteMapa);
		perfilTitulo.setText("Sugerencias");


		String columna;
		if(UserData.lista_preferencias_tipo.size() > 1){
			columna = getColumnName((String)((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(0)).get(verMapa.TAG_PREFERENCIAS_TIPO_NOMBRE));
			preferencias_tipo = columna + " = '" + ((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(0)).get(verMapa.TAG_PREFERENCIAS_TIPO_VALOR) + "'";

			for(int i = 1; i<UserData.lista_preferencias_tipo.size(); i++){
				columna = getColumnName((String)((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(i)).get(verMapa.TAG_PREFERENCIAS_TIPO_NOMBRE));
				preferencias_tipo += " OR " + columna + " = '" +((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(i)).get(verMapa.TAG_PREFERENCIAS_TIPO_VALOR) + "'";
			}
		}
		else if(UserData.lista_preferencias_tipo.size() == 1){
			columna = getColumnName((String)((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(0)).get(verMapa.TAG_PREFERENCIAS_TIPO_NOMBRE));
			preferencias_tipo = columna + " = '" + ((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(0)).get(verMapa.TAG_PREFERENCIAS_TIPO_VALOR) + "'";
		}
		else 
			preferencias_tipo = "";

		System.out.println("preferencias " + preferencias_tipo);
		new LoadAllplatos().execute();



		// Hashmap for ListView
		PlatosList = new ArrayList<HashMap<String, Object>>();;


		// Get listview
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {


				HashMap<String, Object> elemento = (HashMap<String, Object>)getListAdapter().getItem(position);
				RelativeLayout textView = (RelativeLayout)getListAdapter().getView(position, view, parent);
				//nn = ((TextView) elemento.get(TAG_NOMBRE));
				//dd = ((TextView) elemento.get(TAG_DESCRIPCION));
				//pp = ((TextView) elemento.get(TAG_PRECIO));

				n = (String)elemento.get(TAG_NOMBRE);
				d = (String)elemento.get(TAG_DESCRIPCION);
				p = (String)elemento.get(TAG_PRECIO);
				idd = (String)elemento.get(TAG_ID);

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(TAG_NOMBRE, n);
				map.put(TAG_DESCRIPCION, d);
				map.put(TAG_PRECIO, p);
				map.put(TAG_ID, idd);
				map.put(TAG_POSITION, position);


				Intent i = new Intent(getApplicationContext(), Productos.class);
				i.putExtra("idRest", idd);
				i.putExtra("usuario", usuario);
				i.putExtra("mailRest", email_rest);
				i.putExtra("direccionRest", direccion_rest);
				i.putExtra("idCliente", idCliente);
				i.putExtra("Cliente_email", Cliente_email);
				i.putExtra("nombre_rest", nombre_rest);


				startActivity(i);

			}
		});



	}

	private String getColumnName(String name){
		String columna = "Nada";
		if(name.equals("Comidas")){
			columna = "Producto_tipo_preferencia";
		}
		else if(name.equals("Precio")){
			columna = "Producto_precio";
		}
		return columna;

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
			pDialog = new ProgressDialog(verSugerencias.this);
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


			params.add(new BasicNameValuePair("preferencias_tipo", preferencias_tipo));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_Lista_platos, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					platosl = json.getJSONArray(TAG_platos);

					for (int i = 0; i < platosl.length(); i++) {
						JSONObject c = platosl.getJSONObject(i);


						nombreP = c.getString(TAG_NOMBRE);
						descripcionP = c.getString(TAG_DESCRIPCION);
						precioP = c.getString(TAG_PRECIO);
						idRestaurant = c.getString(TAG_ID);


						// creating new HashMap
						HashMap<String, Object> map = new HashMap<String, Object>();


						map.put(TAG_NOMBRE, nombreP);
						map.put(TAG_DESCRIPCION, descripcionP);
						map.put(TAG_PRECIO, precioP);
						map.put(TAG_ID, idRestaurant);
						//Spinner newSpinner = new Spinner(listaPlatos2.this);
						//DatosPorDefecto2(newSpinner);
						//map.put(TAG_SPINNER, null);


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
							verSugerencias.this, PlatosList, R.layout.list_itemplatos,
							new String[] { TAG_NOMBRE, TAG_DESCRIPCION, TAG_PRECIO, TAG_CANTIDAD}, new int[] {R.id.nombrePlato, R.id.descripcionPlato, R.id.precioPlato, R.id.cantidadPlatosSeleccionados});

					//new String[] { TAG_NOMBRE, TAG_DESCRIPCION, TAG_PRECIO,TAG_SPINNER }, new int[] {R.id.nombrePlato, R.id.descripcionPlato, R.id.precioPlato, R.id.spinnerPlato});

					// updating listview
					setListAdapter(adapter);
					//HashMap<String, Object> adapterData;
					//adapterData = (HashMap<String,Object>)adapter.getItem(4);
					//DatosPorDefecto2((Spinner)adapterData.get(TAG_SPINNER));
				}
			});


		}

	}




	public class getRest extends AsyncTask<String, String, String> {
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("buscar", idRest));

			JSONObject json = jParser.makeHttpRequest(url_all_getRest, "POST", params);

			Log.d("All : cliente", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					j1 = json.getJSONArray(TAG_restaurant);

					for (int i = 0; i < j1.length(); i++) {
						JSONObject c = j1.getJSONObject(i);

						//						idCliente = c.getString(TAG_ID);
						//						Cliente_email = c.getString(TAG_EMAIL);
						//						Cliente_nombre  = c.getString(TAG_NOMBRE);
						//						Cliente_apellidoPaterno	 = c.getString(TAG_APELLIDOP);
						//						Cliente_apellidoMaterno = c.getString(TAG_APELLIDOM);
						//						Cliente_rut	 = c.getString(TAG_RUT);
						//						Cliente_direccion = c.getString(TAG_DIRECCION);


						email_rest = c.getString(TAG_EMAILREST);
						nombre_rest  = c.getString(TAG_NOMBREREST);
						tipo_rest	 = c.getString(TAG_TIPOREST);
						direccion_rest = c.getString(TAG_DIRECCIONREST);
					}


				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
	}


}