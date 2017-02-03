package com.example.joseadiazg.technoquizdss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.Entry;


import java.util.ArrayList;

public class ActivityEstadisticas extends AppCompatActivity {

    private Utilidad utilidad;

    private PieChart pieChart;

    private int aciertos;

    private int fallos;

    private FrameLayout layout;

    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        this.utilidad = (Utilidad) getApplicationContext();

        this.texto = (TextView) findViewById(R.id.textView);
        this.layout = (FrameLayout) findViewById(R.id.layout_estadisticas);

        texto.setText(R.string.estadisticasGana);

        this.aciertos= utilidad.getPuntuacion();
        this.fallos=5-aciertos;

        if((this.aciertos>=this.fallos)&&(this.utilidad.getJuegoTerminado()))
        {
            texto.setText(R.string.estadisticasGana);
            this.layout.setVisibility(View.VISIBLE);
        }
        else if((this.fallos>this.aciertos)&&(this.utilidad.getJuegoTerminado()))
        {
            texto.setText(R.string.estadisticasPierde);
            this.layout.setVisibility(View.VISIBLE);
        }
        else
        {
            texto.setText(R.string.estadisticasNoJugado);
            this.layout.setVisibility(View.GONE);
        }


        pieChart = (PieChart) findViewById(R.id.pieChart);

        /*definimos algunos atributos*/
        pieChart.setHoleRadius(40f);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);

		/*creamos una lista para los valores Y*/
        ArrayList<Entry> valsY = new ArrayList<Entry>();
        valsY.add(new Entry(this.aciertos* 100 / 5,0));
        valsY.add(new Entry(this.fallos*100 / 5,0));

 		/*creamos una lista para los valores X*/
        ArrayList<String> valsX = new ArrayList<String>();
        valsX.add("Acierto");
        valsX.add("Fallo");

 		/*creamos una lista de colores*/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.background_material_dark));
        colors.add(getResources().getColor(R.color.accent_material_light));

 		/*seteamos los valores de Y y los colores*/
        PieDataSet set1 = new PieDataSet(valsY, "Resultados");
        set1.setSliceSpace(3f);
        set1.setColors(colors);

		/*seteamos los valores de X*/
        PieData data = new PieData(valsX, set1);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        /*Ocutar descripcion*/
        pieChart.setDescription("");
        /*Ocultar leyenda*/
        pieChart.getLegend().setEnabled(false);

    }
}
