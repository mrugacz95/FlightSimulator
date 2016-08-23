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
import com.mrugas.flightsimulator.scenes.Scene;
import com.mrugas.flightsimulator.scenes.SceneManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by mruga on 09.08.2016.
 */
public class Quad extends TexturedModel {
    static final float guad_vertex_buffer_data[] = {
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            -1.0f,  1.0f, 0.0f,
            -1.0f,  1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f,  1.0f, 0.0f,
    };
    static final float guad_uv[] = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f,  1.0f,
            -1.0f,  1.0f,
            1.0f, -1.0f,
            1.0f,  1.0f
    };
    private int mTimeUniformHandle;

    public Quad(int programHandle, Context context) {
        super(programHandle, context);
    }
    @Override
    public void init(){
        textureResId = getTextureResId();
        meshResId = getMeshResourceId();
        Texture texture = new Texture(context,textureResId);
        TextureManager.getInstance().addTexture("texture"+textureResId,texture);
        mTextureDataHandle = texture.getTextureDataHandle();

        vertexBuffer = ByteBuffer.allocateDirect(guad_vertex_buffer_data.length * OBJParser.BYTES_PER_FLOAT * 3).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(guad_vertex_buffer_data);
        vertexBuffer.position(0);
        uvBuffer = ByteBuffer.allocateDirect(guad_uv.length * OBJParser.BYTES_PER_FLOAT * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        uvBuffer.put(guad_uv);
        uvBuffer.position(0);
        mPositionHandle = GLES30.glGetAttribLocation(programHandle, "a_Position");
        mMVPMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mTextureUniformHandle = GLES30.glGetUniformLocation(programHandle, "u_Texture");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(programHandle, "a_TexCoordinate");
        mTimeUniformHandle = GLES30.glGetUniformLocation(programHandle, "time");
    }

    @Override
    Integer getTextureResId() {
        return R.drawable.water_texture;
    }
    @Override
    public void draw() {

        mModelMatrix.idt();
        mModelMatrix.scale(scale);
        mModelMatrix.rotate(quaternionRotation);
        mModelMatrix.translate(position);

//        PlaneModel plane = (PlaneModel)SceneManager.getInstance().getCurrentScene().getModel("plane");
//        position = plane.position;
        GLES30.glUseProgram(programHandle);
        GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false,
                0, vertexBuffer);
        GLES30.glEnableVertexAttribArray(mPositionHandle);


        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureDataHandle);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0, mModelMatrix.getValues(), 0);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES30.glUniform1f(mTimeUniformHandle, SystemClock.uptimeMillis());

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 18);
    }
}
