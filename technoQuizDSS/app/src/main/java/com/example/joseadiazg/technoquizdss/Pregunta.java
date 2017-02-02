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
    private String imagen;
    private String sonido;


    public Pregunta(String pregunta, String correcta1, String incorrecta1,
                    String incorrecta2, String incorrecta3, int tipo,
                    String imagen, String sonido)
    {
        this.pregunta=pregunta;

        this.tipo=tipo;
        this.imagen=imagen;
        this.sonido=sonido;

        respuestas=new ArrayList<>();

        respuestas.add(0,correcta1);
        respuestas.add(1,incorrecta1);
        respuestas.add(2, incorrecta2);
        respuestas.add(3, incorrecta3);
    }

    public String getImagen()
    {
      return imagen;
    }

    public  String getSonido()
    {
        return sonido;
    }

    public int getTipo()
    {
        return tipo;
    }

    public String getPregunta()
    {
        return this.pregunta;
    }

    public ArrayList<String> getRespuestas()
    {
      return this.respuestas;
    }

    public ArrayList<String> getRespuestasErroneas()
    {
        ArrayList<String> erroneas = new ArrayList();

        for(int i=1; i<=3; i++)
        {
            erroneas.add(respuestas.get(i));
        }

        return erroneas;
    }

    public String getRespuesta()
    {
        return this.respuestas.get(0);
    }
}
