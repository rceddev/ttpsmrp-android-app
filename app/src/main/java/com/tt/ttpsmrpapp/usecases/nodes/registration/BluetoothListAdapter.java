package com.tt.ttpsmrpapp.usecases.nodes.registration;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tt.ttpsmrpapp.R;

import java.util.ArrayList;

public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothListAdapter.ViewHolder> {

    public interface BluetoothItemOnclickListener {
        public void bluetoothItemClicked(int position);
    }

    BluetoothItemOnclickListener listener;
    private ArrayList<BluetoothDevice> bluetoothDeviceList;

    public BluetoothListAdapter(ArrayList<BluetoothDevice> btDeviceList, BluetoothItemOnclickListener listener){
        this.bluetoothDeviceList = btDeviceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BluetoothListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bluetooth_device_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothListAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(String.format("%s (%s)",
                bluetoothDeviceList.get(position).getName(),
                bluetoothDeviceList.get(position).getAddress()));
    }

    @Override
    public int getItemCount() {
        return bluetoothDeviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView bluetoothNameItemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bluetoothNameItemTextView = (TextView) itemView.findViewById(R.id.bluetooth_device_textview);
            itemView.setOnClickListener(this);
        }

        public TextView getTextView(){
            return bluetoothNameItemTextView;
        }

        @Override
        public void onClick(View v) {
            listener.bluetoothItemClicked(getAdapterPosition());
        }
    }
}
