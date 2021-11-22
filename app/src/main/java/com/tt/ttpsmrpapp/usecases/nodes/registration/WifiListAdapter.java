package com.tt.ttpsmrpapp.usecases.nodes.registration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.usecases.nodes.registration.utils.WifiNetWorkModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder> {

    public interface WifiItemOnclickListener {
        public void wifiItemClicked(int position);
    }

    WifiItemOnclickListener listener;

    private ArrayList<WifiNetWorkModel> wifiList;



    public WifiListAdapter(ArrayList<WifiNetWorkModel> wifiList, WifiItemOnclickListener listener){
        this.listener = listener;
        this.wifiList = wifiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_list_item, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getWifiNameTextView().setText(wifiList.get(position).getWifiName());
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView wifiNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wifiNameTextView = (TextView) itemView.findViewById(R.id.text_view_wifi_name);
            itemView.setOnClickListener(this);
        }

        public TextView getWifiNameTextView(){
            return this.wifiNameTextView;
        }

        @Override
        public void onClick(View v) {
            listener.wifiItemClicked(getAdapterPosition());
        }
    }
}
