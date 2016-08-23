package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.os.SystemClock;

import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.Utilities.Matrix4;
import com.mrugas.flightsimulator.Utilities.Vector3;

/**
 * Created by mruga on 01.08.2016.
 */
public class PlaneModel extends TexturedModel {

    private float currentRotation=0;

    float speed = 0.5f;
    float rotationSpeed = 20f;
    float lastTime;
    float deltaTime;
    public PlaneModel(int programHandle, Context context) {
        super(programHandle, context);
        lastTime = SystemClock.uptimeMillis() / 1000.f;
    }

    @Override
    Integer getTextureResId() {
        return R.drawable.candalal;
    }

    @Override
    public int getMeshResourceId() {
        return R.raw.cube;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void draw() {
        float time = SystemClock.uptimeMillis() / 1000.f;
        deltaTime = time - lastTime;
        lastTime=time;
        rotate(currentRotation,0,0);
        Vector3 vec =new Vector3(0,0,0.03f);
        vec.mul(quaternionRotation);
        translate(vec);
        super.draw();
    }


    public void setPlaneRotation(float rotation) {
        this.currentRotation = rotation;
    }
    public Matrix4 getModelViewMatrix(){
        return mModelMatrix;
    }
}
