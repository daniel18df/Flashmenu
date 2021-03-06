package cl.flashmenu.aplicacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import cl.flashmenu.aplicacion.preferencia;

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

public class CopyOfWeatherDataListAdapter extends SimpleAdapter {
	String ListaUsar;
	ArrayList comprarador;
	public CopyOfWeatherDataListAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to, String i) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		if(i.equals("platos")){
			comprarador = UserData.lista2;
			
		}
		 if(i.equals("platos2"))
			comprarador = UserData.lista2;
		
		ListaUsar = i;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		int cantidad=0;
		view = super.getView(arg0, view, arg2);
		for(int i = 0;i<comprarador.size();i++){
			String comparer = (String)((HashMap<String,Object>)comprarador.get(i)).get(UserData.TAG_PREFERENCIAS_TIPO_VALOR);
			//String comparer2 = (String)((HashMap<String,Object>)comprarador.get(i)).get(listaBebidas.TAG_NOMBRE);
			
			if(comparer != null && comparer.equals(((TextView)view.findViewById(R.id.itempreferencia)).getText().toString())){
			
					((TextView)view.findViewById(R.id.itempreferencia)).setTextColor(Color.BLACK);
					return view;
			}
			//}
			
			
			
		}
		

		((TextView)view.findViewById(R.id.itempreferencia)).setTextColor(Color.WHITE);
		
		return view;
	}
	

}