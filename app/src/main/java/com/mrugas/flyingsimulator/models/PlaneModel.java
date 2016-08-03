package com.mrugas.flyingsimulator.models;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.Utilities.Camera;
import com.mrugas.flyingsimulator.Utilities.Matrix4;
import com.mrugas.flyingsimulator.Utilities.Quaternion;
import com.mrugas.flyingsimulator.Utilities.Vector3;

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
    int getTextureResId() {
        return R.drawable.candalal;
    }

    @Override
    public int getMeshResourceId() {
        return R.raw.plane;
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
        this.rotate(1,0,0);
        Vector3 vec =new Vector3(0,0,0.1f);
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
