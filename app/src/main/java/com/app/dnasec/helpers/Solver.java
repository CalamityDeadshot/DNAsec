package com.app.dnasec.helpers;


import android.content.Context;

import com.app.dnasec.R;

import java.util.Arrays;

public class Solver {

    public static void init(Context context) {
        ADENINE = context.getResources().getString(R.string.adenine);
        GUANINE = context.getResources().getString(R.string.guanine);
        CYTOSINE = context.getResources().getString(R.string.cytosine);
        URACIL = context.getResources().getString(R.string.uracil);
        THYMINE = context.getResources().getString(R.string.thymine);

        phenylalanine = context.getResources().getString(R.string.phenylalanine_shortened);
        leucine = context.getResources().getString(R.string.leucine_shortened);
        isoleucine = context.getResources().getString(R.string.isoleucine_shortened);
        methionine = context.getResources().getString(R.string.methionine_shortened);
        valine = context.getResources().getString(R.string.valine_shortened);
        serine = context.getResources().getString(R.string.serine_shortened);
        proline = context.getResources().getString(R.string.proline_shortened);
        threonine = context.getResources().getString(R.string.threonine_shortened);
        tyrosine = context.getResources().getString(R.string.tyrosine_shortened);
        alanine = context.getResources().getString(R.string.alanine_shortened);
        stop = context.getResources().getString(R.string.stop_shortened);
        histidine = context.getResources().getString(R.string.histidine_shortened);
        glutamine = context.getResources().getString(R.string.glutamine_shortened);
        asparagine = context.getResources().getString(R.string.asparagine_shortened);
        lysine = context.getResources().getString(R.string.lysine_shortened);
        aspartic_acid = context.getResources().getString(R.string.aspartic_acid_shortened);
        glutamine_acid = context.getResources().getString(R.string.glutamine_acid_shortened);
        cysteine = context.getResources().getString(R.string.cysteine_shortened);
        tryptophan = context.getResources().getString(R.string.tryptophan_shortened);
        arginine = context.getResources().getString(R.string.arginine_shortened);
        glycine = context.getResources().getString(R.string.glycine_shortened);
    }

    static String ADENINE;
    static String GUANINE;
    static String CYTOSINE;
    static String URACIL;
    static String THYMINE;
    static String phenylalanine;
    static String leucine;
    static String isoleucine;
    static String methionine;
    static String valine;
    static String serine;
    static String proline;
    static String threonine;
    static String tyrosine;
    static String alanine;
    static String stop;
    static String histidine;
    static String glutamine;
    static String asparagine;
    static String lysine;
    static String aspartic_acid;
    static String glutamine_acid;
    static String cysteine;
    static String tryptophan;
    static String arginine;
    static String glycine;

    static public String[] solveLastCodonDNA(String sequenceString) { // Оптимизированный под последний кодон метод

        String lastCodon = sequenceString.substring(sequenceString.length() - 3); // Последний ДНК-кодон
        String[] lastCodonNucleotidesPre = lastCodon.split("");

        String[] lastCodonNucleotides = new String[lastCodonNucleotidesPre.length - 1]; // Последний ДНК-кодон по нуклеотидам
        System.arraycopy(lastCodonNucleotidesPre, 1, lastCodonNucleotides, 0, lastCodonNucleotides.length);

        System.out.println(Arrays.toString(lastCodonNucleotides));

        String[] lastMRnaNucleotides = new String[3];
        String[] lastTRnaNucleotides = new String[3];

        for (int i = 0; i < 3; i++) {
            if (lastCodonNucleotides[i].equals(ADENINE)) {
                lastMRnaNucleotides[i] = URACIL;
                lastTRnaNucleotides[i] = ADENINE;
            } else if (lastCodonNucleotides[i].equals(THYMINE)) {
                lastMRnaNucleotides[i] = ADENINE;
                lastTRnaNucleotides[i] = URACIL;
            } else if (lastCodonNucleotides[i].equals(GUANINE)) {
                lastMRnaNucleotides[i] = CYTOSINE;
                lastTRnaNucleotides[i] = GUANINE;
            } else if (lastCodonNucleotides[i].equals(CYTOSINE)) {
                lastMRnaNucleotides[i] = GUANINE;
                lastTRnaNucleotides[i] = CYTOSINE;
            }
        }

        StringBuilder lastMRNA = new StringBuilder();
        StringBuilder lastTRNA = new StringBuilder();

        for (String str : lastTRnaNucleotides) { // Перевод массива нклеотидов в строку
            lastTRNA.append(str);
        }

        for (String str : lastMRnaNucleotides) { // Перевод массива нклеотидов в строку
            lastMRNA.append(str);
        }
        lastTRNA.append("; ");

        String aminoAcid = handleAminoAcidLastCodon(lastMRNA.toString());

        return new String[] {lastMRNA.toString(), lastTRNA.toString(), aminoAcid};
    }

    public static String[] solveLastCodonMatrixDNA(String sequenceString) { // Оптимизированный под последний кодон метод

        String lastCodon = sequenceString.substring(sequenceString.length() - 3); // Последний ДНК-кодон
        String[] lastCodonNucleotidesPre = lastCodon.split("");

        String[] lastCodonNucleotides = new String[lastCodonNucleotidesPre.length - 1]; // Последний ДНК-кодон по нуклеотидам
        System.arraycopy(lastCodonNucleotidesPre, 1, lastCodonNucleotides, 0, lastCodonNucleotides.length);

        System.out.println(Arrays.toString(lastCodonNucleotides));

        String[] lastMRnaNucleotides = new String[3];
        String[] lastTRnaNucleotides = new String[3];

        for (int i = 0; i < 3; i++) {
            if (lastCodonNucleotides[i].equals(ADENINE)) {
                lastMRnaNucleotides[i] = ADENINE;
                lastTRnaNucleotides[i] = URACIL;
            } else if (lastCodonNucleotides[i].equals(THYMINE)) {
                lastMRnaNucleotides[i] = URACIL;
                lastTRnaNucleotides[i] = ADENINE;
            } else if (lastCodonNucleotides[i].equals(GUANINE)) {
                lastMRnaNucleotides[i] = GUANINE;
                lastTRnaNucleotides[i] = CYTOSINE;
            } else if (lastCodonNucleotides[i].equals(CYTOSINE)) {
                lastMRnaNucleotides[i] = CYTOSINE;
                lastTRnaNucleotides[i] = GUANINE;
            }
        }

        StringBuilder lastMRNA = new StringBuilder();
        StringBuilder lastTRNA = new StringBuilder();

        for (String str : lastTRnaNucleotides) { // Перевод массива нклеотидов в строку
            lastTRNA.append(str);
        }

        for (String str : lastMRnaNucleotides) { // Перевод массива нклеотидов в строку
            lastMRNA.append(str);
        }
        lastTRNA.append("; ");

        String aminoAcid = handleAminoAcidLastCodon(lastMRNA.toString());

        return new String[] {lastMRNA.toString(), lastTRNA.toString(), aminoAcid};
    }

    public static String[] solveLastCodonMRNA(String sequenceString) {

        String lastCodon = sequenceString.substring(sequenceString.length() - 3); // Последний иРНК-кодон
        String[] lastCodonNucleotidesPre = lastCodon.split("");

        String[] lastCodonNucleotides = new String[lastCodonNucleotidesPre.length - 1]; // Последний иРНК-кодон по нуклеотидам
        System.arraycopy(lastCodonNucleotidesPre, 1, lastCodonNucleotides, 0, lastCodonNucleotides.length);

        System.out.println(Arrays.toString(lastCodonNucleotides));

        String[] lastDnaNucleotides = new String[3];
        String[] lastTRnaNucleotides = new String[3];

        for (int i = 0; i < 3; i++) {
            if (lastCodonNucleotides[i].equals(ADENINE)) {
                lastDnaNucleotides[i] = THYMINE;
                lastTRnaNucleotides[i] = URACIL;
            } else if (lastCodonNucleotides[i].equals(URACIL)) {
                lastDnaNucleotides[i] = ADENINE;
                lastTRnaNucleotides[i] = ADENINE;
            } else if (lastCodonNucleotides[i].equals(GUANINE)) {
                lastDnaNucleotides[i] = GUANINE;
                lastTRnaNucleotides[i] = CYTOSINE;
            } else if (lastCodonNucleotides[i].equals(CYTOSINE)) {
                lastDnaNucleotides[i] = GUANINE;
                lastTRnaNucleotides[i] = GUANINE;
            }
        }

        StringBuilder lastDNA = new StringBuilder();
        StringBuilder lastTRNA = new StringBuilder();

        for (String str : lastTRnaNucleotides) { // Перевод массива нклеотидов в строку
            lastTRNA.append(str);
        }

        for (String str : lastDnaNucleotides) { // Перевод массива нклеотидов в строку
            lastDNA.append(str);
        }
        lastTRNA.append("; ");

        String aminoAcid = handleAminoAcidLastCodon(lastCodon);

        return new String[] {lastDNA.toString(), lastTRNA.toString(), aminoAcid};
    }

    public static String[] solveLastCodonTRNA(String sequenceString) {

        String lastCodon = sequenceString.substring(sequenceString.length() - 3); // Последний тРНК-кодон
        String[] lastCodonNucleotidesPre = lastCodon.split("");

        String[] lastCodonNucleotides = new String[lastCodonNucleotidesPre.length - 1]; // Последний тРНК-кодон по нуклеотидам
        System.arraycopy(lastCodonNucleotidesPre, 1, lastCodonNucleotides, 0, lastCodonNucleotides.length);

        System.out.println(Arrays.toString(lastCodonNucleotides));

        String[] lastDnaNucleotides = new String[3];
        String[] lastMRnaNucleotides = new String[3];

        for (int i = 0; i < 3; i++) {
            if (lastCodonNucleotides[i].equals(ADENINE)) {
                lastDnaNucleotides[i] = ADENINE;
                lastMRnaNucleotides[i] = URACIL;
            } else if (lastCodonNucleotides[i].equals(URACIL)) {
                lastDnaNucleotides[i] = THYMINE;
                lastMRnaNucleotides[i] = ADENINE;
            } else if (lastCodonNucleotides[i].equals(GUANINE)) {
                lastDnaNucleotides[i] = GUANINE;
                lastMRnaNucleotides[i] = CYTOSINE;
            } else if (lastCodonNucleotides[i].equals(CYTOSINE)) {
                lastDnaNucleotides[i] = CYTOSINE;
                lastMRnaNucleotides[i] = GUANINE;
            }
        }

        StringBuilder lastDNA = new StringBuilder();
        StringBuilder lastMRNA = new StringBuilder();

        for (String str : lastMRnaNucleotides) { // Перевод массива нклеотидов в строку
            lastMRNA.append(str);
        }

        for (String str : lastDnaNucleotides) { // Перевод массива нклеотидов в строку
            lastDNA.append(str);
        }


        String aminoAcid = handleAminoAcidLastCodon(lastMRNA.toString());

        return new String[] {lastDNA.toString(), lastMRNA.toString(), aminoAcid};
    }

    private static String handleAminoAcidLastCodon(String lastMRNA) {

        String aminoAcid = "";

        if ((URACIL + URACIL + URACIL).equals(lastMRNA) || (URACIL + URACIL + CYTOSINE).equals(lastMRNA)) {
            aminoAcid = phenylalanine;
        } else if ((URACIL + URACIL + ADENINE).equals(lastMRNA) || (URACIL + URACIL + GUANINE).equals(lastMRNA) || (CYTOSINE + URACIL + URACIL).equals(lastMRNA) || (CYTOSINE + URACIL + CYTOSINE).equals(lastMRNA) || (CYTOSINE + URACIL + ADENINE).equals(lastMRNA) || (CYTOSINE + URACIL + GUANINE).equals(lastMRNA)) {
            aminoAcid = leucine;
        } else if ((URACIL + CYTOSINE + URACIL).equals(lastMRNA) || (URACIL + CYTOSINE + CYTOSINE).equals(lastMRNA) || (URACIL + CYTOSINE + ADENINE).equals(lastMRNA) || (URACIL + CYTOSINE + GUANINE).equals(lastMRNA) || (ADENINE + GUANINE + URACIL).equals(lastMRNA) || (ADENINE + GUANINE + CYTOSINE).equals(lastMRNA)) {
            aminoAcid = serine;
        } else if ((URACIL + ADENINE + URACIL).equals(lastMRNA) || (URACIL + ADENINE + CYTOSINE).equals(lastMRNA)) {
            aminoAcid = tyrosine;
        } else if ((URACIL + GUANINE + URACIL).equals(lastMRNA) || (URACIL + GUANINE + CYTOSINE).equals(lastMRNA)) {
            aminoAcid = cysteine;
        } else if ((URACIL + GUANINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = tryptophan;
        } else if ((CYTOSINE + CYTOSINE + URACIL).equals(lastMRNA) || (CYTOSINE + CYTOSINE + CYTOSINE).equals(lastMRNA) || (CYTOSINE + CYTOSINE + ADENINE).equals(lastMRNA) || (CYTOSINE + CYTOSINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = proline;
        } else if ((CYTOSINE + ADENINE + URACIL).equals(lastMRNA) || (CYTOSINE + ADENINE + CYTOSINE).equals(lastMRNA)) {
            aminoAcid = histidine;
        } else if ((CYTOSINE + GUANINE + URACIL).equals(lastMRNA) || (CYTOSINE + GUANINE + CYTOSINE).equals(lastMRNA) || (CYTOSINE + GUANINE + ADENINE).equals(lastMRNA) || (CYTOSINE + GUANINE + GUANINE).equals(lastMRNA) || (ADENINE + GUANINE + ADENINE).equals(lastMRNA) || (ADENINE + GUANINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = arginine;
        } else if ((ADENINE + URACIL + URACIL).equals(lastMRNA) || (ADENINE + URACIL + CYTOSINE).equals(lastMRNA) || (ADENINE + URACIL + ADENINE).equals(lastMRNA)) {
            aminoAcid = isoleucine;
        } else if ((ADENINE + CYTOSINE + URACIL).equals(lastMRNA) || (ADENINE + CYTOSINE + CYTOSINE).equals(lastMRNA) || (ADENINE + CYTOSINE + ADENINE).equals(lastMRNA) || (ADENINE + CYTOSINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = threonine;
        } else if ((ADENINE + ADENINE + URACIL).equals(lastMRNA) || (ADENINE + ADENINE + CYTOSINE).equals(lastMRNA)) {
            aminoAcid = asparagine;
        } else if ((ADENINE + URACIL + GUANINE).equals(lastMRNA)) {
            aminoAcid = methionine;
        } else if ((ADENINE + ADENINE + ADENINE).equals(lastMRNA) || (ADENINE + ADENINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = lysine;
        } else if ((GUANINE + URACIL + URACIL).equals(lastMRNA) || (GUANINE + URACIL + CYTOSINE).equals(lastMRNA) || (GUANINE + URACIL + ADENINE).equals(lastMRNA) || (GUANINE + URACIL + GUANINE).equals(lastMRNA)) {
            aminoAcid = valine;
        } else if ((GUANINE + CYTOSINE + URACIL).equals(lastMRNA) || (GUANINE + CYTOSINE + CYTOSINE).equals(lastMRNA) || (GUANINE + CYTOSINE + ADENINE).equals(lastMRNA) || (GUANINE + CYTOSINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = alanine;
        } else if ((GUANINE + ADENINE + URACIL).equals(lastMRNA) || (GUANINE + ADENINE + CYTOSINE).equals(lastMRNA)) {
            aminoAcid = aspartic_acid;
        } else if ((GUANINE + ADENINE + ADENINE).equals(lastMRNA) || (GUANINE + ADENINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = glutamine_acid;
        } else if ((GUANINE + GUANINE + URACIL).equals(lastMRNA) || (GUANINE + GUANINE + CYTOSINE).equals(lastMRNA) || (GUANINE + GUANINE + ADENINE).equals(lastMRNA) || (GUANINE + GUANINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = glycine;
        } else if ((URACIL + ADENINE + ADENINE).equals(lastMRNA) || (URACIL + ADENINE + GUANINE).equals(lastMRNA) || (URACIL + GUANINE + ADENINE).equals(lastMRNA)) {
            aminoAcid = stop;
        } else if ((CYTOSINE + ADENINE + ADENINE).equals(lastMRNA) || (CYTOSINE + ADENINE + GUANINE).equals(lastMRNA)) {
            aminoAcid = glutamine;
        }

        return aminoAcid + "-";
    }

    public static String[] solveForMatrixDNA(String sequenceString) {

        String codon;

        String[] seqSplitted = sequenceString.split(""); // [, А, Т, Г]
        String[] seqArr = new String[sequenceString.length()];
        System.arraycopy(seqSplitted, 1, seqArr, 0, seqArr.length);

        StringBuilder seq = new StringBuilder();
        StringBuilder iRNA_codon = new StringBuilder();
        StringBuilder tRNA_codon = new StringBuilder();
        StringBuilder aminoacid = new StringBuilder();

        for (String s : seqArr) {
            seq.append(s);
        }

        String[] iRNAseqArr = new String[seqArr.length];
        String[] AAseqArr = new String[seqArr.length / 3];
        for (int i = 0; i < seq.length(); i += 3) {
            codon = seq.substring(i, i + 3);
            preSolveForMatrixDNA(i / 3, codon, iRNAseqArr, seqArr, AAseqArr);
        }


        for (String i :
                iRNAseqArr) {
            iRNA_codon.append(i);
        }

        for (int i = 1; i < seqArr.length + 1; i++) {
            tRNA_codon.append(seqArr[i - 1]);
            if (i % 3 == 0) {
                tRNA_codon.append("; ");
            }
        }

        for (String i :
                AAseqArr) {
            aminoacid.append(i).append("-");
        }
        return new String[] {iRNA_codon.toString(), tRNA_codon.toString(), aminoacid.toString()};
    }

    private static void preSolveForMatrixDNA(int iter, String codon, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split(""); // T, T, T
        String[] codonToArr = new String[codon.length()];
        System.arraycopy(codonToArrPre, 1, codonToArr, 0, codonToArr.length);

        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {

            if (codonToArr[i].equals(ADENINE)) {
                tRNAseqArr[j + i] = URACIL;
            } else if (codonToArr[i].equals(THYMINE)) {
                tRNAseqArr[j + i] = ADENINE;
            } else if (codonToArr[i].equals(GUANINE)) {
                tRNAseqArr[j + i] = CYTOSINE;
            } else if (codonToArr[i].equals(CYTOSINE)) {
                tRNAseqArr[j + i] = GUANINE;
            }

        }

        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals(THYMINE)) {
                iRNAseqArr[j + i] = URACIL;
            } else if (codonToArr[i].equals(GUANINE)) {
                iRNAseqArr[j + i] = GUANINE;
            } else if (codonToArr[i].equals(CYTOSINE)) {
                iRNAseqArr[j + i] = CYTOSINE;
            } else if (codonToArr[i].equals(ADENINE)) {
                iRNAseqArr[j + i] = ADENINE;
            }
        }
        handleAminoAcid(iRNAseqArr, AAseqArr);
    }

    public static String[] solveForDNA(String sequenceString) {
        String codon;


        String[] seqSplitted = sequenceString.split(""); // [, A, T, G]
        String[] seqArr = new String[sequenceString.length()];
        System.arraycopy(seqSplitted, 1, seqArr, 0, seqArr.length); // [A, T, G]

        StringBuilder seq = new StringBuilder();
        StringBuilder iRNA_codon = new StringBuilder();
        StringBuilder tRNA_codon = new StringBuilder();
        StringBuilder aminoacid = new StringBuilder();

        for (String s : seqArr) {
            seq.append(s);
        }

        String[] iRNAseqArr = new String[seqArr.length];
        String[] AAseqArr = new String[seqArr.length / 3];
        for (int i = 0; i < seq.length(); i += 3) {
            codon = seq.substring(i, i + 3);
            preSolveForDNA(i / 3, codon, iRNAseqArr, seqArr, AAseqArr);
        }


        for (String i :
                iRNAseqArr) {
            iRNA_codon.append(i);
        }

        for (int i = 1; i < seqArr.length + 1; i++) {
            tRNA_codon.append(seqArr[i - 1]);
            if (i % 3 == 0) {
                tRNA_codon.append("; ");
            }
        }

        for (String i :
                AAseqArr) {
            aminoacid.append(i).append("-");
        }
        return new String[] {iRNA_codon.toString(), tRNA_codon.toString(), aminoacid.toString()};
    }

    private static void preSolveForDNA(int iter, String codon, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split(""); // T, T, T
        String[] codonToArr = new String[codon.length()];
        System.arraycopy(codonToArrPre, 1, codonToArr, 0, codonToArr.length);

        int j = iter * 3;
        handleIRNA(iter, codon, iRNAseqArr, codonToArr);

        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals(THYMINE)) {
                tRNAseqArr[j + i] = URACIL;
            }
        }
        handleAminoAcid(iRNAseqArr, AAseqArr);
    }

    public static String[] solveForMRNA(String sequenceString) {
        String codon;

        String[] seqSplitted = sequenceString.split(""); // [, А, Т, Г]

        String[] seqArr = new String[sequenceString.length()];
        System.arraycopy(seqSplitted, 1, seqArr, 0, seqArr.length);

        StringBuilder seq = new StringBuilder();
        for (String s : seqArr) {
            seq.append(s);
        }

        String[] DNAseqArr = new String[seqArr.length];
        String[] tRNAseqArr = new String[seqArr.length];
        String[] AAseqArr = new String[seqArr.length / 3];
        StringBuilder DNA_codon = new StringBuilder();
        StringBuilder tRNA_codon = new StringBuilder();
        StringBuilder aminoacid = new StringBuilder();

        for (int i = 0; i < seq.length(); i += 3) {
            codon = seq.substring(i, i + 3);
            preSolveForIRNA(i / 3, codon, DNAseqArr, seqArr, tRNAseqArr, AAseqArr);
        }


        for (String i :
                DNAseqArr) {
            DNA_codon.append(i);
        }

        for (int i = 1; i < tRNAseqArr.length + 1; i++) {
            tRNA_codon.append(tRNAseqArr[i - 1]);
            if (i % 3 == 0) {
                tRNA_codon.append("; ");
            }
        }

        for (String i :
                AAseqArr) {
            aminoacid.append(i).append("-");
        }


        return new String[] {DNA_codon.toString(), tRNA_codon.toString(), aminoacid.toString()};
    }

    public static void preSolveForIRNA(int iter, String codon, String[] DNAseqArr, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split(""); // T, T, T
        String[] codonToArr = new String[codon.length()];
        System.arraycopy(codonToArrPre, 1, codonToArr, 0, codonToArr.length);
        handleDNA(iter, codon, DNAseqArr, codonToArr);

        handleTRNA(iter, codon, tRNAseqArr, codonToArr);

        handleAminoAcid(iRNAseqArr, AAseqArr);
    }

    public static String[] solveForTRNA(String sequenceString) {
        String codon;

        String[] seqSplitted = sequenceString.split(""); // [, А, Т, Г]

        String[] seqArr = new String[sequenceString.length()];
        System.arraycopy(seqSplitted, 1, seqArr, 0, seqArr.length);// [А, Т, Г]

        StringBuilder seq = new StringBuilder();
        for (String s : seqArr) {
            seq.append(s);
        }

        String[] DNAseqArr = new String[seqArr.length];
        String[] iRNAseqArr = new String[seqArr.length];
        String[] AAseqArr = new String[seqArr.length / 3];
        StringBuilder DNA_codon = new StringBuilder();
        StringBuilder iRNA_codon = new StringBuilder();
        StringBuilder aminoacid = new StringBuilder();

        for (int i = 0; i < seq.length(); i += 3) {
            codon = seq.substring(i, i + 3);
            preSolveForTRNA(i / 3, codon, DNAseqArr, iRNAseqArr, AAseqArr);
        }

        for (String i :
                DNAseqArr) {
            DNA_codon.append(i);

        }

        for (String i : iRNAseqArr) {
            iRNA_codon.append(i);
        }

        for (String i :
                AAseqArr) {
            aminoacid.append(i).append("-");
        }

        return new String[] {DNA_codon.toString(), iRNA_codon.toString(), aminoacid.toString()};
    }

    private static void preSolveForTRNA(int iter, String codon, String[] DNAseqArr, String[] iRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split("");
        String[] codonToArr = new String[codon.length()];
        System.arraycopy(codonToArrPre, 1, codonToArr, 0, codonToArr.length);
        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals(URACIL)) {
                DNAseqArr[j + i] = THYMINE;
            } else if (codonToArr[i].equals(ADENINE)) {
                DNAseqArr[j + i] = ADENINE;
            } else if (codonToArr[i].equals(CYTOSINE)) {
                DNAseqArr[j + i] = CYTOSINE;
            } else if (codonToArr[i].equals(GUANINE)) {
                DNAseqArr[j + i] = GUANINE;
            }
        }

        for (int i = 0; i < codon.length(); i++) {

            if (codonToArr[i].equals(ADENINE)) {
                iRNAseqArr[j + i] = URACIL;
            } else if (codonToArr[i].equals(URACIL)) {
                iRNAseqArr[j + i] = ADENINE;
            } else if (codonToArr[i].equals(GUANINE)) {
                iRNAseqArr[j + i] = CYTOSINE;
            } else if (codonToArr[i].equals(CYTOSINE)) {
                iRNAseqArr[j + i] = GUANINE;
            }
        }

        handleAminoAcid(iRNAseqArr, AAseqArr);
    }

    private static void handleDNA(int iter, String codon, String[] DNAseqArr, String[] codonToArr) {
        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {

            if (codonToArr[i].equals(ADENINE)) {
                DNAseqArr[j + i] = THYMINE;
            } else if (codonToArr[i].equals(URACIL)) {
                DNAseqArr[j + i] = ADENINE;
            } else if (codonToArr[i].equals(GUANINE)) {
                DNAseqArr[j + i] = CYTOSINE;
            } else if (codonToArr[i].equals(CYTOSINE)) {
                DNAseqArr[j + i] = GUANINE;
            }
        }
    }

    private static void handleIRNA(int iter, String codon, String[] iRNAseqArr, String[] codonToArr) {
        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {

            if (codonToArr[i].equals(ADENINE)) {
                iRNAseqArr[j + i] = URACIL;
            } else if (codonToArr[i].equals(THYMINE)) {
                iRNAseqArr[j + i] = ADENINE;
            } else if (codonToArr[i].equals(GUANINE)) {
                iRNAseqArr[j + i] = CYTOSINE;
            } else if (codonToArr[i].equals(CYTOSINE)) {
                iRNAseqArr[j + i] = GUANINE;
            }

        }
    }

    private static void handleTRNA(int iter, String codon, String[] tRNAseqArr, String[] codonToArr) {
        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {

            if (codonToArr[i].equals(ADENINE)) {
                tRNAseqArr[j + i] = URACIL;
            } else if (codonToArr[i].equals(URACIL)) {
                tRNAseqArr[j + i] = ADENINE;
            } else if (codonToArr[i].equals(GUANINE)) {
                tRNAseqArr[j + i] = CYTOSINE;
            } else if (codonToArr[i].equals(CYTOSINE)) {
                tRNAseqArr[j + i] = GUANINE;
            }
        }
    }

    private static void handleAminoAcid(String[] iRNAseqArr, String[] AAseqArr) {

        int c = 0;
        String aminoacid;
        for (int i = 0; i < iRNAseqArr.length - 2; i += 3, c++) {

            aminoacid = iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2];

            if ((URACIL + URACIL + URACIL).equals(aminoacid) || (URACIL + URACIL + CYTOSINE).equals(aminoacid)) {
                AAseqArr[c] = phenylalanine;
            } else if ((URACIL + URACIL + ADENINE).equals(aminoacid) || (URACIL + URACIL + GUANINE).equals(aminoacid) || (CYTOSINE + URACIL + URACIL).equals(aminoacid) || (CYTOSINE + URACIL + CYTOSINE).equals(aminoacid) || (CYTOSINE + URACIL + ADENINE).equals(aminoacid) || (CYTOSINE + URACIL + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = leucine;
            } else if ((URACIL + CYTOSINE + URACIL).equals(aminoacid) || (URACIL + CYTOSINE + CYTOSINE).equals(aminoacid) || (URACIL + CYTOSINE + ADENINE).equals(aminoacid) || (URACIL + CYTOSINE + GUANINE).equals(aminoacid) || (ADENINE + GUANINE + URACIL).equals(aminoacid) || (ADENINE + GUANINE + CYTOSINE).equals(aminoacid)) {
                AAseqArr[c] = serine;
            } else if ((URACIL + ADENINE + URACIL).equals(aminoacid) || (URACIL + ADENINE + CYTOSINE).equals(aminoacid)) {
                AAseqArr[c] = tyrosine;
            } else if ((URACIL + GUANINE + URACIL).equals(aminoacid) || (URACIL + GUANINE + CYTOSINE).equals(aminoacid)) {
                AAseqArr[c] = cysteine;
            } else if ((URACIL + GUANINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = tryptophan;
            } else if ((CYTOSINE + CYTOSINE + URACIL).equals(aminoacid) || (CYTOSINE + CYTOSINE + CYTOSINE).equals(aminoacid) || (CYTOSINE + CYTOSINE + ADENINE).equals(aminoacid) || (CYTOSINE + CYTOSINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = proline;
            } else if ((CYTOSINE + ADENINE + URACIL).equals(aminoacid) || (CYTOSINE + ADENINE + CYTOSINE).equals(aminoacid)) {
                AAseqArr[c] = histidine;
            } else if ((CYTOSINE + GUANINE + URACIL).equals(aminoacid) || (CYTOSINE + GUANINE + CYTOSINE).equals(aminoacid) || (CYTOSINE + GUANINE + ADENINE).equals(aminoacid) || (CYTOSINE + GUANINE + GUANINE).equals(aminoacid) || (ADENINE + GUANINE + ADENINE).equals(aminoacid) || (ADENINE + GUANINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = arginine;
            } else if ((ADENINE + URACIL + URACIL).equals(aminoacid) || (ADENINE + URACIL + CYTOSINE).equals(aminoacid) || (ADENINE + URACIL + ADENINE).equals(aminoacid)) {
                AAseqArr[c] = isoleucine;
            } else if ((ADENINE + CYTOSINE + URACIL).equals(aminoacid) || (ADENINE + CYTOSINE + CYTOSINE).equals(aminoacid) || (ADENINE + CYTOSINE + ADENINE).equals(aminoacid) || (ADENINE + CYTOSINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = threonine;
            } else if ((ADENINE + ADENINE + URACIL).equals(aminoacid) || (ADENINE + ADENINE + CYTOSINE).equals(aminoacid)) {
                AAseqArr[c] = asparagine;
            } else if ((ADENINE + URACIL + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = methionine;
            } else if ((ADENINE + ADENINE + ADENINE).equals(aminoacid) || (ADENINE + ADENINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = lysine;
            } else if ((GUANINE + URACIL + URACIL).equals(aminoacid) || (GUANINE + URACIL + CYTOSINE).equals(aminoacid) || (GUANINE + URACIL + ADENINE).equals(aminoacid) || (GUANINE + URACIL + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = valine;
            } else if ((GUANINE + CYTOSINE + URACIL).equals(aminoacid) || (GUANINE + CYTOSINE + CYTOSINE).equals(aminoacid) || (GUANINE + CYTOSINE + ADENINE).equals(aminoacid) || (GUANINE + CYTOSINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = alanine;
            } else if ((GUANINE + ADENINE + URACIL).equals(aminoacid) || (GUANINE + ADENINE + CYTOSINE).equals(aminoacid)) {
                AAseqArr[c] = aspartic_acid;
            } else if ((GUANINE + ADENINE + ADENINE).equals(aminoacid) || (GUANINE + ADENINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = glutamine_acid;
            } else if ((GUANINE + GUANINE + URACIL).equals(aminoacid) || (GUANINE + GUANINE + CYTOSINE).equals(aminoacid) || (GUANINE + GUANINE + ADENINE).equals(aminoacid) || (GUANINE + GUANINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = glycine;
            } else if ((URACIL + ADENINE + ADENINE).equals(aminoacid) || (URACIL + ADENINE + GUANINE).equals(aminoacid) || (URACIL + GUANINE + ADENINE).equals(aminoacid)) {
                AAseqArr[c] = stop;
            } else if ((CYTOSINE + ADENINE + ADENINE).equals(aminoacid) || (CYTOSINE + ADENINE + GUANINE).equals(aminoacid)) {
                AAseqArr[c] = glutamine;
            }
        }

    }

}
