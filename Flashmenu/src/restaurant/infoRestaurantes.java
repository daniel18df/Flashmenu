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

	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();


	private static String url_all_inforest = servidor.ip() + servidor.ruta2() + "restaurantes.php";



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
		if(UserData.ratings.containsKey(UserData.Rest_nombre)){
			rating.setRating(UserData.ratings.get(UserData.Rest_nombre));
		}
	
		n = (TextView) findViewById(R.id.nr);
		t = (TextView) findViewById(R.id.tr);
		d = (TextView) findViewById(R.id.dr);
		
		d = (TextView) findViewById(R.id.dr);
		//c = (TextView) findViewById(R.id.cr);



		new Loadrest().execute();
		
		perfilUsuaio = (TextView) findViewById(R.id.nombreCliente);
		perfilUsuaio.setText(UserData.Cliente_email);
		
		
		perfil = (TextView) findViewById(R.id.perfilInfoRest);
		perfil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
				startActivity(i);

				//finish();
			}
		});
		
		

		s = (Button) findViewById(R.id.sigPaso);
		s.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), Productos.class);
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
			params.add(new BasicNameValuePair("buscar", UserData.Rest_nombre));

			JSONObject json = jParser.makeHttpRequest(url_all_inforest, "POST", params);

			Log.d("All restaurantes: ", json.toString());

			try {
				int success = json.getInt(UserData.TAG_SUCCESS);

				if (success == 1) {
					rest = json.getJSONArray(UserData.TAG_restaurant);

					for (int i = 0; i < rest.length(); i++) {
						JSONObject c = rest.getJSONObject(i);

						UserData.idRestaurant = c.getString(UserData.TAG_ID_REST);
						UserData.Rest_nombre = c.getString(UserData.TAG_NOMBRE_REST);
						UserData.Rest_tipo = c.getString(UserData.TAG_TIPO_REST);
						UserData.Rest_descripcion = c.getString(UserData.TAG_DESCRIPCION_REST);
						UserData.Rest_email = c.getString(UserData.TAG_EMAIL_REST);
						UserData.Rest_direccion = c.getString(UserData.TAG_DIRECCION_REST);
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

			n.setText(UserData.Rest_nombre);
			t.setText(UserData.Rest_tipo);
			d.setText(UserData.Rest_descripcion);
			//c.setText(caracteristicas);
			RatingBar rating = (RatingBar) findViewById(R.id.ratingBar1);
			if(UserData.ratings == null){
				UserData.ratings = new HashMap<String, Float>();
			}
			if(UserData.ratings.containsKey(UserData.Rest_nombre)){
				rating.setRating(UserData.ratings.get(UserData.Rest_nombre));
			}
		}


	}
	protected void onResume (){
		super.onResume();
		if(UserData.ratings == null){
			UserData.ratings = new HashMap<String, Float>();
		}
		RatingBar rating = (RatingBar) findViewById(R.id.ratingBar1);
		if(UserData.ratings.containsKey(UserData.Rest_nombre)){
			rating.setRating(UserData.ratings.get(UserData.Rest_nombre));
		}
		System.out.print(UserData.Rest_nombre);
	}

    public void onRatingChanged(RatingBar rating, float ratingValue, boolean fromTouch) {
        if(UserData.ratings.containsKey(UserData.Rest_nombre)){
        	UserData.ratings.remove(UserData.Rest_nombre);
		}
		HashMap<String,Float> map = new HashMap<String, Float>();
		UserData.ratings.put(UserData.Rest_nombre, rating.getRating());
    }


}
