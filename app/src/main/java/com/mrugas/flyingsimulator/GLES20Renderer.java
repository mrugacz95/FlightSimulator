package com.mrugas.flyingsimulator;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;


import com.mrugas.flyingsimulator.Utilities.Camera;
import com.mrugas.flyingsimulator.models.BaseModel;
import com.mrugas.flyingsimulator.models.PlaneModel;
import com.mrugas.flyingsimulator.scenes.Scene;
import com.mrugas.flyingsimulator.scenes.SceneManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Mrugi on 2016-06-04.
 */
public class GLES20Renderer implements GLSurfaceView.Renderer, View.OnTouchListener {

    Context context;
    int width, height;
    public GLES20Renderer(Context context) {
        this.context = context;
    }
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to gray.
        GLES20.glClearColor(0.2f, 0.55f, 0.5f, 0.9f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
// Position the eye behind the origin.

        Scene scene = new Scene();
        scene.init(context);
        SceneManager.getInstance().addScene(scene);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
        Camera.setup(width,height);
    }


    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        SceneManager.getInstance().draw();
       // myModel.draw();

//        myModel.draw();
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {

        Scene scene = SceneManager.getInstance().getCurrentScene();
        if(scene==null) return false;
        return scene.onTouch(e,width,height);


    }
}