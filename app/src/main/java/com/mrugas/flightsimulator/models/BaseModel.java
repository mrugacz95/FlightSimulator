package com.mrugas.flightsimulator.models;

import com.mrugas.flightsimulator.Utilities.Matrix4;
import com.mrugas.flightsimulator.Utilities.Quaternion;
import com.mrugas.flightsimulator.Utilities.Vector3;

/**
 * Created by mruga on 01.08.2016.
 */
public abstract class BaseModel {

    protected Matrix4 mModelMatrix = new Matrix4();

    protected Vector3 position = new Vector3(0.f);
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
        }

    public void translate(float x, float y, float z){
        position.x+=x;
        position.y+=y;
        position.z+=z;
    }
    public void translate(Vector3 vec){
        position.x+=vec.x;
        position.y+=vec.y;
        position.z+=vec.z;
    }

    public void scale(float x, float y, float z){
        scale.x+=x;
        scale.y+=y;
        scale.z+=z;
    }

    public Vector3 getPosition() {
        return position;
    }
    public Quaternion getRotation(){ return quaternionRotation; }

    public void rotate(float x, float y, float z){
        Quaternion q = Quaternion.Euler(x,y,z);
        quaternionRotation.mul(q);
    }
    public void rotate(Quaternion q){
        quaternionRotation.mul(q);
    }
}
