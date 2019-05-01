package com.app.dnasec.adapters.models;

import com.app.dnasec.views.NucleotidesPair;

public class PairModel {
    private int topNucleotideName;
    private int bottomNucleotideName;
    private NucleotidesPair item;

    public PairModel(int topNucleotideName, int bottomNucleotideName, NucleotidesPair item) {
        this.topNucleotideName = topNucleotideName;
        this.bottomNucleotideName = bottomNucleotideName;
        this.item = item;
    }

    public int getTopNucleotideName() {
        return topNucleotideName;
    }

    public int getBottomNucleotideName() {
        return bottomNucleotideName;
    }

    public NucleotidesPair getItem() {
        return item;
    }
}
