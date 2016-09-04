package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.Utilities.ObjParser.OBJParser;

import java.nio.FloatBuffer;

/**
 * Created by Mrugi on 2016-07-10.
 */
public abstract class Model extends BaseModel {
    static public int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexBuffer;
    private int mPositionHandle,
            mMVPMatrixHandle,
            mColorHandle,
            mGlobaColorHandle;
    private float[] mMVPMatrix = new float[16];
    private int vertexCount;
    public Model(int meshResId, int programHandle, Context context){


        OBJParser parser = new OBJParser(context);
        parser=parser.parseOBJ(meshResId);
        vertexCount = parser.getVertexCount();
        vertexBuffer = parser.getVertexBuffer();

        mPositionHandle = GLES30.glGetAttribLocation(programHandle, "a_Position");
        mMVPMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mColorHandle = GLES30.glGetAttribLocation(programHandle, "a_Color");
        mGlobaColorHandle = GLES30.glGetAttribLocation(programHandle, "glob_Color");




    }
    @Override
    public void draw() {
        super.draw();
        GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false,
                0, vertexBuffer);
        GLES30.glEnableVertexAttribArray(mPositionHandle);


//        GLES30.glVertexAttribPointer(mColorHandle, 4, GLES30.GL_FLOAT, false,
//                0, colorBuffer);
//        GLES30.glEnableVertexAttribArray(mColorHandle);
        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0,mModelMatrix.getValues(), 0);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

    }
}
