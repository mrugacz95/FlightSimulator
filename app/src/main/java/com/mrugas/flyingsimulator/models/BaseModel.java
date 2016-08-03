package com.mrugas.flyingsimulator.models;

import android.opengl.Matrix;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.Utilities.Matrix4;
import com.mrugas.flyingsimulator.Utilities.Quaternion;
import com.mrugas.flyingsimulator.Utilities.Vector3;

/**
 * Created by mruga on 01.08.2016.
 */
public abstract class BaseModel {

    protected Matrix4 mModelMatrix = new Matrix4();

    protected Vector3 position = new Vector3(0.f);
    protected Vector3 rotation = new Vector3(0.f);
    protected Quaternion quaternionRotation = new Quaternion();
    protected Vector3 scale = new Vector3(1.f);
    BaseModel(){
       mModelMatrix.idt();
    }
    public abstract void init();
    public void draw(){
        mModelMatrix.idt();
        mModelMatrix.scale(scale);
        mModelMatrix.rotate(quaternionRotation);
        mModelMatrix.translate(position);
//        Matrix.translateM(mModelMatrix,0,position.x,position.y,position.z);
//        Matrix.scaleM(mModelMatrix,0,scale.x,scale.y,scale.z);
//        Matrix.multiplyMM(mModelMatrix,0,qut,0, mModelMatrix,0);
//        Matrix.rotateM(mModelMatrix,0,rotation.x,1.f,0.f,0.f);
//        Matrix.rotateM(mModelMatrix,0,rotation.y,0.f,1.f,0.f);
//        Matrix.rotateM(mModelMatrix,0,rotation.z,0.f,0.f,1.f);
        //Matrix.rotateM(mModelMatrix,0,f,vec.x,vec.y,vec.z);
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

    public void rotate(float x, float y, int z){
        Quaternion q = Quaternion.Euler(x,y,z);
        quaternionRotation.mul(q);
    }
}
