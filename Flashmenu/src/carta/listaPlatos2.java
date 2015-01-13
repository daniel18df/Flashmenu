package carta;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
import cl.flashmenu.aplicacion.MainActivity;
import cl.flashmenu.aplicacion.MainActivity.UserDa;
import cl.flashmenu.aplicacion.Paypal;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.UserData;
import cl.flashmenu.aplicacion.WeatherDataListAdapter;
import cl.flashmenu.aplicacion.servidor;
import cl.flashmenu.aplicacion.verMapa;
import cliente.perfilCliente;


import android.R.menu;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class listaPlatos2 extends ListActivity{

	// Progress Dialog
	private ProgressDialog pDialog;

	//recibidos por intent
	String usuario;


	String idd, nombreP, descripcionP, precioP;

	String n, d, p;

	TextView nn, dd, pp, totalEnCarta;

	TextView perfil, cerrar, perfilTitulo;
	ImageView imagen;
	RadioButton rb;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, Object>> PlatosList;
	ArrayList<HashMap<String, Object>> DatosPlatos;
	DecimalFormat fmt;
	private static String url_Lista_platos = servidor.ip() + servidor.ruta2()+"ListaPlatos.php";




	private boolean agregar = true;
	private Spinner spinner2;
	private List<String> lista2;
	String L;
	ListActivity me;

	JSONArray j1 = null;
	private int tipo = 0;

	String preferencias_tipo; 

	// JSONArray
	JSONArray platosl = null;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		url_Lista_platos = UserData.url_actual;
		tipo = UserData.tipo;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista); 

		//RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

//			idRest  = extras.getString("idRest");
//			usuario  = extras.getString("usuario");
//			mailRest  = extras.getString("mailRest");
//			direccionRest  = extras.getString("direccionRest");
//			idCliente = extras.getString("idCliente");

		}else{
//			idRest="error";
//			usuario="error";
//			mailRest="error";
//			direccionRest="error";
//			idCliente = "error";
		}///
		
		DatosPorDefecto2();
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

		me = this;
		//		imagen = (ImageView) findViewById(R.id.imagen);
		//		imagen.setImageResource(R.drawable.menu);



		fmt = new DecimalFormat();
		DecimalFormatSymbols fmts = new DecimalFormatSymbols();

		fmts.setGroupingSeparator('.');

		fmt.setGroupingSize(3);
		fmt.setGroupingUsed(true);
		fmt.setDecimalFormatSymbols(fmts);




	
		


		perfilTitulo = (TextView) findViewById(R.id.nombreClienteLISTA);
		perfilTitulo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
//				i.putExtra("usuario",usuario);
//				i.putExtra("idCliente",idCliente);
				startActivity(i);

				//finish();
			}
		});
perfilTitulo.setText(UserData.Cliente_email);

		// Hashmap for ListView
		PlatosList = new ArrayList<HashMap<String, Object>>();
		DatosPlatos = new ArrayList<HashMap<String, Object>>();
		totalEnCarta = (TextView)findViewById(R.id.totalEnCarta2);
		totalEnCarta.setText("$" + fmt.format(UserData.Precio(tipo)));


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

				n = (String)elemento.get(UserData.TAG_NOMBRE_PRODUCTO);
				d = (String)elemento.get(UserData.TAG_DESCRIPCION_PRODUCTO);
				p = (String)elemento.get(UserData.TAG_PRECIO_PRODUCTO);

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(UserData.TAG_NOMBRE_PRODUCTO, n);
				map.put(UserData.TAG_DESCRIPCION_PRODUCTO, d);
				map.put(UserData.TAG_PRECIO_PRODUCTO, p);
				map.put(UserData.TAG_POSITION_PRODUCTO, position);
				map.put(UserData.TAG_TIPO, tipo);
				// adding HashList to ArrayList
				int cantidad = Integer.parseInt(((TextView)view.findViewById(R.id.cantidadPlatosSeleccionados)).getText().toString());

				if(agregar){
					DatosPlatos.add(map);
					UserData.lista.add(map);
					cantidad++;
					((TextView)view.findViewById(R.id.nombrePlato)).setTextColor(Color.BLACK);
					((TextView)textView.findViewById(R.id.descripcionPlato)).setTextColor(Color.BLACK);
					((TextView)textView.findViewById(R.id.precioPlato)).setTextColor(Color.BLACK);
				}else{
					if(cantidad>0){
						DatosPlatos.remove(map);
						UserData.lista.remove(map);
						cantidad--;	
					}
					if(cantidad == 0){
						((TextView)view.findViewById(R.id.nombrePlato)).setTextColor(Color.WHITE);
						((TextView)textView.findViewById(R.id.descripcionPlato)).setTextColor(Color.WHITE);
						((TextView)textView.findViewById(R.id.precioPlato)).setTextColor(Color.WHITE);
					}

				}
				((TextView)view.findViewById(R.id.cantidadPlatosSeleccionados)).setText(String.valueOf(cantidad));


				totalEnCarta.setText("$" + fmt.format(UserData.Precio(tipo)));
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
	private void DatosPorDefecto2() {
		spinner2 = (Spinner) findViewById(R.id.spinnerCarta2);
		lista2 = new ArrayList<String>();
		spinner2 = (Spinner) this.findViewById(R.id.spinnerCarta2);
		lista2.add("Agregar");
		lista2.add("Quitar");


		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista2);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adaptador);

		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();
				if(arg0.getItemAtPosition(arg2).toString().equals("Agregar")){
					agregar = true;
				}
				else{
					agregar = false;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
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
			pDialog = new ProgressDialog(listaPlatos2.this);
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

			params.add(new BasicNameValuePair("buscar", UserData.idRestaurant));
			params.add(new BasicNameValuePair("preferencias_tipo", preferencias_tipo));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_Lista_platos, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {

					platosl = json.getJSONArray(UserData.TAG_producto);

					for (int i = 0; i < platosl.length(); i++) {
						JSONObject c = platosl.getJSONObject(i);


						nombreP = c.getString(UserData.TAG_NOMBRE_PRODUCTO);
						descripcionP = c.getString(UserData.TAG_DESCRIPCION_PRODUCTO);
						precioP = c.getString(UserData.TAG_PRECIO_PRODUCTO);


						// creating new HashMap
						HashMap<String, Object> map = new HashMap<String, Object>();


						map.put(UserData.TAG_NOMBRE_PRODUCTO, nombreP);
						map.put(UserData.TAG_DESCRIPCION_PRODUCTO, descripcionP);
						map.put(UserData.TAG_PRECIO_PRODUCTO, precioP);
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
					WeatherDataListAdapter adapter = new WeatherDataListAdapter(
							listaPlatos2.this, PlatosList, R.layout.list_itemplatos,
							new String[] { UserData.TAG_NOMBRE_PRODUCTO, UserData.TAG_DESCRIPCION_PRODUCTO, UserData.TAG_PRECIO_PRODUCTO, UserData.TAG_CANTIDAD_PRODUCTO}, new int[] {R.id.nombrePlato, R.id.descripcionPlato, R.id.precioPlato, R.id.cantidadPlatosSeleccionados}, "platos");

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


}