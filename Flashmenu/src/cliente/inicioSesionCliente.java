package cliente;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.flashmenu.aplicacion.Coneccion;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.servidor;
import cl.flashmenu.aplicacion.verMapa;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class inicioSesionCliente extends Activity {

	EditText nombre,clave;
	Button entrar;

	Coneccion post;
	TextView creC;

	private static String URL_connect = servidor.ip() + servidor.ruta2() + "iniciarSesionCliente.php";

	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iniciosesioncliente);

		post=new Coneccion();

		nombre = (EditText) findViewById(R.id.iniciarEmail1);
		clave = (EditText) findViewById(R.id.iniciarPass1);
		entrar = (Button) findViewById(R.id.buttonsesusuario);

		entrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String usuario = nombre.getText().toString();
				String pass = clave.getText().toString();

				if(checklogindata(usuario, pass) == true){        			
					new asynclogin().execute(usuario,pass); 



				}else{
					err_login();
				}
			}
		});




		creC = (TextView) findViewById(R.id.regisCli);
		creC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), crearCliente.class);
				startActivity(i);

				//finish();
			}
		});
	}//onCreate

	//_______________________________________________________
	//validamos si no hay ningun campo en blanco
	public boolean checklogindata(String username ,String password ){
		if 	(username.equals("") || password.equals("")){
			Log.e("Login ui", "checklogindata user or pass error");
			return false;
		}else{
			return true;
		}
	} 
	//_______________________________________________________
	//vibra y muestra un Toast
	public void err_login(){
		Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Nombre de Usuario o Contrasena Incorrecto.", Toast.LENGTH_SHORT);
		toast1.show();    	
	}
	//_______________________________________________________

	public boolean loginstatus(String username ,String password ) {
		int logstatus=-1;

		ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
		postparameters2send.add(new BasicNameValuePair("usuario",username));
		postparameters2send.add(new BasicNameValuePair("password",password));

		JSONArray jdata=post.getserverdata(postparameters2send, URL_connect);

		SystemClock.sleep(50);

		if (jdata!=null && jdata.length() > 0){
			JSONObject json_data; //creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
				logstatus=json_data.getInt("logstatus");//accedemos al valor 
				Log.e("loginstatus","logstatus= "+logstatus);//muestro por log que obtuvimos
			} catch (JSONException e) {
				e.printStackTrace();
			}		            
			//validamos el valor obtenido
			if (logstatus==0){// [{"logstatus":"0"}] 
				Log.e("loginstatus ", "invalido");
				return false;
			}
			else{// [{"logstatus":"1"}]
				Log.e("loginstatus ", "valido");
				return true;
			}
		}else{	//json obtenido invalido verificar parte WEB.
			Log.e("JSON  ", "ERROR");
			return false;
		}	
	}//loginStatus

	//_______________________________________________________



	class asynclogin extends AsyncTask< String, String, String > {
		String user,pass;
		protected void onPreExecute() {
			//para el progress dialog
			pDialog = new ProgressDialog(inicioSesionCliente.this);
			pDialog.setMessage("Autentificando....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			//obtnemos usr y pass
			user=params[0];
			pass=params[1];

			//enviamos y recibimos y analizamos los datos en segundo plano.
			if (loginstatus(user,pass)==true){    		    		
				return "ok"; //login valido
			}else{    		
				return "err"; //login invalido     	          	  
			}
		}

		/*Una vez terminado doInBackground segun lo que halla ocurrido 
		pasamos a la sig. activity
		o mostramos error*/
		protected void onPostExecute(String result) {
			pDialog.dismiss();//ocultamos progess dialog.
			Log.e("onPostExecute=",""+result);
			if (result.equals("ok")){

				Toast toast1 = Toast.makeText(getApplicationContext(),"Bienvenido: "+user, Toast.LENGTH_SHORT);
				toast1.show();



				Intent i = new Intent(inicioSesionCliente.this, verMapa.class);
				i.putExtra("usuario",user);
				startActivity(i); 

				//   	finish();

			}else{
				err_login();
			}

		}//onPostExecute

	}//asyncLogin

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ini, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		case R.id.salir:
			super.onDestroy();
			//finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}


	//--------- menu ----------------------------




	//Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	/* @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	         // no hacemos nada.
	         return true;
	     }

	     return super.onKeyDown(keyCode, event);
	 }*/

}