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
import cl.flashmenu.aplicacion.UserData;
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
			i = extras.getString("i");
	
		}else{
			i="error";
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
		perfilUsuario.setText(UserData.Cliente_email);

		perfil = (TextView) findViewById(R.id.perfil_perfilreserva);
		perfil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
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
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {

					platosl = json.getJSONArray(UserData.TAG_reserva);


					for (int i = 0; i < platosl.length(); i++) {
						JSONObject c = platosl.getJSONObject(i);

//						idReserva = c.getString(UserData.TAG_ID_RESERVA);
//						fecha = c.getString(UserData.TAG_FECHA_RESERVA);
//						hora = c.getString(UserData.TAG_HORA_RESERVA);
						UserData.VReserva_total = c.getString(UserData.TAG_TOTAL_RESERVA);
						UserData.VReserva_direccion = c.getString(UserData.TAG_DIRECCION_RESERVA);
						UserData.VReserva_detalleProductos = c.getString(UserData.TAG_DETALLEPRO_RESERVA);
						UserData.VReserva_email = c.getString(UserData.TAG_EMAIL_RESERVA);
						UserData.VReserva_Mesa_Nro_mesa = c.getString(UserData.TAG_MESA_RESERVA);
						UserData.VReserva_Cliente_idCliente = c.getString(UserData.TAG_CLIENTE_RESERVA);



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
			
			fecha_reserva.setText(UserData.VReserva_fecha);
			hora_reserva.setText(UserData.VReserva_hora);
			direccion_reserva.setText(UserData.VReserva_direccion);
			total_reserva.setText(UserData.VReserva_total);
			productos_reserva.setText(UserData.VReserva_detalleProductos);
			perfilUsuario.setText(UserData.Cliente_email);
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
				int success = json.getInt(UserData.TAG_SUCCESS);

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