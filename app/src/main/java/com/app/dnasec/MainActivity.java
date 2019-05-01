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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                sequence.setText(String.format("%sА", sequence.getText()));

                break;

            case R.id.T:
                sequence.setText(String.format("%s%s", sequence.getText(), ((Button) findViewById(R.id.T)).getText().toString()));
                break;

            case R.id.G:
                sequence.setText(String.format("%sГ", sequence.getText()));
                break;

            case R.id.C:
                sequence.setText(String.format("%sЦ", sequence.getText()));
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
                    beforeEnteringText.setText("Внимание: неполная исходная цепь");
                    explanation.setEnabled(false);
                    color(firstResult);
                }
                if (sequence.getText().length() < 3) {
                    firstResult.setText("");
                    secondResult.setText("");
                    thirdResult.setText("");
                }

                switch (spinner.getSelectedItem().toString()) {
                    case "ДНК":
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

                    case "иРНК":
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

                    case "тРНК":
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
            switch (spinner.getSelectedItem().toString()) {
                case "ДНК":
                    if (sequence.getText().toString().length() % 3 == 0) {
                        color(sequence);

                        String[] solvedArr = solveForDNA();

                        firstResult.setText("-" + solvedArr[0] + "-");
                        color(firstResult);
                        secondResult.setText(solvedArr[1]);
                        thirdResult.setText(solvedArr[2]);

                        explanation.setEnabled(true);


                    } else {
                        beforeEnteringText.setText("Внимание: неполная исходная цепь");
                        explanation.setEnabled(false);
                    }
                    break;

                case "иРНК":
                    if (sequence.getText().toString().length() % 3 == 0) {
                        color(sequence);

                        String[] solvedArr = solveForIRNA();
                        firstResult.setText("-" + solvedArr[0] + "-");
                        color(firstResult);
                        secondResult.setText(solvedArr[1]);
                        thirdResult.setText(solvedArr[2]);
                        explanation.setEnabled(true);

                    } else {
                        beforeEnteringText.setText("Внимание: неполная исходная цепь");
                        explanation.setEnabled(false);
                    }
                    break;
                case "тРНК":
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
                        beforeEnteringText.setText("Внимание: неполная исходная цепь");
                        explanation.setEnabled(false);
                    }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getSelectedItem().toString()) {
            case "ДНК":
                firstResultHeading.setText("иРНК:");
                secondResultHeading.setText("тРНК:");
                thirdResultHeading.setText("АК-цепь:");
                ((Button)findViewById(R.id.T)).setText("Т");
                if (spinnerInteractionIsAllowed) {
                    clearFields();
                }
                break;

            case "иРНК":
                firstResultHeading.setText("ДНК:");
                secondResultHeading.setText("тРНК:");
                thirdResultHeading.setText("АК-цепь:");
                ((Button)findViewById(R.id.T)).setText("У");
                if (spinnerInteractionIsAllowed) {
                    clearFields();
                }
                break;
            case "тРНК":
                firstResultHeading.setText("ДНК:");
                secondResultHeading.setText("иРНК:");
                thirdResultHeading.setText("АК-цепь:");
                ((Button)findViewById(R.id.T)).setText("У");
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
            if (codonToArr[i].equals("Т")) {
                tRNAseqArr[j + i] = "У";
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
            switch (codonToArr[i]) {
                case "У":
                    DNAseqArr[j + i] = "Т";
                    break;
                case "А":
                    DNAseqArr[j + i] = "А";
                    break;
                case "Ц":
                    DNAseqArr[j + i] = "Ц";
                    break;
                case "Г":
                    DNAseqArr[j + i] = "Г";
                    break;
            }
        }

        for (int i = 0; i < codon.length(); i++) {
            switch (codonToArr[i]) {
                case "А":
                    iRNAseqArr[j + i] = "У";
                    break;
                case "У":
                    iRNAseqArr[j + i] = "А";
                    break;
                case "Г":
                    iRNAseqArr[j + i] = "Ц";
                    break;
                case "Ц":
                    iRNAseqArr[j + i] = "Г";
                    break;
            }
        }

        handleAminoAcid(iRNAseqArr, AAseqArr);
    }

    void handleDNA(int iter, String codon, String[] DNAseqArr, String[] codonToArr) {
        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {
            switch (codonToArr[i]) {
                case "У":
                    DNAseqArr[j + i] = "А";
                    break;
                case "А":
                    DNAseqArr[j + i] = "Т";
                    break;
                case "Ц":
                    DNAseqArr[j + i] = "Г";
                    break;
                case "Г":
                    DNAseqArr[j + i] = "Ц";
                    break;
            }
        }
    }

    void handleIRNA(int iter, String codon, String[] iRNAseqArr, String[] codonToArr) {
        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {
            switch (codonToArr[i]) {
                case "А":
                    iRNAseqArr[j + i] = "У";
                    break;
                case "Т":
                    iRNAseqArr[j + i] = "А";
                    break;
                case "Г":
                    iRNAseqArr[j + i] = "Ц";
                    break;
                case "Ц":
                    iRNAseqArr[j + i] = "Г";
                    break;
            }
        }
    }

    void handleTRNA(int iter, String codon, String[] tRNAseqArr, String[] codonToArr) {
        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {
            switch (codonToArr[i]) {
                case "А":
                    tRNAseqArr[j + i] = "У";
                    break;
                case "У":
                    tRNAseqArr[j + i] = "А";
                    break;
                case "Г":
                    tRNAseqArr[j + i] = "Ц";
                    break;
                case "Ц":
                    tRNAseqArr[j + i] = "Г";
                    break;
            }
        }
    }

    void handleAminoAcid(String[] iRNAseqArr, String[] AAseqArr) {

        int c = 0;
        for (int i = 0; i < iRNAseqArr.length - 2; i += 3, c++) {

            switch ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2])) {
                case "УУУ":
                case "УУЦ":
                    AAseqArr[c] = "Фен";
                    break;
                case "УУА":
                case "УУГ":
                case "ЦУУ":
                case "ЦУЦ":
                case "ЦУА":
                case "ЦУГ":
                    AAseqArr[c] = "Лей";
                    break;
                case "УЦУ":
                case "УЦЦ":
                case "УЦА":
                case "УЦГ":
                case "АГУ":
                case "АГЦ":
                    AAseqArr[c] = "Сер";
                    break;
                case "УАУ":
                case "УАЦ":
                    AAseqArr[c] = "Тир";
                    break;
                case "УГУ":
                case "УГЦ":
                    AAseqArr[c] = "Цис";
                    break;
                case "УГГ":
                    AAseqArr[c] = "Три";
                    break;
                case "ЦЦУ":
                case "ЦЦЦ":
                case "ЦЦА":
                case "ЦЦГ":
                    AAseqArr[c] = "Про";
                    break;
                case "ЦАУ":
                case "ЦАЦ":
                    AAseqArr[c] = "Гис";
                    break;
                case "ЦГУ":
                case "ЦГЦ":
                case "ЦГА":
                case "ЦГГ":
                case "АГА":
                case "АГГ":
                    AAseqArr[c] = "Арг";
                    break;
                case "АУУ":
                case "АУЦ":
                case "АУА":
                    AAseqArr[c] = "Иле";
                    break;
                case "АЦУ":
                case "АЦЦ":
                case "АЦА":
                case "АЦГ":
                    AAseqArr[c] = "Тре";
                    break;
                case "ААУ":
                case "ААЦ":
                    AAseqArr[c] = "Асн";
                    break;
                case "АУГ":
                    AAseqArr[c] = "Мет";
                    break;
                case "ААА":
                case "ААГ":
                    AAseqArr[c] = "Лиз";
                    break;
                case "ГУУ":
                case "ГУЦ":
                case "ГУА":
                case "ГУГ":
                    AAseqArr[c] = "Вал";
                    break;
                case "ГЦУ":
                case "ГЦЦ":
                case "ГЦА":
                case "ГЦГ":
                    AAseqArr[c] = "Ала";
                    break;
                case "ГАУ":
                case "ГАЦ":
                    AAseqArr[c] = "Асп";
                    break;
                case "ГАА":
                case "ГАГ":
                    AAseqArr[c] = "Глу";
                    break;
                case "ГГУ":
                case "ГГЦ":
                case "ГГА":
                case "ГГГ":
                    AAseqArr[c] = "Гли";
                    break;
                case "УАА":
                case "УАГ":
                case "УГА":
                    AAseqArr[c] = "———";
                    break;
                case "ЦАА":
                case "ЦАГ":
                    AAseqArr[c] = "Глн";
                    break;
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
        beforeEnteringText.setText("начните вводить цепь");
        firstResult.setText("");
        secondResult.setText("");
        thirdResult.setText("");
        explanation.setEnabled(false);
    }

}
