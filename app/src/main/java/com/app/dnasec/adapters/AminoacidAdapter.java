package com.app.dnasec.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.dnasec.R;
import com.app.dnasec.adapters.models.CodonAminoacidPairModel;
import com.app.dnasec.adapters.models.PairModel;
import com.app.dnasec.views.CodonAminoacidPair;

import java.util.List;

public class AminoacidAdapter extends RecyclerView.Adapter<AminoacidAdapter.ViewHolder> {

    private final int ADENINE = 0;
    private final int THYMINE = 1;
    private final int GUANINE = 2;
    private final int CYTOSINE = 3;
    private final int URACIL = 4;

    private List<CodonAminoacidPairModel> items;
    private Context mContext;

    public AminoacidAdapter(List<CodonAminoacidPairModel> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AminoacidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aminoacid_codon_pair_item, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AminoacidAdapter.ViewHolder holder, int position) {

        final CodonAminoacidPairModel model = items.get(position);

        holder.divider.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        int[] types = new int[] {model.getFirstNuclName(), model.getSecondNuclName(), model.getThirdNuclName()};
        TextView[] nucleotides = new TextView[] {holder.firstNicl, holder.secondNucl, holder.thirdNucl};
        int i = 0;
        for (int type : types) {
            switch (type) {
                case ADENINE:
                    nucleotides[i].setText(R.string.adenine);
                    nucleotides[i].setBackgroundResource(R.drawable.adenine_top_background);
                    break;

                case URACIL:
                    nucleotides[i].setText(R.string.uracil);
                    nucleotides[i].setBackgroundResource(R.drawable.uracil_top_background);
                    break;

                case GUANINE:
                    nucleotides[i].setText(R.string.guanine);
                    nucleotides[i].setBackgroundResource(R.drawable.guanine_top_background);
                    break;

                case CYTOSINE:
                    nucleotides[i].setText(R.string.cytosine);
                    nucleotides[i].setBackgroundResource(R.drawable.cytosine_top_background);
                    break;
            }
            i++;
        }

        holder.aminoacid.setText(model.getAminoacid());



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CodonAminoacidPair pair;
        TextView firstNicl;
        TextView secondNucl;
        TextView thirdNucl;
        TextView aminoacid;
        View divider;
        ConstraintLayout container;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            pair = itemView.findViewById(R.id.item);
            firstNicl = pair.findViewById(R.id.top_nucleotide);
            secondNucl = pair.findViewById(R.id.top_nucleotide2);
            thirdNucl = pair.findViewById(R.id.top_nucleotide3);
            aminoacid = pair.findViewById(R.id.aminoacid);
            divider = pair.findViewById(R.id.divider);
            container = pair.findViewById(R.id.container);

        }
    }
}
