package com.mrugas.flyingsimulator.models;

import android.content.Context;

import com.mrugas.flyingsimulator.R;

/**
 * Created by mruga on 01.08.2016.
 */
public class PlaneModel extends TexturedModel {
    public PlaneModel(int programHandle, Context context) {
        super(programHandle, context);
    }

    @Override
    int getTextureId() {
        return R.drawable.usb_android;
    }

    @Override
    public int getMeshResourceId() {
        return R.raw.cube;
    }
}
