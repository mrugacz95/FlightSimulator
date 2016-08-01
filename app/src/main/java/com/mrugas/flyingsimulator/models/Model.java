package com.mrugas.flyingsimulator.models;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.Utilities.Camera;
import com.mrugas.flyingsimulator.Utilities.ObjParser.OBJParser;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Mrugi on 2016-07-10.
 */
public abstract class Model implements BaseModel {
    static public int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexBuffer;
    private FloatBuffer uvBuffer;
    private int mPositionHandle,
            mMVPMatrixHandle,
            mColorHandle,
            mGlobaColorHandle;
    private float[] mModelMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];
    private int vertexCount;
    public Model(int resId, int programHandle, Context context){


        OBJParser parser = new OBJParser(context);
        parser.parseOBJ(R.raw.plane);
        vertexCount = parser.getVertexCount();
        vertexBuffer = parser.getVertexBuffer();
        uvBuffer = parser.getUVBuffer();
//        vertexBuffer = ByteBuffer.allocateDirect(Cube.vertices.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        vertexBuffer.put(Cube.vertices);
//        vertexBuffer.position(0);

//        uvBuffer = ByteBuffer.allocateDirect(Triangle.colors.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        uvBuffer.put(Triangle.colors);
//        uvBuffer.position(0);

        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
        mGlobaColorHandle = GLES20.glGetAttribLocation(programHandle, "glob_Color");




    }
    @Override
    public void draw() {

        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);


//        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false,
//                0, colorBuffer);
//        GLES20.glEnableVertexAttribArray(mColorHandle);


        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.9f, 0.5f, 1.0f);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0, mModelMatrix, 0);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    }
}
