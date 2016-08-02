package com.mrugas.flyingsimulator.models;

import android.opengl.Matrix;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.Utilities.Vector3;

/**
 * Created by mruga on 01.08.2016.
 */
public abstract class BaseModel {

    protected float[] mModelMatrix = new float[16];

    protected Vector3 position = new Vector3(0.f);
    protected Vector3 rotation = new Vector3(0.f);
    protected Vector3 scale = new Vector3(1.f);
    BaseModel(){
        Matrix.setIdentityM(mModelMatrix,0);
    }
    public void draw(){
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix,0,position.x,position.y,position.z);
        Matrix.scaleM(mModelMatrix,0,scale.x,scale.y,scale.z);
        Matrix.rotateM(mModelMatrix,0,rotation.x,1.f,0.f,0.f);
        Matrix.rotateM(mModelMatrix,0,rotation.y,0.f,1.f,0.f);
        Matrix.rotateM(mModelMatrix,0,rotation.z,0.f,0.f,1.f);
        }
    int getMeshResourceId(){
        return R.raw.cube;
    };
    public void translate(float x, float y, float z){
        position.x+=x;
        position.y+=y;
        position.z+=z;
    }

    public void scale(float x, float y, float z){
        scale.x+=x;
        scale.y+=y;
        scale.z+=z;
    }

    public Vector3 getPosition() {
        return position;
    }
    public Vector3 getRotation(){ return rotation; }

}
