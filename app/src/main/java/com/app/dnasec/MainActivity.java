package com.app.dnasec;

import android.animation.ArgbEvaluator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.transition.TransitionManager;
import android.util.Property;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dnasec.helpers.MutableBackgroundColorSpan;

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
    CheckBox DnaIsMatrix;

    boolean spinnerInteractionIsAllowed;

    final int ID_DNA = 0;
    final int ID_mRNA = 1;
    final int ID_tRNA = 2;
    final boolean ERASED = true;

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

    private boolean codonsAreHighlighted;
    private boolean animationIsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        codonsAreHighlighted = preferences.getBoolean("KEY_HIGHLIGHT", true);
        animationIsEnabled = preferences.getBoolean("KEY_ANIMATION", true);

        ADENINE = getResources().getString(R.string.adenine);
        GUANINE = getResources().getString(R.string.guanine);
        CYTOSINE = getResources().getString(R.string.cytosine);
        URACIL = getResources().getString(R.string.uracil);
        THYMINE = getResources().getString(R.string.thymine);

        phenylalanine = getResources().getString(R.string.phenylalanine_shortened);
        leucine = getResources().getString(R.string.leucine_shortened);
        isoleucine = getResources().getString(R.string.isoleucine_shortened);
        methionine = getResources().getString(R.string.methionine_shortened);
        valine = getResources().getString(R.string.valine_shortened);
        serine = getResources().getString(R.string.serine_shortened);
        proline = getResources().getString(R.string.proline_shortened);
        threonine = getResources().getString(R.string.threonine_shortened);
        tyrosine = getResources().getString(R.string.tyrosine_shortened);
        alanine = getResources().getString(R.string.alanine_shortened);
        stop = getResources().getString(R.string.stop_shortened);
        histidine = getResources().getString(R.string.histidine_shortened);
        glutamine = getResources().getString(R.string.glutamine_shortened);
        asparagine = getResources().getString(R.string.asparagine_shortened);
        lysine = getResources().getString(R.string.lysine_shortened);
        aspartic_acid = getResources().getString(R.string.aspartic_acid_shortened);
        glutamine_acid = getResources().getString(R.string.glutamine_acid_shortened);
        cysteine = getResources().getString(R.string.cysteine_shortened);
        tryptophan = getResources().getString(R.string.tryptophan_shortened);
        arginine = getResources().getString(R.string.arginine_shortened);
        glycine = getResources().getString(R.string.glycine_shortened);

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
        DnaIsMatrix = findViewById(R.id.DNA_is_matrix);

        sequence.setMovementMethod(LinkMovementMethod.getInstance());

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

        DnaIsMatrix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sequence.getText().length() > 0) {
                    if (sequence.getText().toString().length() % 3 == 0) {
                        if (codonsAreHighlighted) {
                            color(sequence, ERASED);
                        }

                        String[] solvedArr;
                        if (DnaIsMatrix.isChecked()) {
                            solvedArr = solveForMatrixDNA();
                        } else {
                            solvedArr = solveForDNA();
                        }

                        if (animationIsEnabled) {
                            fadeInFields(solvedArr[0], solvedArr[1], solvedArr[2]);
                        } else {
                            firstResult.setText(solvedArr[0]);
                            secondResult.setText(solvedArr[1]);
                            thirdResult.setText(solvedArr[2]);
                        }

                        if (codonsAreHighlighted) {
                            color(firstResult, ERASED);
                        }

                        explanation.setEnabled(true);


                    } else {
                        beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                        explanation.setEnabled(false);
                    }
                }
            }
        });

        if (animationIsEnabled) {
            TransitionManager.beginDelayedTransition((ConstraintLayout) findViewById(R.id.container));
            ((ConstraintLayout) findViewById(R.id.container)).getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(settingsIntent, 0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            recreate();
        }
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
        savedInstanceState.putBoolean("DnaIsMatrix", DnaIsMatrix.isChecked());
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
        if (codonsAreHighlighted) {
            color(sequence, ERASED);
            color(firstResult, ERASED);
            if (spinner.getSelectedItem().toString().equals("тРНК")) {
                color(secondResult, ERASED);
            }
        }
        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
            explanation.setEnabled(true);
        }
        DnaIsMatrix.setChecked(savedInstanceState.getBoolean("DnaIsMatrix"));
    }

    boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.A:
                sequence.append(ADENINE);
                break;

            case R.id.T:
                sequence.append(((Button) findViewById(R.id.T)).getText().toString());
                break;

            case R.id.G:
                sequence.append(GUANINE);
                break;

            case R.id.C:
                sequence.append(CYTOSINE);
                break;

            case R.id.clear_button:
                clearFields();
                break;
            case R.id.erase_button:
                if (sequence.getText().toString().length() > 0) {
                    sequence.setText(sequence.getText().toString().substring(0, sequence.getText().toString().length() - 1));
                    if (codonsAreHighlighted) {
                        color(sequence, ERASED);
                        color(firstResult, ERASED);
                    }
                }
                if (sequence.getText().length() == 0) {
                    clearFields();
                }
                if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                    if (codonsAreHighlighted) {
                        color(firstResult, false);
                    }
                    beforeEnteringText.setText("");
                    explanation.setEnabled(true);

                } else if (sequence.getText().length() % 3 != 0){
                    beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                    explanation.setEnabled(false);
                }
                if (sequence.getText().length() < 3) {
                    firstResult.setText("");
                    secondResult.setText("");
                    thirdResult.setText("");
                }

                switch ((int)spinner.getSelectedItemId()) {
                    case ID_DNA:
                        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                            String[] solvedArr;
                            if (DnaIsMatrix.isChecked()) {
                                solvedArr = solveForMatrixDNA();
                            } else {
                                solvedArr = solveForDNA();
                            }

                            firstResult.setText(solvedArr[0]);
                            secondResult.setText(solvedArr[1]);
                            thirdResult.setText(solvedArr[2]);

                            if (codonsAreHighlighted) {
                                color(firstResult, ERASED);
                            }
                        } else if (!isPrime(sequence.getText().length()) && sequence.getText().length() % 3 != 0) {
                            try {
                                firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().length() - 3));
                                secondResult.setText(secondResult.getText().toString().substring(0, secondResult.getText().length() - 5));
                                thirdResult.setText(thirdResult.getText().toString().substring(0, thirdResult.getText().length() - 4));
                            } catch (StringIndexOutOfBoundsException e) {
                                firstResult.setText("");
                            }
                        }
                        break;

                    case ID_mRNA:
                        if (sequence.getText().length() % 3 == 0 && sequence.getText().length() != 0) {
                            String[] solvedArr = solveForMRNA();
                            firstResult.setText(solvedArr[0]);
                            if (codonsAreHighlighted) {
                                color(firstResult, ERASED);
                            }
                            secondResult.setText(solvedArr[1]);
                            thirdResult.setText(solvedArr[2]);
                        } else if (sequence.getText().toString().length() % 2 != 0 && sequence.getText().toString().length() % 3 != 0) {
                            try {
                                firstResult.setText(firstResult.getText().toString().substring(0, firstResult.getText().length() - 3));
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
                            firstResult.setText(solvedArr[0]);
                            secondResult.setText(solvedArr[1]);
                            if (codonsAreHighlighted) {
                                color(firstResult, ERASED);
                                color(secondResult, ERASED);
                            }
                            thirdResult.setText(solvedArr[2]);
                        } else if (sequence.getText().length() % 2 != 0 && sequence.getText().length() % 3 != 0) {
                            try {
                                firstResult.setText( firstResult.getText().toString().substring(0, firstResult.getText().length() - 3));
                                secondResult.setText( secondResult.getText().toString().substring(0, secondResult.getText().length() - 3));
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
//                openExplanationActivity.putExtra("firstResult", firstResult.getText().toString().substring(1, firstResult.getText().length() - 1));
                openExplanationActivity.putExtra("firstResult", firstResult.getText().toString());
//                if (spinner.getSelectedItemId() == 2) {
//                    openExplanationActivity.putExtra("secondResult", secondResult.getText().toString().substring(1, secondResult.getText().length() - 1));
//                }  else {
//                    openExplanationActivity.putExtra("secondResult", secondResult.getText().toString());
//                }
                openExplanationActivity.putExtra("secondResult", secondResult.getText().toString());
                openExplanationActivity.putExtra("thirdResult", thirdResult.getText().toString());
                openExplanationActivity.putExtra("DNA_is_matrix", DnaIsMatrix.isChecked());

                startActivity(openExplanationActivity);

                break;
        }
        if (v.getId() == R.id.A || v.getId() == R.id.T || v.getId() == R.id.G || v.getId() == R.id.C) {

            beforeEnteringText.setText("");
            switch ((int)spinner.getSelectedItemId()) {
                case ID_DNA:
                    if (sequence.getText().toString().length() % 3 == 0) {
                        if (codonsAreHighlighted) {
                            color(sequence, false);
                        }

                        String[] optimizedSolved;
                        if (DnaIsMatrix.isChecked()) {
                            optimizedSolved = solveLastCodonMatrixDNA();

                        } else {
                            optimizedSolved = solveLastCodonDNA();
                        }

                        firstResult.append(optimizedSolved[0]);
                        secondResult.append(optimizedSolved[1]);
                        thirdResult.append(optimizedSolved[2]);

                        if (codonsAreHighlighted) {
                            color(firstResult, false);
                        }

                        explanation.setEnabled(true);


                    } else {
                        beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                        explanation.setEnabled(false);
                    }
                    break;

                case ID_mRNA:
                    if (sequence.getText().toString().length() % 3 == 0) {
                        if (codonsAreHighlighted) {
                            color(sequence, false);
                        }

//                        String[] solvedArr = solveForMRNA();
//                        firstResult.setText(String.format(, solvedArr[0]));
//                        secondResult.setText(solvedArr[1]);
//                        thirdResult.setText(solvedArr[2]);

                        String[] optimizedSolved = solveLastCodonMRNA();
                        firstResult.append(optimizedSolved[0]);
                        secondResult.append(optimizedSolved[1]);
                        thirdResult.append(optimizedSolved[2]);
                        if (codonsAreHighlighted) {
                            color(firstResult, false);
                        }
                        explanation.setEnabled(true);

                    } else {
                        beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                        explanation.setEnabled(false);
                    }
                    break;
                case ID_tRNA:
                    if (sequence.getText().length() % 3 == 0) {

                        String[] optimizedSolved = solveLastCodonTRNA();
                        firstResult.append(optimizedSolved[0]);

                        secondResult.append(optimizedSolved[1]);

                        if (codonsAreHighlighted) {
                            color(sequence, false);
                            color(firstResult, false);
                            color(secondResult, false);
                        }
                        thirdResult.append(optimizedSolved[2]);
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
                ((Button)findViewById(R.id.T)).setText(THYMINE);
                if (spinnerInteractionIsAllowed) {
                    clearFields();
                }

                DnaIsMatrix.setEnabled(true);
                break;

            case ID_mRNA:
                firstResultHeading.setText(getResources().getString(R.string.DNA));
                secondResultHeading.setText(getResources().getString(R.string.tRNA));
                thirdResultHeading.setText(getResources().getString(R.string.aminoacid_chain));
                ((Button)findViewById(R.id.T)).setText(URACIL);
                if (spinnerInteractionIsAllowed) {
                    clearFields();
                }
                DnaIsMatrix.setChecked(false);
                DnaIsMatrix.setEnabled(false);
                break;
            case ID_tRNA:
                firstResultHeading.setText(getResources().getString(R.string.DNA));
                secondResultHeading.setText(getResources().getString(R.string.iRNA));
                thirdResultHeading.setText(getResources().getString(R.string.aminoacid_chain));
                ((Button)findViewById(R.id.T)).setText(URACIL);
                if (spinnerInteractionIsAllowed) {
                    clearFields();
                }
                DnaIsMatrix.setChecked(false);
                DnaIsMatrix.setEnabled(false);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public String[] solveLastCodonDNA() { // Оптимизированный под последний кодон метод

        String sequenceString = sequence.getText().toString();

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

    public String[] solveLastCodonMatrixDNA() { // Оптимизированный под последний кодон метод

        String sequenceString = sequence.getText().toString();

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

    public String[] solveLastCodonMRNA() {
        String sequenceString = sequence.getText().toString();

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

    public String[] solveLastCodonTRNA() {
        String sequenceString = sequence.getText().toString();

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

    String handleAminoAcidLastCodon(String lastMRNA) {

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


    public String[] solveForMatrixDNA() {

        String codon;

        String sequenceString = sequence.getText().toString();
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

    public void preSolveForMatrixDNA(int iter, String codon, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
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

    public String[] solveForDNA() {
        String codon;

        String sequenceString = sequence.getText().toString();

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

    public void preSolveForDNA(int iter, String codon, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
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

    public String[] solveForMRNA() {
        String codon;

        String sequenceString = sequence.getText().toString();
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

    public void preSolveForIRNA(int iter, String codon, String[] DNAseqArr, String[] iRNAseqArr, String[] tRNAseqArr, String[] AAseqArr) {
        String[] codonToArrPre = codon.split(""); // T, T, T
        String[] codonToArr = new String[codon.length()];
        System.arraycopy(codonToArrPre, 1, codonToArr, 0, codonToArr.length);
        handleDNA(iter, codon, DNAseqArr, codonToArr);

        handleTRNA(iter, codon, tRNAseqArr, codonToArr);

        handleAminoAcid(iRNAseqArr, AAseqArr);
    }

    public String[] solveForTRNA() {
        String codon;

        String sequenceString = sequence.getText().toString();
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

    public void preSolveForTRNA(int iter, String codon, String[] DNAseqArr, String[] iRNAseqArr, String[] AAseqArr) {
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

    private static final Property<MutableBackgroundColorSpan, Integer> MUTABLE_BACKGROUND_COLOR_SPAN_FC_PROPERTY =
            new Property<MutableBackgroundColorSpan, Integer>(Integer.class, "MUTABLE_BACKGROUND_COLOR_SPAN_FC_PROPERTY") {

                @Override
                public void set(MutableBackgroundColorSpan span, Integer value) {
                    span.setBackgroundColor(value);
                }

                @Override
                public Integer get(MutableBackgroundColorSpan span) {
                    return span.getBackgroundColor();
                }
            };

    void color(final TextView textView, boolean erased) {
        String sequenceColored = textView.getText().toString();
        final SpannableString ss = new SpannableString(sequenceColored);
        int iter = 0;
        try {
            if (!textView.getText().toString().substring(0, 1).equals("-")/*textView.getId() == R.id.sequence || textView.getId() == R.id.resultText1*/) {
                for (int i = 0; i < sequenceColored.length(); i += 3, iter++) {
                    if (iter == 0) {

                        if (animationIsEnabled && !(i < textView.getText().length() - 3) && !erased) {
                            colorWithAnimation(ss, i, textView, 255, 136, 0);
                        } else ss.setSpan(new BackgroundColorSpan(Color.argb(123, 255, 136, 0)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        /*final int finalI = i;
                        final MutableClickableSpan clickableSpan = new MutableClickableSpan(sequenceColored.substring(finalI, i+3), this) {
                            @Override
                            public void onClick(@NonNull View widget) {

                                widget.invalidate();
                            }
                        };
                        ss.setSpan(clickableSpan, i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/





                    } else if (iter == 1) {
                        if (animationIsEnabled && !(i < textView.getText().length() - 3) && !erased) {
                            colorWithAnimation(ss, i, textView, 255, 187, 51);
                        } else ss.setSpan(new BackgroundColorSpan(Color.argb(123, 255, 187, 51)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 2) {
                        if (animationIsEnabled && !(i < textView.getText().length() - 3) && !erased) {
                            colorWithAnimation(ss, i, textView, 0, 153, 204);
                        } else ss.setSpan(new BackgroundColorSpan(Color.argb(123, 0, 153, 204)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 3) {
                        if (animationIsEnabled && !(i < textView.getText().length() - 3) && !erased) {
                            colorWithAnimation(ss, i, textView, 170, 102, 204);
                        } else ss.setSpan(new BackgroundColorSpan(Color.argb(123, 170, 102, 204)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        iter = -1;
                    }
                }
            } else {
                for (int i = 1; i < sequenceColored.length() - 1; i+=3, iter++) {
                    if (iter == 0) {
                        if (animationIsEnabled && !(i < textView.getText().length() - 4) && !erased) {
                            colorWithAnimation(ss, i, textView, 255, 136, 0);
                        } else ss.setSpan(new BackgroundColorSpan(Color.argb(123, 255, 136, 0)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 1) {
                        if (animationIsEnabled && !(i < textView.getText().length() - 4) && !erased) {
                            colorWithAnimation(ss, i, textView, 255, 187, 51);
                        } else ss.setSpan(new BackgroundColorSpan(Color.argb(123, 255,187,51)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 2) {
                        if (animationIsEnabled && !(i < textView.getText().length() - 4) && !erased) {
                            colorWithAnimation(ss, i, textView, 0, 153, 204);
                        } else ss.setSpan(new BackgroundColorSpan(Color.argb(123, 0,153,204)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if (iter == 3) {
                        if (animationIsEnabled && !(i < textView.getText().length() - 4) && !erased) {
                            colorWithAnimation(ss, i, textView, 170, 102, 204);
                        } else ss.setSpan(new BackgroundColorSpan(Color.argb(123, 170,102,204)), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        iter = -1;
                    }
                }
            }
        } catch (Exception e) {
            textView.setText(ss);
        }
        textView.setText(ss);
    }

    void colorWithAnimation(final SpannableString ss, int i, final TextView textView, int r, int g, int b) {
        MutableBackgroundColorSpan span = new MutableBackgroundColorSpan(123, Color.WHITE);
        ss.setSpan(span,  i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_BACKGROUND_COLOR_SPAN_FC_PROPERTY, Color.WHITE, Color.rgb(r, g, b));
        objectAnimator.setDuration(100);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //refresh
                textView.setText(ss);
            }
        });
        objectAnimator.start();
    }

    void clearFields() {
        if (animationIsEnabled) {
            fadeOutFields();
        } else {
            sequence.setText("");
            firstResult.setText("");
            secondResult.setText("");
            thirdResult.setText("");
        }

        beforeEnteringText.setText(R.string.start_typing);
        explanation.setEnabled(false);
    }

    void fadeOutFields() {
            Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    sequence.setText("");
                    firstResult.setText("");
                    secondResult.setText("");
                    thirdResult.setText("");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            sequence.startAnimation(fadeOut);
            firstResult.startAnimation(fadeOut);
            secondResult.startAnimation(fadeOut);
            thirdResult.startAnimation(fadeOut);
    }

    void fadeInFields(final String first, final String second, final String third) {
        firstResult.setText(first);
        secondResult.setText(second);
        thirdResult.setText(third);
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fadeIn.setDuration(300);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                color(firstResult, ERASED);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        firstResult.startAnimation(fadeIn);
        secondResult.startAnimation(fadeIn);
        thirdResult.startAnimation(fadeIn);
    }

}
