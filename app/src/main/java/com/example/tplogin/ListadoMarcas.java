package com.example.tplogin;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListadoMarcas extends Fragment {

    private View layoutRoot = null;
    public ArrayList<Marcas> listaMarcas;


    public ListadoMarcas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(layoutRoot == null){
            layoutRoot = inflater.inflate(R.layout.fragment_listado_marcas, container, false);
            ObtenerReferencias();
        }
        return layoutRoot;
    }

/*private class TareaAsincronicaInsertarMarcas extends AsyncTask<Void, Void, String> {

        private Context context;
        private void CargarDatos (String resultado){
            ArrayList<Marcas> datosArrayList;
            ArrayAdapter<Marcas> adapter;

            Gson marcas = new Gson();
            //listaMarcas = marcas.fromJson(resultado,miType);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Estoy en el Main Thread.
        }

        @Override
        protected String doInBackground(Void... parametros) {
            HttpURLConnection miConexion = null;
            URL strAPIUrl; // Estoy en el Background Thread.
            BufferedReader responseReader;
            String responseLine, strResultado = "";
            StringBuilder sbResponse;

            try {
                //strAPIUrl = new URL("http://api.polshu.com.ar/api/v1/usuarios/login/" + edNombre + "/" + edPassword);
                //miConexion = (HttpURLConnection) strAPIUrl.openConnection();
                miConexion.setRequestMethod("GET");
                if (miConexion.getResponseCode() == 200) {
                    // En un BufferedReader leo todo!
                    responseReader = new BufferedReader(new InputStreamReader(miConexion.getInputStream()));
                    sbResponse = new StringBuilder();
                    while ((responseLine = responseReader.readLine()) != null) {
                        sbResponse.append(responseLine);
                    }
                    responseReader.close();
                    // Hasta aca lei la respuesta en el StringBuilder!
                    // La paso a la variable “strResultado”
                    strResultado = sbResponse.toString();
                } else {
                    // Ocurrió algún error.
                    Log.d("resultado", "hay error");
                }
            } catch (Exception e) {
                // Ocurrió algún error al conectarme, serán permisos?
                Log.d("resultado", "errores: " + e.getMessage());
            } finally {
                if (miConexion != null) {
                    miConexion.disconnect();
                }
            }
            Log.d("resultado", strResultado);
            return strResultado;
        }
        @Override protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            // Estoy en el Main Thread.
            Gson usuario = new Gson();
            //user = usuario.fromJson(resultado,Usuario.class);
        }
    }*/

    private void ObtenerReferencias(){

    }
}