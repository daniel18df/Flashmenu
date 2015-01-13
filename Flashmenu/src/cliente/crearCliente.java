package cliente;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import cl.flashmenu.aplicacion.JSONParser;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.UserData;
import cl.flashmenu.aplicacion.servidor;
import cl.flashmenu.aplicacion.verMapa;
import cl.flashmenu.aplicacion.R.id;
import cl.flashmenu.aplicacion.R.layout;
import cl.flashmenu.aplicacion.R.menu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class crearCliente extends Activity{
	
		private ProgressDialog pDialog;

		JSONParser jsonParser = new JSONParser();

    	EditText rutCli;
		EditText nom;
		EditText apeP;
		EditText apeM;
		EditText pass;
		EditText email;

		
		//private static String url_create_Cliente = "http://190.153.212.77/daniel_fernandez/nuevoCliente.php";
		private static String url_create_Cliente = servidor.ip() + servidor.ruta2()+"nuevoCliente.php";
		//private static String url_create_Cliente = "http://10.40.3.149/PHP/FlashmenuPHP/nuevoCliente.php";

		

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.crearcliente);

			rutCli = (EditText) findViewById(R.id.rutC);
			nom = (EditText) findViewById(R.id.NombreC);
			apeP = (EditText) findViewById(R.id.ApellidopaternoC);
			apeM = (EditText) findViewById(R.id.ApellidomaternoC);
			pass = (EditText) findViewById(R.id.contrasenaC);
			email = (EditText) findViewById(R.id.emailC);
			
			
			Button btnCreateCliente = (Button) findViewById(R.id.crearcliente);

			btnCreateCliente.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					
					new CrearCliente().execute();
				}
			});
			
			
		
		}

		/**
		 * Background Async Task to Create new cliente
		 * */
		class CrearCliente extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(crearCliente.this);
				pDialog.setMessage("Registrando al cliente..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Creating cliente
			 * */
			protected String doInBackground(String... args) {
				
				String rutCliente = rutCli.getText().toString();
				String name = nom.getText().toString();
				String apePaterno = apeP.getText().toString();
				String apeMaterno = apeM.getText().toString();
				String password = pass.getText().toString();
				String mail = email.getText().toString();

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("Cliente_rut", rutCliente));
				params.add(new BasicNameValuePair("Cliente_nombre", name));
				params.add(new BasicNameValuePair("Cliente_apellidoPaterno", apePaterno));
				params.add(new BasicNameValuePair("Cliente_apellidoMaterno", apeMaterno));
				params.add(new BasicNameValuePair("Cliente_direccion", password));
				params.add(new BasicNameValuePair("Cliente_email", mail));

				JSONObject json = jsonParser.makeHttpRequest(url_create_Cliente,"POST", params);
				
				Log.d("Create Response", json.toString());

				try {
					int success = json.getInt(UserData.TAG_SUCCESS);

					if (success == 1) {
						Intent i = new Intent(getApplicationContext(), inicioSesionCliente.class);
						startActivity(i);
						///Toast toast1 = Toast.makeText(getApplicationContext(),"Usuario registrado satisfactoriamente", Toast.LENGTH_SHORT);
				 	 //   toast1.show();  
						
						
					} else {
						// fallo
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
			}

		}
		
		 @Override
			public boolean onCreateOptionsMenu(Menu menu) {
				getMenuInflater().inflate(R.menu.main, menu);
				return true;
			}

			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				int id = item.getItemId();
				if (id == R.id.action_settings) {
					return true;
				}
				return super.onOptionsItemSelected(item);
			}

}
