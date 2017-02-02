package com.example.joseadiazg.technoquizdss;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by joseadiazg on 31/1/17.
 */

public class ActivityJuego extends Activity implements View.OnClickListener
{

    private final static int PREGUNTAS=4;

    private int indicePregunta=0;

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

    private ImageButton botonPlay;
    private ImageButton botonStop;

    //Tendremos que declarar también lo necesario para el sonido de acierto o fallo

    private MediaPlayer acierto;
    private MediaPlayer fallo;

    //Necesitaremos crear una variable para guardar la puntuación

    int puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas);

        //Instanciamos los atributos

        this.db = new DBPref(this);

        this.puntuacion=0;

        this.preguntaView = (TextView) findViewById(R.id.preguntaView);

        this.respuesta1 = (Button) findViewById(R.id.respuesta1);
        this.respuesta2 = (Button) findViewById(R.id.respuesta2);
        this.respuesta3 = (Button) findViewById(R.id.respuesta3);
        this.respuesta4 = (Button) findViewById(R.id.respuesta4);

        this.preguntaImagen = (ImageView) findViewById(R.id.preguntaImagen);

        this.acierto = MediaPlayer.create(this, R.raw.acierto);
        this.fallo = MediaPlayer.create(this, R.raw.fallo);

        this.botonPlay = (ImageButton) findViewById(R.id.boton_play);
        this.botonStop = (ImageButton) findViewById(R.id.boton_pause);

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

        respuesta1.setOnClickListener(this);
        respuesta2.setOnClickListener(this);
        respuesta3.setOnClickListener(this);
        respuesta3.setOnClickListener(this);

        Collections.shuffle(listaPreguntas);

        instanciaPregunta(listaPreguntas.get(indicePregunta));
        indicePregunta++;
    }

    public void instanciaPregunta(Pregunta pregunta)
    {

        ArrayList<String> respuestas = new ArrayList<>();

        //Añadimos la pregunta

        this.preguntaView.setText(pregunta.getPregunta());

        //Añadimos las respuestas en un Array para hacer un shuffle y que vayan cambiando de orden

        respuestas=pregunta.getRespuestas();
        Collections.shuffle(respuestas);

        this.respuesta1.setText(respuestas.get(0));
        this.respuesta2.setText(respuestas.get(1));
        this.respuesta3.setText(respuestas.get(2));
        this.respuesta4.setText(respuestas.get(3));

        //Ahora en función del tipo de pregunta debemos generar una cosa u otra

        switch (pregunta.getTipo())
        {
            case 1:
            /*Texto Normal: Debemos ocultar los botones de play y la imagen */
                this.preguntaImagen.setVisibility(View.INVISIBLE);
                this.botonStop.setVisibility(View.INVISIBLE);
                this.botonPlay.setVisibility(View.INVISIBLE);
                break;

            case 2:
            /*Imagen: Ocultamos los botones de sonido y hacemos visible la imagen */
                this.preguntaImagen.setVisibility(View.VISIBLE);
                this.preguntaImagen.setImageResource(getResources().getIdentifier(this.pregunta.getImagen(), "drawable", getPackageName()));
                this.botonStop.setVisibility(View.INVISIBLE);
                this.botonPlay.setVisibility(View.INVISIBLE);

                break;

            case 3:
            /*Imagen: Ocultamos la imagen y hacemos visible los botones de sonido */
                this.preguntaImagen.setVisibility(View.INVISIBLE);
                this.botonStop.setVisibility(View.VISIBLE);
                this.botonPlay.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick (View view)
    {
        Button pulsado = (Button) view;

        if(pulsado.getText().equals(pregunta.getRespuesta()))
        {
            //Acierto
            fallo.stop();
            acierto.start();
            instanciaPregunta(listaPreguntas.get(indicePregunta));
            indicePregunta++;
            puntuacion++;

        }
        else
        {
            //Fallo
            acierto.stop();
            fallo.start();
            instanciaPregunta(listaPreguntas.get(indicePregunta));
            indicePregunta++;
        }

    }
}
