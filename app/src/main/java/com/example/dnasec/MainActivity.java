package com.example.dnasec;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sequence = findViewById(R.id.sequence);
        beforeEnteringText = findViewById(R.id.beforeEnteringText);
        firstResultHeading = findViewById(R.id.result1);
        firstResult = findViewById(R.id.resultText1);
        secondResultHeading = findViewById(R.id.result2);
        secondResult = findViewById(R.id.resultText2);
        thirdResultHeading = findViewById(R.id.result3);
        thirdResult = findViewById(R.id.resultText3);

        findViewById(R.id.A).setOnClickListener(this);
        findViewById(R.id.T).setOnClickListener(this);
        findViewById(R.id.G).setOnClickListener(this);
        findViewById(R.id.C).setOnClickListener(this);
        findViewById(R.id.clear_button).setOnClickListener(this);
        findViewById(R.id.erase_button).setOnClickListener(this);
        ((Spinner)findViewById(R.id.spinner)).setOnItemSelectedListener(this);

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
                sequence.setText("");
                beforeEnteringText.setText("начните вводить цепь");
                firstResult.setText("-");

                if (((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString().equals("ДНК")) {
                    secondResult.setText("");
                } else {
                    secondResult.setText("-");
                }

                thirdResult.setText("-");


                break;
            case R.id.erase_button:
                if (sequence.getText().toString().length() > 0) {
                    sequence.setText(sequence.getText().toString().substring(0, sequence.getText().toString().length() - 1));
                    colorSequence();
                    colorFirstResult();
                }
                if (sequence.getText().toString().length() == 0) {
                    beforeEnteringText.setText("начните вводить цепь");
                    firstResult.setText("-");
                    if (((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString().equals("ДНК")) {
                            secondResult.setText("");
                    } else {
                        secondResult.setText("-");
                    }
                    thirdResult.setText("-");
                }
                if (sequence.getText().toString().length() % 3 == 0) {
                    colorFirstResult();
                }

                switch (((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString()) {
                    case "ДНК":
                        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                            String[] solvedArr = solveForDNA();

                            firstResult.setText("-" + solvedArr[0] + "-");
                            colorFirstResult();
                            secondResult.setText(solvedArr[1]);
                            thirdResult.setText(solvedArr[2]);
                        } else if (sequence.getText().length() % 2 != 0 && sequence.getText().length() % 3 != 0) {
                            try {
                                firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().length() - 4) + "--");
                                secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().length() - 5));
                                thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().length() - 4));
                            } catch (StringIndexOutOfBoundsException e) {
                                firstResult.setText("-");
                            }
                        }
                        break;

                    case "иРНК":
                        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                            String[] solvedArr = solveForIRNA();
                            firstResult.setText("-" + solvedArr[0] + "-");
                            colorFirstResult();
                            secondResult.setText(solvedArr[1]);
                            thirdResult.setText(solvedArr[2]);
                        } else if (sequence.getText().toString().length() % 2 != 0 && sequence.getText().toString().length() % 3 != 0) {
                            try {
                                firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().length() - 4) + "--");
                                secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().length() - 5));
                                thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().length() - 4));
                            } catch (StringIndexOutOfBoundsException e) {
                                firstResult.setText("-");
                            }
                        }
                        break;

                    case "тРНК":
                        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                            String[] solvedArr = solveForTRNA();
                            firstResult.setText("-" + solvedArr[0] + "-");
                            colorFirstResult();
                            secondResult.setText("-" + solvedArr[1] + "-");
                            colorSecondResult();
                            thirdResult.setText(solvedArr[2]);
                        } else if (sequence.getText().length() % 2 != 0 && sequence.getText().length() % 3 != 0) {
                            try {
                                firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().length() - 4) + "--");
                                secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().length() - 4) + "-");
                                thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().length() - 4));
                            } catch (StringIndexOutOfBoundsException e) {
                                firstResult.setText("-");
                            }
                        }
                        break;

                }
                break;
        }
        if (v.getId() == R.id.A || v.getId() == R.id.T || v.getId() == R.id.G || v.getId() == R.id.C) {
            colorSequence();
            colorFirstResult();
            beforeEnteringText.setText("");
            if (sequence.getText().toString().length() < 3) {
                firstResult.setText("-");
                secondResult.setText("-");
                thirdResult.setText("-");
            } else if (sequence.getText().toString().length() > 3) {
                colorFirstResult();
            }
            switch (((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString()) {
                case "ДНК":
                    if (sequence.getText().toString().length() % 3 == 0) {
//                        firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().toString().length() - 1));
//                        secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().toString().length() - 1));
//                        thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().toString().length() - 1));

                        colorSequence();

                        String[] solvedArr = solveForDNA();

                        firstResult.setText("-" + solvedArr[0] + "-");
                        colorFirstResult();
                        secondResult.setText(solvedArr[1]);
                        thirdResult.setText(solvedArr[2]);


                    } else {
                        firstResult.setText(firstResult.getText().toString() + "-");
                        secondResult.setText(secondResult.getText().toString() + "-");
                        thirdResult.setText(thirdResult.getText().toString() + "-");
                    }
                    break;

                case "иРНК":
                    if (sequence.getText().toString().length() % 3 == 0) {
//                        firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().toString().length() - 1));
//                        secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().toString().length() - 1));
//                        thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().toString().length() - 1));

                        colorSequence();

                        String[] solvedArr = solveForIRNA();
                        firstResult.setText("-" + solvedArr[0] + "-");
                        colorFirstResult();
                        secondResult.setText(solvedArr[1]);
                        thirdResult.setText(solvedArr[2]);


                    } else {
                        firstResult.setText(firstResult.getText().toString() + "-");
                        secondResult.setText(secondResult.getText().toString() + "-");
                        thirdResult.setText(thirdResult.getText().toString() + "-");
                    }
                    break;
                case "тРНК":
                    if (sequence.getText().toString().length() % 3 == 0) {
//                        firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().toString().length() - 1));
//                        secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().toString().length() - 1));
//                        thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().toString().length() - 1));

                        colorSequence();

                        String[] solvedArr = solveForTRNA();
                        firstResult.setText("-" + solvedArr[0] + "-");
                        colorFirstResult();
                        secondResult.setText("-" + solvedArr[1] + "-");
                        colorSecondResult();
                        thirdResult.setText(solvedArr[2]);


                    } else {
                        firstResult.setText(firstResult.getText().toString() + "-");
                        secondResult.setText(secondResult.getText().toString() + "-");
                        thirdResult.setText(thirdResult.getText().toString() + "-");
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
                sequence.setText("");
                beforeEnteringText.setText("начните вводить цепь");
                firstResult.setText("-");
                secondResult.setText("");
                thirdResult.setText("-");
                break;

            case "иРНК":
                firstResultHeading.setText("ДНК:");
                secondResultHeading.setText("тРНК:");
                thirdResultHeading.setText("АК-цепь:");
                ((Button)findViewById(R.id.T)).setText("У");
                sequence.setText("");
                beforeEnteringText.setText("начните вводить цепь");
                firstResult.setText("-");
                secondResult.setText("");
                thirdResult.setText("-");
                break;
            case "тРНК":
                firstResultHeading.setText("ДНК:");
                secondResultHeading.setText("иРНК:");
                thirdResultHeading.setText("АК-цепь:");
                ((Button)findViewById(R.id.T)).setText("У");
                sequence.setText("");
                beforeEnteringText.setText("начните вводить цепь");
                firstResult.setText("-");
                secondResult.setText("-");
                thirdResult.setText("-");
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
            System.out.println(Arrays.toString(iRNAseqArr));
            System.out.println();
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
//        System.out.println("иРНК: " + iRNA_codon.length() + "\n тРНК: " + tRNA_codon.length() + "\n АК: " + aminoacid.length());
        return new String[] {iRNA_codon.toString(), tRNA_codon.toString(), aminoacid.toString()};
    }

    public void preSolveForDNA(int iter, String codon, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split(""); // T, T, T
        String[] codonToArr = new String[codon.length()];
        for (int i = 0; i < codonToArr.length; i++) {
            codonToArr[i] = codonToArrPre[i + 1];
        }

        int j = iter * 3;
        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals("А")) {
                iRNAseqArr[j + i] = "У";
            } else if (codonToArr[i].equals("Т")) {
                iRNAseqArr[j + i] = "А";
            } else if (codonToArr[i].equals("Г")) {
                iRNAseqArr[j + i] = "Ц";
            } else if (codonToArr[i].equals("Ц")) {
                iRNAseqArr[j + i] = "Г";
            }
        }

        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals("Т")) {
                tRNAseqArr[j + i] = "У";
            }
        }
        int c = 0;
        for (int i = 0; i < iRNAseqArr.length - 2; i += 3, c++) {

            if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУЦ")) {
                AAseqArr[c] = "Фен";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i+1] + iRNAseqArr[i + 2]).equals("УУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУГ")) {
                AAseqArr[c] = "Лей";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГЦ")) {
                AAseqArr[c] = "Сер";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАЦ")) {
                AAseqArr[c] = "Тир";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГЦ")) {
                AAseqArr[c] = "Цис";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГГ")) {
                AAseqArr[c] = "Три";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦГ")) {
                AAseqArr[c] = "Про";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАЦ")) {
                AAseqArr[c] = "Гис";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГГ")) {
                AAseqArr[c] = "Арг";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУА")) {
                AAseqArr[c] = "Иле";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦГ")) {
                AAseqArr[c] = "Тре";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААЦ")) {
                AAseqArr[c] = "Асн";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУГ")) {
                AAseqArr[c] = "Мет";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААГ")) {
                AAseqArr[c] = "Лиз";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУГ")) {
                AAseqArr[c] = "Вал";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦГ")) {
                AAseqArr[c] = "Ала";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАЦ")) {
                AAseqArr[c] = "Асп";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАГ")) {
                AAseqArr[c] = "Глу";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГГ")) {
                AAseqArr[c] = "Гли";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГА")) {
                AAseqArr[c] = "———";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАГ")) {
                AAseqArr[c] = "Глн";
            }
        }
    }

    public String[] solveForIRNA() {
        String codon;

        String sequenceString = sequence.getText().toString();
        String[] seqSplitted = sequenceString.split(""); // [, А, Т, Г]

        String[] seqArr = new String[sequenceString.length()];
        for (int i = 0; i < seqArr.length; i++) {
            seqArr[i] = seqSplitted[i + 1];
        }

        String seq = "";
        for (int i = 0; i < seqArr.length; i++) {
            seq += seqArr[i];
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
        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals("У")) {
                DNAseqArr[j + i] = "А";
            } else if (codonToArr[i].equals("А")) {
                DNAseqArr[j + i] = "Т";
            } else if (codonToArr[i].equals("Ц")) {
                DNAseqArr[j + i] = "Г";
            } else if (codonToArr[i].equals("Г")) {
                DNAseqArr[j + i] = "Ц";
            }
        }

        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals("А")) {
                tRNAseqArr[j + i] = "У";
            } else if (codonToArr[i].equals("У")) {
                tRNAseqArr[j + i] = "А";
            } else if (codonToArr[i].equals("Г")) {
                tRNAseqArr[j + i] = "Ц";
            } else if (codonToArr[i].equals("Ц")) {
                tRNAseqArr[j + i] = "Г";
            }
        }

        int c = 0;
        for (int i = 0; i < iRNAseqArr.length - 2; i += 3, c++) {

            if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУЦ")) {
                AAseqArr[c] = "Фен";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i+1] + iRNAseqArr[i + 2]).equals("УУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУГ")) {
                AAseqArr[c] = "Лей";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГЦ")) {
                AAseqArr[c] = "Сер";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАЦ")) {
                AAseqArr[c] = "Тир";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГЦ")) {
                AAseqArr[c] = "Цис";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГГ")) {
                AAseqArr[c] = "Три";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦГ")) {
                AAseqArr[c] = "Про";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАЦ")) {
                AAseqArr[c] = "Гис";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГГ")) {
                AAseqArr[c] = "Арг";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУА")) {
                AAseqArr[c] = "Иле";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦГ")) {
                AAseqArr[c] = "Тре";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААЦ")) {
                AAseqArr[c] = "Асн";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУГ")) {
                AAseqArr[c] = "Мет";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААГ")) {
                AAseqArr[c] = "Лиз";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУГ")) {
                AAseqArr[c] = "Вал";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦГ")) {
                AAseqArr[c] = "Ала";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАЦ")) {
                AAseqArr[c] = "Асп";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАГ")) {
                AAseqArr[c] = "Глу";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГГ")) {
                AAseqArr[c] = "Гли";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГА")) {
                AAseqArr[c] = "———";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАГ")) {
                AAseqArr[c] = "Глн";
            }
        }
    }

    public String[] solveForTRNA() {
        String codon;

        String sequenceString = sequence.getText().toString();
        String[] seqSplitted = sequenceString.split(""); // [, А, Т, Г]

        String[] seqArr = new String[sequenceString.length()];
        for (int i = 0; i < seqArr.length; i++) {
            seqArr[i] = seqSplitted[i + 1];
        }

        String seq = "";
        for (int i = 0; i < seqArr.length; i++) {
            seq += seqArr[i];
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
            if (codonToArr[i].equals("У")) {
                DNAseqArr[j + i] = "Т";
            } else if (codonToArr[i].equals("А")) {
                DNAseqArr[j + i] = "А";
            } else if (codonToArr[i].equals("Ц")) {
                DNAseqArr[j + i] = "Ц";
            } else if (codonToArr[i].equals("Г")) {
                DNAseqArr[j + i] = "Г";
            }
        }

        for (int i = 0; i < codon.length(); i++) {
            if (codonToArr[i].equals("А")) {
                iRNAseqArr[j + i] = "У";
            } else if (codonToArr[i].equals("У")) {
                iRNAseqArr[j + i] = "А";
            } else if (codonToArr[i].equals("Г")) {
                iRNAseqArr[j + i] = "Ц";
            } else if (codonToArr[i].equals("Ц")) {
                iRNAseqArr[j + i] = "Г";
            }
        }

        int c = 0;
        for (int i = 0; i < iRNAseqArr.length - 2; i += 3, c++) {

            if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУЦ")) {
                AAseqArr[c] = "Фен";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i+1] + iRNAseqArr[i + 2]).equals("УУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УУГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦУГ")) {
                AAseqArr[c] = "Лей";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УЦГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГЦ")) {
                AAseqArr[c] = "Сер";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАЦ")) {
                AAseqArr[c] = "Тир";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГЦ")) {
                AAseqArr[c] = "Цис";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГГ")) {
                AAseqArr[c] = "Три";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦЦГ")) {
                AAseqArr[c] = "Про";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАЦ")) {
                AAseqArr[c] = "Гис";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦГГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АГГ")) {
                AAseqArr[c] = "Арг";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУА")) {
                AAseqArr[c] = "Иле";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АЦГ")) {
                AAseqArr[c] = "Тре";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААЦ")) {
                AAseqArr[c] = "Асн";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("АУГ")) {
                AAseqArr[c] = "Мет";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ААГ")) {
                AAseqArr[c] = "Лиз";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГУГ")) {
                AAseqArr[c] = "Вал";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГЦГ")) {
                AAseqArr[c] = "Ала";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАЦ")) {
                AAseqArr[c] = "Асп";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГАГ")) {
                AAseqArr[c] = "Глу";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГУ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГЦ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ГГГ")) {
                AAseqArr[c] = "Гли";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УАГ") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("УГА")) {
                AAseqArr[c] = "———";
            } else if ((iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАА") || (iRNAseqArr[i] + iRNAseqArr[i + 1] + iRNAseqArr[i + 2]).equals("ЦАГ")) {
                AAseqArr[c] = "Глн";
            }
        }
    }

    void colorSequence() {
        String sequenceColored = sequence.getText().toString(); // AAATTTAA
        SpannableString ss = new SpannableString(sequenceColored);
        int iter = 0;
        try {
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
        } catch (Exception e) {
            sequence.setText(ss);
        }
        sequence.setText(ss);
    }

    void colorFirstResult() {
        String sequenceColored = firstResult.getText().toString();
        SpannableString ss = new SpannableString(sequenceColored);
        int iter = 0;
        try {
            for (int i = 1; i < sequenceColored.length(); i += 3, iter++) {
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
        } catch (Exception e) {
            firstResult.setText(ss);
        }
        firstResult.setText(ss);
    }

    void colorSecondResult() {
        String sequenceColored = secondResult.getText().toString();
        SpannableString ss = new SpannableString(sequenceColored);
        int iter = 0;
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
        secondResult.setText(ss);
    }

}
