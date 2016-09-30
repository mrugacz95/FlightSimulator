package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.Utilities.ObjParser.OBJParser;
import com.mrugas.flightsimulator.managers.Texture;
import com.mrugas.flightsimulator.managers.TextureManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by mruga on 09.08.2016.
 */
public class Water extends TexturedModel {

    protected int mTimeUniformHandle;
    int mWaterBumpDataHandle =0;
    int mCameraDir = 0;
    int mCameraPos = 0;

    private int mProjectionMatrixHandle;
    private int mViewMatrixHandle;
    int mWaterBumpUnifromHandle;
    int mMVPMatrixHandle;
    public Water(int programHandle, Context context) {
        super(programHandle, context);
    }
    @Override
    public void init(){
        meshResId = getMeshResourceId();

        Texture waterBumpTexture = new Texture(context,R.drawable.waterbump);
        TextureManager.getInstance().addTexture("texture"+R.drawable.waterbump,waterBumpTexture);
        mWaterBumpDataHandle = waterBumpTexture.getTextureDataHandle();

        vertexBuffer = ByteBuffer.allocateDirect(Quad.guad_vertex_buffer_data.length * OBJParser.BYTES_PER_FLOAT * 3).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(Quad.guad_vertex_buffer_data);
        vertexBuffer.position(0);
        uvBuffer = ByteBuffer.allocateDirect(Quad.guad_uv.length * OBJParser.BYTES_PER_FLOAT * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        uvBuffer.put(Quad.guad_uv);
        uvBuffer.position(0);
        mPositionHandle = GLES30.glGetAttribLocation(programHandle, "a_Position");
        mMVPMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mTextureUniformHandle = GLES30.glGetUniformLocation(programHandle, "texture");
        mWaterBumpUnifromHandle = GLES30.glGetUniformLocation(programHandle, "waterBump");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(programHandle, "a_TexCoordinate");
        mTimeUniformHandle = GLES30.glGetUniformLocation(programHandle, "time");
        mViewMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_ViewMatrix");
        mProjectionMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_ProjectionMatrix");
        //mCameraDir = GLES30.glGetUniformLocation(programHandle, "cameraDir");
        //mCameraPos = GLES30.glGetUniformLocation(programHandle, "cameraPos");
        initTextureData();

    }

    @Override
    Integer getTextureResId() {
        return R.drawable.waterbump;
    }
    @Override
    public void draw() {

        mModelMatrix.idt();
        mModelMatrix.scale(scale);
        mModelMatrix.rotate(rotation);
        mModelMatrix.translate(position);

//        PlaneModel plane = (PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane");
//        position = plane.position;
        GLES30.glUseProgram(programHandle);
        GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false,
                0, vertexBuffer);
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        GLES30.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES30.GL_FLOAT, false,
                0, uvBuffer);
        GLES30.glEnableVertexAttribArray(mTextureCoordinateHandle);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureDataHandle);

        GLES30.glUniform1i(mTextureUniformHandle, 0);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mWaterBumpDataHandle);
        GLES30.glUniform1i(mWaterBumpUnifromHandle, 1);
//        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
//        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureDataHandle);


        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0, mModelMatrix.getValues(), 0);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES30.glUniformMatrix4fv(mProjectionMatrixHandle, 1, false, Camera.getmProjectionMatrix(), 0);
        GLES30.glUniformMatrix4fv(mViewMatrixHandle, 1, false, Camera.getmViewMatrixForSkybox(), 0);
        float time = (SystemClock.uptimeMillis()%10000);
        GLES30.glUniform1f(mTimeUniformHandle, time );
        //BaseModel plane = SceneManager.getInstance().getCurrentScene().getModel("plane");
        //Quaternion planeRotation = plane.getRotation();
        //GLES30.glUniform3fv(mCameraDir, 1,new Vector3(planeRotation.getPitch(),planeRotation.getYaw(),planeRotation.getRoll()).getValues(),0);
        //GLES30.glUniform3fv(mCameraPos, 1,plane.position.getValues(),0);



        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 6);
    }

    @Override
    public boolean isCollidable() {
        return false;
    }
}
