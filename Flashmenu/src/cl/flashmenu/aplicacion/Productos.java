package cl.flashmenu.aplicacion;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import cliente.perfilCliente;

import mesas.Calendario;
import mesas.horario;
import carta.listaPlatos2;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Productos extends Activity {

	Button platos, bebidas, postres, menu;
	TextView totalplatos, totalbebidas, totalpostres, totalmenu, cantidadplatos, cantidadbebidas, cantidadpostres, cantidadmenu,
				perfil, perfilUsuario, titulo_rest;
	//recibidos por intent
	String usuario, idRest, mailRest, direccionRest, idCliente, Cliente_email;
	String nombre_rest;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productos);


		//RECIBIR DATOS POR INTENT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			idRest  = extras.getString("idRest");
			usuario  = extras.getString("usuario");
			mailRest  = extras.getString("mailRest");
			direccionRest  = extras.getString("direccionRest");
			idCliente = extras.getString("idCliente");
			Cliente_email = extras.getString("Cliente_email");
			nombre_rest = extras.getString("nombre_rest");

		}else{
			idRest="error";
			usuario="error";
			mailRest="error";
			direccionRest="error";
			idCliente = "error";
			Cliente_email = "error";
			nombre_rest = "error";
		}///

		perfilUsuario = (TextView) findViewById(R.id.nombreClienteSelectMenu);
		perfilUsuario.setText(usuario);
		
		titulo_rest = (TextView) findViewById(R.id.titulo_rest);
		titulo_rest.setText(nombre_rest);
		
		
		perfil = (TextView) findViewById(R.id.perfilSelectMenu);
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




		platos = (Button) findViewById(R.id.platos);
		platos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				UserData.url_actual = servidor.ip() + servidor.ruta2()+"ListaPlatos.php";
				UserData.tipo = 0;
				Intent i = new Intent(getApplicationContext(), listaPlatos2.class);
				i.putExtra("idRest", idRest);
				i.putExtra("usuario",usuario);
				i.putExtra("mailRest", Cliente_email);
				i.putExtra("direccionRest",direccionRest);
				i.putExtra("idCliente",idCliente);
				i.putExtra("Cliente_email",Cliente_email);
				startActivity(i);
				//finish();
			}
		});

		bebidas = (Button) findViewById(R.id.bebidas);
		bebidas.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				UserData.url_actual = servidor.ip() + servidor.ruta2()+"ListaBebidas.php";
				UserData.tipo = 1;
				Intent i = new Intent(getApplicationContext(), listaPlatos2.class);
				i.putExtra("idRest", idRest);
				i.putExtra("usuario",usuario);
				i.putExtra("mailRest", Cliente_email);
				i.putExtra("direccionRest",direccionRest);
				i.putExtra("idCliente",idCliente);
				i.putExtra("Cliente_email",Cliente_email);
				startActivity(i);

				//finish();
			}
		});

		postres = (Button) findViewById(R.id.postres);
		postres.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				UserData.url_actual = servidor.ip() + servidor.ruta2()+"ListaPostres.php";
				UserData.tipo = 2;
				Intent i = new Intent(getApplicationContext(), listaPlatos2.class);
				i.putExtra("idRest", idRest);
				i.putExtra("usuario",usuario);
				i.putExtra("mailRest", Cliente_email);
				i.putExtra("direccionRest",direccionRest);
				i.putExtra("idCliente",idCliente);
				i.putExtra("Cliente_email",Cliente_email);
				startActivity(i);

				//finish();
			}
		});
		
		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				UserData.url_actual = servidor.ip() + servidor.ruta2()+"ListaMenu.php";
				UserData.tipo = 3;
				Intent i = new Intent(getApplicationContext(), listaPlatos2.class);
				i.putExtra("idRest", idRest);
				i.putExtra("usuario",usuario);
				i.putExtra("mailRest", Cliente_email);
				i.putExtra("direccionRest",direccionRest);
				i.putExtra("idCliente",idCliente);
				i.putExtra("Cliente_email",Cliente_email);
				startActivity(i);

				//finish();
			}
		});

		Button siguiente = (Button) findViewById(R.id.botonProductos);
		siguiente.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),	Calendario.class);

				in.putExtra("idRest", idRest);
				in.putExtra("idCliente",idCliente);
				in.putExtra("usuario", usuario);
				in.putExtra("mailRest", mailRest);
				in.putExtra("direccionRest", direccionRest);
				Toast.makeText(getApplicationContext(), idCliente, Toast.LENGTH_LONG).show();

				startActivity(in);

				System.out.print(UserData.lista.toString());
			}
		});


	
	}
	protected void onResume (){
		DecimalFormat fmt = new DecimalFormat();
		DecimalFormatSymbols fmts = new DecimalFormatSymbols();

		fmts.setGroupingSeparator('.');

		fmt.setGroupingSize(3);
		fmt.setGroupingUsed(true);
		fmt.setDecimalFormatSymbols(fmts);


		super.onResume();
		totalplatos = (TextView) findViewById(R.id.totalProductosPlato);			
		totalplatos.setText("$" + fmt.format(UserData.PrecioPlatos()));

		totalbebidas = (TextView) findViewById(R.id.totalProductosBebidas);		
		totalbebidas.setText("$" + fmt.format(UserData.PrecioBebidas()));

		totalpostres = (TextView) findViewById(R.id.totalProductosPostres);		
		totalpostres.setText("$" + fmt.format(UserData.PrecioPostres()));
		
		totalmenu = (TextView) findViewById(R.id.totalProductosMenu);		
		totalmenu.setText("$" + fmt.format(UserData.PrecioMenu()));
		
		cantidadplatos = (TextView) findViewById(R.id.cantidadPlatos);		
		cantidadplatos.setText(String.valueOf(UserData.CountPlatos()));

		cantidadbebidas = (TextView) findViewById(R.id.cantidadBebidas);		
		cantidadbebidas.setText(String.valueOf(UserData.CountBebidas()));

		cantidadpostres = (TextView) findViewById(R.id.cantidadPostres);		
		cantidadpostres.setText(String.valueOf(UserData.CountPostres()));
		
		cantidadmenu = (TextView) findViewById(R.id.cantidadProductosMenu);		
		cantidadmenu.setText(String.valueOf(UserData.CountMenu()));



	}


}
