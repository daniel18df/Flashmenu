package cliente;


import reserva.VerReserva;
import cl.flashmenu.aplicacion.MainActivity;
import cl.flashmenu.aplicacion.R;
import cl.flashmenu.aplicacion.preferencia;
import cl.flashmenu.aplicacion.verMapa;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class perfilCliente extends Activity {

	Button  modificarCli, verReservas, volverMapa, cerrarSesion, preferencias;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfilcliente);
		
		
		modificarCli = (Button) findViewById(R.id.modifDatos);
		modificarCli.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), modificarCliente.class);
				startActivity(i);

				//finish();
			}
		});
		
		verReservas = (Button) findViewById(R.id.verReservas);
		verReservas.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), VerReserva.class);
				startActivity(i);

				//finish();
			}
		});
		
		volverMapa = (Button) findViewById(R.id.volverMapa);
		volverMapa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), verMapa.class);
				startActivity(i);

				//finish();
			}
		});
		
		cerrarSesion = (Button) findViewById(R.id.cerrarSesion);
		cerrarSesion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);

				//finish();
			}
		});
		
		preferencias = (Button) findViewById(R.id.preferencias);
		preferencias.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), preferencia.class);
				startActivity(i);

				//finish();
			}
		});
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