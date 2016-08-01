package com.mrugas.flyingsimulator.models;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.Utilities.Camera;
import com.mrugas.flyingsimulator.Utilities.ObjParser.OBJParser;
import com.mrugas.flyingsimulator.managers.Texture;
import com.mrugas.flyingsimulator.managers.TextureManager;

import java.nio.FloatBuffer;

/**
 * Created by mruga on 01.08.2016.
 */
public abstract class TexturedModel implements BaseModel {
    static public int BYTES_PER_FLOAT = 4;
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer uvBuffer;
    protected int mPositionHandle,
            mMVPMatrixHandle,
            mColorHandle,
            mTextureUniformHandle,
            mTextureCoordinateHandle,
            mGlobaColorHandle,
            mTextureDataHandle;
    protected float[] mModelMatrix = new float[16];
    protected float[] mMVPMatrix = new float[16];
    protected int vertexCount;
    public TexturedModel(int programHandle, Context context){

        int textureResId = getTextureId();
        Texture texture = new Texture(context,textureResId);
        TextureManager.getInstance().addTexture("plane_texture",texture);
        mTextureDataHandle = texture.getTextureDataHandle();


        OBJParser parser = new OBJParser(context);
        parser.parseOBJ(getMeshResourceId());
        vertexCount = parser.getVertexCount();
        vertexBuffer = parser.getVertexBuffer();
        uvBuffer = parser.getUVBuffer();

        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
        mGlobaColorHandle = GLES20.glGetAttribLocation(programHandle, "glob_Color");
        mTextureUniformHandle = GLES20.glGetUniformLocation(programHandle, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate");




    }
    @Override
    public void draw() {

        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);




        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
                0, uvBuffer);
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(mTextureUniformHandle, 0);

        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0, mModelMatrix, 0);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    }
    abstract int getTextureId();
}
