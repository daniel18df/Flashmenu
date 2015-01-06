package reserva;

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
import cl.flashmenu.aplicacion.servidor;
import cliente.perfilCliente;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class perfilReserva extends Activity{

	//desde intent
	String f, h, i, usuario, idCliente;

	TextView tituloPerfil, perfilUsuario, perfil, avisar, eliminar;

	TextView fecha_reserva, hora_reserva, direccion_reserva, total_reserva, productos_reserva;

	String idReserva ,fecha, hora, total, dir, pro, email, mesa, cli; 



	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> PlatosList;

	private static String url_perfil_reserva = servidor.ip() + servidor.ruta2()+"perfilreserva.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_reserva = "reserva";
	private static final String TAG_ID = "idReserva";
	private static final String TAG_FECHA = "Reserva_fecha";
	private static final String TAG_HORA = "Reserva_hora";
	private static final String TAG_TOTAL = "Reserva_total";
	private static final String TAG_DIRECCION = "Reserva_direccion";
	private static final String TAG_PRODUCTOS = "Reserva_detalleProductos";
	private static final String TAG_EMAIL = "Reserva_emailRest";
	private static final String TAG_MESA = "Mesa_Nro_mesa";
	private static final String TAG_CLIENTE = "Cliente_idCliente";



	JSONArray j1 = null;

	// JSONArray
	JSONArray platosl = null;


	
	JSONParser jsonParser2 = new JSONParser();

	private static String url_email = servidor.ip() + servidor.ruta2()+"AvisarLlegada.php";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfilreserva); 


		//RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			usuario  = extras.getString("usuario");
			idCliente = extras.getString("idCliente");
			f  = extras.getString("f");
			h = extras.getString("h");
			i = extras.getString("i");


		}else{
			f="error";
			h="error";
			i="error";
			usuario="error";
			idCliente = "error";
		}///

		new LoadAllReserva().execute();

		tituloPerfil = (TextView) findViewById(R.id.IDreserva);
		tituloPerfil.setText(i);

		fecha_reserva = (TextView) findViewById(R.id.detallefecha_reserva);

		hora_reserva = (TextView) findViewById(R.id.detallehora_reserva);

		direccion_reserva= (TextView) findViewById(R.id.detalledireccion_reserva);

		total_reserva = (TextView) findViewById(R.id.totalcompra_reserva);

		productos_reserva = (TextView) findViewById(R.id.detalleproductosComprados_reserva);

		perfilUsuario = (TextView) findViewById(R.id.nombreCliente_perfilreserva);
		perfilUsuario.setText(usuario);

		perfil = (TextView) findViewById(R.id.perfil_perfilreserva);
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


		avisar = (TextView) findViewById(R.id.AvisoLlegada);
		avisar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				new enviarMail().execute();

			}
		});


		eliminar = (TextView) findViewById(R.id.eliminar_reserva);
		eliminar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}





	/**
	 * Background Async Task to Load all platos by making HTTP Request
	 * */
	class LoadAllReserva extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(perfilReserva.this);
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

			params.add(new BasicNameValuePair("buscar", i));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_perfil_reserva, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("All platos: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					platosl = json.getJSONArray(TAG_reserva);


					for (int i = 0; i < platosl.length(); i++) {
						JSONObject c = platosl.getJSONObject(i);

						idReserva = c.getString(TAG_ID);
						fecha = c.getString(TAG_FECHA);
						hora = c.getString(TAG_HORA);
						total = c.getString(TAG_TOTAL);
						dir = c.getString(TAG_DIRECCION);
						pro = c.getString(TAG_PRODUCTOS);
						email = c.getString(TAG_EMAIL);
						mesa = c.getString(TAG_MESA);
						cli = c.getString(TAG_CLIENTE);



					}
				} else {


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
			
			fecha_reserva.setText(fecha);
			hora_reserva.setText(hora);
			direccion_reserva.setText(dir);
			total_reserva.setText(total);
			productos_reserva.setText(pro);
			perfilUsuario.setText(usuario);
		}
	}
	
	
	
	
	////para email

	class enviarMail extends AsyncTask<String, String, String> {


		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();			
			params.add(new BasicNameValuePair("Cliente_email", usuario));
			params.add(new BasicNameValuePair("Rest_email", String.valueOf(email)));
			params.add(new BasicNameValuePair("Hora", h));
			params.add(new BasicNameValuePair("Fecha", f));
			params.add(new BasicNameValuePair("Nro_mesa", String.valueOf(mesa)));
			params.add(new BasicNameValuePair("idreserva", i));
			params.add(new BasicNameValuePair("productos", String.valueOf(pro)));



			JSONObject json = jsonParser2.makeHttpRequest(url_email,"POST", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

				} else {
					// fallo
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		protected void onPostExecute(String file_url) {

			pDialog.dismiss();
			
			Toast.makeText(getApplicationContext(),"Listo!", Toast.LENGTH_LONG).show();
		}


	}

	////para email




}