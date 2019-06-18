package com.app.dnasec.helpers;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class MutableClickableSpan extends ClickableSpan {
    
    private String content;
    private Context mContext;
    private boolean underlined;

    public MutableClickableSpan(String content, Context mContext) {
        this.content = content;
        this.mContext = mContext;
    }

//    public abstract void onClick(@NonNull View widget);

    @Override
    public void onClick(@NonNull View widget) {
        setUnderlined(true);
    }

    public void updateDrawState(TextPaint ds) {// override updateDrawState
        ds.setUnderlineText(underlined); // set to false to remove underline
    }

    public String getContent() {
        return content;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }
}
