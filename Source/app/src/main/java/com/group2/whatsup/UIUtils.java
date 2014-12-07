package com.group2.whatsup;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.group2.whatsup.Managers.SettingsManager;

public class UIUtils {
    //region Text Retrieval Methods
    public static String getText(EditText txt){
        return txt.getText().toString();
    }
    public static int getInt(EditText txt){
        String text = getText(txt);
        return Integer.parseInt(text);
    }
    public static double getDouble(EditText txt){
        String text = getText(txt);
        return Double.parseDouble(text);
    }
    public static float getFloat(EditText txt){
        String text = getText(txt);
        return Float.parseFloat(text);
    }
    public static long getLong(EditText txt){
        String text = getText(txt);
        return Long.parseLong(text);
    }
    public static long getLong(EditText txt, int radix){
        String text = getText(txt);
        return Long.parseLong(text, radix);
    }
    //endregion

    //region Theme Methods
    public static void ThemeButton(Button b){
        b.setBackgroundColor(SettingsManager.Instance().PrimaryColor());
        b.setTextColor(SettingsManager.Instance().SecondaryColor());
        SpannableString spanString = new SpannableString(b.getText());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        b.setText(spanString);
    }

    public static void ThemeButtons(Button... btns){
        for(Button b : btns){
            ThemeButton(b);
        }
    }

    public static void ThemeTextbox(EditText txt) {
        /*
        ApplyTheme(txt, "#333333", "#FFFFFF", 1);
        txt.setTextColor(Color.BLACK);
        */
    }

    public static void ThemeTextboxes(EditText... txts){
        for(EditText txt : txts){
            ThemeTextbox(txt);
        }
    }

    private static void ApplyTheme(View targetView, String borderColor, String backgroundColor, int width){

        //Border
        RectShape borderRect = new RectShape();
        ShapeDrawable rectShapeDrawable = new ShapeDrawable(borderRect);
        Paint paint = rectShapeDrawable.getPaint();
        paint.setColor(Color.parseColor(borderColor));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);

        //Background
        RectShape backgroundRect = new RectShape();
        ShapeDrawable backgroundDrawable = new ShapeDrawable(backgroundRect);
        Paint bgPaint = backgroundDrawable.getPaint();
        bgPaint.setColor(Color.parseColor(backgroundColor));
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeWidth(0);


        Drawable[] layers = {backgroundDrawable, rectShapeDrawable};

        //Full Layers.
        LayerDrawable fullLayers = new LayerDrawable(layers);

        // apply to layout
        // my singleButton here is actually a relative layout
        targetView.setBackground(fullLayers);
        int viewPadding = 10;
        targetView.setPadding(viewPadding, viewPadding, viewPadding, viewPadding);
    }
    //endregion
}


