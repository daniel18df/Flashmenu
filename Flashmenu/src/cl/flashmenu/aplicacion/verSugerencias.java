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

import cl.flashmenu.aplicacion.JSONParser;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.UserData;
import cl.flashmenu.aplicacion.servidor;
import cl.flashmenu.aplicacion.verMapa;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class verSugerencias extends ListActivity{

	// Progress Dialog
	private ProgressDialog pDialog;

	String n, d, p, idd;

	TextView perfil, cerrar, perfilTitulo, perfilUsuario;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, Object>> SuegerenciasList;
	ArrayList<HashMap<String, Object>> DatosPlatos;
	DecimalFormat fmt;
	private static String url_Lista_platos = servidor.ip() + servidor.ruta2()+"verSugerencias.php";
	private static String url_all_getRest = servidor.ip() + servidor.ruta2()+"restaurantes.php";

	// JSON Node names
	JSONArray j1 = null;

	String preferencias_tipo;

	// JSONArray
	JSONArray sugerencias = null;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista2); 

		
		perfilUsuario = (TextView) findViewById(R.id.nombreClienteLISTA2);
		perfilUsuario.setText(UserData.Cliente_email);
		
		perfilTitulo = (TextView) findViewById(R.id.titulolista2);
		perfilTitulo.setText("Sugerencias");

		perfil = (TextView) findViewById(R.id.perfilInfoRest2);
		perfil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
				startActivity(i);

				//finish();
			}
		});
		


		String columna;
		if(UserData.lista_preferencias_tipo.size() > 1){
			columna = getColumnName((String)((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(0)).get(UserData.TAG_PREFERENCIAS_TIPO_NOMBRE));
			preferencias_tipo = columna + " = '" + ((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(0)).get(UserData.TAG_PREFERENCIAS_TIPO_VALOR) + "'";

			for(int i = 1; i<UserData.lista_preferencias_tipo.size(); i++){
				columna = getColumnName((String)((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(i)).get(UserData.TAG_PREFERENCIAS_TIPO_NOMBRE));
				preferencias_tipo += " OR " + columna + " = '" +((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(i)).get(UserData.TAG_PREFERENCIAS_TIPO_VALOR) + "'";
			}
		}
		else if(UserData.lista_preferencias_tipo.size() == 1){
			columna = getColumnName((String)((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(0)).get(UserData.TAG_PREFERENCIAS_TIPO_NOMBRE));
			preferencias_tipo = columna + " = '" + ((HashMap<String, Object>)UserData.lista_preferencias_tipo.get(0)).get(UserData.TAG_PREFERENCIAS_TIPO_VALOR) + "'";
		}
		else 
			preferencias_tipo = "";

		System.out.println("preferencias " + preferencias_tipo);
		new LoadAllplatos().execute();



		// Hashmap for ListView
		SuegerenciasList = new ArrayList<HashMap<String, Object>>();;


		// Get listview
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {


				HashMap<String, Object> elemento = (HashMap<String, Object>)getListAdapter().getItem(position);
				RelativeLayout textView = (RelativeLayout)getListAdapter().getView(position, view, parent);

				n = (String)elemento.get(UserData.TAG_NOMBRE_PRODUCTO);
				d = (String)elemento.get(UserData.TAG_DESCRIPCION_PRODUCTO);
				p = (String)elemento.get(UserData.TAG_PRECIO_PRODUCTO);
				UserData.idRestaurant = (String)elemento.get(UserData.TAG_ID_PRODUCTO);
				new getRest().execute();

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(UserData.TAG_NOMBRE_PRODUCTO, n);
				map.put(UserData.TAG_DESCRIPCION_PRODUCTO, d);
				map.put(UserData.TAG_PRECIO_PRODUCTO, p);
				map.put(UserData.TAG_ID_PRODUCTO, UserData.idRestaurant);
				map.put(UserData.TAG_POSITION_PRODUCTO, position);


				Intent i = new Intent(getApplicationContext(), Productos.class);
//				i.putExtra("idRest", idd);
//				i.putExtra("usuario", usuario);
//				i.putExtra("mailRest", email_rest);
//				i.putExtra("direccionRest", direccion_rest);
//				i.putExtra("idCliente", UserData.idCliente);
//				i.putExtra("Cliente_email",UserData.Cliente_email);
//				i.putExtra("nombre_rest", nombre_rest);


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
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {

					sugerencias = json.getJSONArray(UserData.TAG_producto);

					for (int i = 0; i < sugerencias.length(); i++) {
						JSONObject c = sugerencias.getJSONObject(i);


						UserData.SProducto_nombre = c.getString(UserData.TAG_NOMBRE_PRODUCTO);
						UserData.SProducto_descripcion = c.getString(UserData.TAG_DESCRIPCION_PRODUCTO);
						UserData.SProducto_precio = c.getString(UserData.TAG_PRECIO_PRODUCTO);
						UserData.SProducto_tipo	= c.getString(UserData.TAG_TIPO_PRODUCTO);
						UserData.SProducto_tipo_preferencia= c.getString(UserData.TAG_TIPO_PREFERENCIAS_PRODUCTO);
						UserData.sRestaurant_idRestaurant = c.getString(UserData.TAG_ID_PRODUCTO);


						// creating new HashMap
						HashMap<String, Object> map = new HashMap<String, Object>();


						map.put(UserData.TAG_NOMBRE_PRODUCTO, UserData.SProducto_nombre);
						map.put(UserData.TAG_DESCRIPCION_PRODUCTO, UserData.SProducto_descripcion);
						map.put(UserData.TAG_PRECIO_PRODUCTO, UserData.SProducto_precio);
						map.put(UserData.TAG_TIPO_PRODUCTO, UserData.SProducto_tipo) ;
						map.put(UserData.TAG_TIPO_PREFERENCIAS_PRODUCTO, UserData.SProducto_tipo_preferencia);
						map.put(UserData.TAG_ID_PRODUCTO, UserData.sRestaurant_idRestaurant);
						

						// adding HashList to ArrayList
						SuegerenciasList.add(map);
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
							verSugerencias.this, SuegerenciasList, R.layout.list_itemplatos,
							new String[] { UserData.TAG_NOMBRE_PRODUCTO, UserData.TAG_DESCRIPCION_PRODUCTO, UserData.TAG_PRECIO_PRODUCTO, UserData.TAG_CANTIDAD_PRODUCTO}, new int[] {R.id.nombrePlato, R.id.descripcionPlato, R.id.precioPlato, R.id.cantidadPlatosSeleccionados});

					
					// updating listview
					setListAdapter(adapter);
				}
			});
		}
	}


	public class getRest extends AsyncTask<String, String, String> {
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("buscar", UserData.idRestaurant));

			JSONObject json = jParser.makeHttpRequest(url_all_getRest, "POST", params);

			Log.d("All : cliente", json.toString());

			try {
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {
					j1 = json.getJSONArray(UserData.TAG_restaurant);

					for (int i = 0; i < j1.length(); i++) {
						JSONObject c = j1.getJSONObject(i);

						UserData.Rest_email = c.getString(UserData.TAG_EMAIL_REST);
						UserData.Rest_nombre  = c.getString(UserData.TAG_NOMBRE_REST);
						UserData.Rest_tipo	 = c.getString(UserData.TAG_TIPO_REST);
						UserData.Rest_direccion = c.getString(UserData.TAG_DIRECCION_REST);
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