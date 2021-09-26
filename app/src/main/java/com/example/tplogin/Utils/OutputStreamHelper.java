package com.example.tplogin.Utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class OutputStreamHelper {
    public static void writeOutPut (OutputStream stream, JSONObject jsonParam) {
        OutputStreamWriter outputStream = new OutputStreamWriter(stream);
        try {
            outputStream.write(jsonParam.toString());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Log.d("resultado", "hay error stream helper");;
        }
    }
}
