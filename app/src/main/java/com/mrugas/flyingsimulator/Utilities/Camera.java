package com.mrugas.flyingsimulator.Utilities;

import android.opengl.Matrix;

/**
 * Created by mruga on 01.08.2016.
 */
public class Camera {

    public static float[] getmViewMatrix() {
        return mViewMatrix;
    }

    public static float[] getmProjectionMatrix() {
        return mProjectionMatrix;
    }

    static private float[] mViewMatrix = new float[16];
    static private float[] mProjectionMatrix = new float[16];
    static private float[] mVPMatrix = new float[16];

    static public float[] getVPMatrix() {
        return mVPMatrix;
    }
    static public void setup(int width, int height){

        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 20.0f;

        Matrix.frustumM( mProjectionMatrix, 0, left, right, bottom, top, near, far);

        final float eyeX = 2.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 2.0f;

        final float lookX = -5.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;


        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);


        Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }
}
