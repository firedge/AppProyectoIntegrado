package com.fdgproject.firedge.proyectointegrado.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdgproject.firedge.proyectointegrado.R;
import com.fdgproject.firedge.proyectointegrado.objects.Categoria;
import com.fdgproject.firedge.proyectointegrado.objects.Pedido;
import com.fdgproject.firedge.proyectointegrado.objects.Producto;
import com.fdgproject.firedge.proyectointegrado.stickylist.StickyListHeadersAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by David on 08/05/2015.
 */
public class StickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<Producto> productos;
    private ArrayList<Categoria> categorias;
    private LayoutInflater inflater;
    private Context context;
    private static final String URLIMG = "http://192.168.1.136:8080/Proyecto/images/";
    private final String PEDIDOS = "com.firedge.pedidos";

    public StickyListAdapter(Context context, ArrayList<Producto> productos, ArrayList<Categoria> categorias) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.productos = productos;
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_layout_producto, parent, false);
            holder.tv_titulo = (TextView) convertView.findViewById(R.id.tv_titulo);
            holder.tv_stock = (TextView) convertView.findViewById(R.id.tv_stock);
            holder.tv_precio = (TextView) convertView.findViewById(R.id.tv_precio);
            holder.iv_foto = (ImageView) convertView.findViewById(R.id.iv_imagen);
            holder.iv_carrito = (ImageView) convertView.findViewById(R.id.iv_carrito);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_titulo.setText(productos.get(position).getTitulo());
        holder.tv_precio.setText(productos.get(position).getPrecio()+"€");
        Picasso.with(context).load(URLIMG+productos.get(position).getImagen()).into(holder.iv_foto);

        int stock = productos.get(position).getStock();
        if(stock < 1){
            holder.tv_stock.setTextColor(Color.parseColor("#ffcccccc"));
            holder.iv_carrito.setBackgroundColor(Color.parseColor("#ffcccccc"));
            holder.tv_stock.setText("--");
        } else {
            holder.tv_stock.setText(stock+"");
            holder.tv_stock.setTextColor(Color.parseColor("#ff00cc00"));
            holder.iv_carrito.setBackgroundColor(Color.parseColor("#ff00cc00"));
        }

        holder.iv_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PEDIDOS);
                intent.putExtra("pedido", new Pedido(productos.get(position)));
                context.sendBroadcast(intent);
            }
        });

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header_layout_producto, parent, false);
            holder.tv_categoria = (TextView) convertView.findViewById(R.id.tv_categoria);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        //String headerText = "" + countries[position].subSequence(0, 1).charAt(0);
        for(Categoria c: categorias){
            if(c.getId() == productos.get(position).getId_categoria()){
                holder.tv_categoria.setText(c.getNombre());
            }
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return productos.get(position).getId_categoria();
    }

    class HeaderViewHolder {
        TextView tv_categoria;
    }

    class ViewHolder {
        TextView tv_titulo, tv_stock, tv_precio;
        ImageView iv_foto, iv_carrito;
    }

}
