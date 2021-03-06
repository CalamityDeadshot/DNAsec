package com.app.dnasec;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    SharedPreferences.Editor preferencesEditor;

    SwitchCompat highlightCodonsSwitch;
    SwitchCompat enableAnimationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(R.string.settings);



        highlightCodonsSwitch = findViewById(R.id.highlight_codons_switch);
        enableAnimationSwitch = findViewById(R.id.enable_animation_switch);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferencesEditor = preferences.edit();
        highlightCodonsSwitch.setChecked(preferences.getBoolean("KEY_HIGHLIGHT", true));
        enableAnimationSwitch.setChecked(preferences.getBoolean("KEY_ANIMATION", true));

        highlightCodonsSwitch.setOnCheckedChangeListener(this);
        enableAnimationSwitch.setOnCheckedChangeListener(this);


        findViewById(R.id.enable_animation_desc).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            preferencesEditor.apply();
            finish();
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            preferencesEditor.apply();
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.highlight_codons_switch:
                preferencesEditor.putBoolean("KEY_HIGHLIGHT", isChecked);
                break;

            case R.id.enable_animation_switch:
                preferencesEditor.putBoolean("KEY_ANIMATION", isChecked);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enable_animation_desc:
                enableAnimationSwitch.performClick();

        }
    }
}
