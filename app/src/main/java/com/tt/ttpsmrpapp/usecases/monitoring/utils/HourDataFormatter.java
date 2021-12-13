package com.tt.ttpsmrpapp.usecases.monitoring.utils;

import android.os.health.TimerStat;
import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.tt.ttpsmrpapp.data.model.MeasurementV2;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class HourDataFormatter extends ValueFormatter {

    private List<MeasurementV2> items;

    public HourDataFormatter(List<MeasurementV2> items){
        this.items = items;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Log.d("Value" , "" + value + " timeSizw" + items.size());
        int index = (int) value;

        String valueFormatter;

        if ( (value>(float)index) && (value<(float)index+1) ) {
            valueFormatter = "";
        }else {
            Timestamp ts = new Timestamp((long)items.get(index).getTimestamp() * 1000 );
            Date date = new Date(ts.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            valueFormatter = simpleDateFormat.format(date);
        }

        return valueFormatter;
    }
}
