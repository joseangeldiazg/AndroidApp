package com.example.joseadiazg.technoquizdss;

import java.util.ArrayList;

/**
 * Created by joseadiazg on 31/1/17.
 */

public class Pregunta
{

    private String pregunta;
    private ArrayList<String> respuestas;
    private int tipo;

    public Pregunta(String pregunta, String correcta1, String incorrecta1,
                    String incorrecta2, String incorrecta3, int tipo)
    {
        this.pregunta=pregunta;
        this.tipo=tipo;

        respuestas.add(0,correcta1);
        respuestas.add(1,incorrecta1);
        respuestas.add(2, incorrecta2);
        respuestas.add(3, incorrecta3);
    }

    public String getPregunta()
    {
        return this.pregunta;
    }

    public String[] getRespuestasErroneas()
    {
        String[] erroneas = new String[3];

        for(int i=1; i<=3; i++)
        {
            erroneas[i]=this.respuestas.get(i);
        }

        return erroneas;
    }

    public String getRespuesta()
    {
        return this.respuestas.get(0);
    }
}
