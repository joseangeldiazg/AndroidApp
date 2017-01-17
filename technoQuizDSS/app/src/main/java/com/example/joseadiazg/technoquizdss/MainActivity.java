package com.example.joseadiazg.technoquizdss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos las vistas de los juegos que abriremos en el activity relacionado con los nuevos juegos

        WebView juego1= (WebView) this.findViewById(R.id.webView1);
        juego1.loadUrl("www.growsoundmag.com");

        WebView juego2= (WebView) this.findViewById(R.id.webView2);
        juego2.loadUrl("www.growsoundmag.com");


        b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "BOTON PULSADO", Toast.LENGTH_SHORT).show();
            }
        });
    }
}