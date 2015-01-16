package cl.flashmenu.aplicacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import carta.listaPlatos2;

public class UserData {
	public static String TAG_TIPO = "tipo";

	public static  ArrayList lista;
	public static  ArrayList lista2;
	public static  ArrayList lista_preferencias;
	public static  ArrayList lista_preferencias_tipo;
	public static  ArrayList lista_restaurantes;
	public static  ArrayList lista_historial;
	public static HashMap<String, Float> ratings;
	public static int tipo;
	public static String url_actual;

	public static final String TAG_SUCCESS = "success";

	//TAG productos
	public static final String TAG_producto = "Producto";
	public static final String TAG_ID_PRODUCTO = "idProducto";
	public static final String TAG_NOMBRE_PRODUCTO = "Producto_nombre";
	public static final String TAG_DESCRIPCION_PRODUCTO = "Producto_descripcion";
	public static final String TAG_PRECIO_PRODUCTO = "Producto_precio";
	public static final String TAG_TIPO_PRODUCTO = "Producto_tipo";
	public static final String TAG_TIPO_PREFERENCIAS_PRODUCTO = "Producto_tipo_preferencia";
	public static final String TAG_ID_PRODUCTO_REST = "Restaurant_idRestaurant";
	public static final String TAG_SPINNER_PRODUCTO = "Restaurant_spinner";
	public static final String TAG_POSITION_PRODUCTO = "Position_Lista";
	public static final String TAG_CANTIDAD_PRODUCTO = "cantidad";
	//Todas las sugerencias
	public static String SProducto_nombre;
	public static String SProducto_descripcion;
	public static String SProducto_precio;
	public static String SidProducto;
	public static String SProducto_tipo;
	public static String SProducto_tipo_preferencia;
	public static String sRestaurant_idRestaurant;

	//Producto_has_menu
	public static final String TAG_producto_has_menu = "Producto_has_Menu";
	public static final String TAG_PRODUCTO_IDPRODUCTO = "Producto_idProducto";	
	public static final String TAG_MENU_IDMENU = "Menu_idMenu";
	public static String Producto_idProducto;	
	public static String Menu_idMenu;
	

	//Menu
	public static final String TAG_menu = "Menu";
	public static final String TAG_ID_MENU = "idMenu";
	public static final String TAG_MENU_NOMBRE = "Menu_nombre";
	public static final String TAG_MENU_DESCRIPCION = "Menu_descripcion";
	public static final String TAG_MENU_PRECIO = "Menu_precio";
	public static String idMenu;
	public static String Menu_nombre;
	public static String Menu_descripcion;
	public static String Menu_precio;

	//Cliente
	public static String idCliente;
	public static  String Cliente_email;
	public static  String Cliente_nombre;
	public static String Cliente_apellidoPaterno;
	public static String Cliente_apellidoMaterno;
	public static String Cliente_rut;
	public static String Cliente_direccion;
	public static final String TAG_ID_CLIENTE = "idCliente";
	public static final String TAG_EMAIL_CLIENTE = "Cliente_email";
	public static final String TAG_cliente = "Cliente";
	public static final String TAG_NOMBRE_CLIENTE = "Cliente_nombre";
	public static final String TAG_APELLIDOP_CLIENTE = "Cliente_apellidoPaterno";
	public static final String TAG_APELLIDOM_CLIENTE = "Cliente_apellidoMaterno";
	public static final String TAG_RUT_CLIENTE = "Cliente_rut";
	public static final String TAG_DIRECCION_CLIENTE = "Cliente_direccion";

	//Restaurant
	public static String idRestaurant;
	public static String Rest_nombre;
	public static String Rest_tipo;
	public static String Rest_descripcion;
	public static String Rest_email;
	public static String Rest_direccion;
	public static String Rest_lat;
	public static String Rest_long;
	public static final String TAG_ID_REST = "idRestaurant";
	public static final String TAG_NOMBRE_REST = "Rest_nombre";
	public static final String TAG_TIPO_REST = "Rest_tipo";
	public static final String TAG_DESCRIPCION_REST = "Rest_descripcion";
	public static final String TAG_EMAIL_REST = "Rest_email";
	public static final String TAG_DIRECCION_REST = "Rest_direccion";
	public static final String TAG_LAT_REST = "Rest_lat";
	public static final String TAG_LONG_REST = "Rest_long";
	public static final String TAG_restaurant = "Restaurant";

	//preferencias
	public static String tipo_preferencia;
	public static final String TAG_PREFERENCIAS = "Preferencia";
	public static final String TAG_TIPO_PREFERENCIAS = "Preferencia_tipo_idPreferencia_tipo";

	//preferencias_tipo
	public static String Preferencia_tipo_nombre;
	public static String Preferencia_tipo_valor;
	public static final String TAG_preferencia_tipo = "Preferencia_tipo";
	public static final String TAG_PREFERENCIAS_TIPO_NOMBRE = "Preferencia_tipo_nombre";
	public static final String TAG_PREFERENCIAS_TIPO_VALOR = "Preferencia_tipo_valor";

	//horarios_mesa
	public static String TAG_horarios_mesa = "Horarios_mesa";
	public static String TAG_ID_HORARIOS_MESA = "idHorarios_mesa";	
	public static String TAG_HORARIOS_MESA_FECHA = "Horarios_mesa_fecha";
	public static String TAG_HORARIOS_MESA_HORA = "Horarios_mesa_hora";	
	public static String TAG_HORARIOS_MESA_NRO_MESA = "Mesa_Nro_mesa";
	public static String idHorarios_mesa;	
	public static String Horarios_mesa_fecha;
	public static String Horarios_mesa_hora;	
	public static String Mesa_Nro_mesa;

	//mesa
	public static String TAG_mesa = "mesa";
	public static String TAG_ID_MESA = "Nro_mesa";	
	public static String TAG_MESA_NRO = "Mesa_nro";
	public static String TAG_MESA_DESCRIPCION = "Mesa_descripcion";	
	public static String TAG_MESA_CANTIDAD = "Mesa_cantPersonas";
	public static String TAG_MESA_REST = "Restaurant_idRestaurant";
	public static String Nro_mesa;	
	public static String Mesa_nro;
	public static String Mesa_descripcion;	
	public static String Mesa_cantPersonas;
	public static String Mesa_Restaurant_idRestaurant;

	//reserva
	public static String TAG_reserva = "reserva";
	public static String TAG_ID_RESERVA = "idReserva";
	public static String TAG_FECHA_RESERVA = "Reserva_fecha";
	public static String TAG_HORA_RESERVA = "Reserva_hora";
	public static String TAG_TOTAL_RESERVA = "Reserva_total";	
	public static String TAG_DIRECCION_RESERVA = "Reserva_direccion";	
	public static String TAG_DETALLEPRO_RESERVA = "Reserva_detalleProductos";
	public static String TAG_DETALLEPRO_RESERVA_2 = "Reserva_detalleProductos_2";
	public static String TAG_EMAIL_RESERVA = "Reserva_email";
	public static String TAG_MESA_RESERVA = "Mesa_Nro_mesa";	
	public static String TAG_CLIENTE_RESERVA = "Cliente_idCliente";
	public static String idReserva;
	public static String Reserva_fecha;
	public static String Reserva_hora;
	public static String Reserva_total;	
	public static String Reserva_direccion;	
	public static String Reserva_detalleProductos;	
	public static String Reserva_detalleProductos_2;	
	public static String Reserva_email;
	public static String Reserva_Mesa_Nro_mesa;	
	public static String Reserva_Cliente_idCliente;
	//ver reserva
	public static String VidReserva;
	public static String VReserva_fecha;
	public static String VReserva_hora;
	public static String VReserva_total;	
	public static String VReserva_direccion;	
	public static String VReserva_detalleProductos;	
	public static String VReserva_email;
	public static String VReserva_Mesa_Nro_mesa;	
	public static String VReserva_Cliente_idCliente;
	
	//historial
	public static String TAG_CLIENTE_HISTORIAL = "historial";
	public static String TAG_CLIENTE_HISTORIAL_2 = "Cliente_idCliente";
	public static String Cliente_historial;
	public static String Cliente_historial_2;

	
	public static String TAG_SugerenciasMenu = "SugerenciasMenu";
	
	
	//inicializacion ArrayList
	public static  Map<String,String> tipoxString ;
	public static void initall(){
		lista2 = new ArrayList();
		lista_preferencias = new ArrayList();
		lista_preferencias_tipo = new ArrayList();
		lista_restaurantes = new ArrayList();
		lista_historial = new ArrayList();
		tipoxString = new HashMap<String,String>();
	}


	//contador de productos
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
	//


	//Suma precio
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
				String precioString = (String)extract.get(TAG_PRECIO_PRODUCTO);
				precio += Integer.parseInt(precioString);
			}
		}
		return precio;
	}
	//

}
