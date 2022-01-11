package com.tt.ttpsmrpapp.usecases.monitoring.adapters;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.MeasurementV2;

import java.sql.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasurementV2Adapter  extends RecyclerView.Adapter<MeasurementV2Adapter.ViewHolder> {
    private ArrayList<MeasurementV2> measurementV2List;

    public MeasurementV2Adapter(ArrayList<MeasurementV2> measurementV2List){
        this.measurementV2List = measurementV2List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_measurement_v2_list, parent, false);

        return new MeasurementV2Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getLogTempValueTextView().setText(String.valueOf(measurementV2List.get(position).getTemperatura()));
        holder.getLogHumValueTextView().setText(String.valueOf(measurementV2List.get(position).getHumedad()));
        holder.getLogLightValueTextView().setText(String.valueOf(measurementV2List.get(position).getHumedad()));
        holder.getLogPhValueTextView().setText(String.valueOf(measurementV2List.get(position).getPh()));

        //Manage date
        Timestamp ts = new Timestamp((long)measurementV2List.get(position).getTimestamp() * 1000 );
        Log.d("TimeStamp", "" + measurementV2List.get(position).getTimestamp());
        holder.getLogDateValueTextView().setText( new Date(ts.getTime()).toString());
    }

    @Override
    public int getItemCount() {
        return measurementV2List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView logTempValueTextView;
        private TextView logHumValueTextView;
        private TextView logLightValueTextView;
        private TextView logPhValueTextView;
        private TextView logDateValueTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            logTempValueTextView = (TextView) itemView.findViewById(R.id.text_view_log_temp_value);
            logHumValueTextView = (TextView) itemView.findViewById(R.id.text_view_log_hum_value);
            logLightValueTextView = (TextView) itemView.findViewById(R.id.text_view_log_light_value);
            logPhValueTextView = (TextView) itemView.findViewById(R.id.text_view_log_ph_value);
            logDateValueTextView = (TextView) itemView.findViewById(R.id.text_view_log_date_value);
        }

        public TextView getLogTempValueTextView() {
            return logTempValueTextView;
        }

        public TextView getLogHumValueTextView() {
            return logHumValueTextView;
        }

        public TextView getLogLightValueTextView() {
            return logLightValueTextView;
        }

        public TextView getLogPhValueTextView() {
            return logPhValueTextView;
        }

        public TextView getLogDateValueTextView() {
            return logDateValueTextView;
        }
    }
}
