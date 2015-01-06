package cliente;

import java.util.ArrayList;
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
import cl.flashmenu.aplicacion.verMapa;
import cl.flashmenu.aplicacion.R.id;
import cl.flashmenu.aplicacion.R.layout;
import cl.flashmenu.aplicacion.R.menu;
import cliente.crearCliente.CrearCliente;

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
import android.widget.Toast;

public class crearReserva extends Activity{

	
	JSONParser jsonParser = new JSONParser();


	//desde intent
	String idRest, usuario, mailRest, direccionRest, hora, fecha, Cliente_email, mesa;
	
	String idCliente;

	private static String url_create_Cliente = servidor.ip() + servidor.ruta2()+"nuevaReserva.php";

	private static final String TAG_SUCCESS = "success";
	
	JSONParser jParser = new JSONParser();
	
	
	
	/////para mail
	private ProgressDialog pDialog;

	JSONParser jsonParser2 = new JSONParser();

	private static String url_email = servidor.ip() + servidor.ruta2()+"email.php";


	/////para mail



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.calendario);

		//RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			fecha  = extras.getString("fecha");//
			hora  = extras.getString("hora");//
			idRest  = extras.getString("idRest");//
			usuario  = extras.getString("usuario");//
			mailRest  = extras.getString("mailRest");//
			direccionRest  = extras.getString("direccionRest");//
			idCliente = extras.getString("idCiente");
			Cliente_email = extras.getString("Ciente_email");
			mesa = extras.getString("mesa");

		}else{
			fecha="error";
			hora="error";
			idRest="error";
			usuario="error";
			mailRest="error";
			direccionRest="error";
			idCliente = "error";
			Cliente_email = "error";
			mesa = "error";
		}///
		
		new CrearReserva().execute();
	}
	

	class CrearReserva extends AsyncTask<String, String, String> {
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("Reserva_fecha", fecha));
			params.add(new BasicNameValuePair("Reserva_hora", hora));
			params.add(new BasicNameValuePair("Cliente_idCliente", idCliente));
			params.add(new BasicNameValuePair("Mesa_Nro_mesa", mesa));


			JSONObject json = jsonParser.makeHttpRequest(url_create_Cliente,"POST", params);
			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					new enviarMail().execute();

				} else {
					}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
	}
	
//
	
	
	
////para email

	class enviarMail extends AsyncTask<String, String, String> {

	
		protected String doInBackground(String... args) {


			List<NameValuePair> params = new ArrayList<NameValuePair>();			
			params.add(new BasicNameValuePair("Cliente_email", Cliente_email));

			JSONObject json = jsonParser2.makeHttpRequest(url_email,"POST", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					
					Intent i = new Intent(getApplicationContext(),	Paypal.class);
					i.putExtra("fecha", fecha);
					i.putExtra("hora", hora);
					i.putExtra("idRest", idRest);
					i.putExtra("usuario", usuario);
					i.putExtra("mailRest", mailRest);
					i.putExtra("direccionRest", direccionRest);
					i.putExtra("idCliente", idCliente);
					i.putExtra("Cliente_email", Cliente_email);
					i.putExtra("mesa", mesa);

					startActivity(i);				


				} else {
					// fallo
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


	}

	////para email
	
}
