package com.fdgproject.firedge.proyectointegrado.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdgproject.firedge.proyectointegrado.R;
import com.fdgproject.firedge.proyectointegrado.objects.Pedido;

import java.util.ArrayList;

/**
 * Created by David on 08/05/2015.
 */
public class ListViewAdapter extends ArrayAdapter<Pedido> {

    private Context cnt;
    private ArrayList<Pedido> list;
    private int rec;
    private static LayoutInflater lin;
    private ViewHolder vh = null;
    private final String PEDIDOS = "com.firedge.pedidos";

    static class ViewHolder{
        public TextView tv_titulo, tv_unidades, tv_precio;
        public ImageView iv_cancel;
        public int posicion;
    }

    public ListViewAdapter(Context context, int resource, ArrayList<Pedido> objects) {
        super(context, resource, objects);
        this.cnt = context;
        this.list = objects;
        this.rec = resource;
        this.lin = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = lin.inflate(rec, null);
            vh =new ViewHolder();
            vh.tv_titulo = (TextView)convertView.findViewById(R.id.tv_title);
            vh.tv_unidades = (TextView)convertView.findViewById(R.id.tv_unidades);
            vh.tv_precio = (TextView)convertView.findViewById(R.id.tv_precio);
            vh.iv_cancel = (ImageView)convertView.findViewById(R.id.iv_cancelar);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }
        vh.posicion = position;
        vh.tv_titulo.setText(list.get(position).getProducto().getTitulo());
        vh.tv_unidades.setText(list.get(position).getUnidades()+"");
        double precio = list.get(position).getProducto().getPrecio()*list.get(position).getUnidades();
        vh.tv_precio.setText(precio+"€");
        vh.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar(position);
            }
        });
        return convertView;
    }

    private void borrar(final int pos){
        AlertDialog.Builder alert = new AlertDialog.Builder(cnt);
        alert.setTitle(cnt.getResources().getString(R.string.msg_borrar));
        LayoutInflater inflater = LayoutInflater.from(cnt);

        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        list.remove(pos);
                        notifyDataSetChanged();
                        Intent intent = new Intent(PEDIDOS);
                        intent.putExtra("pedido", new Pedido());
                        cnt.sendBroadcast(intent);
                    }
                });
        alert.setNegativeButton(android.R.string.cancel,null);
        alert.show();
    }

}