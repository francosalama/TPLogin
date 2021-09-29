package com.example.tplogin;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Login extends Fragment {

    private View layoutRoot = null;
    EditText edNombre;
    EditText edPassword;
    Button btnLogin;

    Usuario user = new Usuario();
    TareaAsincronicaUsuarios miTarea = new TareaAsincronicaUsuarios();


    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(layoutRoot == null){
            layoutRoot = inflater.inflate(R.layout.fragment_login, container, false);
            ObtenerReferencias();
        }

        SetearListeners();


        return layoutRoot;
    }

    private class TareaAsincronicaUsuarios extends AsyncTask<Void, Void, String> {


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
                strAPIUrl = new URL("https://api.polshu.com.ar/api/v1/usuarios/login/" + edNombre.getText().toString() + "/" + edPassword.getText().toString());
                miConexion = (HttpURLConnection) strAPIUrl.openConnection();
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
            Log.d("usuario", resultado);
            user = usuario.fromJson(resultado,Usuario.class);
            if(user.Id != 0){
                Log.d("logueado", "logueado");

                MainActivity actividadContenedora;
                actividadContenedora = (MainActivity) getActivity();
                assert actividadContenedora != null;
                actividadContenedora.SavePreferences("usuarioNombre", user.Nombre);
                actividadContenedora.SavePreferences("usuarioApellido", user.Apellido);
                actividadContenedora.SavePreferences("usuarioToken", user.TokenKey);
                actividadContenedora.SavePreferences("usuarioId", user.Id);
                actividadContenedora.IrAlFragmentInsertarMarca(user.TokenKey);
            }
        }
    }

    private void ObtenerReferencias(){
        btnLogin   = (Button) layoutRoot.findViewById(R.id.btnLogin) ;
        edNombre   = (EditText) layoutRoot.findViewById(R.id.edNombre) ;
        edPassword   = (EditText) layoutRoot.findViewById(R.id.edPassword) ;
    }

    private void SetearListeners(){
        btnLogin.setOnClickListener(btnLogin_Click);
    }

    View.OnClickListener btnLogin_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            miTarea.execute();
        }
    };
}