package com.tt.ttpsmrpapp.usecases.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.tt.ttpsmrpapp.data.model.NodeCentral;
import com.tt.ttpsmrpapp.usecases.home.HomeActivity;
import com.tt.ttpsmrpapp.usecases.monitoring.NodeCentralActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NodeCentralAdapter extends RecyclerView.Adapter<NodeCentralAdapter.ViewHolder> {
    private ArrayList<NodeCentral> nodeCentrals;
    private Context context;

    private static final String ID_BLUETOOTH = "ID_BLUETOOTH";
    private static final String NODE_NAME = "NODE_NAME";

    public ArrayList<NodeCentral> getNodeCentrals() {
        return nodeCentrals;
    }

    public void setNodeCentrals(ArrayList<NodeCentral> nodeCentrals) {
        this.nodeCentrals = nodeCentrals;
    }

    public NodeCentralAdapter (ArrayList<NodeCentral> nodeCentrals, Context context) {
        this.nodeCentrals = nodeCentrals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_node_central, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Set each view the node central information
        holder.getNodeCentralName().setText(nodeCentrals.get(position).getNodeName());
        holder.getNodeCentralPlant().setText(String.format("%s (%s)", nodeCentrals.get(position).getAlias(), nodeCentrals.get(position).getScientificName()));
        Picasso.get().load(nodeCentrals.get(position).getUrl()).fit().into(holder.getNodeCentralImage());
    }

    @Override
    public int getItemCount() {
        return nodeCentrals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView nodeCentralCardView;
        private ImageView nodeCentralImage;
        private TextView nodeCentralName;
        private TextView nodeCentralPlant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Find all the view on the cardView layout
            this.nodeCentralCardView = (CardView) itemView.findViewById(R.id.card_view_node_c);
            this.nodeCentralImage = (ImageView) itemView.findViewById(R.id.imageView_node_c_image);
            this.nodeCentralName = (TextView) itemView.findViewById(R.id.text_view_node_c_name);
            this.nodeCentralPlant = (TextView) itemView.findViewById(R.id.text_view_node_c_plant);

            //Setting onClickListener for cardview
            this.nodeCentralCardView.setOnClickListener(this);

        }

        public CardView getNodeCentralCardView() {
            return nodeCentralCardView;
        }

        public ImageView getNodeCentralImage() {
            return nodeCentralImage;
        }

        public TextView getNodeCentralName() {
            return nodeCentralName;
        }

        public TextView getNodeCentralPlant() {
            return nodeCentralPlant;
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, NodeCentralActivity.class);
            intent.putExtra(NODE_NAME, nodeCentrals.get(getAdapterPosition()).getNodeName());
            intent.putExtra(ID_BLUETOOTH, nodeCentrals.get(getAdapterPosition()).getIdBluetooth());
            context.startActivity(intent);
        }
    }
}
