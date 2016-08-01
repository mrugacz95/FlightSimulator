package com.mrugas.flyingsimulator;

import android.opengl.GLES20;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mruga on 01.08.2016.
 */
public class TouchListener implements View.OnTouchListener {
    GLES20Renderer renderer;
    public TouchListener(GLES20Renderer renderer){
        this.renderer = renderer;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return renderer.onTouch(view,motionEvent);
    }
}
