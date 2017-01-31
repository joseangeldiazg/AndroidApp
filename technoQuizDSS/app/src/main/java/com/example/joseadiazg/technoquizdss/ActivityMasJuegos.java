package com.example.joseadiazg.technoquizdss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by joseadiazg on 25/1/17.
 */

public class ActivityMasJuegos extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otrosjuegos);

        WebView juego1= (WebView) this.findViewById(R.id.webView1);
        juego1.loadUrl("http://growsoundmag.com/category/underground/");

        WebView juego2= (WebView) this.findViewById(R.id.webView2);
        juego2.loadUrl("http://growsoundmag.com/category/alternativo/");

    }
}
