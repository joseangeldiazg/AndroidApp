package com.example.joseadiazg.technoquizdss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class ActivityTematica extends AppCompatActivity implements View.OnClickListener
{


    private ImageButton categoria1;
    private ImageButton categoria2;
    private Utilidad utilidad;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tematica);

        this.categoria1 = (ImageButton) findViewById(R.id.artistas);
        this.categoria2 = (ImageButton) findViewById(R.id.historia);

        this.utilidad = (Utilidad) getApplicationContext();

        categoria1.setOnClickListener(this);
        categoria2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        ImageButton pulsado = (ImageButton) v;

        if(pulsado.getId()==R.id.artistas)
        {
            this.utilidad.setCategoria(DBPref.Categoria.ARTISTAS);
            this.startActivity(new Intent(ActivityTematica.this, ActivityJuego.class));
        }
        else if(pulsado.getId()==R.id.historia)
        {
            this.startActivity(new Intent(ActivityTematica.this, ActivityJuego.class));
            this.utilidad.setCategoria(DBPref.Categoria.HISTORIA);
        }


    }
}
