package com.mrugas.flightsimulator.scenes;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES30;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.mrugas.flightsimulator.MainActivity;
import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.TextureHelper;
import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.Utilities.RotationGestureDetector;
import com.mrugas.flightsimulator.managers.ShaderManger;
import com.mrugas.flightsimulator.models.BaseModel;
import com.mrugas.flightsimulator.models.PlaneModel;
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
    public void init(final Activity activity){
        rotationGestureDetector = new RotationGestureDetector(this);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gestureDetector = new GestureDetector(activity,new GestureListener());
            }
        });
        ShaderManger.getInstance().addProgram(R.raw.simple_vertex_shader,R.raw.texture_fragment_shader,"texture_program",activity);
        ShaderManger.getInstance().addProgram(R.raw.simple_vertex_shader,R.raw.simple_fragment_shader,"simple_program",activity);
        ShaderManger.getInstance().addProgram(R.raw.skybox_vertex_shader,R.raw.skybox_fragment_shader,"skybox_program",activity);
        ShaderManger.getInstance().addProgram(R.raw.water_vertex_shader,R.raw.water_fragment_shader,"water_program",activity);

//        BaseModel cube = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), context, R.raw.cube, R.drawable.uv_checker_large);
//        cube.translate(0,-25,0);
//        cube.scale(50,50,50);
//        models.put("cube", cube);

        PlaneModel plane = new PlaneModel(ShaderManger.getInstance().getProgramHandle("texture_program"), activity);
        plane.translate(-20,8,7);
        models.put("plane", plane);

        BaseModel skybox = new Skybox(ShaderManger.getInstance().getProgramHandle("skybox_program"),activity);
        skybox.scale(30,30,30);
        models.put("skybox", skybox);

        BaseModel terrain = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), activity, R.raw.terrain, R.drawable.terrain);
        terrain.scale(15,15,15);
        terrain.translate(70,0.5f,120);
        models.put("terrain",terrain);

        BaseModel quad = new Water(ShaderManger.getInstance().getProgramHandle("water_program"),activity);
        models.put("quad", quad);
        quad.scale(160,160,160);

        BaseModel sun = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), activity, R.raw.sphere, R.drawable.sun);
        models.put("sun", sun);
        sun.translate(60,60,60);


        BaseModel landing = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"), activity, R.raw.landing, R.drawable.landing);
        models.put("landing", landing);
        landing.scale(10,10,10);
        landing.translate(-38,0.5f,30);

//        BaseModel flare = new TexturedModel(ShaderManger.getInstance().getProgramHandle("texture_program"),context, R.raw.cube, R.drawable.uv_checker_large);
//        models.put("flare",flare);
//        flare.translate(0,6,0);
        frameBuffer = TextureHelper.createFrameBuffer(400, 400);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)activity).hideLoading();
            }
        });

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

    float mPreviousX;
    float mPreviousY;

    float SENSIVITY = 0.05f;
    Integer mainTouchId = null;
    GestureDetector gestureDetector;
    long tapTime = 0;
    public boolean onTouch(MotionEvent e, int width, int height) {

        rotationGestureDetector.onTouchEvent(e);
        gestureDetector.onTouchEvent(e);
        if(e.getPointerCount()>2) return true;

        BaseModel plane = getModel("plane");

        float x = e.getX();
        float y = e.getY();

        float dx = x - mPreviousX;
        float dy = y - mPreviousY;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mainTouchId = e.getPointerId(0);

                tapTime = SystemClock.uptimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:


                if(plane instanceof PlaneModel){
                    float speed = ((PlaneModel) plane).getSpeed();
                    if(speed>0.02)
                        plane.rotate(-dx*SENSIVITY,dy*SENSIVITY,0);

                    tapTime = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                mainTouchId = null;
                if(plane instanceof PlaneModel){
                    if (x < width / 2)
                        ((PlaneModel)plane).setPlaneRotation(0);
                }
                break;
        }

        mPreviousX = x;
        mPreviousY = y;
        if(SystemClock.uptimeMillis()-tapTime>2500)
            return false;
        else
            return true;
    }

    @Override
    public void OnRotation(RotationGestureDetector rotationDetector) {
        Log.d("Rotation",String.valueOf(rotationDetector.getAngle()));

        PlaneModel plane = (PlaneModel) getModel("plane");
        plane.rotate(0,0,(float) Math.toRadians(-rotationDetector.getAngle()));
    }

    public boolean onLongClick() {
        PlaneModel plane = (PlaneModel) getModel("plane");
        plane.slowDown();
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
}
