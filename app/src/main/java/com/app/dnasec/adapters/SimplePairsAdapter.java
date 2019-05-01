package com.app.dnasec.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.dnasec.R;
import com.app.dnasec.adapters.models.PairModel;
import com.app.dnasec.views.NucleotidesPair;

import java.util.List;

public class SimplePairsAdapter extends RecyclerView.Adapter<SimplePairsAdapter.ViewHolder> {

    private final int ADENINE = 0;
    private final int THYMINE = 1;
    private final int GUANINE = 2;
    private final int CYTOSINE = 3;
    private final int URACIL = 4;

    private List<PairModel> items;
    private Context mContext;

    private SimplePairsAdapter.ViewHolder mHolder;

    public SimplePairsAdapter(List<PairModel> dataset, Context context) {
        items = dataset;
        mContext = context;
    }

    @NonNull
    @Override
    public SimplePairsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pair_item, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SimplePairsAdapter.ViewHolder holder, int position) {

        final PairModel model = items.get(position);

        mHolder = holder;

        holder.item = model.getItem();

        holder.double_1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        holder.double_2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        holder.triple_1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        holder.triple_2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        holder.triple_3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mHolder.triple_1.setVisibility(View.VISIBLE);
        mHolder.triple_2.setVisibility(View.VISIBLE);
        mHolder.triple_3.setVisibility(View.VISIBLE);
        mHolder.double_1.setVisibility(View.VISIBLE);
        mHolder.double_2.setVisibility(View.VISIBLE);


        switch (model.getTopNucleotideName()) {
            case ADENINE:
                holder.topNucleotide.setText(mContext.getResources().getString(R.string.adenine));
                holder.topNucleotide.setBackgroundResource(R.drawable.adenine_top_background);
                hideTriples();
                break;

            case THYMINE:
                holder.topNucleotide.setText(mContext.getResources().getString(R.string.thymine));
                holder.topNucleotide.setBackgroundResource(R.drawable.thymine_top_background);
                hideTriples();
                break;

            case GUANINE:
                holder.topNucleotide.setText(mContext.getResources().getString(R.string.guanine));
                holder.topNucleotide.setBackgroundResource(R.drawable.guanine_top_background);
                hideDoubles();
                break;

            case CYTOSINE:
                holder.topNucleotide.setText(mContext.getResources().getString(R.string.cytosine));
                holder.topNucleotide.setBackgroundResource(R.drawable.cytosine_top_background);
                hideDoubles();
                break;

            case URACIL:
                holder.topNucleotide.setText(mContext.getResources().getString(R.string.uracil));
                holder.topNucleotide.setBackgroundResource(R.drawable.uracil_top_background);
                hideTriples();
        }

        switch (model.getBottomNucleotideName()) {
            case ADENINE:
                holder.bottomNucleotide.setText(mContext.getResources().getString(R.string.adenine));
                holder.bottomNucleotide.setBackgroundResource(R.drawable.adenine_bottom_background);
                break;

            case GUANINE:
                holder.bottomNucleotide.setText(mContext.getResources().getString(R.string.guanine));
                holder.bottomNucleotide.setBackgroundResource(R.drawable.guanine_bottom_background);
                break;

            case THYMINE:
                holder.bottomNucleotide.setText(R.string.thymine);
                holder.bottomNucleotide.setBackgroundResource(R.drawable.thymine_bottom_background);
                break;

            case CYTOSINE:
                holder.bottomNucleotide.setText(mContext.getResources().getString(R.string.cytosine));
                holder.bottomNucleotide.setBackgroundResource(R.drawable.cytosine_bottom_background);
                break;

            case URACIL:
                holder.bottomNucleotide.setText(mContext.getResources().getString(R.string.uracil));
                holder.bottomNucleotide.setBackgroundResource(R.drawable.uracil_bottom_background);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        NucleotidesPair item;
        TextView topNucleotide;
        TextView bottomNucleotide;
        View triple_1;
        View triple_2;
        View triple_3;
        View double_1;
        View double_2;

        ViewHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            topNucleotide = item.findViewById(R.id.top_nucleotide);
            bottomNucleotide = item.findViewById(R.id.bottom_nucleotide);
            triple_1 = item.findViewById(R.id.triple_group_1);
            triple_2 = item.findViewById(R.id.triple_group_2);
            triple_3 = item.findViewById(R.id.triple_group_3);
            double_1 = item.findViewById(R.id.double_group_1);
            double_2 = item.findViewById(R.id.double_group_2);
        }
    }

    private void hideTriples() {
        mHolder.triple_1.setVisibility(View.GONE);
        mHolder.triple_2.setVisibility(View.GONE);
        mHolder.triple_3.setVisibility(View.GONE);
    }

    private void hideDoubles() {
        mHolder.double_1.setVisibility(View.GONE);
        mHolder.double_2.setVisibility(View.GONE);
    }
}
