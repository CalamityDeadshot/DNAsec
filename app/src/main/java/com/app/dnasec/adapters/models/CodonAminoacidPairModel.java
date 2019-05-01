package com.app.dnasec.adapters.models;

import com.app.dnasec.views.CodonAminoacidPair;

public class CodonAminoacidPairModel {
    private int firstNuclName;
    private int secondNuclName;
    private int thirdNuclName;

    private String aminoacid;

    private CodonAminoacidPair item;

    public CodonAminoacidPairModel(int firstNuclName, int secondNuclName, int thirdNuclName, String aminoacid, CodonAminoacidPair item) {
        this.firstNuclName = firstNuclName;
        this.secondNuclName = secondNuclName;
        this.thirdNuclName = thirdNuclName;
        this.aminoacid = aminoacid;
        this.item = item;
    }

    public int getFirstNuclName() {
        return firstNuclName;
    }

    public int getSecondNuclName() {
        return secondNuclName;
    }

    public int getThirdNuclName() {
        return thirdNuclName;
    }

    public String getAminoacid() {
        return aminoacid;
    }

    public CodonAminoacidPair getItem() {
        return item;
    }
}
