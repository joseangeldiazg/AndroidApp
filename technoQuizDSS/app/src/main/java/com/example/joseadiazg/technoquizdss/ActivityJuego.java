package com.example.joseadiazg.technoquizdss;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joseadiazg on 31/1/17.
 */

public class ActivityJuego extends Activity
{


    private final static int PREGUNTAS=2;

    private DBPref db;

    private Pregunta pregunta;

    private ArrayList<Pregunta>  listaPreguntas = new ArrayList<>();

    private TextView preguntaView;

    private Button respuesta1;
    private Button respuesta2;
    private Button respuesta3;
    private Button respuesta4;

    //Para las preguntas  de imagen y las de sonido deberemos crear un ImageView y MediaPlayer

    private MediaPlayer preguntaSonido;
    private ImageView preguntaImagen;

    //Tendremos que declarar también lo necesario para el sonido de acierto o fallo

    private MediaPlayer acierto;
    private MediaPlayer fallo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas);

        //Instanciamos los atributos

        this.db = new DBPref(this);

        this.preguntaView = (TextView) findViewById(R.id.preguntaView);

        this.respuesta1 = (Button) findViewById(R.id.respuesta1);
        this.respuesta2 = (Button) findViewById(R.id.respuesta2);
        this.respuesta3 = (Button) findViewById(R.id.respuesta3);
        this.respuesta4 = (Button) findViewById(R.id.respuesta4);

        this.preguntaImagen = (ImageView) findViewById(R.id.preguntaImagen);

        this.acierto = MediaPlayer.create(this, R.raw.acierto);
        this.fallo = MediaPlayer.create(this, R.raw.fallo);

        //AQUI CUANDO CREAMOS ESTE CURSOR DEBEREMOS AÑADIR ALGUNA MANERA PARA ELEGIR LA CATEGORIA O LA DIFICULTAD -> OPCIONAL

        Cursor cuestiones = this.db.getPreguntas(DBPref.Categoria.HISTORIA, DBPref.Dificultad.FACIL, PREGUNTAS);

        if (cuestiones.moveToFirst()) {
            do {
                String question = cuestiones.getString(cuestiones.getColumnIndex("pregunta"));
                int tipo = cuestiones.getInt(cuestiones.getColumnIndex("tipo"));
                String imagen = cuestiones.getString(cuestiones.getColumnIndex("imagen"));
                String sonido = cuestiones.getString(cuestiones.getColumnIndex("sonido"));
                String respuestaCorrecta = cuestiones.getString(cuestiones.getColumnIndex("respuesta_correcta"));

                ArrayList<String> respuestasIncorrectas = new ArrayList();

                respuestasIncorrectas.add(cuestiones.getString(cuestiones.getColumnIndex("respuesta_incorrecta_1")));
                respuestasIncorrectas.add(cuestiones.getString(cuestiones.getColumnIndex("respuesta_incorrecta_2")));
                respuestasIncorrectas.add(cuestiones.getString(cuestiones.getColumnIndex("respuesta_incorrecta_3")));

                this.pregunta = new Pregunta(question, respuestaCorrecta, respuestasIncorrectas.get(0)
                        ,respuestasIncorrectas.get(1), respuestasIncorrectas.get(2), tipo, imagen, sonido );

                this.listaPreguntas.add(this.pregunta);

            } while (cuestiones.moveToNext());
        }
        this.db.close();
    }

}
