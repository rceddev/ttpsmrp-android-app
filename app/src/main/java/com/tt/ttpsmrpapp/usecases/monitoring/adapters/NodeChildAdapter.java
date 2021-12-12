package com.tt.ttpsmrpapp.usecases.monitoring.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.NodeChild;

import java.util.ArrayList;

public class NodeChildAdapter extends RecyclerView.Adapter<NodeChildAdapter.ViewHolder> {
    private ArrayList<NodeChild> nodes;
    private Context context;

    public NodeChildAdapter (ArrayList<NodeChild> nodes, Context context) {
        this.nodes = nodes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_node_child, parent, false);
        return new NodeChildAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getPlantName().setText(nodes.get(position).getAlias());
        holder.getPlantScientificName().setText(nodes.get(position).getScientificName());
        Picasso.get().load(nodes.get(position).getUrl()).fit().into(holder.getPlantImage());
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView plantName;
        private TextView plantScientificName;
        private ImageView plantImage;
        private CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.plantName = (TextView) itemView.findViewById(R.id.text_view_node_plant);
            this.plantScientificName = (TextView) itemView.findViewById(R.id.text_view_node_plant_sn);
            this.plantImage = (ImageView) itemView.findViewById(R.id.image_view_node);
            this.card = (CardView) itemView.findViewById(R.id.card_view_node);

            this.card.setOnClickListener(this);
        }

        public TextView getPlantName() {
            return plantName;
        }

        public TextView getPlantScientificName() {
            return plantScientificName;
        }

        public ImageView getPlantImage() {
            return plantImage;
        }

        @Override
        public void onClick(View view) {

        }
    }
}
