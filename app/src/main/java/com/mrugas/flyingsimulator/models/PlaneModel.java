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
        return R.raw.cube;
    }

    @Override
    public void init() {
        super.init();
        //scale = new Vector3(4.f,4.f,4.f);
        //translate(5f,4f,12f);
    }

    @Override
    public void draw() {
        float time = SystemClock.uptimeMillis() / 1000.f;
        deltaTime = time - lastTime;
        lastTime=time;
//        if(rotation.y!=currentRotation){
//            if(rotation.y>currentRotation)
//                rotation.y-=deltaTime*rotationSpeed;
//            else
//                rotation.y+=deltaTime*rotationSpeed;
//        }

        //translate(deltaTime*speed*(float)Math.cos(rotation.x)*(float)Math.cos(rotation.y),deltaTime*speed*(float)Math.sin(rotation.y),deltaTime*speed*(float)Math.cos(rotation.z));
        //position.x = 5.f * (float)Math.sin(Math.toRadians(50*time));
        //position.z = 5.f * (float)Math.cos(Math.toRadians(50*time));
        //rotation.y = time*50f+90;
        //rotation.x = -20;
        this.rotate(2,0,0);
        //translate(time*Math.sin(Math.toDegrees(rotation.x)*Math.sin(Math.toDegrees(rotation.y)),0f,0f);
//        rotation.x = quaternionRotation.getPitch();
//        rotation.y = quaternionRotation.getYaw();
//        rotation.z = quaternionRotation.getRoll();
        super.draw();
    }


    public void setPlaneRotation(float rotation) {
        this.currentRotation = rotation;
    }
    public Matrix4 getModelViewMatrix(){
        return mModelMatrix;
    }
}
