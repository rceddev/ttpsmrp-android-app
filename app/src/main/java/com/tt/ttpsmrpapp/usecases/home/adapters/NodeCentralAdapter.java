package com.tt.ttpsmrpapp.usecases.home.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tt.ttpsmrpapp.R;
import com.tt.ttpsmrpapp.data.model.NodeCentral;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NodeCentralAdapter extends RecyclerView.Adapter<NodeCentralAdapter.ViewHolder> {
    private ArrayList<NodeCentral> nodeCentrals;

    public ArrayList<NodeCentral> getNodeCentrals() {
        return nodeCentrals;
    }

    public void setNodeCentrals(ArrayList<NodeCentral> nodeCentrals) {
        this.nodeCentrals = nodeCentrals;
    }

    public NodeCentralAdapter (ArrayList<NodeCentral> nodeCentrals) {
        this.nodeCentrals = nodeCentrals;
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

        }
    }
}
