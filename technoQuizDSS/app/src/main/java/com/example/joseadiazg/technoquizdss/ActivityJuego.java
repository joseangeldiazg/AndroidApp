package com.example.joseadiazg.technoquizdss;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by joseadiazg on 31/1/17.
 */

public class ActivityJuego extends Activity
{

    private DBPref db;

    private Pregunta pregunta;

    private ArrayList<Pregunta>  listaPreguntas = new ArrayList<>();

    private final static int PREGUNTAS=2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas);

        db = new DBPref(this);

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
