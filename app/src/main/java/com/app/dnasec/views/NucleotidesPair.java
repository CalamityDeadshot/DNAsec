package com.app.dnasec.views;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.app.dnasec.R;

public class NucleotidesPair extends ConstraintLayout {

    final int ADENINE = 0;
    final int THYMINE = 1;
    final int GUANINE = 2;
    final int CYTOSINE = 3;
    final int URACIL = 4;

    int topNucleotideName;
    int bottomNucleotideName;

    TextView topNucleotide;
    TextView bottomNucleotide;

    Context mContext;

    public NucleotidesPair(Context context) {
        super(context);
        mContext = context;
        initView();
        topNucleotide = findViewById(R.id.top_nucleotide);
        bottomNucleotide = findViewById(R.id.bottom_nucleotide);
    }

    public NucleotidesPair(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        topNucleotide = findViewById(R.id.top_nucleotide);
        bottomNucleotide = findViewById(R.id.bottom_nucleotide);
    }

    private void initView() {
        inflate(getContext(), R.layout.nucleotides_pair, this);
    }
}
