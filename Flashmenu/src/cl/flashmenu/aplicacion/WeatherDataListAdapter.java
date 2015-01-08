package cl.flashmenu.aplicacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import carta.listaPlatos2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WeatherDataListAdapter extends SimpleAdapter {
	String ListaUsar;
	ArrayList comprarador;
	public WeatherDataListAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to, String i) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		if(i.equals("platos")){
			comprarador = UserData.lista;
			
		}
		 if(i.equals("platos2"))
			comprarador = UserData.lista;
		
		ListaUsar = i;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		int cantidad=0;
		view = super.getView(arg0, view, arg2);
		for(int i = 0;i<comprarador.size();i++){
			String comparer = (String)((HashMap<String,Object>)comprarador.get(i)).get(listaPlatos2.TAG_NOMBRE);
			//String comparer2 = (String)((HashMap<String,Object>)comprarador.get(i)).get(listaBebidas.TAG_NOMBRE);
			
			if(comparer != null && comparer.equals(((TextView)view.findViewById(R.id.nombrePlato)).getText().toString())){
				if(cantidad == 0){
					((TextView)view.findViewById(R.id.nombrePlato)).setTextColor(Color.BLACK);
					((TextView)view.findViewById(R.id.descripcionPlato)).setTextColor(Color.BLACK);
					((TextView)view.findViewById(R.id.precioPlato)).setTextColor(Color.BLACK);
				}
				cantidad++;
			}
			
		/*	if(comparer2 != null && comparer2.equals(((TextView)view.findViewById(R.id.nombrePlato)).getText().toString())){
				((TextView)view.findViewById(R.id.nombrePlato)).setTextColor(Color.BLACK);
				((TextView)view.findViewById(R.id.descripcionPlato)).setTextColor(Color.BLACK);
				((TextView)view.findViewById(R.id.precioPlato)).setTextColor(Color.BLACK);
				return view;
			}*/
		}
		if(ListaUsar.equals("platos")){
		((TextView)view.findViewById(R.id.cantidadPlatosSeleccionados)).setText(String.valueOf(cantidad));
		if(cantidad>0){
			return view;
		}
		}
		((TextView)view.findViewById(R.id.nombrePlato)).setTextColor(Color.WHITE);
		((TextView)view.findViewById(R.id.descripcionPlato)).setTextColor(Color.WHITE);
		((TextView)view.findViewById(R.id.precioPlato)).setTextColor(Color.WHITE);
		return view;
	}
	

}