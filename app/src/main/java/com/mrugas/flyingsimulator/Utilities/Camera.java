package com.mrugas.flyingsimulator.Utilities;

import android.opengl.Matrix;

import com.mrugas.flyingsimulator.models.PlaneModel;
import com.mrugas.flyingsimulator.scenes.SceneManager;

/**
 * Created by mruga on 01.08.2016.
 */
public class Camera {

    static final float eyeX = 0.0f;
    static final float eyeY = 0.0f;
    static final float eyeZ = -2.5f;

    static final float lookX = 0.0f;
    static final float lookY = 0.0f;
    static final float lookZ = 0.0f;

    static final float upX = 0.0f;
    static final float upY = 1.0f;
    static final float upZ = 0.0f;

    public static float[] getmViewMatrix() {
//        float[] view = ((PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane")).getModelViewMatrix();
//
//        Matrix.invertM(view,0,view,0);
//        Matrix.translateM(view,0,0.f,0.f,-3.f);
//        return view;
        Vector3 pos = ((PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane")).getPosition();
        Vector3 rot = ((PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane")).getPosition();
        Matrix.setLookAtM(mViewMatrix, 0,  pos.x-(float) Math.cos(Math.toRadians(rot.y))*5, pos.y+(float) Math.cos(Math.toRadians(rot.z))*5, pos.z-(float) Math.sin(Math.toRadians(rot.x))*5, pos.x, pos.y, pos.z, upX, upY, upZ);
        return mViewMatrix;
    }

    public static float[] getmProjectionMatrix() {
        return mProjectionMatrix;
    }

    static private float[] mViewMatrix = new float[16];
    static private float[] mProjectionMatrix = new float[16];
    static private float[] mVPMatrix = new float[16];

    static public float[] getVPMatrix() {
        float[] view = ((PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane")).getModelViewMatrix();
        Matrix.translateM(view,0,0.f,0.f,-3.f);
        return view;
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






        Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    public static void rotate(int degree, float x, float y, float z) {
        Matrix.rotateM(mViewMatrix, 0, degree, x, y, z);
    }
}
