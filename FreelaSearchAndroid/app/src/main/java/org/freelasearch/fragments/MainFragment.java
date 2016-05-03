package org.freelasearch.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.freelasearch.R;
import org.freelasearch.activities.PerfisActivity;
import org.freelasearch.utils.IntegerFormatter;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private static final String PREF_NAME = "SignupActivityPreferences";

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        PieChart pieChart = (PieChart) view.findViewById(R.id.piechart_freelasearch);

        // creating data values
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(29, 0)); // Freelancers
        entries.add(new Entry(33, 1)); // Anunciantes
        entries.add(new Entry(12, 2)); // Anúncios
        entries.add(new Entry(20, 3)); // Inscrições
        entries.add(new Entry(7, 4)); // Contratações

        // creating labels
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Freelacers");
        labels.add("Anunciantes");
        labels.add("Anúncios");
        labels.add("Inscrições");
        labels.add("Contratações");

        PieDataSet pieDataSet = new PieDataSet(entries, null);
        PieData data = new PieData(labels, pieDataSet); // initialize Piedata
        data.setValueFormatter(new IntegerFormatter());

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(15f);

        pieChart.setData(data);
        pieChart.setDescription("");
        pieChart.getLegend().setEnabled(false);
        pieChart.animateY(1300);
        pieChart.setHoleColor(Color.TRANSPARENT);


        // Dependendo do perfil o item exibido é diferente
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (sharedpreferences.getString("perfil", "").equals("")) {
            Intent intent = new Intent(getActivity(), PerfisActivity.class);
            startActivity(intent);
            getActivity().finish();
            return view;
        } else if (sharedpreferences.getString("perfil", "").equals("anunciante")) {
            view.findViewById(R.id.txt_freelancer).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.txt_vaga).setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return view;
    }

}
