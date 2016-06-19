package org.freelasearch.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DataUtils {

    public static String format(Date data) {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            DateFormat dfDia = new SimpleDateFormat("HH:mm");

            Date dataHoje = new Date();

            Calendar calData = Calendar.getInstance();
            Calendar calDataHoje = Calendar.getInstance();

            calData.setTime(data);
            calDataHoje.setTime(dataHoje);

            if (calData.get(Calendar.YEAR) != calDataHoje.get(Calendar.YEAR) || calData.get(Calendar.MONTH) != calDataHoje.get(Calendar.MONTH)) {
                return df.format(data);
            } else if (calDataHoje.get(Calendar.DAY_OF_MONTH) - 1 == calData.get(Calendar.DAY_OF_MONTH)) {
                //ONTEM
                return "ONTEM";
            } else if (calData.get(Calendar.DAY_OF_MONTH) == calDataHoje.get(Calendar.DAY_OF_MONTH)) {
                //HOJE
                return dfDia.format(data);
            } else {
                // MESMO MÊS (Dia diferente de ONTEM e/ou HOJE)
                return df.format(data);
            }
        } catch (Exception e) {
            Log.e("DataUtils.format", e.getMessage(), e);
        }
        return data.toString();
    }
}
