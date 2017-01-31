package com.example.joseadiazg.technoquizdss;

import android.content.Context;
import android.database.Cursor;

public class DBPref extends DBHelper
{
    public DBPref(Context contexto)
    {
        super(contexto);
    }

    public Cursor getPreguntas(Categoria c, Dificultad d, int limit) {
        return this.db.rawQuery("SELECT `pregunta`, `respuesta_correcta`, " +
                        "`respuesta_incorrecta_1`, `respuesta_incorrecta_2`, `respuesta_incorrecta_3`, " +
                        "`tipo`, `imagen`, `sonido` FROM `preguntas` WHERE categoria = ? AND dificultad = ? " +
                        "ORDER BY RANDOM() LIMIT ?",
                new String[]{String.valueOf(c.C), String.valueOf(d.D), String.valueOf(limit)});
    }

    public static enum Categoria
    {
        HISTORIA('H'),
        MUSICA('C'),
        ARTISTAS('A');
        public final char C;

        Categoria(char c)
        {
            this.C = c;
        }
    }

    public static enum Dificultad
    {
        FACIL('F'),
        DIFICIL('D');

        public final char D;

        Dificultad(char d) {
            this.D = d;
        }
    }

}