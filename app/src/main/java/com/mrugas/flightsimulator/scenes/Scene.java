package com.mrugas.flightsimulator.scenes;

import android.app.Activity;
import android.opengl.GLES30;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.mrugas.flightsimulator.MainActivity;
import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.TextureHelper;
import com.mrugas.flightsimulator.Utilities.Bounds;
import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.Utilities.Collision;
import com.mrugas.flightsimulator.Utilities.RotationGestureDetector;
import com.mrugas.flightsimulator.Utilities.Vector3;
import com.mrugas.flightsimulator.managers.ShaderManger;
import com.mrugas.flightsimulator.models.BaseModel;
import com.mrugas.flightsimulator.models.ParticleSystem.ParticleSystem;
import com.mrugas.flightsimulator.models.PlaneModel;
import com.mrugas.flightsimulator.models.Quad;
import com.mrugas.flightsimulator.models.Water;
import com.mrugas.flightsimulator.models.Skybox;
import com.mrugas.flightsimulator.models.TexturedModel;

import java.util.HashMap;

/**
 * Created by mruga on 01.08.2016.
 */
public class Scene implements RotationGestureDetector.OnRotationGestureListener {
    HashMap<String,BaseModel> models = new HashMap<>();
    RotationGestureDetector rotationGestureDetector;
    Runnable logic;
    public void init(final Activity mActivity){
        rotationGestureDetector = new RotationGestureDetector(this);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gestureDetector = new GestureDetector(mActivity,new GestureListener());
            }
        });
        ShaderManger.getInstance().addProgram(R.raw.simple_vertex_shader,R.raw.texture_fragment_shader,"texture_program",mActivity);
        ShaderManger.getInstance().addProgram(R.raw.simple_vertex_shader,R.raw.simple_fragment_shader,"simple_program",mActivity);
        ShaderManger.getInstance().addProgram(R.raw.skybox_vertex_shader,R.raw.skybox_fragment_shader,"skybox_program",mActivity);
        ShaderManger.getInstance().addProgram(R.raw.water_vertex_shader,R.raw.water_fragment_shader,"water_program",mActivity);

//        BaseModel cube = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), activity, R.raw.cube, R.drawable.uv_checker_large);
//        cube.translate(0,-25,0);
//        cube.scale(50,50,50);
//        models.put("cube", cube);

        BaseModel quad = new Water(ShaderManger.getInstance().getProgramHandle("water_program"),mActivity);
        models.put("quad", quad);
        quad.scale(600,1,600);

        PlaneModel plane = new PlaneModel(ShaderManger.getInstance().getProgramHandle("texture_program"), mActivity);
        plane.translate(-20,9f,7);
        models.put("plane", plane);

        BaseModel skybox = new Skybox(ShaderManger.getInstance().getProgramHandle("skybox_program"),mActivity);
        //skybox.scale(30,30,30);
        models.put("skybox", skybox);

        BaseModel terrain = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), mActivity, R.raw.terrain, R.drawable.terrain);
        terrain.scale(15,15,15);
        terrain.translate(70,0.5f,120);
        models.put("terrain",terrain);


//        BaseModel sun = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), activity, R.raw.sphere, R.drawable.sun);
//        models.put("sun", sun);
//        sun.translate(60,60,60);

        //BaseModel particleSys = new ParticleSystem(ShaderManger.getInstance().getProgramHandle("texture_program"), mActivity);

        BaseModel landing = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), mActivity, R.raw.landing, R.drawable.landing);
        models.put("landing", landing);
        landing.scale(10,10,10);
        landing.translate(-38,-0.3f,30);

        BaseModel terrain2 = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), mActivity, R.raw.terrain2, R.drawable.landing);
        models.put("terrain2", terrain2);
        terrain2.scale(10,10,10);
        terrain2.translate(-50,-0.3f,-60);

//        BaseModel explosion = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"),mActivity,R.raw.particle,R.drawable.explosion);
//        models.put("explosion",explosion);
//        explosion.translate(0,5,0);

//        BaseModel flare = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"),context, R.raw.cube, R.drawable.uv_checker_large);
//        models.put("flare",flare);
//        flare.translate(0,6,0);


        frameBuffer = TextureHelper.createFrameBuffer(400, 400);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)mActivity).hideLoading();
            }
        });
//        logic = new Logic();
//        logic.run();

    }
    int frameBuffer = 0;
    void draw(int width, int height){
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        Camera.update();
        for(BaseModel model : models.values()){
            model.draw();
        }
        Camera.rotated=true;
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBuffer);
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        GLES30.glViewport(0,0,width,height);
        Camera.update();
        for(BaseModel model : models.values()){
            model.draw();
        }
        Camera.rotated=false;
    }
    public BaseModel getModel(String name){
        return models.get(name);
    }
    public BaseModel isColiding(Vector3 pos){
        for(BaseModel model : models.values())
            if(model instanceof TexturedModel && !(model instanceof PlaneModel))
                if(model.isCollidable())
                    if(((TexturedModel)model).getBounds().contains(pos))
                        return model;
        return null;
    }
    float mPreviousX;
    float mPreviousY;
    float mFirstX;
    float mFirstY;
    boolean isRotating = false;

    float SENSITIVITY = 0.05f;
    Integer mainTouchId = null;
    GestureDetector gestureDetector;
    long tapTime = 0;
    public boolean onTouch(MotionEvent e, int width, int height) {

        rotationGestureDetector.onTouchEvent(e);
        gestureDetector.onTouchEvent(e);
        if(e.getPointerCount()>2) return false;

        PlaneModel plane = (PlaneModel) getModel("plane");

        float x = e.getX();
        float y = e.getY();

        float dx = x - mPreviousX;
        float dy = y - mPreviousY;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(mainTouchId==null)
                    mainTouchId = e.getPointerId(0);

                tapTime = SystemClock.uptimeMillis();
                isRotating=false;
                mFirstX = x;
                mFirstY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                    if(e.getPointerId(0) == mainTouchId )
                    if(Math.abs(mFirstX-x) > 10 && Math.abs(mFirstY-y)>10){
                        isRotating=true;
                        plane.rotate(-dx * SENSITIVITY, dy * SENSITIVITY, 0);
                    }

                break;
            case MotionEvent.ACTION_UP:
                mainTouchId = null;
                    if (x < width / 2)
                        ((PlaneModel)plane).setPlaneRotation(0);
                long tapDuration = SystemClock.uptimeMillis()-tapTime;
                if(tapDuration>500 && !isRotating)
                    plane.slowDown();
                break;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    @Override
    public void OnRotation(RotationGestureDetector rotationDetector) {
        Log.d("Rotation",String.valueOf(rotationDetector.getAngle()));

        PlaneModel plane = (PlaneModel) getModel("plane");
        plane.rotate(0,0,(float) Math.toRadians(-rotationDetector.getAngle()));
    }

    public boolean onLongClick() {
        // plane = (PlaneModel) getModel("plane");
        //plane.slowDown(); //too fast
        return true;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {

            PlaneModel plane = (PlaneModel) getModel("plane");
            plane.speedUp();
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }
    }
    class Logic extends Thread{
        public boolean running = true;
        @Override
        public void run() {
            for (BaseModel model : models.values()){
                while(running) {
                    model.update();
                }
            }
        }
    }
}
