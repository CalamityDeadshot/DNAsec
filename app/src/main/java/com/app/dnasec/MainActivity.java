package com.app.dnasec;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView sequence;
    TextView beforeEnteringText;
    TextView firstResultHeading;
    TextView firstResult;
    TextView secondResultHeading;
    TextView secondResult;
    TextView thirdResultHeading;
    TextView thirdResult;
    Spinner spinner;
    Button explanation;

    boolean spinnerInteractionIsAllowed;

    final int ID_DNA = 0;
    final int ID_mRNA = 1;
    final int ID_tRNA = 2;

    String ADENINE;
    String GUANINE;
    String CYTOSINE;
    String URACIL;
    String THYMINE;

    String phenylalanine;
    String leucine;
    String isoleucine;
    String methionine;
    String valine;
    String serine;
    String proline;
    String threonine;
    String tyrosine;
    String alanine;
    String stop;
    String histidine;
    String glutamine;
    String asparagine;
    String lysine;
    String aspartic_acid;
    String glutamine_acid;
    String cysteine;
    String tryptophan;
    String arginine;
    String glycine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ADENINE = getResources().getString(R.string.adenine);
        GUANINE = getResources().getString(R.string.guanine);
        CYTOSINE = getResources().getString(R.string.cytosine);
        URACIL = getResources().getString(R.string.uracil);
        THYMINE = getResources().getString(R.string.thymine);

        phenylalanine = getResources().getString(R.string.phenylalanine);
        leucine = getResources().getString(R.string.leucine);
        isoleucine = getResources().getString(R.string.isoleucine);
        methionine = getResources().getString(R.string.methionine);
        valine = getResources().getString(R.string.phenylalanine);
        serine = getResources().getString(R.string.serine);
        proline = getResources().getString(R.string.proline);
        threonine = getResources().getString(R.string.threonine);
        tyrosine = getResources().getString(R.string.tyrosine);
        alanine = getResources().getString(R.string.alanine);
        stop = getResources().getString(R.string.stop);
        histidine = getResources().getString(R.string.histidine);
        glutamine = getResources().getString(R.string.glutamine);
        asparagine = getResources().getString(R.string.asparagine);
        lysine = getResources().getString(R.string.lysine);
        aspartic_acid = getResources().getString(R.string.aspartic_acid);
        glutamine_acid = getResources().getString(R.string.glutamine_acid);
        cysteine = getResources().getString(R.string.cysteine);
        tryptophan = getResources().getString(R.string.tryptophan);
        arginine = getResources().getString(R.string.arginine);
        glycine = getResources().getString(R.string.glycine);

        spinnerInteractionIsAllowed = false;

        sequence = findViewById(R.id.sequence);
        beforeEnteringText = findViewById(R.id.beforeEnteringText);
        firstResultHeading = findViewById(R.id.result1);
        firstResult = findViewById(R.id.resultText1);
        secondResultHeading = findViewById(R.id.result2);
        secondResult = findViewById(R.id.resultText2);
        thirdResultHeading = findViewById(R.id.result3);
        thirdResult = findViewById(R.id.resultText3);
        spinner = findViewById(R.id.spinner);
        explanation = findViewById(R.id.explanation);

        findViewById(R.id.A).setOnClickListener(this);
        findViewById(R.id.T).setOnClickListener(this);
        findViewById(R.id.G).setOnClickListener(this);
        findViewById(R.id.C).setOnClickListener(this);
        findViewById(R.id.clear_button).setOnClickListener(this);
        findViewById(R.id.erase_button).setOnClickListener(this);
        explanation.setOnClickListener(this);
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    spinnerInteractionIsAllowed = true;
                    spinner.performClick();
                }
                return false;
            }
        });
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sequence", sequence.getText().toString());
        savedInstanceState.putString("beforeEnteringText", beforeEnteringText.getText().toString());
        savedInstanceState.putString("firstResultHeading", beforeEnteringText.getText().toString());
        savedInstanceState.putString("firstResult", firstResult.getText().toString());
        savedInstanceState.putString("secondResultHeading", secondResultHeading.getText().toString());
        savedInstanceState.putString("secondResult", secondResult.getText().toString());
        savedInstanceState.putString("thirdResultHeading", thirdResultHeading.getText().toString());
        savedInstanceState.putString("thirdResult", thirdResult.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sequence.setText(savedInstanceState.getString("sequence"));
        beforeEnteringText.setText(savedInstanceState.getString("beforeEnteringText"));
        firstResultHeading.setText(savedInstanceState.getString("firstResultHeading"));
        firstResult.setText(savedInstanceState.getString("firstResult"));
        secondResultHeading.setText(savedInstanceState.getString("secondResultHeading"));
        secondResult.setText(savedInstanceState.getString("secondResult"));
        thirdResultHeading.setText(savedInstanceState.getString("thirdResultHeading"));
        thirdResult.setText(savedInstanceState.getString("thirdResult"));
        color(sequence);
        color(firstResult);
        if (spinner.getSelectedItem().toString().equals("тРНК")) {
            color(secondResult);
        }
        if (sequence.getText().length() % 3 == 0) {
            explanation.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.A:
                sequence.append(getResources().getString(R.string.adenine));

                break;

            case R.id.T:
                sequence.append(((Button) findViewById(R.id.T)).getText().toString());
                break;

            case R.id.G:
                sequence.append(getResources().getString(R.string.guanine));
                break;

            case R.id.C:
                sequence.append(getResources().getString(R.string.cytosine));
                break;

            case R.id.clear_button:
                clearFields();
                break;
            case R.id.erase_button:
                if (sequence.getText().toString().length() > 0) {
                    sequence.setText(sequence.getText().toString().substring(0, sequence.getText().toString().length() - 1));
                    color(sequence);
                    color(firstResult);
                }
                if (sequence.getText().length() == 0) {
                    clearFields();
                }
                if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                    color(firstResult);
                    beforeEnteringText.setText("");
                    explanation.setEnabled(true);

                } else if (sequence.getText().length() % 3 != 0){
                    beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                    explanation.setEnabled(false);
                    color(firstResult);
                }
                if (sequence.getText().length() < 3) {
                    firstResult.setText("");
                    secondResult.setText("");
                    thirdResult.setText("");
                }

                switch ((int)spinner.getSelectedItemId()) {
                    case ID_DNA:
                        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                            String[] solvedArr = solveForDNA();

                            firstResult.setText("-" + solvedArr[0] + "-");
                            color(firstResult);
                            secondResult.setText(solvedArr[1]);
                            thirdResult.setText(solvedArr[2]);
                        } else if (sequence.getText().length() % 2 != 0 && sequence.getText().length() % 3 != 0) {
                            try {
                                firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().length() - 4) + "-");
                                secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().length() - 5));
                                thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().length() - 4));
                            } catch (StringIndexOutOfBoundsException e) {
                                firstResult.setText("");
                            }
                        }
                        break;

                    case ID_mRNA:
                        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                            String[] solvedArr = solveForIRNA();
                            firstResult.setText("-" + solvedArr[0] + "-");
                            color(firstResult);
                            secondResult.setText(solvedArr[1]);
                            thirdResult.setText(solvedArr[2]);
                        } else if (sequence.getText().toString().length() % 2 != 0 && sequence.getText().toString().length() % 3 != 0) {
                            try {
                                firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().length() - 4) + "-");
                                secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().length() - 5));
                                thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().length() - 4));
                            } catch (StringIndexOutOfBoundsException e) {
                                firstResult.setText("");
                            }
                        }
                        break;

                    case ID_tRNA:
                        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                            String[] solvedArr = solveForTRNA();
                            firstResult.setText("-" + solvedArr[0] + "-");
                            color(firstResult);
                            secondResult.setText("-" + solvedArr[1] + "-");
                            color(secondResult);
                            thirdResult.setText(solvedArr[2]);
                        } else if (sequence.getText().length() % 2 != 0 && sequence.getText().length() % 3 != 0) {
                            try {
                                firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().length() - 4) + "-");
                                secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().length() - 4) + "-");
                                thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().length() - 4));
                            } catch (StringIndexOutOfBoundsException e) {
//                                firstResult.setText("-");
                            }
                        }
                        break;

                }
                break;

            case R.id.explanation:
                Intent openExplanationActivity = new Intent(this, ExplanationActivity.class);

                openExplanationActivity.putExtra("sequence_type", spinner.getSelectedItemId());
                openExplanationActivity.putExtra("sequence", sequence.getText().toString());
                openExplanationActivity.putExtra("firstResult", firstResult.getText().toString().substring(1, firstResult.getText().length() - 1));
                if (spinner.getSelectedItemId() == 2) {
                    openExplanationActivity.putExtra("secondResult", secondResult.getText().toString().substring(1, secondResult.getText().length() - 1));
                }  else {
                    openExplanationActivity.putExtra("secondResult", secondResult.getText().toString());
                }
                openExplanationActivity.putExtra("thirdResult", thirdResult.getText().toString());

                startActivity(openExplanationActivity);

                break;
        }
        if (v.getId() == R.id.A || v.getId() == R.id.T || v.getId() == R.id.G || v.getId() == R.id.C) {
            color(sequence);
            color(firstResult);
            beforeEnteringText.setText("");
            switch ((int)spinner.getSelectedItemId()) {
                case ID_DNA:
                    if (sequence.getText().toString().length() % 3 == 0) {
                        color(sequence);

                        String[] solvedArr = solveForDNA();

                        firstResult.setText("-" + solvedArr[0] + "-");
                        color(firstResult);
                        secondResult.setText(solvedArr[1]);
                        thirdResult.setText(solvedArr[2]);

                        explanation.setEnabled(true);


                    } else {
                        beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                        explanation.setEnabled(false);
                    }
                    break;

                case ID_mRNA:
                    if (sequence.getText().toString().length() % 3 == 0) {
                        color(sequence);

                        String[] solvedArr = solveForIRNA();
                        firstResult.setText("-" + solvedArr[0] + "-");
                        color(firstResult);
                        secondResult.setText(solvedArr[1]);
                        thirdResult.setText(solvedArr[2]);
                        explanation.setEnabled(true);

                    } else {
                        beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                        explanation.setEnabled(false);
                    }
                    break;
                case ID_tRNA:
                    if (sequence.getText().length() % 3 == 0) {
                        color(sequence);

                        String[] solvedArr = solveForTRNA();
                        firstResult.setText("-" + solvedArr[0] + "-");
                        color(firstResult);
                        secondResult.setText("-" + solvedArr[1] + "-");
                        color(secondResult);
                        thirdResult.setText(solvedArr[2]);
                        explanation.setEnabled(true);

                    } else {
                        beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                        explanation.setEnabled(false);
                    }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch ((int)parent.getSelectedItemId()) {
            case ID_DNA:
                firstResultHeading.setText(getResources().getString(R.string.iRNA));
                secondResultHeading.setText(getResources().getString(R.string.tRNA));
                thirdResultHeading.setText(getResources().getString(R.string.aminoacid_chain));
                ((Button)findViewById(R.id.T)).setText(getResources().getString(R.string.thymine));
                if (spinnerInteractionIsAllowed) {
                    clearFields();
                }
                break;

            case ID_mRNA:
                firstResultHeading.setText(getResources().getString(R.string.DNA));
                secondResultHeading.setText(getResources().getString(R.string.tRNA));
                thirdResultHeading.setText(getResources().getString(R.string.aminoacid_chain));
                ((Button)findViewById(R.id.T)).setText(getResources().getString(R.string.uracil));
                if (spinnerInteractionIsAllowed) {
                    clearFields();
                }
                break;
            case ID_tRNA:
                firstResultHeading.setText(getResources().getString(R.string.DNA));
                secondResultHeading.setText(getResources().getString(R.string.iRNA));
                thirdResultHeading.setText(getResources().getString(R.string.aminoacid_chain));
                ((Button)findViewById(R.id.T)).setText(getResources().getString(R.string.uracil));
                if (spinnerInteractionIsAllowed) {
                    clearFields();
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String[] solveForDNA() {
        String codon = "";

        String sequenceString = sequence.getText().toString();
        String[] seqSplitted = sequenceString.split(""); // [, А, Т, Г]
        String[] seqArr = new String[sequenceString.length()];
        for (int i = 0; i < seqArr.length; i++) {
            seqArr[i] = seqSplitted[i + 1];
        }

        StringBuilder seq = new StringBuilder();
        StringBuilder iRNA_codon = new StringBuilder();
        StringBuilder tRNA_codon = new StringBuilder();
        StringBuilder aminoacid = new StringBuilder("-");

        for (int i = 0; i < seqArr.length; i++) {
            seq.append(seqArr[i]);
        }

        String[] iRNAseqArr = new String[seqArr.length];
        String[] tRNAseqArr = seqArr;
        String[] AAseqArr = new String[seqArr.length / 3];
        for (int i = 0; i < seq.length(); i += 3) {
            codon = seq.substring(i, i + 3);
            preSolveForDNA(i / 3, codon, iRNAseqArr, tRNAseqArr, AAseqArr);
        }


        for (String i :
                iRNAseqArr) {
            iRNA_codon.append(i);
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
        return new String[] {iRNA_codon.toString(), tRNA_codon.toString(), aminoacid.toString()};
    }

    public void preSolveForDNA(int iter, String codon, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split(""); // T, T, T
        String[] codonToArr = new String[codon.length()];
        for (int i = 0; i < codonToArr.length; i++) {
            codonToArr[i] = codonToArrPre[i + 1];
        }

        int j = iter * 3;
        handleIRNA(iter, codon, iRNAseqArr, codonToArr);

        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals(THYMINE)) {
                tRNAseqArr[j + i] = URACIL;
            }
        }
        handleAminoAcid(iRNAseqArr, AAseqArr);
    }

    public String[] solveForIRNA() {
        String codon;

        String sequenceString = sequence.getText().toString();
        String[] seqSplitted = sequenceString.split(""); // [, А, Т, Г]

        String[] seqArr = new String[sequenceString.length()];
        for (int i = 0; i < seqArr.length; i++) {
            seqArr[i] = seqSplitted[i + 1];
        }

        StringBuilder seq = new StringBuilder();
        for (int i = 0; i < seqArr.length; i++) {
            seq.append(seqArr[i]);
        }

        String[] DNAseqArr = new String[seqArr.length];
        String[] tRNAseqArr = new String[seqArr.length];
        String[] AAseqArr = new String[seqArr.length / 3];
        StringBuilder DNA_codon = new StringBuilder();
        StringBuilder tRNA_codon = new StringBuilder();
        StringBuilder aminoacid = new StringBuilder("-");

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

    public void preSolveForIRNA(int iter, String codon, String[] DNAseqArr, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split(""); // T, T, T
        String[] codonToArr = new String[codon.length()];
        for (int i = 0; i < codonToArr.length; i++) {
            codonToArr[i] = codonToArrPre[i + 1];
        }
        int j = iter * 3;
        handleDNA(iter, codon, DNAseqArr, codonToArr);

        handleTRNA(iter, codon, tRNAseqArr, codonToArr);

        handleAminoAcid(iRNAseqArr, AAseqArr);
    }

    public String[] solveForTRNA() {
        String codon;

        String sequenceString = sequence.getText().toString();
        String[] seqSplitted = sequenceString.split(""); // [, А, Т, Г]

        String[] seqArr = new String[sequenceString.length()];
        for (int i = 0; i < seqArr.length; i++) {
            seqArr[i] = seqSplitted[i + 1];
        }

        StringBuilder seq = new StringBuilder();
        for (int i = 0; i < seqArr.length; i++) {
            seq.append(seqArr[i]);
        }

        String[] DNAseqArr = new String[seqArr.length];
        String[] iRNAseqArr = new String[seqArr.length];
        String[] AAseqArr = new String[seqArr.length / 3];
        StringBuilder DNA_codon = new StringBuilder();
        StringBuilder iRNA_codon = new StringBuilder();
        StringBuilder aminoacid = new StringBuilder("-");

        for (int i = 0; i < seq.length(); i += 3) {
            codon = seq.substring(i, i + 3);
            preSolveForTRNA(i / 3, codon, DNAseqArr, iRNAseqArr, seqArr, AAseqArr);
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

    public void preSolveForTRNA(int iter, String codon, String[] DNAseqArr, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split("");
        String[] codonToArr = new String[codon.length()];
        for (int i = 0; i < codonToArr.length; i++) {
            codonToArr[i] = codonToArrPre[i + 1];
        }
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

    void handleDNA(int iter, String codon, String[] DNAseqArr, String[] codonToArr) {
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

    void handleIRNA(int iter, String codon, String[] iRNAseqArr, String[] codonToArr) {
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

    void handleTRNA(int iter, String codon, String[] tRNAseqArr, String[] codonToArr) {
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

    void handleAminoAcid(String[] iRNAseqArr, String[] AAseqArr) {

        int c = 0;
        for (int i = 0; i < iRNAseqArr.length - 2; i += 3, c++) {

            if ((URACIL + URACIL + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + URACIL + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = phenylalanine;
            } else if ((URACIL + URACIL + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + URACIL + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + URACIL + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + URACIL + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + URACIL + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + URACIL + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = leucine;
            } else if ((URACIL + CYTOSINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + CYTOSINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + CYTOSINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + CYTOSINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + GUANINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + GUANINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = serine;
            } else if ((URACIL + ADENINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + ADENINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = tyrosine;
            } else if ((URACIL + GUANINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + GUANINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = cysteine;
            } else if ((URACIL + GUANINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = tryptophan;
            } else if ((CYTOSINE + CYTOSINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + CYTOSINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + CYTOSINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + CYTOSINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = proline;
            } else if ((CYTOSINE + ADENINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + ADENINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = histidine;
            } else if ((CYTOSINE + GUANINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + GUANINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + GUANINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + GUANINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + GUANINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + GUANINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = arginine;
            } else if ((ADENINE + URACIL + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + URACIL + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + URACIL + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = isoleucine;
            } else if ((ADENINE + CYTOSINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + CYTOSINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + CYTOSINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + CYTOSINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = threonine;
            } else if ((ADENINE + ADENINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + ADENINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = asparagine;
            } else if ((ADENINE + URACIL + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = methionine;
            } else if ((ADENINE + ADENINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (ADENINE + ADENINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = lysine;
            } else if ((GUANINE + URACIL + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + URACIL + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + URACIL + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + URACIL + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = valine;
            } else if ((GUANINE + CYTOSINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + CYTOSINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + CYTOSINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + CYTOSINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = alanine;
            } else if ((GUANINE + ADENINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + ADENINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = asparagine;
            } else if ((GUANINE + ADENINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + ADENINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = glutamine_acid;
            } else if ((GUANINE + GUANINE + URACIL).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + GUANINE + CYTOSINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + GUANINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (GUANINE + GUANINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = glycine;
            } else if ((URACIL + ADENINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + ADENINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (URACIL + GUANINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = stop;
            } else if ((CYTOSINE + ADENINE + ADENINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) || (CYTOSINE + ADENINE + GUANINE).equals((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]))) {
                AAseqArr[c] = glutamine;
            }
        }

    }

    void color(TextView textView) {
        String sequenceColored = textView.getText().toString();
        SpannableString ss = new SpannableString(sequenceColored);
        int iter = 0;
        try {
            if (textView.getId() == R.id.sequence) {
                for (int i = 0; i < sequenceColored.length(); i += 3, iter++) {
                    if (iter == 0) {
                        ss.setSpan(new BackgroundColorSpan(Color.argb(123, 255, 136, 0)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 1) {
                        ss.setSpan(new BackgroundColorSpan(Color.argb(123, 255, 187, 51)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 2) {
                        ss.setSpan(new BackgroundColorSpan(Color.argb(123, 0, 153, 204)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 3) {
                        ss.setSpan(new BackgroundColorSpan(Color.argb(123, 170, 102, 204)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        iter = -1;
                    }
                }
            } else {
                for (int i = 1; i < sequenceColored.length() - 1; i+=3, iter++) {
                    if (iter == 0) {
                        ss.setSpan(new BackgroundColorSpan(Color.argb(123, 255, 136, 0)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 1) {
                        ss.setSpan(new BackgroundColorSpan(Color.argb(123, 255,187,51)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 2) {
                        ss.setSpan(new BackgroundColorSpan(Color.argb(123, 0,153,204)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 3) {
                        ss.setSpan(new BackgroundColorSpan(Color.argb(123, 170,102,204)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        iter = -1;
                    }
                }
            }
        } catch (Exception e) {
            textView.setText(ss);
        }
        textView.setText(ss);
    }

    void clearFields() {
        sequence.setText("");
        beforeEnteringText.setText(R.string.start_typing);
        firstResult.setText("");
        secondResult.setText("");
        thirdResult.setText("");
        explanation.setEnabled(false);
    }

}
