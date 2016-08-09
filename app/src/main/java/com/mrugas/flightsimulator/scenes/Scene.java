package com.mrugas.flightsimulator.scenes;

import android.content.Context;
import android.opengl.GLES30;
import android.util.Log;
import android.view.MotionEvent;

import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.TextureHelper;
import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.Utilities.RotationGestureDetector;
import com.mrugas.flightsimulator.managers.ShaderManger;
import com.mrugas.flightsimulator.models.BaseModel;
import com.mrugas.flightsimulator.models.PlaneModel;
import com.mrugas.flightsimulator.models.Quad;
import com.mrugas.flightsimulator.models.Skybox;
import com.mrugas.flightsimulator.models.TexturedModel;

import java.util.HashMap;

/**
 * Created by mruga on 01.08.2016.
 */
public class Scene implements RotationGestureDetector.OnRotationGestureListener {
    HashMap<String,BaseModel> models = new HashMap<>();
    RotationGestureDetector rotationGestureDetector;
    public void init(Context context){
        rotationGestureDetector = new RotationGestureDetector(this);
        ShaderManger.getInstance().addProgram(R.raw.simple_vertex_shader,R.raw.texture_fragment_shader,"simple_program",context);
        ShaderManger.getInstance().addProgram(R.raw.skybox_vertex_shader,R.raw.skybox_fragment_shader,"skybox_program",context);
        ShaderManger.getInstance().addProgram(R.raw.water_vartex_shader,R.raw.water_fragment_shader,"water_program",context);

        BaseModel cube = new TexturedModel(ShaderManger.getInstance().getProgramHandle("simple_program"), context, R.raw.cube, R.drawable.uv_checker_large);
        cube.translate(0,-30,0);
        cube.scale(50,50,50);
        models.put("cube", cube);

        PlaneModel plane = new PlaneModel(ShaderManger.getInstance().getProgramHandle("simple_program"), context);
        plane.translate(0,2,4);
        models.put("plane", plane);

        BaseModel skybox = new Skybox(ShaderManger.getInstance().getProgramHandle("skybox_program"),context);
        skybox.scale(30,30,30);
        models.put("skybox", skybox);

        BaseModel quad = new Quad(ShaderManger.getInstance().getProgramHandle("skybox_program"),context);
        models.put("quad", quad);

        frameBuffer = TextureHelper.createFrameBuffer(400, 400);

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
    public boolean onTouch(MotionEvent e, int width, int height) {

        rotationGestureDetector.onTouchEvent(e);
        if(e.getPointerCount()>2) return true;

        BaseModel plane = getModel("plane");

        float x = e.getX();
        float y = e.getY();

        float dx = x - mPreviousX;
        float dy = y - mPreviousY;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mainTouchId = e.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:


                if(plane instanceof PlaneModel){
                        plane.rotate(-dx*SENSIVITY,dy*SENSIVITY,0);
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
        return true;
    }

    @Override
    public void OnRotation(RotationGestureDetector rotationDetector) {
        Log.d("Rotation",String.valueOf(rotationDetector.getAngle()));

        PlaneModel plane = (PlaneModel) getModel("plane");
        plane.rotate(0,0,(float) Math.toRadians(-rotationDetector.getAngle()));
    }
}
