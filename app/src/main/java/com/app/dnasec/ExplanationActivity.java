package com.app.dnasec;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.dnasec.adapters.AminoacidAdapter;
import com.app.dnasec.adapters.SimplePairsAdapter;
import com.app.dnasec.adapters.models.CodonAminoacidPairModel;
import com.app.dnasec.adapters.models.PairModel;
import com.app.dnasec.views.CodonAminoacidPair;
import com.app.dnasec.views.NucleotidesPair;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.Objects;

public class ExplanationActivity extends AppCompatActivity {

    final int DNA_SEQUENCE = 0;
    final int IRNA_SEQUENCE = 1;
    final int TRNA_SEQUENCE = 2;

    private final int ADENINE = 0;
    private final int THYMINE = 1;
    private final int GUANINE = 2;
    private final int CYTOSINE = 3;
    private final int URACIL = 4;

    int sequenceType;
    boolean DnaIsMatrix;
    String mainSequence;
    String firstResult;
    String secondResult;
    String thirdResult;

    TextView linkedText;
    TextView secondExpl;
    TextView thirdExpl;

    TextView sequenceName;

    ExpansionLayout expansionLayout;

    RecyclerView firstResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        setTitle(R.string.explanation_activity_title);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Intent extras = getIntent();
        sequenceType = (int)(extras.getLongExtra("sequence_type", DNA_SEQUENCE));
        DnaIsMatrix = extras.getBooleanExtra("DNA_is_matrix", false);
        mainSequence = extras.getStringExtra("sequence");
        firstResult = extras.getStringExtra("firstResult");
        secondResult = extras.getStringExtra("secondResult");
        thirdResult = extras.getStringExtra("thirdResult");

        sequenceName = findViewById(R.id.sequence_name);
        secondExpl = findViewById(R.id.second_expl);
        thirdExpl = findViewById(R.id.third_expl);
        linkedText = findViewById(R.id.linked);
        linkedText.setMovementMethod(LinkMovementMethod.getInstance());

        HandleRecyclers handler = new HandleRecyclers();
        handler.execute();


        final ScrollView scroll = findViewById(R.id.scrollView3);
        expansionLayout = findViewById(R.id.expansionLayout);
        expansionLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded) {
                    scroll.fullScroll(View.FOCUS_DOWN);
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


    class HandleRecyclers extends AsyncTask<Void, Void, ArrayList> {

        @Override
        protected ArrayList doInBackground(Void... voids) {

            ArrayList<PairModel> pairs1 = new ArrayList<>();
            ArrayList<PairModel> pairs2 = new ArrayList<>();
            ArrayList<CodonAminoacidPairModel> pairs3 = new ArrayList<>();

            String[] sequenceNucleotidesPre = mainSequence.split("");
            String[] sequenceNucleotides = new String[sequenceNucleotidesPre.length - 1];
            if (sequenceNucleotidesPre.length - 1 >= 0)
                System.arraycopy(sequenceNucleotidesPre, 1, sequenceNucleotides, 0, sequenceNucleotidesPre.length - 1);
            String[] firstResultNucleotidesPre = firstResult.split("");
            String[] firstResultNucleotides = new String[firstResultNucleotidesPre.length - 1];
            if (sequenceNucleotidesPre.length - 1 >= 0)
                System.arraycopy(firstResultNucleotidesPre, 1, firstResultNucleotides, 0, sequenceNucleotidesPre.length - 1);



            switch (sequenceType) {
                case DNA_SEQUENCE:
                    sequenceName.setText(getResources().getString(R.string.DNA));
//                    String[] secondResult = new String[] {};
                    if (!DnaIsMatrix) {
                        linkedText.setText(R.string.DNA_transcription_explanation);
                        secondExpl.setText(R.string.DNA_translation_explanation);
                        thirdExpl.setText(R.string.DNA_acid_synthesis_explanation);
//                        secondResult = parseTRNA();
                    } else {
                        linkedText.setText(R.string.DNA_is_matrix_first_step);
                        secondExpl.setText(R.string.DNA_is_matrix_second_step);
                        thirdExpl.setText(R.string.DNA_is_matrix_third_step);
                        ((TextView) findViewById(R.id.first_result_bottom)).setText(R.string.tRNA);
                        ((TextView) findViewById(R.id.second_result_bottom)).setText(R.string.tRNA);
                        ((TextView) findViewById(R.id.second_result_top)).setText(R.string.iRNA);
                        ((ImageView)findViewById(R.id.arrow_2)).setImageResource(R.drawable.ic_up_arrow);
                    }

                    String[] secondResult = parseTRNA();

                    if (DnaIsMatrix) {
                        for (int i = 0; i < sequenceNucleotides.length; i++) {
                            pairs1.add(
                                    new PairModel(
                                            getNucleotideType(sequenceNucleotides[i]),
                                            getNucleotideType(secondResult[i]),
                                            new NucleotidesPair(getApplicationContext())
                                    )
                            );
                        }
                    } else {
                        for (int i = 0; i < sequenceNucleotides.length; i++) {
                            pairs1.add(
                                    new PairModel(
                                            getNucleotideType(sequenceNucleotides[i]),
                                            getNucleotideType(firstResultNucleotides[i]),
                                            new NucleotidesPair(getApplicationContext())
                                    )
                            );
                        }
                    }


                    for (int i = 0; i < secondResult.length; i++) {
                        pairs2.add(
                                new PairModel(
                                        getNucleotideType(firstResultNucleotides[i]),
                                        getNucleotideType(secondResult[i]),
                                        new NucleotidesPair(getApplicationContext())
                                )
                        );
                    }

                    String[] aminoacid = parseAminoacid();
                    int j = 0;
                    for (int i = 0; i < aminoacid.length; i++, j += 3) {
                        pairs3.add(
                                new CodonAminoacidPairModel(
                                        getNucleotideType(firstResultNucleotides[j]),
                                        getNucleotideType(firstResultNucleotides[j + 1]),
                                        getNucleotideType(firstResultNucleotides[j + 2]),
                                        aminoacid[i],
                                        new CodonAminoacidPair(getApplicationContext())
                                )
                        );
                    }

                    break;

                case IRNA_SEQUENCE:
                    sequenceName.setText(getResources().getString(R.string.iRNA));
                    linkedText.setText(R.string.iRNA_transcription_explanation);
                    secondExpl.setText(R.string.DNA_translation_explanation);
                    thirdExpl.setText(R.string.DNA_acid_synthesis_explanation);
                    ((ImageView)findViewById(R.id.arrow_1)).setImageResource(R.drawable.ic_up_arrow);

                    for (int i = 0; i < sequenceNucleotides.length; i++) {
                        pairs1.add(
                                new PairModel(
                                        getNucleotideType(firstResultNucleotides[i]),
                                        getNucleotideType(sequenceNucleotides[i]),
                                        new NucleotidesPair(getApplicationContext())
                                )
                        );
                    }

                    secondResult = parseTRNA();

                    for (int i = 0; i < secondResult.length; i++) {
                        pairs2.add(
                                new PairModel(
                                        getNucleotideType(sequenceNucleotides[i]),
                                        getNucleotideType(secondResult[i]),
                                        new NucleotidesPair(getApplicationContext())
                                )
                        );
                    }

                    aminoacid = parseAminoacid();

                    j = 0;
                    for (int i = 0; i < aminoacid.length; i++, j += 3) {
                        pairs3.add(
                                new CodonAminoacidPairModel(
                                        getNucleotideType(sequenceNucleotides[j]),
                                        getNucleotideType(sequenceNucleotides[j + 1]),
                                        getNucleotideType(sequenceNucleotides[j + 2]),
                                        aminoacid[i],
                                        new CodonAminoacidPair(getApplicationContext())
                                )
                        );
                    }

                    break;


                case TRNA_SEQUENCE:
                    sequenceName.setText(getResources().getString(R.string.tRNA));
                    linkedText.setText(R.string.tRNA_translation_explanation);
                    secondExpl.setText(R.string.tRNA_transcription_explanation);
                    ((TextView) findViewById(R.id.first_result_top)).setText(R.string.iRNA);
                    ((TextView) findViewById(R.id.first_result_bottom)).setText(R.string.tRNA);
                    ((TextView) findViewById(R.id.second_result_top)).setText(R.string.DNA);
                    ((TextView) findViewById(R.id.second_result_bottom)).setText(R.string.iRNA);
                    ((ImageView)findViewById(R.id.arrow_1)).setImageResource(R.drawable.ic_up_arrow);
                    ((ImageView)findViewById(R.id.arrow_2)).setImageResource(R.drawable.ic_up_arrow);

                    secondResult = parseIRNAforTRNA();

                    for (int i = 0; i < sequenceNucleotides.length; i++) {
                        pairs1.add(
                                new PairModel(
                                        getNucleotideType(secondResult[i]),
                                        getNucleotideType(sequenceNucleotides[i]),
                                        new NucleotidesPair(getApplicationContext())
                                )
                        );
                    }

                    firstResultNucleotides = parseDNAforTRNA();

                    for (int i = 0; i < sequenceNucleotides.length; i++) {
                        pairs2.add(
                                new PairModel(
                                        getNucleotideType(firstResultNucleotides[i]),
                                        getNucleotideType(secondResult[i]),
                                        new NucleotidesPair(getApplicationContext())
                                )
                        );
                    }

                    aminoacid = parseAminoacid();

                    j = 0;
                    for (int i = 0; i < aminoacid.length; i++, j += 3) {
                        pairs3.add(
                                new CodonAminoacidPairModel(
                                        getNucleotideType(secondResult[j]),
                                        getNucleotideType(secondResult[j + 1]),
                                        getNucleotideType(secondResult[j + 2]),
                                        aminoacid[i],
                                        new CodonAminoacidPair(getApplicationContext())
                                )
                        );
                    }

                    break;
            }

            ArrayList result = new ArrayList();
            result.add(new SimplePairsAdapter(pairs1, getApplicationContext()));
            result.add(new SimplePairsAdapter(pairs2, getApplicationContext()));
            result.add(new AminoacidAdapter(pairs3, getApplicationContext()));

            return result;

        }

        @Override
        protected void onPostExecute(ArrayList explanationAdapter) {
            super.onPostExecute(explanationAdapter);
            LinearLayoutManager horizontalLayout
                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

            firstResultList = findViewById(R.id.first_tesult_list);
            firstResultList.setAdapter((RecyclerView.Adapter)explanationAdapter.get(0));
            findViewById(R.id.progressBar_1).setVisibility(View.GONE);
            firstResultList.setLayoutManager(horizontalLayout);


            LinearLayoutManager horizontalLayout1
                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            RecyclerView secondResultList = findViewById(R.id.second_result_list);
            secondResultList.setAdapter((RecyclerView.Adapter)explanationAdapter.get(1));
            findViewById(R.id.progressBar_2).setVisibility(View.GONE);
            secondResultList.setLayoutManager(horizontalLayout1);


            LinearLayoutManager horizontalLayout2
                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            RecyclerView thirdResultList = findViewById(R.id.third_result_list);
            thirdResultList.setAdapter((RecyclerView.Adapter)explanationAdapter.get(2));
            findViewById(R.id.progressBar_3).setVisibility(View.GONE);
            thirdResultList.setLayoutManager(horizontalLayout2);

        }
    }

    private int getNucleotideType(String nucleotide) {
        if (nucleotide.equals(getResources().getString(R.string.adenine))) {
            return ADENINE;
        }
        if (nucleotide.equals(getResources().getString(R.string.uracil))) {
            return URACIL;
        }
        if (nucleotide.equals(getResources().getString(R.string.guanine)) ) {
            return GUANINE;
        }
        if (nucleotide.equals(getResources().getString(R.string.cytosine)) ) {
            return CYTOSINE;
        }
        if (nucleotide.equals(getResources().getString(R.string.thymine))) {
            return THYMINE;
        }

        return -1;
    }

    private String[] parseDNAforTRNA() {

        String[] firstResultNucleotidesPre = firstResult.split("");
        String[] firstResultNucleotides = new String[firstResultNucleotidesPre.length - 1];
        if (firstResultNucleotidesPre.length - 1 >= 0)
            System.arraycopy(firstResultNucleotidesPre, 1, firstResultNucleotides, 0, firstResultNucleotidesPre.length - 1);

        return firstResultNucleotides;
    }

    private String[] parseIRNAforTRNA() {

        String[] iRNAsequencePre = secondResult.split("");
        String[] iRNAsequence = new String[iRNAsequencePre.length - 1];

        if (iRNAsequencePre.length - 1 >= 0) {
            System.arraycopy(iRNAsequencePre, 1, iRNAsequence, 0, iRNAsequencePre.length - 1);
        }

        return iRNAsequence;

    }

    private String[] parseTRNA() {
        String[] secondResultPrePre = secondResult.split("; ");
        StringBuilder formattedSecondResult = new StringBuilder();
        for (String s : secondResultPrePre) {
            formattedSecondResult.append(s);
        }
        String[] secondResultPre = formattedSecondResult.toString().split("");
        String[] secondResult = new String[secondResultPre.length - 1];
        if (secondResultPre.length - 1 >= 0)
            System.arraycopy(secondResultPre, 1, secondResult, 0, secondResultPre.length - 1);

        return secondResult;
    }

    private String[] parseAminoacid() {
        String[] aminoacidPre = thirdResult.split("-");
        String[] aminoacid = new String[aminoacidPre.length - 1];

        if (aminoacidPre.length - 1 >= 0)
            System.arraycopy(aminoacidPre, 1, aminoacid, 0, aminoacidPre.length - 1);

        return aminoacid;
    }

}
