
package com.app.dnasec.helpers;

import android.graphics.Color;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;

public class MutableBackgroundColorSpan extends BackgroundColorSpan {

    private int mAlpha = 255;
    private int mBackgroundColor;

    public MutableBackgroundColorSpan(int alpha, int color) {
        super(color);
        mAlpha = alpha;
        mBackgroundColor = color;
    }

    public MutableBackgroundColorSpan(Parcel src) {
        super(src);
        mBackgroundColor = src.readInt();
        mAlpha = src.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mBackgroundColor);
        dest.writeFloat(mAlpha);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.bgColor = getBackgroundColor();
    }

    /**
     * @param alpha from 0 to 255
     */
    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public float getAlpha() {
        return mAlpha;
    }

    @Override
    public int getBackgroundColor() {
        return Color.argb(mAlpha, Color.red(mBackgroundColor), Color.green(mBackgroundColor), Color.blue(mBackgroundColor));
    }
}