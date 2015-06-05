package com.fdgproject.firedge.proyectointegrado;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdgproject.firedge.proyectointegrado.objects.Producto;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    private static final String URLIMG = "http://192.168.1.136:8080/Proyecto/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Producto producto;
        producto = (Producto) getIntent().getExtras().getSerializable("producto");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(producto.getTitulo());
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView tvtitulo = (TextView)findViewById(R.id.tv_titulo);
        tvtitulo.setText(producto.getTitulo());
        TextView tvdescripcion = (TextView)findViewById(R.id.tv_descripcion);
        tvdescripcion.setText(producto.getDescripcion());
        TextView tvprecio = (TextView)findViewById(R.id.tv_precio);
        tvprecio.setText(getResources().getString(R.string.tv_precio)+" "+producto.getPrecio()+"€");
        ImageView image = (ImageView)findViewById(R.id.iv_imagen);
        Picasso.with(this).load(URLIMG+producto.getImagen()).into(image);
        int stock = producto.getStock();
        TextView tvstock = (TextView)findViewById(R.id.tv_stock);
        if(stock < 1){
            tvstock.setTextColor(Color.parseColor("#ffcccccc"));
            tvstock.setText(getResources().getString(R.string.tv_stock)+" --");
        } else {
            tvstock.setText(getResources().getString(R.string.tv_stock)+" "+stock);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vacio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
