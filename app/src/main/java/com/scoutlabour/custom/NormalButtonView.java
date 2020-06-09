package com.scoutlabour.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.scoutlabour.app.MyApplication;


/**
 * Created by nirav on 10/10/15.
 */
public class NormalButtonView extends AppCompatButton {


    public NormalButtonView(Context context) {
        super(context);
        setTypeface(context);
    }

    public NormalButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public NormalButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getNormalFont());
        }
    }
}