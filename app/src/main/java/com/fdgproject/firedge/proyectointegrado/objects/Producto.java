package com.fdgproject.firedge.proyectointegrado.objects;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Firedge on 16/03/2015.
 */
public class Producto implements Serializable, Comparable<Producto>{

    private int id;
    private int id_categoria;
    private String titulo;
    private String descripcion;
    private double precio;
    private int stock;
    private String imagen;

    public Producto(int id, int id_categoria, String titulo, String descripcion, double precio, int stock, String imagen) {
        this.id = id;
        this.id_categoria = id_categoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }

    public Producto(JSONObject object) {
        try {
            this.id = Integer.parseInt(object.getString("id"));
            this.id_categoria = Integer.parseInt(object.getString("id_categoria"));
            this.titulo = object.getString("titulo");
            this.descripcion = object.getString("descripcion");
            this.precio = Double.parseDouble(object.getString("precio"));
            this.stock = Integer.parseInt(object.getString("stock"));
            this.imagen = object.getString("imagen");
        } catch (Exception ex){}
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public int compareTo(Producto another) {
        if(this.id_categoria < another.getId_categoria())
            return -1;
        else if(this.id_categoria > another.getId_categoria())
            return 1;
        else
            return 0;
    }
}
