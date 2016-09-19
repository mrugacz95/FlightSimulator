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



    protected Quaternion rotation = new Quaternion();



    protected Vector3 scale = new Vector3(1.f);

    public BaseModel(){
       mModelMatrix.idt();
    }

    public abstract void init();
    public void update(){};
    public void draw(){
        mModelMatrix.idt();
        mModelMatrix.scale(scale);
        mModelMatrix.rotate(rotation);
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
    public void setPosition(Vector3 position){
        this.position=position;
    }

    public void scale(float x, float y, float z){
        scale.x+=x;
        scale.y+=y;
        scale.z+=z;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }
    public Vector3 getPosition() {
        return position;
    }
    public Quaternion getRotation(){ return rotation; }

    public void rotate(float x, float y, float z){
        Quaternion q = Quaternion.Euler(x,y,z);
        rotation.mul(q);
    }
    public void rotate(Quaternion q){
        rotation.mul(q);
    }

    public Vector3 getScale() {
        return scale;
    }
    public boolean isCollidable(){
        return false;
    }
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }
}
