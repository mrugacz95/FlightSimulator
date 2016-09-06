package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.os.SystemClock;

import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.Utilities.Matrix4;
import com.mrugas.flightsimulator.Utilities.Quaternion;
import com.mrugas.flightsimulator.Utilities.Vector3;
import com.mrugas.flightsimulator.scenes.Scene;
import com.mrugas.flightsimulator.scenes.SceneManager;

/**
 * Created by mruga on 01.08.2016.
 */
public class PlaneModel extends TexturedModel {

    private float currentRotation=0;


    float speed = 0.0f;
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
        return R.raw.plane;
    }

    @Override
    public void init() {
        super.init();
    }
    public static final int WORLD_SIZE=350;
    @Override
    public void draw() {
        float time = SystemClock.uptimeMillis() / 1000.f;
        deltaTime = time - lastTime;
        lastTime=time;
        rotate(currentRotation,0,0);
        Vector3 currentPosition =position.cpy();
        Vector3 vec =new Vector3(0,0,speed);
        vec.mul(rotation);
        translate(vec);
        if( SceneManager.getInstance().getCurrentScene().isColiding(position)) {
            position = currentPosition;
            speed=0;
        }
        currentPosition=position.cpy();
        translate(new Vector3(0,-1/(speed*120+20),0));
        if(SceneManager.getInstance().getCurrentScene().isColiding(position)) {
            position = currentPosition;
            //rotation.setEulerAngles(rotation.getYaw(),0,rotation.getRoll());
            //speed=0;
        }
        if(position.y<0) {
            position.y = 0;
            //rotation= Quaternion.Euler(rotation.getPitch(),0,rotation.getRoll());
        }
        if(position.x>WORLD_SIZE) position.x=-WORLD_SIZE;
        if(position.x<-WORLD_SIZE) position.x=WORLD_SIZE;
        if(position.z>WORLD_SIZE) position.z=-WORLD_SIZE;
        if(position.z<-WORLD_SIZE) position.z=WORLD_SIZE;
        super.draw();
    }


    public void setPlaneRotation(float rotation) {
        this.currentRotation = rotation;
    }
    public Matrix4 getModelViewMatrix(){
        return mModelMatrix;
    }

    public void speedUp() {
        speed+=0.01;
    }

    public float getSpeed() {
        return speed;
    }

    public void slowDown() {
        speed/=2;
        if(speed<0.02) speed=0;
        //speed=Math.max(0,speed);
    }

    @Override
    public boolean isCollidable() {
        return false;
    }
}
