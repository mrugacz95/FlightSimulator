package com.mrugas.flightsimulator.Utilities;

import com.mrugas.flightsimulator.models.BaseModel;

/**
 * Created by mruga on 06.09.2016.
 */
public class Bounds {
    Float front;
    Float back;
    Float bottom;
    Float top;
    Float left;
    Float right;
    Float frontWithPos;
    Float backWithPos;
    Float bottomWithPos;
    Float topWithPos;
    Float leftWithPos;
    Float rightWithPos;

    public Bounds(Float front, Float back, Float bottom, Float top, Float left, Float right) {
        this.frontWithPos =this.front= front;
        this.backWithPos = this.back= back;
        this.bottomWithPos =this.bottom= bottom;
        this.topWithPos =this.top= top;
        this.leftWithPos = this.left =left;
        this.rightWithPos = this.right = right;
    }

    public boolean contains(Vector3 point){
        return topWithPos > point.y && rightWithPos > point.x && leftWithPos < point.x &&
                backWithPos > point.z && frontWithPos < point.z;


    }
    public void setModelPosition(BaseModel model){
        topWithPos =top;
        bottomWithPos =bottom;
        frontWithPos =front;
        backWithPos =back;
        leftWithPos =left;
        rightWithPos =right;

        topWithPos*=model.getScale().y;
        bottomWithPos*=model.getScale().y;
        frontWithPos*=model.getScale().z;
        backWithPos*=model.getScale().z;
        leftWithPos*=model.getScale().x;
        rightWithPos*=model.getScale().x;

        topWithPos +=model.getPosition().y;
        bottomWithPos +=model.getPosition().y;
        frontWithPos +=model.getPosition().z;
        backWithPos +=model.getPosition().z;
        leftWithPos +=model.getPosition().x;
        rightWithPos +=model.getPosition().x;
    }
    public Float getTopWithPos(){
        return topWithPos;
    }

}
