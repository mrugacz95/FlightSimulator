package com.mrugas.flightsimulator.Utilities;

import com.mrugas.flightsimulator.models.BaseModel;

/**
 * Created by mruga on 19.09.2016.
 */
public class Collision {
    BaseModel baseModel;
    public enum COLLISION_TYPE {TOP,SIDE,NONE};

    public Collision(BaseModel baseModel, COLLISION_TYPE collisionType) {
        this.baseModel = baseModel;
        this.collisionType = collisionType;
    }

    COLLISION_TYPE collisionType;

    public BaseModel getBaseModel() {
        return baseModel;
    }

    public COLLISION_TYPE getCollisionType() {
        return collisionType;
    }
}
