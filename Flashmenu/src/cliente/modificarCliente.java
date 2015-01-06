package cliente;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import cl.flashmenu.aplicacion.JSONParser;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.servidor;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class modificarCliente extends Activity {

	EditText emailModiff, contrase–aModiff;
	Button enviarModif;


	

	//variables intent
	String usuario, idRest, idCliente;


	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();


	private static String url_cliente = servidor.ip() + servidor.ruta2() + "obtenerDatosCliente.php";
	private static String url_actualizar_cliente = servidor.ip() + servidor.ruta2() + "actualizarCliente.php";

//datos tablas
	String id, nombre, apellido_paterno, apellido_materno, rut, email, direccion;

	private static final String TAG_SUCCESS = "success";
//	private static final String TAG_ID = "idCliente";
//	private static final String TAG_NOMBRE = "Cliente_nombre";
//	private static final String TAG_APELLIDOPATERNO = "Cliente_apellidoPaterno";
//	private static final String TAG_APELLIDOMATERNO = "Cliente_apellidoMaterno";
//	private static final String TAG_RUT = "Cliente_rut";
	private static final String TAG_EMAIL = "Cliente_email";
	private static final String TAG_DIRECCION = "Cliente_direccion";
	private static final String TAG_cliente = "Cliente";

	String nom, contrasena;
	JSONArray rest = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modificarcliente);


		//RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			usuario  = extras.getString("usuario");
			idCliente = extras.getString("idCliente");


		}else{
			usuario="error";
			idCliente = "error";
		}///
		
		
		Toast.makeText(getApplicationContext(), idCliente, Toast.LENGTH_LONG).show();
		new LoadCliente().execute();
		
		
		
		enviarModif = (Button) findViewById(R.id.enviarModifCliente);
		enviarModif.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update Empleado
				new SaveEmpleadoDetails().execute();
			}
		});



	

	

	}
	 class LoadCliente extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(modificarCliente.this);
			pDialog.setMessage("Cargando informacio. Espere porfavor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


		protected String doInBackground(String... args) {
			
			Thread thread = new Thread(new Runnable(){
		//		runOnUiThread(new Runnable() {
				public void run() {
				
					int success;
					try {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("buscar", idCliente));

			JSONObject json = jParser.makeHttpRequest(url_cliente, "GET", params);

			Log.d("All clientes: ", json.toString());

		
				 success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					rest = json.getJSONArray(TAG_cliente);
					
				
					//for (int i = 0; i < rest.length(); i++) {
						JSONObject c = rest.getJSONObject(0);
						
						emailModiff = (EditText) findViewById(R.id.emailModifCliente);
						contrase–aModiff = (EditText) findViewById(R.id.contrasenaModifCliente);
						
							
//						emailModiff.setText(TAG_EMAIL);
//						contrase–aModiff.setText(TAG_DIRECCION);
//						
					
//						id = c.getString(TAG_ID);
//						nombre = c.getString(TAG_NOMBRE);
//						apellido_paterno = c.getString(TAG_APELLIDOPATERNO);
//						apellido_materno = c.getString(TAG_APELLIDOMATERNO);
//						rut = c.getString(TAG_RUT);
//						email = c.getString(TAG_EMAIL);
//						direccion = c.getString(TAG_DIRECCION);
//						nom = c.getString(TAG_EMAIL);
//						contrasena = c.getString(TAG_DIRECCION);
//						 nom = emailModiff.getText().toString();
//						 contrasena = contrase–aModiff.getText().toString();
						
					
				//	}
				} else {

//					Intent i = new Intent(getApplicationContext(), verMapa.class);
//					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					startActivity(i);
				}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();

			return null;
		}



		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
////
//			emailModif.setHint(email);
//			contrase–aModif.setHint(direccion);
			
		}


	}
	
	
	

	class SaveEmpleadoDetails extends AsyncTask<String, String, String> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(modificarCliente.this);
			pDialog.setMessage("Actualizando cliente ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			
			 nom = emailModiff.getText().toString();
			 contrasena = contrase–aModiff.getText().toString();


			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idCliente", idCliente));
			params.add(new BasicNameValuePair(TAG_EMAIL, nom));
			params.add(new BasicNameValuePair(TAG_DIRECCION, contrasena));


			// sending modified data through http request
			// Notice that update Empleado url accepts POST method
			JSONObject json = jParser.makeHttpRequest(url_actualizar_cliente, "POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about Empleado update
					setResult(100, i);
					
					finish();
				} else {
					// failed to update Empleado
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once Empleado uupdated
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), "Datos actualizados correctamente", Toast.LENGTH_LONG).show();
		}
	}




}
