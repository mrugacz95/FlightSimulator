package com.mrugas.flightsimulator.Utilities;

import android.opengl.Matrix;

import com.mrugas.flightsimulator.models.BaseModel;
import com.mrugas.flightsimulator.models.PlaneModel;
import com.mrugas.flightsimulator.scenes.SceneManager;

/**
 * Created by mruga on 01.08.2016.
 */
public class Camera {

    static final float eyeX = 0.0f;
    static final float eyeY = 3.0f;
    static final float eyeZ = -9f;

    static final float lookX = 0.0f;
    static final float lookY = 0.0f;
    static final float lookZ = 0.0f;

    static final float upX = 0.0f;
    static final float upY = 1.0f;
    static final float upZ = 0.0f;

    static final float bottom = -1.0f;
    static final float top = 1.0f;
    static final float near = 1.0f;
    static final float far = 250.0f;
    static public boolean rotated = false;
    public static void update(){

        PlaneModel plane = ((PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane"));

        Matrix4 mat = new  Matrix4();
        Vector3 pos = new Vector3(plane.getPosition());
        Quaternion rot = plane.getRotation();
        mat.translate(-pos.x,-pos.y,-pos.z);
        mat.rotate(plane.getRotation());
        mat.translate(new Vector3(0,0,0).mul(plane.getRotation()));

        Vector3 camPos = new Vector3(pos).add(new Vector3(0,2,-3).mul(rot));
        Matrix.setIdentityM(mViewMatrix,0);
        Vector3 up = new Vector3(0,1,0);
        Quaternion qup = new Quaternion(rot);
        qup.mul(Quaternion.Euler(90f,0,0));
        up.mul(rot);
        Matrix.setLookAtM(mViewMatrix, 0,  camPos.x,camPos.y,camPos.z, pos.x, pos.y,pos.z, up.x, up.y, up.z);

        if(rotated) Matrix.scaleM(mViewMatrix,0,mViewMatrix,0,1f,-1f,1f);
        //mViewMatrix = mat.getValues();
    }

    public static float[] getmViewMatrix() {

        return mViewMatrix;

    }
    public static float[] getmViewMatrixForSkybox() {


//        Matrix4 mat = new  Matrix4();
//        PlaneModel plane = ((PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane"));
//        mat.rotate(new Quaternion(plane.getRotation()).inv());
        return new Matrix4(new Matrix3(new Matrix4(mViewMatrix))).getValues();
    }
    public static float[] getViewMatrixForWater(){
        float[] toRet = new float[16];
        return toRet;
    }

    public static float[] getmProjectionMatrix() {
        return mProjectionMatrix;
    }

    static private float[] mViewMatrix = new float[16];
    static private float[] mProjectionMatrix = new float[16];
    static private float[] mVPMatrix = new float[16];

    static public float[] getVPMatrix() {
        float[] view = ((PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane")).getModelViewMatrix().getValues();
        Matrix.translateM(view,0,0.f,0.f,-3.f);
        return view;
    }
    static public void setup(int width, int height){

        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;

        Matrix.frustumM( mProjectionMatrix, 0, left, right, bottom, top, near, far);


        Matrix.setLookAtM(mViewMatrix, 0,  eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);



        //Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    public static void rotate(int degree, float x, float y, float z) {
        Matrix.rotateM(mViewMatrix, 0, degree, x, y, z);
    }
}
