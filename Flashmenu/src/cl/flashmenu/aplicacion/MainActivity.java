package cl.flashmenu.aplicacion;


import java.util.ArrayList;
import cliente.inicioSesionCliente;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {
	public static class UserDa{
		public static String name;

	}
	private Button iniciar2, tuerca; 
	ImageView flash;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		UserData.lista = new ArrayList();


		flash = (ImageView) findViewById(R.id.flashmenu_cl);
		flash.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.flashmenu.cl"));
				startActivity(i);

				//finish();
			}
		});

		iniciar2 = (Button) findViewById(R.id.bInicioCli);
		iniciar2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), inicioSesionCliente.class);
				startActivity(i);

				//finish();
			}
		});


		tuerca = (Button) findViewById(R.id.tuerca);
		tuerca.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Reemplazamos el email por algun otro real
				String[] to = { "flashmenu.daniel@gmail.com", "contacto@flashmenu.cl" };
				String[] cc = { "daniel18.df@gmail.com" };
				enviar(to, cc, "Asunto",
						"Introducir texto .....");
			}
		});

	}


	private void enviar(String[] to, String[] cc,
			String asunto, String mensaje) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
		emailIntent.putExtra(Intent.EXTRA_CC, cc);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
		emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Email "));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
