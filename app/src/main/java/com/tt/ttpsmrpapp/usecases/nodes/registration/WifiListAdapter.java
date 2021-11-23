package com.tt.ttpsmrpapp.usecases.nodes.registration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.AccessPointBean;
import com.tt.ttpsmrpapp.usecases.nodes.registration.utils.WifiNetWorkModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WifiListAdapter extends ListAdapter<WifiNetWorkModel, WifiListAdapter.ViewHolder> {

    public WifiListAdapter(@NonNull DiffUtil.ItemCallback<WifiNetWorkModel> diffCallback
            , WifiItemOnclickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    public interface WifiItemOnclickListener {
        void wifiItemClicked(WifiNetWorkModel position);
    }

    WifiItemOnclickListener listener;

    /*public WifiListAdapter(ArrayList<WifiNetWorkModel> wifiList, WifiItemOnclickListener listener){
        this.listener = listener;
        this.wifiList = wifiList;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_list_item, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getWifiNameTextView().setText(getItem(position).getWifiName());
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
            int position = getLayoutPosition();
            listener.wifiItemClicked(getItem(position));
        }
    }

    public static class WifiListDiff extends DiffUtil.ItemCallback<WifiNetWorkModel> {

        @Override
        public boolean areItemsTheSame(@NonNull WifiNetWorkModel oldItem, @NonNull WifiNetWorkModel newItem) {
            return oldItem.getWifiName().equals(newItem.getWifiName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull WifiNetWorkModel oldItem, @NonNull WifiNetWorkModel newItem) {
            return oldItem.equals(newItem);
        }
    }
}
