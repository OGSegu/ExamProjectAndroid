package com.segu.client;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RandomGeneratorMatcher extends TypeSafeMatcher<View> {
    @Override
    protected boolean matchesSafely(View item) {
        EditText editText = (EditText) item;
        String value = editText.getText().toString();
        int editTextIntValue = -1;
        try {
            editTextIntValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            Log.e("MatchFormat", "Failed to cast text to int");
            return false;
        }
        return editTextIntValue >= 0 && editTextIntValue < Integer.MAX_VALUE;
    }

    @Override
    public void describeTo(Description description) {

    }
}
