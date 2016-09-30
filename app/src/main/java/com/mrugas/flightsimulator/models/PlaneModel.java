package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mrugas.flightsimulator.MainActivity;
import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.Utilities.Collision;
import com.mrugas.flightsimulator.Utilities.Matrix4;
import com.mrugas.flightsimulator.Utilities.Quaternion;
import com.mrugas.flightsimulator.Utilities.Vector3;
import com.mrugas.flightsimulator.managers.Texture;
import com.mrugas.flightsimulator.scenes.Scene;
import com.mrugas.flightsimulator.scenes.SceneManager;

/**
 * Created by mruga on 01.08.2016.
 */
public class PlaneModel extends TexturedModel {

    private float currentRotation=0;

    boolean crashed = false;
    float speed = 0.0f;
    float rotationSpeed = 20f;
    float lastTime;
    float deltaTime;
    public PlaneModel(int programHandle, Context context) {
        super(programHandle, context);
        lastTime = SystemClock.uptimeMillis() / 1000.f;
    }
    TexturedModel explosion;

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
        explosion = new TexturedModel(this.programHandle,context,R.raw.particle,R.drawable.explosion);
        explosion.init();
    }
    public static final int WORLD_SIZE=350;
    @Override
    public void draw() {
        if(crashed){
            explosion.setPosition(this.position);
            if(explosion.getScale().x > 2.5f)
                explosion.setScale(new Vector3(1,1,1));
            else
                explosion.scale(0.01f,0.01f,0.01f);
            explosion.draw();
            return;
        }
        float time = SystemClock.uptimeMillis() / 1000.f;
        deltaTime = time - lastTime;
        lastTime=time;
        rotate(currentRotation,0,0);
        Vector3 currentPosition =position.cpy();
        Vector3 vec =new Vector3(0,0,speed);
        vec.mul(rotation);
        translate(vec.add(new Vector3(0,-1/(speed*120+20),0)));
        BaseModel collider =SceneManager.getInstance().getCurrentScene().isColiding(position);
        if( collider != null) {
            TexturedModel landing = (TexturedModel) SceneManager.getInstance().getCurrentScene().getModel("landing");
            if(collider != landing){
                explode();
                position = currentPosition;
            }
            else{
                Float topOfLanding = landing.getBounds().getTopWithPos();
                position = new Vector3(this.position.x,topOfLanding,position.z);
            }
        }
        if(position.y<0) {
            position.y = 0;
            explode();
        }
        if(position.x>WORLD_SIZE) position.x=-WORLD_SIZE;
        if(position.x<-WORLD_SIZE) position.x=WORLD_SIZE;
        if(position.z>WORLD_SIZE) position.z=-WORLD_SIZE;
        if(position.z<-WORLD_SIZE) position.z=WORLD_SIZE;
        super.draw();
    }


    public void setPlaneRotation(float rotation) {
        if(!crashed)this.currentRotation = rotation;
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
    }
    void explode(){
        Log.d("Explode","Boom");
        crashed = true;
        final Button restertButton = (Button) ((MainActivity)context).findViewById(R.id.bt_restart);
        Runnable restartButtonRunnable = new Runnable(){
            @Override
            public void run() {
                restertButton.setVisibility(View.VISIBLE);
                restertButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        restertButton.setVisibility(View.GONE);
                        crashed=false;
                        setPosition(new Vector3(-20,9f,7));
                        setRotation(new Quaternion());
                        speed=0;
                    }
                });
            }
        };
        Handler handler = new Handler(context.getMainLooper());
        handler.post(restartButtonRunnable);
    }

    @Override
    public boolean isCollidable() {
        return false;
    }
}
