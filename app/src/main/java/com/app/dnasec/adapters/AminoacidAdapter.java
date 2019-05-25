package com.app.dnasec.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.dnasec.R;
import com.app.dnasec.adapters.models.CodonAminoacidPairModel;
import com.app.dnasec.views.CodonAminoacidPair;
import com.tooltip.Tooltip;

import java.util.List;

public class AminoacidAdapter extends RecyclerView.Adapter<AminoacidAdapter.ViewHolder> {

    private final int ADENINE = 0;
    private final int GUANINE = 2;
    private final int CYTOSINE = 3;
    private final int URACIL = 4;

    /*private String phenylalanine;
    private String leucine;
    private String isoleucine;
    private String methionine;
    private String valine;
    private String serine;
    private String proline;
    private String threonine;
    private String tyrosine;
    private String alanine;
    private String stop;
    private String histidine;
    private String glutamine;
    private String asparagine;
    private String lysine;
    private String aspartic_acid;
    private String glutamine_acid;
    private String cysteine;
    private String tryptophan;
    private String arginine;
    private String glycine;*/

    private Resources res;

    private List<CodonAminoacidPairModel> items;
    private Context mContext;

    public AminoacidAdapter(List<CodonAminoacidPairModel> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;

        res = mContext.getResources();

        /*phenylalanine = res.getString(R.string.phenylalanine_shortened);
        leucine = res.getString(R.string.leucine_shortened);
        isoleucine = res.getString(R.string.isoleucine_shortened);
        methionine = res.getString(R.string.methionine_shortened);
        valine = res.getString(R.string.phenylalanine_shortened);
        serine = res.getString(R.string.serine_shortened);
        proline = res.getString(R.string.proline_shortened);
        threonine = res.getString(R.string.threonine_shortened);
        tyrosine = res.getString(R.string.tyrosine_shortened);
        alanine = res.getString(R.string.alanine_shortened);
        stop = res.getString(R.string.stop_shortened);
        histidine = res.getString(R.string.histidine_shortened);
        glutamine = res.getString(R.string.glutamine_shortened);
        asparagine = res.getString(R.string.asparagine_shortened);
        lysine = res.getString(R.string.lysine_shortened);
        aspartic_acid = res.getString(R.string.aspartic_acid_shortened);
        glutamine_acid = res.getString(R.string.glutamine_acid_shortened);
        cysteine = res.getString(R.string.cysteine_shortened);
        tryptophan = res.getString(R.string.tryptophan_shortened);
        arginine = res.getString(R.string.arginine_shortened);
        glycine = res.getString(R.string.glycine_shortened);*/
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
                    nucleotides[i].setBackgroundResource(R.drawable.adenine_top_background_vector);
                    break;

                case URACIL:
                    nucleotides[i].setText(R.string.uracil);
                    nucleotides[i].setBackgroundResource(R.drawable.thymine_top_background_vector);
                    break;

                case GUANINE:
                    nucleotides[i].setText(R.string.guanine);
                    nucleotides[i].setBackgroundResource(R.drawable.guanine_top_background);
                    break;

                case CYTOSINE:
                    nucleotides[i].setText(R.string.cytosine);
                    nucleotides[i].setBackgroundResource(R.drawable.cytosine_top_background_vector);
                    break;
            }
            i++;
        }

        holder.aminoacidTextView.setText(model.getAminoacid());

        holder.bind(model.getAminoacid());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CodonAminoacidPair pair;
        TextView firstNicl;
        TextView secondNucl;
        TextView thirdNucl;
        TextView aminoacidTextView;
        View divider;
        ConstraintLayout container;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            pair = itemView.findViewById(R.id.item);
            firstNicl = pair.findViewById(R.id.top_nucleotide);
            secondNucl = pair.findViewById(R.id.top_nucleotide2);
            thirdNucl = pair.findViewById(R.id.top_nucleotide3);
            aminoacidTextView = pair.findViewById(R.id.aminoacid);
            divider = pair.findViewById(R.id.divider);
            container = pair.findViewById(R.id.container);

        }

        String aminoAcid;
        void bind(String aminoAcid) {
            this.aminoAcid = aminoAcid;
        }

        @Override
        public void onClick(View v) {
            showTooltip(v, aminoAcid);
        }
    }

    private void showTooltip(View v, String aminoAcid) {

        Tooltip.Builder tooltip = new Tooltip.Builder(v)
                .setTextColor(Color.WHITE)
                .setGravity(Gravity.TOP)
                .setBackgroundColor(res.getColor(R.color.colorPrimary))
                .setCancelable(true)
                .setCornerRadius(15f);

        if (aminoAcid.equals(res.getString(R.string.phenylalanine_shortened))) {
            tooltip.setText(res.getString(R.string.phenylalanine));
        } else if (aminoAcid.equals(res.getString(R.string.leucine_shortened))) {
            tooltip.setText(res.getString(R.string.leucine));
        } else if (aminoAcid.equals(res.getString(R.string.isoleucine_shortened))) {
            tooltip.setText(res.getString(R.string.isoleucine));
        } else if (aminoAcid.equals(res.getString(R.string.methionine_shortened))) {
            tooltip.setText(res.getString(R.string.methionine));
        } else if (aminoAcid.equals(res.getString(R.string.valine_shortened))) {
            tooltip.setText(res.getString(R.string.valine));
        } else if (aminoAcid.equals(res.getString(R.string.serine_shortened))) {
            tooltip.setText(res.getString(R.string.serine));
        } else if (aminoAcid.equals(res.getString(R.string.proline_shortened))) {
            tooltip.setText(res.getString(R.string.proline));
        } else if (aminoAcid.equals(res.getString(R.string.threonine_shortened))) {
            tooltip.setText(res.getString(R.string.threonine));
        } else if (aminoAcid.equals(res.getString(R.string.tyrosine_shortened))) {
            tooltip.setText(res.getString(R.string.tyrosine));
        } else if (aminoAcid.equals(res.getString(R.string.alanine_shortened))) {
            tooltip.setText(res.getString(R.string.alanine));
        } else if (aminoAcid.equals(res.getString(R.string.stop_shortened))) {
            tooltip.setText(res.getString(R.string.stop));
        } else if (aminoAcid.equals(res.getString(R.string.histidine_shortened))) {
            tooltip.setText(res.getString(R.string.histidine));
        } else if (aminoAcid.equals(res.getString(R.string.glutamine_shortened))) {
            tooltip.setText(res.getString(R.string.glutamine));
        } else if (aminoAcid.equals(res.getString(R.string.asparagine_shortened))) {
            tooltip.setText(res.getString(R.string.asparagine));
        } else if (aminoAcid.equals(res.getString(R.string.lysine_shortened))) {
            tooltip.setText(res.getString(R.string.lysine));
        } else if (aminoAcid.equals(res.getString(R.string.aspartic_acid_shortened))) {
            tooltip.setText(res.getString(R.string.aspartic_acid));
        } else if (aminoAcid.equals(res.getString(R.string.glutamine_acid_shortened))) {
            tooltip.setText(res.getString(R.string.glutamine_acid));
        } else if (aminoAcid.equals(res.getString(R.string.cysteine_shortened))) {
            tooltip.setText(res.getString(R.string.cysteine));
        } else if (aminoAcid.equals(res.getString(R.string.tryptophan_shortened))) {
            tooltip.setText(res.getString(R.string.tryptophan));
        } else if (aminoAcid.equals(res.getString(R.string.arginine_shortened))) {
            tooltip.setText(res.getString(R.string.arginine));
        } else if (aminoAcid.equals(res.getString(R.string.glycine_shortened))) {
            tooltip.setText(res.getString(R.string.glycine));
        }

        tooltip.show();

    }
}
