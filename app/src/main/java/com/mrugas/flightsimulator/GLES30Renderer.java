package com.mrugas.flightsimulator;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;


import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.scenes.Scene;
import com.mrugas.flightsimulator.scenes.SceneManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Mrugi on 2016-06-04.
 */
public class GLES30Renderer implements GLSurfaceView.Renderer, View.OnTouchListener {

    Activity mActivity;
    int width, height;
    public GLES30Renderer(Activity activity) {
        this.mActivity = activity;
    }
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to gray.
        GLES30.glClearColor(0.2f, 0.55f, 0.5f, 0.9f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glEnable(GLES30.GL_TEXTURE_2D);
        GLES30.glEnable(GLES30.GL_BLEND);

        GLES30.glBlendFunc (GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glBlendEquation(GLES30.GL_FUNC_ADD);
// Position the eye behind the origin.

        Scene scene = new Scene();
        scene.init(mActivity);
        SceneManager.getInstance().addScene(scene);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        this.width = width;
        this.height = height;
        GLES30.glViewport(0, 0, width, height);
        Camera.setup(width,height);
    }


    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        SceneManager.getInstance().draw(width,height);
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {

        Scene scene = SceneManager.getInstance().getCurrentScene();
        if(scene==null) return false;
        return scene.onTouch(e,width,height);


    }

    public boolean onLongClick() {

        Scene scene = SceneManager.getInstance().getCurrentScene();
        if(scene==null) return false;
        return scene.onLongClick();
    }
}