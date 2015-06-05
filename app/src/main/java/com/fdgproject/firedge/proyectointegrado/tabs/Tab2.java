package com.fdgproject.firedge.proyectointegrado.tabs;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.fdgproject.firedge.proyectointegrado.DetailActivity;
import com.fdgproject.firedge.proyectointegrado.R;
import com.fdgproject.firedge.proyectointegrado.adapters.StickyListAdapter;
import com.fdgproject.firedge.proyectointegrado.controlers.ControlWeb;
import com.fdgproject.firedge.proyectointegrado.objects.Categoria;
import com.fdgproject.firedge.proyectointegrado.objects.Producto;
import com.fdgproject.firedge.proyectointegrado.stickylist.StickyListHeadersListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {

    private View view;
    private StickyListHeadersListView stickyList;
    private StickyListAdapter adapter;

    //URLS
    private static final String URLBASE = "http://192.168.1.136:8080/Proyecto/controlandroid?action=";

    //ArrayList
    private ArrayList<Categoria> categorias;
    private ArrayList<Producto> productos;

    public Tab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.tab2, container, false);

        categorias = new ArrayList<Categoria>();
        productos = new ArrayList<Producto>();

        stickyList = (StickyListHeadersListView) view.findViewById(R.id.lv_productos);
        adapter = new StickyListAdapter(view.getContext(), productos, categorias);
        stickyList.setAdapter(adapter);
        stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("producto", productos.get(position));
                startActivity(intent);
            }
        });

        cargarDatos();

        return view;
    }

    private void cargarDatos(){
        String[] peticiones = new String[2];
        peticiones[0] = "productos";
        peticiones[1] = "categorias";
        GetRestFul get = new GetRestFul();
        get.execute(peticiones);
    }

    private class GetRestFul extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... s) {
            String[] r = new String[s.length];
            int i = 0;
            for(String a: s){
                r[i] = ControlWeb.get(URLBASE + a);
                i++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String... s) {
            super.onPostExecute(s);
            //Productos
            JSONTokener token = new JSONTokener(s[0]);
            try {
                JSONArray array = new JSONArray(token);
                for(int i=0;  i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    Producto p = new Producto(object);
                    productos.add(p);
                }
            }catch(Exception ex){}
            //Categorias
            token = new JSONTokener(s[1]);
            try {
                JSONArray array = new JSONArray(token);
                for(int i=0;  i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    Categoria c = new Categoria(object);
                    categorias.add(c);
                }
            }catch(Exception ex){}
            Collections.sort(productos);
            adapter.notifyDataSetChanged();
            RelativeLayout rl = (RelativeLayout)view.findViewById(R.id.rlCarga);
            rl.setVisibility(View.GONE);
        }
    }


}
