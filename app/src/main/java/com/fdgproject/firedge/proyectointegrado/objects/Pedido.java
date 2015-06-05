package com.fdgproject.firedge.proyectointegrado.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Firedge on 25/03/2015.
 */
public class Pedido implements Serializable{

    private Producto producto;
    private int unidades;

    public Pedido(Producto producto) {
        this.producto = producto;
        this.unidades = 1;
    }

    public Pedido() {
        this.producto = null;
        this.unidades = 0;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public JSONObject getJson(int id){
        JSONObject objetoJson = new JSONObject();
        try {
            objetoJson.put("id_pedido", id);
            objetoJson.put("id_producto", this.producto.getId());
            objetoJson.put("cantidad", this.unidades);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objetoJson;
    }
}
