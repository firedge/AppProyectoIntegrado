package com.fdgproject.firedge.proyectointegrado.objects;

import org.json.JSONObject;

/**
 * Created by Firedge on 16/03/2015.
 */
public class Categoria {

    private int id;
    private String nombre;

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Categoria(JSONObject object) {
        try {
            this.id = Integer.parseInt(object.getString("id"));
            this.nombre = object.getString("nombre");
        } catch (Exception ex){}
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
