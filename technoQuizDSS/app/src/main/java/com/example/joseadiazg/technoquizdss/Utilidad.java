package com.example.joseadiazg.technoquizdss;

/**
 * Created by joseadiazg on 31/1/17.
 */

import android.app.Application;
import android.content.res.Configuration;

public class Utilidad extends Application {

    private int puntuacion = 0;

    private boolean juegoTerminado=false;

    private DBPref.Categoria categoria = DBPref.Categoria.HISTORIA;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    public int getPuntuacion()
    {

        return this.puntuacion;
    }

    void setPuntuacion(int puntuacion)
    {
        this.puntuacion = puntuacion;
    }

    public boolean getJuegoTerminado()
    {
        return this.juegoTerminado;
    }

    public void setJuegoTerminado(boolean cambio)
    {
        this.juegoTerminado=cambio;
    }

    public void setCategoria(DBPref.Categoria categoria)
    {
        this.categoria=categoria;
    }

    public DBPref.Categoria getCategoria()
    {
        return this.categoria;
    }
}
