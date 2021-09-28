package com.example.tplogin;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tplogin.Utils.OutputStreamHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ActualizarMarca extends Fragment {

    private View layoutRoot = null;
    EditText edActualizarMarca;
    EditText edId;
    Button btnActualizar;
    Button btnEliminar;
    String token = null;
    String Id = null;


    TareaAsincronicaActualizarMarcas tareaActualizar = new TareaAsincronicaActualizarMarcas();
    TareaAsincronicaEliminarMarcas tareaEliminar = new TareaAsincronicaEliminarMarcas();
    protected JSONObject jsonParam = new JSONObject();
    protected JSONObject jsonEliminar = new JSONObject();

    public ActualizarMarca() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(layoutRoot == null){
            layoutRoot = inflater.inflate(R.layout.fragment_actualizar_marca, container, false);
            ObtenerReferencias();
        }
        SetearListeners();

        return layoutRoot;
    }

    private void SetearListeners(){
        btnActualizar.setOnClickListener(btnActualizarMarca_Click);
        btnEliminar.setOnClickListener(btnEliminar_Click);
    }

    View.OnClickListener btnActualizarMarca_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setParamsActualizar("Id",Id);
            setParamsActualizar("Nombre", edActualizarMarca.getText().toString());
            tareaActualizar.execute();
        }
    };

    View.OnClickListener btnEliminar_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setParamsEliminar("Id", Id);
            tareaEliminar.execute();
        }
    };

    private void ObtenerReferencias(){
        btnActualizar   = (Button) layoutRoot.findViewById(R.id.btnActualizar);
        btnEliminar   = (Button) layoutRoot.findViewById(R.id.btnEliminar);
        edActualizarMarca = (EditText) layoutRoot.findViewById(R.id.edMarcaActualizar);
    }

    public void setParamsActualizar(String key, String value) {
        try {
            jsonParam.put(key, value);
        } catch (Exception e) {
            //CustomLog.logException(e);
        }
    }

    public void setParamsEliminar(String key, String value) {
        try {
            jsonEliminar.put(key, value);
        } catch (Exception e) {
            //CustomLog.logException(e);
        }
    }


    public void setToken(String token) {
        this.token = token;
    }

    public void setId(String Id){
        this.Id = Id;
    }

    private class TareaAsincronicaActualizarMarcas extends AsyncTask<Void, Void, String> {

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
                strAPIUrl = new URL("https://api.polshu.com.ar/api/v1/tablas/marcas/");
                miConexion = (HttpURLConnection) strAPIUrl.openConnection();
                miConexion.setRequestProperty("Content-Type", "application/json");
                miConexion.setRequestProperty("Accept", "application/json");
                miConexion.setRequestMethod("PUT");
                miConexion.setRequestProperty("tokenkey", token);
                if (jsonParam.length() > 0)
                    OutputStreamHelper.writeOutPut(miConexion.getOutputStream(), jsonParam);
                if (miConexion.getResponseCode() == 200) {
                    responseReader = new BufferedReader(new InputStreamReader(miConexion.getInputStream()));
                    sbResponse = new StringBuilder();
                    while ((responseLine = responseReader.readLine()) != null) {
                        sbResponse.append(responseLine);
                    }
                    responseReader.close();
                    strResultado = sbResponse.toString();
                } else {
                    Log.d("resultado", "hay error");
                }
            } catch (Exception e) {
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
            Toast.makeText(getActivity(), "Marca Actualizada", Toast.LENGTH_LONG).show();
        }
    }

    private class TareaAsincronicaEliminarMarcas extends AsyncTask<Void, Void, String> {

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
                strAPIUrl = new URL("https://api.polshu.com.ar/api/v1/tablas/marcas/");
                miConexion = (HttpURLConnection) strAPIUrl.openConnection();
                miConexion.setRequestProperty("Content-Type", "application/json");
                miConexion.setRequestProperty("Accept", "application/json");
                miConexion.setRequestMethod("DELETE");
                miConexion.setRequestProperty("tokenkey", token);
                if (jsonEliminar.length() > 0)
                    OutputStreamHelper.writeOutPut(miConexion.getOutputStream(), jsonEliminar);
                if (miConexion.getResponseCode() == 200) {
                    responseReader = new BufferedReader(new InputStreamReader(miConexion.getInputStream()));
                    sbResponse = new StringBuilder();
                    while ((responseLine = responseReader.readLine()) != null) {
                        sbResponse.append(responseLine);
                    }
                    responseReader.close();
                    strResultado = sbResponse.toString();
                } else {
                    Log.d("resultado", "hay error");
                }
            } catch (Exception e) {
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
            Toast.makeText(getActivity(), "Marca Eliminada", Toast.LENGTH_LONG).show();
        }
    }
}