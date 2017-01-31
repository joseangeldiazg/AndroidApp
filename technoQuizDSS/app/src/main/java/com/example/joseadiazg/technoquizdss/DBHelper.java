package com.example.joseadiazg.technoquizdss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Scanner;


public class DBHelper extends SQLiteOpenHelper
{
    public static final String NOMBRE_BD = "TechnoQuiz";
    public static final int VERSION_ACTUAL_BD = 1;
    protected SQLiteDatabase db;
    protected Context ctx;


    public DBHelper(Context contexto)
    {
        super(contexto, NOMBRE_BD, null, VERSION_ACTUAL_BD);
        this.ctx = contexto;
        this.open();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        Log.d("debug", "Creando la base de datos...");

        StringBuilder sb = new StringBuilder();

        Scanner sc = new Scanner(this.ctx.getResources().openRawResource(R.raw.database));

        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
            sb.append("\n");

            if (sb.indexOf(";") != -1) {
                sqLiteDatabase.execSQL(sb.toString());
                sb.delete(0, sb.capacity());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        Log.d("debug", "Actualizando la base de datos desde la version " + i + " a la version " + i2);
    }

    public void open() {
        Log.d("debug", "Abriendo la base de datos...");
        this.db = this.getWritableDatabase();
    }

    public void close() {
        Log.d("debug", "Cerrando la base de datos...");
        this.db.close();
    }
}
