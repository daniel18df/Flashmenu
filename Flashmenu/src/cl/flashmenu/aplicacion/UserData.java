package cl.flashmenu.aplicacion;

import java.util.ArrayList;
import java.util.HashMap;

import carta.listaPlatos2;

public class UserData {
	public static String TAG_TIPO = "tipo";


	public static final String TAG_platos = "Producto";
	public static final String TAG_NOMBRE = "Producto_nombre";
	public static final String TAG_DESCRIPCION = "Producto_descripcion";
	public static final String TAG_PRECIO = "Producto_precio";
	public static final String TAG_ID = "Restaurant_idRestaurant";
	public static final String TAG_SPINNER = "Restaurant_spinner";
	public static final String TAG_POSITION = "Position_Lista";
	public static final String TAG_CANTIDAD = "cantidad";

	public static String nombreUsuario;
	public static  ArrayList lista;
	public static  ArrayList lista2;
	public static HashMap<String, Float> ratings;
	public static int tipo;
	public static String url_actual;

	public static String preferenciaComida;
	public static String preferenciaPrecio;
	public static String preferenciaDistancia;
	
	
	public static int CountPlatos(){
		return Count(0);
	}
	
	public static int CountBebidas(){
		return Count(1);
	}
	

	public static int CountPostres(){
		return Count(2);
	}
	
	public static int CountMenu(){
		return Count(3);
	}
	
	static int Count(int tipo){
		int cantidad = 0;

		HashMap<String, Object> extract;
		for(int i=0;i<UserData.lista.size();i++){
			extract = (HashMap<String, Object>)UserData.lista.get(i);
			if((int)extract.get(TAG_TIPO) == tipo){
				cantidad++;
			}

		}

		return cantidad;
	}




	public static int PrecioPlatos(){
		return Precio(0);
	}
	public static int PrecioBebidas(){
		return Precio(1);
	}

	public static int PrecioPostres(){
		return Precio(2);
	}
	public static int PrecioMenu(){
		return Precio(3);
	}
	public static int Precio(int tipo){
		int precio = 0;
		HashMap<String, Object> extract;
		for(int i=0;i<UserData.lista.size();i++){
			extract = (HashMap<String, Object>)UserData.lista.get(i);
			if((int)extract.get(TAG_TIPO) == tipo){
				String precioString = (String)extract.get(TAG_PRECIO);
				precio += Integer.parseInt(precioString);
			}
		}
		return precio;
	}









}
