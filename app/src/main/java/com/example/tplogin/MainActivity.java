package com.example.tplogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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
        IrAlFragmentLogin();
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
}