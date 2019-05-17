package com.app.dnasec;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(R.string.settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Switch highlightCodonsSwitch = findViewById(R.id.highlight_codons_switch);

        preferencesEditor = preferences.edit();
        highlightCodonsSwitch.setChecked(preferences.getBoolean("KEY_HIGHLIGHT", false));

        highlightCodonsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferencesEditor.putBoolean("KEY_HIGHLIGHT", isChecked);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                preferencesEditor.apply();
                finish();
                break;
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
}
