package com.app.dnasec;

import android.animation.ArgbEvaluator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import android.widget.Toast;

import com.app.dnasec.helpers.MutableBackgroundColorSpan;
import com.app.dnasec.helpers.Solver;


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


    private boolean codonsAreHighlighted;
    private boolean animationIsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Solver.init(this);
        codonsAreHighlighted = preferences.getBoolean("KEY_HIGHLIGHT", true);
        animationIsEnabled = preferences.getBoolean("KEY_ANIMATION", true);

        ADENINE = getResources().getString(R.string.adenine);
        GUANINE = getResources().getString(R.string.guanine);
        CYTOSINE = getResources().getString(R.string.cytosine);
        URACIL = getResources().getString(R.string.uracil);
        THYMINE = getResources().getString(R.string.thymine);

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
                            solvedArr = Solver.solveForMatrixDNA(sequence.getText().toString());
                        } else {
                            solvedArr = Solver.solveForDNA(sequence.getText().toString());
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
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.incomlete_sequence), Toast.LENGTH_LONG).show();
                        vibrate();
                        DnaIsMatrix.setChecked(!DnaIsMatrix.isChecked());
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
    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createWaveform(new long[] {200, 200}, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(200);
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
            if (spinner.getSelectedItem().toString().equals(getResources().getString(R.string.tRNA))) {
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
                String text = sequence.getText().toString();
                if (text.length() > 0) {
                    sequence.setText(text.substring(0, text.length() - 1));
                    text = sequence.getText().toString();
                    if (codonsAreHighlighted) {
                        color(sequence, ERASED);
                        color(firstResult, ERASED);
                    }
                }
                if (text.length() == 0) {
                    clearFields();
                }
                boolean validText = text.length() % 3 == 0 && text.length() != 0;
                if (validText) {
                    if (codonsAreHighlighted) {
                        color(firstResult, false);
                    }
                    beforeEnteringText.setText("");
                    explanation.setEnabled(true);

                } else if (text.length() % 3 != 0){
                    beforeEnteringText.setText(getResources().getString(R.string.incomlete_sequence));
                    explanation.setEnabled(false);
                }
                if (text.length() < 3) {
                    firstResult.setText("");
                    secondResult.setText("");
                    thirdResult.setText("");
                    return;
                }
                try {
                    String firstResultText = firstResult.getText().toString();
                    String secondResultText = secondResult.getText().toString();
                    String thirdResultText = thirdResult.getText().toString();
                    String firstSubstring = firstResultText.substring(0, firstResultText.length() - 3);
                    String secondSubstring = secondResultText.substring(0, secondResultText.length() - 5);
                    String thirdSubstring = thirdResultText.substring(0, thirdResultText.length() - 4);
                    boolean resultErasingCondition = text.length() % 3 != 0 && (text.length() - 1) % 3 != 0;
                    switch ((int) spinner.getSelectedItemId()) {
                        case ID_DNA:
                            if (validText) {
                                String[] solvedArr;
                                if (DnaIsMatrix.isChecked()) {
                                    solvedArr = Solver.solveForMatrixDNA(text);
                                } else {
                                    solvedArr = Solver.solveForDNA(text);
                                }

                                firstResult.setText(solvedArr[0]);
                                secondResult.setText(solvedArr[1]);
                                thirdResult.setText(solvedArr[2]);

                                if (codonsAreHighlighted) {
                                    color(firstResult, ERASED);
                                }
                            } else if (resultErasingCondition) {
                                try {
                                    firstResult.setText(firstSubstring);
                                    secondResult.setText(secondSubstring);
                                    thirdResult.setText(thirdSubstring);
                                } catch (StringIndexOutOfBoundsException e) {
                                    firstResult.setText("");
                                }
                            }
                            break;

                        case ID_mRNA:
                            if (validText) {
                                String[] solvedArr = Solver.solveForMRNA(text);
                                firstResult.setText(solvedArr[0]);
                                if (codonsAreHighlighted) {
                                    color(firstResult, ERASED);
                                }
                                secondResult.setText(solvedArr[1]);
                                thirdResult.setText(solvedArr[2]);
                            } else if (resultErasingCondition) {
                                try {
                                    firstResult.setText(firstSubstring);
                                    secondResult.setText(secondSubstring);
                                    thirdResult.setText(thirdSubstring);
                                } catch (StringIndexOutOfBoundsException e) {
                                    firstResult.setText("");
                                }
                            }
                            break;

                        case ID_tRNA:
                            if (validText) {
                                String[] solvedArr = Solver.solveForTRNA(sequence.getText().toString());
                                firstResult.setText(solvedArr[0]);
                                secondResult.setText(solvedArr[1]);
                                if (codonsAreHighlighted) {
                                    color(firstResult, ERASED);
                                    color(secondResult, ERASED);
                                }
                                thirdResult.setText(solvedArr[2]);
                            } else if (resultErasingCondition) {
                                try {
                                    firstResult.setText(firstSubstring);
                                    secondResult.setText(secondResultText.substring(0, secondResultText.length() - 3));
                                    thirdResult.setText(thirdSubstring);
                                } catch (StringIndexOutOfBoundsException e) {
//                                firstResult.setText("-");
                                }
                            }
                            break;

                    }
                } catch (Exception e) {}
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
                            optimizedSolved = Solver.solveLastCodonMatrixDNA(sequence.getText().toString());

                        } else {
                            optimizedSolved = Solver.solveLastCodonDNA(sequence.getText().toString());
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

                        String[] optimizedSolved = Solver.solveLastCodonMRNA(sequence.getText().toString());
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

                        String[] optimizedSolved = Solver.solveLastCodonTRNA(sequence.getText().toString());
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
