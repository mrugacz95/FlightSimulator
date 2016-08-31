package com.mrugas.flightsimulator;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mruga on 01.08.2016.
 */
public class TouchListener implements View.OnTouchListener, View.OnLongClickListener {
    GLES30Renderer renderer;
    public TouchListener(GLES30Renderer renderer){
        this.renderer = renderer;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return renderer.onTouch(view,motionEvent);
    }

    @Override
    public boolean onLongClick(View view) {
        return renderer.onLongClick();
    }
}
