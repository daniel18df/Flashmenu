package restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import carta.listaPlatos2;
import cl.flashmenu.aplicacion.JSONParser;
import cl.flashmenu.aplicacion.MainActivity;
import cl.flashmenu.aplicacion.Productos;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.UserData;
import cl.flashmenu.aplicacion.servidor;
import cl.flashmenu.aplicacion.verMapa;
import cliente.perfilCliente;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class infoRestaurantes extends Activity implements OnRatingBarChangeListener {

	TextView n, t, d, c, perfilUsuaio;
	Button s;
	TextView perfil, cerrar;


	//datos tablas
	String id, nombre, tipo, descripcion, email, direccion, idCliente, Cliente_email;


	//variables intent
	String usuario, idRest;


	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();


	//private static String url_all_inforest = "http://10.40.3.149/PHP/FlashmenuPHP/restaurantes.php";
	//private static String url_all_inforest = "http://190.153.212.77/daniel_fernandez/restaurantes.php";
	private static String url_all_inforest = servidor.ip() + servidor.ruta2() + "restaurantes.php";



	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ID = "idRestaurant";
	private static final String TAG_NOMBRE = "Rest_nombre";
	private static final String TAG_TIPO = "Rest_tipo";
	private static final String TAG_DESCRIPCION = "Rest_descripcion";
	private static final String TAG_EMAIL = "Rest_email";
	private static final String TAG_DIRECCION = "Rest_direccion";
	private static final String TAG_restaurant = "restaurant";


	JSONArray rest = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inforestaurantes);
		RatingBar rating = (RatingBar) findViewById(R.id.ratingBar1);
		rating.setOnRatingBarChangeListener(this);
		if(UserData.ratings == null){
			UserData.ratings = new HashMap<String, Float>();
		}
		if(UserData.ratings.containsKey(nombre)){
			rating.setRating(UserData.ratings.get(nombre));
		}
		//RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			usuario  = extras.getString("usuario");
			idRest  = extras.getString("idRest");
			idCliente = extras.getString("idCliente");
			Cliente_email  = extras.getString("Cliente_email");//usuario


		}else{
			usuario="error";
			idRest="error";
			idCliente = "error";
			Cliente_email ="error";
		}///

		n = (TextView) findViewById(R.id.nr);
		t = (TextView) findViewById(R.id.tr);
		d = (TextView) findViewById(R.id.dr);
		
		d = (TextView) findViewById(R.id.dr);
		//c = (TextView) findViewById(R.id.cr);



		new Loadrest().execute();
		
		perfilUsuaio = (TextView) findViewById(R.id.nombreCliente);
		perfilUsuaio.setText(usuario);
		
		
		perfil = (TextView) findViewById(R.id.perfilInfoRest);
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
		
		

		s = (Button) findViewById(R.id.sigPaso);
		s.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


		//		Intent i = new Intent(getApplicationContext(), listaPlatos2.class);
				Intent i = new Intent(getApplicationContext(), Productos.class);
				i.putExtra("idRest", idRest);
				i.putExtra("usuario",usuario);
				i.putExtra("mailRest", email);
				i.putExtra("direccionRest",direccion);
				i.putExtra("idCliente",idCliente);
				i.putExtra("Cliente_email",Cliente_email);
				Toast.makeText(getApplicationContext(), idCliente, Toast.LENGTH_LONG).show();
				startActivity(i);

				//finish();
			}
		});

	}
	public class Loadrest extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(infoRestaurantes.this);
			pDialog.setMessage("Cargando informacio. Espere porfavor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All restaurantes from url
		 * */
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("buscar", idRest));

			JSONObject json = jParser.makeHttpRequest(url_all_inforest, "POST", params);

			Log.d("All restaurantes: ", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					rest = json.getJSONArray(TAG_restaurant);

					for (int i = 0; i < rest.length(); i++) {
						JSONObject c = rest.getJSONObject(i);

						id = c.getString(TAG_ID);
						nombre = c.getString(TAG_NOMBRE);
						tipo = c.getString(TAG_TIPO);
						descripcion = c.getString(TAG_DESCRIPCION);
						email = c.getString(TAG_EMAIL);
						direccion = c.getString(TAG_DIRECCION);
						//caracteristicas = c.getString(TAG_CARACTERISTICAS);
					}
				} else {

					Intent i = new Intent(getApplicationContext(), verMapa.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}



		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			n.setText(nombre);
			t.setText(tipo);
			d.setText(descripcion);
			//c.setText(caracteristicas);
			RatingBar rating = (RatingBar) findViewById(R.id.ratingBar1);
			if(UserData.ratings == null){
				UserData.ratings = new HashMap<String, Float>();
			}
			if(UserData.ratings.containsKey(nombre)){
				rating.setRating(UserData.ratings.get(nombre));
			}
		}


	}
	protected void onResume (){
		super.onResume();
		if(UserData.ratings == null){
			UserData.ratings = new HashMap<String, Float>();
		}
		RatingBar rating = (RatingBar) findViewById(R.id.ratingBar1);
		if(UserData.ratings.containsKey(nombre)){
			rating.setRating(UserData.ratings.get(nombre));
		}
		System.out.print(nombre);
	}

    public void onRatingChanged(RatingBar rating, float ratingValue, boolean fromTouch) {
        if(UserData.ratings.containsKey(nombre)){
        	UserData.ratings.remove(nombre);
		}
		HashMap<String,Float> map = new HashMap<String, Float>();
		UserData.ratings.put(nombre, rating.getRating());
    }


}
