package com.scoutlabour.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.scoutlabour.app.MyApplication;


/**
 * Created by nirav on 10/10/15.
 */
public class NormalEditTextView extends AppCompatEditText {


    public NormalEditTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public NormalEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public NormalEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getNormalFont());
        }
    }
}