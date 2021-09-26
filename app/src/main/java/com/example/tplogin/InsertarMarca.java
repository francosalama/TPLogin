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
import android.widget.Toast;

import com.example.tplogin.Utils.OutputStreamHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class InsertarMarca extends Fragment {

    private View layoutRoot = null;
    public ArrayList<Marcas> listaMarcas;
    EditText edMarca;
    Button btnAgregarMarca;
    String token = null;


    TareaAsincronicaInsertarMarcas miTarea = new TareaAsincronicaInsertarMarcas();
    protected JSONObject jsonParam = new JSONObject();

    public InsertarMarca() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(layoutRoot == null){
            layoutRoot = inflater.inflate(R.layout.fragment_insertar_marca, container, false);
            ObtenerReferencias();
        }
        SetearListeners();

        return layoutRoot;
    }

    private void SetearListeners(){
        btnAgregarMarca.setOnClickListener(btnAgregarMarca_Click);
    }

    View.OnClickListener btnAgregarMarca_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            makeJSON();
            miTarea.execute();
        }
    };

    private void ObtenerReferencias(){
        btnAgregarMarca   = (Button) layoutRoot.findViewById(R.id.btnAgregarMarca) ;
        edMarca = (EditText) layoutRoot.findViewById(R.id.edMarca);
    }

    public JSONObject makeJSON() {
        try {
            jsonParam.put("Nombre", edMarca.getText().toString());
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return jsonParam;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private class TareaAsincronicaInsertarMarcas extends AsyncTask<Void, Void, String> {

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
                strAPIUrl = new URL("http://api.polshu.com.ar/api/v1/tablas/marcas/");
                miConexion = (HttpURLConnection) strAPIUrl.openConnection();
                miConexion.setRequestMethod("POST");
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
            Toast.makeText(getActivity(), "Marca Agregada", Toast.LENGTH_LONG).show();
            }
        }
}