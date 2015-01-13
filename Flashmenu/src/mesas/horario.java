package mesas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import cliente.perfilCliente;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;



public class horario  extends ListActivity {




	//botones
	Button btnHaciaMesas, btnlista;
	TextView perfil, cerrar, perfilTitulo;

	//desde intent
//	String idRest, usuario, mailRest, direccionRest, idCliente, nombrePlato, precioPlato, Cliente_email, fechas;
//	int tamano;
//	String elemento;

	//variables para obtener json y radiobutton
	String hora, f, h;
	TextView h1, titulo; 
	ImageView imagen;


	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> horariosList;

	private static String url_Lista_horario = servidor.ip() + servidor.ruta2()+"verHorario.php";

	// JSON Node names




	// JSONArray
	JSONArray horariosl = null;

	// Progress Dialog
	private ProgressDialog pDialog;
	String arreglo[] = new String[3];

	ArrayList<String> nombreArrayList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista2);


//		//RECIBIR DATOS POR INTENT
//		Bundle extras = getIntent().getExtras();
//		if (extras != null) {
//
//			idRest  = extras.getString("idRest");//
//			usuario  = extras.getString("usuario");//
//			mailRest  = extras.getString("mailRest");//
//			direccionRest  = extras.getString("direccionRest");//
//			idCliente = extras.getString("idCliente");
//			Cliente_email = extras.getString("Cliente_email");
//			precioPlato = extras.getString("precioPlato");
//			nombrePlato = extras.getString("nombrePlato");
//			fechas = extras.getString("fecha");
//			//			for(int i = 0; i<16; i++){
//			//				Object obj = sll.extract();
//			//				if(obj != null)
//			//					arreglo2[i] = (String)obj;
//			//
//			//				in.putExtra("nombrePlato", arreglo2);
//			//			}
//
//
//		}else{
//			idRest="error";
//			usuario="error";
//			mailRest="error";
//			direccionRest="error";
//			idCliente = "error";
//			Cliente_email = "error";
//		}///

	Toast.makeText(getApplicationContext(), "id rest: " + UserData.Horarios_mesa_fecha, Toast.LENGTH_LONG).show();

		//Toast.makeText(getApplicationContext(), nombrePlato, Toast.LENGTH_LONG).show();

		new LoadAllplatos().execute();

		titulo = (TextView) findViewById(R.id.titulolista2);
		titulo.setText("Horarios");

		imagen = (ImageView) findViewById(R.id.imagen2);
		imagen.setImageResource(R.drawable.reloj);

		perfilTitulo = (TextView) findViewById(R.id.nombreClienteLISTA2);
		perfilTitulo.setText(UserData.Cliente_email);


		perfil = (TextView) findViewById(R.id.perfilInfoRest2);
		perfil.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
//				i.putExtra("usuario",usuario);
//				i.putExtra("idCliente",idCliente);
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


				h1 = ((TextView) view.findViewById(R.id.textview_hora));
				UserData.Horarios_mesa_hora =	h1.getText().toString();

				Intent i = new Intent(getApplicationContext(), verMesas.class);
//				i.putExtra("hora", h);
//				i.putExtra("idRest", idRest);
//				i.putExtra("usuario", usuario);
//				i.putExtra("mailRest", mailRest);
//				i.putExtra("direccionRest", direccionRest);
//				i.putExtra("idCliente",idCliente);
//				i.putExtra("Cliente_email",Cliente_email);
//				i.putExtra("fecha",fechas);
				startActivity(i);


			}
		});

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
			pDialog = new ProgressDialog(horario.this);
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
			params.add(new BasicNameValuePair("buscar2", UserData.Horarios_mesa_fecha));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_Lista_horario, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {

					horariosl = json.getJSONArray(UserData.TAG_horarios_mesa);


					for (int i = 0; i < horariosl.length(); i++) {
						JSONObject c = horariosl.getJSONObject(i);


						//fecha = c.getString(TAG_FECHA);
						hora = c.getString(UserData.TAG_HORARIOS_MESA_HORA);



						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();


						//	map.put(TAG_FECHA, fecha);
						map.put(UserData.TAG_HORARIOS_MESA_HORA, hora);


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
							horario.this, horariosList, R.layout.lista_itemhorarios,
							new String[] { UserData.TAG_HORARIOS_MESA_HORA }, new int[] {R.id.textview_hora});


					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}



}

