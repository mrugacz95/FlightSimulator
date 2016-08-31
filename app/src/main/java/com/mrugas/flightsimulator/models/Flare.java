package com.mrugas.flightsimulator.models;

import android.content.Context;

import com.mrugas.flightsimulator.R;

/**
 * Created by mruga on 27.08.2016.
 */
public class Flare extends Quad {
    public Flare(int programHandle, Context context) {
        super(programHandle, context);
    }

    @Override
    Integer getTextureResId() {
        return R.drawable.flare1;
    }
}
