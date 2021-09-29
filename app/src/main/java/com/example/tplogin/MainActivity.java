package com.example.tplogin;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Login fragmentLogin;
    InsertarMarca fragmentInsertarMarca;
    ListadoMarcas fragmentListado;
    ActualizarMarca fragmentActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrearFragments();
        String token = ReadPreferences("usuarioToken", "string").toString();
        String nombre = ReadPreferences("usuarioNombre", "string").toString();
        if(nombre.equals("")) {
            IrAlFragmentLogin();
        }else{
            IrAlFragmentListado(token);
        }
    }

    private void CrearFragments(){
        fragmentLogin = new Login();
        fragmentInsertarMarca = new InsertarMarca();
        fragmentListado = new ListadoMarcas();
        fragmentActualizar = new ActualizarMarca();
    }

    public void ReemplazarFragment(Fragment fragmento){
        FragmentManager manager;
        FragmentTransaction transaction;

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fraContenedor,fragmento,null);
        transaction.commit();
    }

    public void IrAlFragmentLogin(){
        ReemplazarFragment(fragmentLogin);
    }
    public void IrAlFragmentInsertarMarca(String token){
        fragmentInsertarMarca.setToken(token);
        ReemplazarFragment(fragmentInsertarMarca);
    }
    public void IrAlFragmentListado(String token){
        fragmentListado.setToken(token);
        ReemplazarFragment(fragmentListado);
    }
    public void IrAlFragmentActualizar(String token, String Id){
        fragmentActualizar.setToken(token);
        fragmentActualizar.setId(Id);
        ReemplazarFragment(fragmentActualizar);
    }

    protected void SavePreferences(String key, String value){
        if(key == null){
            Log.d("LLaveError","Error, ingrese llave");
            return;
        }
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.apply(); //editor.commit():
    }

    protected void SavePreferences(String key, boolean value){
        if(key == null) {
            Log.d("LLaveError","Error, ingrese llave");
            return;
        }
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key,value);
        editor.apply(); //editor.commit():
    }

    protected void SavePreferences(String key, float value){
        if(key == null){
            Log.d("LLaveError","Error, ingrese llave");
            return;
        }
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(key,value);
        editor.apply(); //editor.commit():
    }


    protected void SavePreferences(String key, long value){
        if(key == null){
            Log.d("LLaveError","Error, ingrese llave");
            return;
        }
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key,value);
        editor.apply(); //editor.commit():
    }

    protected void SavePreferences(String key,  Set<String> value){
        if(key == null){
            Log.d("LLaveError","Error, ingrese llave");
            return;
        }
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(key,value);
        editor.apply(); //editor.commit():
    }

    protected Object ReadPreferences (String key, String type) {
        //needs the datatype of the return value
        if(key == null){
            Log.d("LLaveError","Error, ingrese llave");
            return null;
        }
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        Object result = null;
        switch (type.toLowerCase(Locale.ROOT)){
            case "int":
                result =  (int) sharedPref.getInt(key,0);// can replace default with whatever you want
                break;
            case "float":
                result =  (float) sharedPref.getFloat(key,0);
                break;
            case "long":
                result =  (long) sharedPref.getLong(key,0);
                break;
            case "string":
                result =  (String) sharedPref.getString(key,"");
                break;
            case "boolean":
                result =  (boolean) sharedPref.getBoolean(key,false);
                break;
            case "set":
                result =  (Set<String>) sharedPref.getStringSet(key,null);
                break;
            default:
                Log.d("DatoError","Error, tipo de dato no identificado");
                break;
        }
        return result;
    }

    public void clearPreferences(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }
}