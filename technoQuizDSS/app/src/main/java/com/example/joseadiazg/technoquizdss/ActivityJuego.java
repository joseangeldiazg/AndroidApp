package com.example.joseadiazg.technoquizdss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by joseadiazg on 31/1/17.
 */

public class ActivityJuego extends AppCompatActivity implements View.OnClickListener
{
    private final static int PREGUNTAS=5;

    private int indicePregunta;

    private boolean haSonado;

    private DBPref db;

    private Pregunta pregunta;

    private ArrayList<Pregunta>  listaPreguntas = new ArrayList<>();

    private Utilidad utilidad;

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

    int puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas);


        this.utilidad = (Utilidad) this.getApplicationContext();


        //Instanciamos los atributos

        this.db = new DBPref(this);

        this.haSonado=false;
        this.puntos=0;
        this.indicePregunta=0;

        this.preguntaView = (TextView) findViewById(R.id.preguntaView);

        this.respuesta1 = (Button) findViewById(R.id.respuesta1);
        this.respuesta2 = (Button) findViewById(R.id.respuesta2);
        this.respuesta3 = (Button) findViewById(R.id.respuesta3);
        this.respuesta4 = (Button) findViewById(R.id.respuesta4);

        this.preguntaImagen = (ImageView) findViewById(R.id.preguntaImagen);

        this.acierto = new MediaPlayer();
        this.fallo = new MediaPlayer();

        this.acierto = MediaPlayer.create(this, R.raw.acierto);
        this.fallo = MediaPlayer.create(this, R.raw.fallo);

        this.botonPlay = (ImageButton) findViewById(R.id.boton_play);
        this.botonStop = (ImageButton) findViewById(R.id.boton_pause);

        //AQUI CUANDO CREAMOS ESTE CURSOR DEBEREMOS AÑADIR ALGUNA MANERA PARA ELEGIR LA CATEGORIA O LA DIFICULTAD -> OPCIONAL

        Cursor cuestiones = this.db.getPreguntas(this.utilidad.getCategoria(), DBPref.Dificultad.FACIL, PREGUNTAS);

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


        int tipo =listaPreguntas.get(indicePregunta).getTipo();
        do{
            Collections.shuffle(listaPreguntas);
            tipo = listaPreguntas.get(indicePregunta).getTipo();
        } while (tipo!=1);

        instanciaPregunta(listaPreguntas.get(indicePregunta));
        indicePregunta++;

        respuesta1.setOnClickListener(this);
        respuesta2.setOnClickListener(this);
        respuesta3.setOnClickListener(this);
        respuesta4.setOnClickListener(this);

    }

    public void instanciaPregunta(Pregunta pregunta)
    {

        ArrayList<String> respuestas = new ArrayList<>();

        //Añadimos la pregunta

        this.preguntaView.setText(pregunta.getPregunta());

        // Si ha habido sonido lo paramos

        if(haSonado==true)
        {
            this.preguntaSonido.stop();
        }

        //Solución del problema que impide mostrar imagén al iniciar la actividad

        //Añadimos las respuestas en un Array para hacer un shuffle y que vayan cambiando de orden

        respuestas.add((pregunta.getRespuestasIncorrectas().get(0)));
        respuestas.add((pregunta.getRespuestasIncorrectas().get(1)));
        respuestas.add((pregunta.getRespuestasIncorrectas().get(2)));
        respuestas.add(pregunta.getRespuesta());

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
                this.preguntaImagen.setVisibility(View.GONE);
                this.preguntaImagen.setBackgroundResource(0);
                this.botonStop.setVisibility(View.GONE);
                this.botonPlay.setVisibility(View.GONE);
                this.haSonado=false;
                break;

            case 2:
            /*Imagen: Ocultamos los botones de sonido y hacemos visible la imagen */
                this.preguntaImagen.setVisibility(View.VISIBLE);
                this.preguntaImagen.setBackgroundResource(getResources().getIdentifier(this.pregunta.getImagen(), "drawable", getPackageName()));
                this.botonStop.setVisibility(View.GONE);
                this.botonPlay.setVisibility(View.GONE);
                this.haSonado=false;
                break;

            case 3:
            /*Imagen: Ocultamos la imagen y hacemos visible los botones de sonido */
                this.preguntaSonido=MediaPlayer.create(this, getResources().getIdentifier(this.pregunta.getSonido(), "raw", getPackageName()));
                this.preguntaImagen.setVisibility(View.GONE);
                this.preguntaImagen.setBackgroundResource(0);
                this.botonStop.setVisibility(View.VISIBLE);
                this.botonPlay.setVisibility(View.VISIBLE);
                this.haSonado=true;
                break;
        }
    }

    @Override
    public void onClick (View view)
    {
        Button pulsado = (Button) view;

        if(pulsado.getText().toString().equals(pregunta.getRespuesta()))
        {
            //acierto
            acierto.start();

            //Comprobamos que no sea la última pregunta
            if(indicePregunta<listaPreguntas.size())
            {
                this.pregunta=listaPreguntas.get(indicePregunta);
                Toast.makeText(this, R.string.acierto, Toast.LENGTH_SHORT).show();
                indicePregunta++;
                this.puntos++;
                instanciaPregunta(this.pregunta);
            }
            else
            {
                Toast.makeText(this, R.string.finjuego, Toast.LENGTH_LONG).show();
                this.puntos++;
                this.utilidad.setPuntuacion(puntos);
                this.utilidad.setJuegoTerminado(true);
                if(haSonado){this.preguntaSonido.stop();}
                this.startActivity(new Intent(ActivityJuego.this, ActivityEstadisticas.class));
            }
        }
        else
        {
            //fallo
            fallo.start();

            AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
            alert
                    .setMessage("¿Quieres continuar o vuelves al inicio de la cola?")
                    .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            if(indicePregunta<listaPreguntas.size())
                            {
                                pregunta=listaPreguntas.get(indicePregunta);
                                instanciaPregunta(pregunta);
                                indicePregunta++;
                            }
                            else
                            {
                                utilidad.setPuntuacion(puntos);
                                utilidad.setJuegoTerminado(true);
                                if(haSonado){preguntaSonido.stop();}
                                startActivity(new Intent(ActivityJuego.this, ActivityEstadisticas.class));
                            }
                        }
                    })
                    .setNegativeButton("Volver a la cola", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            finish();
                        }
                    })
                    .setTitle(R.string.sven)
                    .setIcon(R.drawable.sven)
                    .show();
        }
    }
    public void play(View view)
    {
        this.preguntaSonido.reset();
        this.preguntaSonido=MediaPlayer.create(this, getResources().getIdentifier(this.pregunta.getSonido(), "raw", getPackageName()));
        this.preguntaSonido.start();
    }
    public void stop(View view)
    {
        this.preguntaSonido.stop();
    }
}
