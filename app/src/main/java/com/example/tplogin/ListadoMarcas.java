package com.example.tplogin;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListadoMarcas extends Fragment {

    private View layoutRoot = null;
    ArrayList<Marca> listaMarcas;
    ArrayAdapter<Marca> marcasAdapter;
    ListView lvMarcas;

    String token = null;
    Type fooType = new TypeToken<ArrayList<Marca>>() {}.getType();


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
            SetearListeners();
        }
        TareaAsincronicaListarMarcas miTarea = new TareaAsincronicaListarMarcas();
        miTarea.execute();


        return layoutRoot;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private class TareaAsincronicaListarMarcas extends AsyncTask<Void, Void, String> {


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
                miConexion.setRequestProperty("tokenkey", token);
                miConexion.setRequestMethod("GET");
                Log.d("locura", "value" + miConexion.getResponseCode());
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
            Gson marcasJson = new Gson();
            listaMarcas = marcasJson.fromJson(resultado, fooType);
            marcasAdapter = new ArrayAdapter<Marca>(getContext(), android.R.layout.simple_list_item_1,  listaMarcas);
            lvMarcas.setAdapter(marcasAdapter);
        }
    }

    private void ObtenerReferencias(){
        lvMarcas = (ListView) layoutRoot.findViewById(R.id.lvMarcas);
    }

    private void SetearListeners(){
        lvMarcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Marca marcaSeleccionada;
                marcaSeleccionada = (Marca) lvMarcas.getItemAtPosition(position);
                MainActivity actividadContenedora;
                actividadContenedora = (MainActivity) getActivity();
                //assert actividadContenedora != null;
                actividadContenedora.IrAlFragmentActualizar(token, marcaSeleccionada.Id);
            }
        });
    }
}