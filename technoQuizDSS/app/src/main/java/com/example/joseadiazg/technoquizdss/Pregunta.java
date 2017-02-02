package com.example.joseadiazg.technoquizdss;

import java.util.ArrayList;

/**
 * Created by joseadiazg on 31/1/17.
 */

public class Pregunta
{

    private String pregunta;
    private ArrayList<String> respuestasIncorrectas;
    private String respuesta;

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

        this.respuesta=correcta1;

        respuestasIncorrectas=new ArrayList<>();

        respuestasIncorrectas.add(0,incorrecta1);
        respuestasIncorrectas.add(1,incorrecta2);
        respuestasIncorrectas.add(2,incorrecta3);
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

    public ArrayList<String> getRespuestasIncorrectas()
    {
      return this.respuestasIncorrectas;
    }


    public String getRespuesta()
    {
        return this.respuesta;
    }
}
