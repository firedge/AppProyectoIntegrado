package com.fdgproject.firedge.proyectointegrado.tabs;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fdgproject.firedge.proyectointegrado.DetailActivity;
import com.fdgproject.firedge.proyectointegrado.R;
import com.fdgproject.firedge.proyectointegrado.adapters.ListViewAdapter;
import com.fdgproject.firedge.proyectointegrado.controlers.ControlWeb;
import com.fdgproject.firedge.proyectointegrado.objects.Cliente;
import com.fdgproject.firedge.proyectointegrado.objects.Pedido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3 extends Fragment {

    private View viewPrincipal;
    private ArrayList<Pedido> pedido;
    private final String PEDIDOS = "com.firedge.pedidos";

    private ListView lv;
    private ListViewAdapter ad;

    public Tab3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewPrincipal = inflater.inflate(R.layout.tab3, container, false);
        pedido = new ArrayList<Pedido>();
        lv = (ListView) viewPrincipal.findViewById(R.id.lv_pedido);
        ad = new ListViewAdapter(viewPrincipal.getContext(), R.layout.item_layout_carrito, pedido);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(viewPrincipal.getContext(), DetailActivity.class);
                intent.putExtra("producto", pedido.get(i).getProducto());
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogUd(i);
                return true;
            }
        });
        refresh();

        Button bt = (Button) viewPrincipal.findViewById(R.id.bt_pedido);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hacerPedido();
            }
        });

        return viewPrincipal;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receptor, new IntentFilter(PEDIDOS));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receptor);
    }

    //BroadcastReceiver
    private BroadcastReceiver receptor= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Pedido ped = (Pedido) bundle.getSerializable("pedido");
            if(ped.getProducto()!=null && ped.getUnidades()!=0) {
                boolean encontrado = false;
                for (Pedido p : pedido) {
                    if (p.getProducto().getId() == ped.getProducto().getId()) {
                        if (p.getUnidades() + 1 > p.getProducto().getStock()) {
                            Toast.makeText(viewPrincipal.getContext(), getResources().getString(R.string.msg_nostock), Toast.LENGTH_SHORT).show();
                        } else {
                            p.setUnidades(p.getUnidades() + 1);
                            Toast.makeText(viewPrincipal.getContext(), getResources().getString(R.string.msg_addok), Toast.LENGTH_SHORT).show();
                        }
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    if(ped.getProducto().getStock() > 0) {
                        pedido.add(ped);
                        Toast.makeText(viewPrincipal.getContext(), getResources().getString(R.string.msg_addok), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(viewPrincipal.getContext(), getResources().getString(R.string.msg_nostock), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            refresh();
        }
    };

    private void refresh(){
        ad.notifyDataSetChanged();
        double i = 0;
        for(Pedido p: pedido){
            i+=p.getUnidades()*p.getProducto().getPrecio();
        }
        TextView tv = (TextView)viewPrincipal.findViewById(R.id.tv_total);
        tv.setText(i+"€");
    }

    private void dialogUd(final int i){
        AlertDialog.Builder alert = new AlertDialog.Builder(viewPrincipal.getContext());
        LayoutInflater inflater = LayoutInflater.from(viewPrincipal.getContext());
        View vista = inflater.inflate(R.layout.dialog_add, null);
        alert.setView(vista);
        final Pedido ped = pedido.get(i);
        final EditText et = (EditText) vista.findViewById(R.id.et_unidades);
        et.setText(ped.getUnidades()+"");
        Button bt_mas = (Button)vista.findViewById(R.id.bt_mas);
        bt_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(et.getText().toString()) + 1;
                if (num <= ped.getProducto().getStock()) {
                    et.setText(num + "");
                }
            }
        });
        Button bt_menos = (Button)vista.findViewById(R.id.bt_menos);
        bt_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(et.getText().toString())-1;
                if(num > 0) {
                    et.setText(num + "");
                }
            }
        });
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int num = Integer.parseInt(et.getText().toString());
                        if (num <= ped.getProducto().getStock()) {
                            pedido.get(i).setUnidades(Integer.parseInt(et.getText().toString()));
                            refresh();
                        } else {
                            Toast.makeText(viewPrincipal.getContext(), getResources().getString(R.string.msg_nostock), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alert.setNegativeButton(android.R.string.cancel, null);
        alert.show();
    }

    private void hacerPedido(){
        AlertDialog.Builder alert = new AlertDialog.Builder(viewPrincipal.getContext());
        LayoutInflater inflater = LayoutInflater.from(viewPrincipal.getContext());
        View vista = inflater.inflate(R.layout.dialog_info, null);
        alert.setView(vista);
        final EditText et_nombre = (EditText) vista.findViewById(R.id.et_nombre);
        final EditText et_apellidos = (EditText) vista.findViewById(R.id.et_apellido);
        final EditText et_direccion = (EditText) vista.findViewById(R.id.et_direccion);
        final EditText et_telefono = (EditText) vista.findViewById(R.id.et_telefono);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Cliente c = new Cliente(et_nombre.getText().toString(), et_apellidos.getText().toString(),
                                et_direccion.getText().toString(), et_telefono.getText().toString());
                        new Upload().execute(c);
                    }
                });
        alert.setNegativeButton(android.R.string.cancel,null);
        alert.show();
    }

    class Upload extends AsyncTask<Cliente, Integer, String> {

        private static final String URLBASE = "http://192.168.1.136:8080/Proyecto/controlandroid?action=";

        @Override
        protected String doInBackground(Cliente... objects) {
            Cliente cliente = objects[0];
            String s = ControlWeb.post(URLBASE + "pedido", cliente.getJson());
            s = s.trim();
            int id;
            JSONTokener token = new JSONTokener(s);
            try {
                JSONArray array = new JSONArray(token);
                id = array.getJSONObject(0).getInt("id");
            } catch (JSONException e) {
                id = -1;
            }
            if(id >= 0) {
                for(Pedido p: pedido) {
                    ControlWeb.post(URLBASE+"lineapedido", p.getJson(id));
                }
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(viewPrincipal.getContext(), getResources().getString(R.string.msg_pedido), Toast.LENGTH_SHORT).show();
        }
    }
}
