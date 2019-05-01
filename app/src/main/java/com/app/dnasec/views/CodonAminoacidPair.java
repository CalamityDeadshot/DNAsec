package com.app.dnasec.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.app.dnasec.R;

public class CodonAminoacidPair extends ConstraintLayout {
    final int ADENINE = 0;
    final int THYMINE = 1;
    final int GUANINE = 2;
    final int CYTOSINE = 3;
    final int URACIL = 4;

    int firstNuclType;
    int secondNuclType;
    int thirdNuclType;

    TextView firstNucl;
    TextView secondNucl;
    TextView thirdNucl;

    Context mContext;

    public CodonAminoacidPair(Context context) {
        super(context);
        mContext = context;
        initView();
        firstNucl = findViewById(R.id.top_nucleotide);
        secondNucl = findViewById(R.id.top_nucleotide2);
        thirdNucl = findViewById(R.id.top_nucleotide3);
    }

    public CodonAminoacidPair(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        firstNucl = findViewById(R.id.top_nucleotide);
        secondNucl = findViewById(R.id.top_nucleotide2);
        thirdNucl = findViewById(R.id.top_nucleotide3);
    }

    private void initView() {
        inflate(getContext(), R.layout.codon_aminoacid_pair, this);
    }
}

