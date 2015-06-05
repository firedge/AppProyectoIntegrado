package com.fdgproject.firedge.proyectointegrado.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Firedge on 26/03/2015.
 */
public class Cliente {
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;

    public Cliente(String nombre, String apellidos, String direccion, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public JSONObject getJson(){
        JSONObject objetoJson = new JSONObject();
        try {
            objetoJson.put("nombre", this.nombre);
            objetoJson.put("apellidos", this.apellidos);
            objetoJson.put("direccion", this.direccion);
            objetoJson.put("telefono", this.telefono);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objetoJson;
    }

    public String getPost(){
        String s = "nombre="+this.nombre+"&" +
                "apellidos="+this.apellidos+"&" +
                "direccion=" + this.direccion+"&" +
                "telefono=" + this.telefono;
        return s;
    }
}
