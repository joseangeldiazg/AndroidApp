package com.example.joseadiazg.technoquizdss;

/**
 * Created by joseadiazg on 31/1/17.
 */

import android.app.Application;

public class Puntuacion extends Application {

    private int puntuacion = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getPuntuacion()
    {

        return this.puntuacion;
    }

    void setPuntuacion(int puntuacion)
    {
        this.puntuacion = puntuacion;
    }
}
