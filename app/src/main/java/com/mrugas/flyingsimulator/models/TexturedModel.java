package com.mrugas.flyingsimulator.models;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.Utilities.Camera;
import com.mrugas.flyingsimulator.Utilities.ObjParser.OBJParser;
import com.mrugas.flyingsimulator.managers.Texture;
import com.mrugas.flyingsimulator.managers.TextureManager;

import java.nio.FloatBuffer;

/**
 * Created by mruga on 01.08.2016.
 */
public class TexturedModel extends BaseModel {
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer uvBuffer;
    protected int mPositionHandle,
            mMVPMatrixHandle,
            mColorHandle,
            mTextureUniformHandle,
            mTextureCoordinateHandle,
            mGlobaColorHandle,
            mTextureDataHandle,
            programHandle;
    protected Context context;
    protected float[] mMVPMatrix = new float[16];
    protected int vertexCount;
    Integer textureResId = R.drawable.uv_checker_large;
    int meshResId = R.raw.cube;
    public TexturedModel(int programHandle, Context context){
        this.programHandle = programHandle;
        this.context = context;
        init();
    }

    public TexturedModel(int programHandle, Context context, int meshResId, Integer textureResId){
        this(programHandle,context);
        this.meshResId=meshResId;
        this.textureResId = textureResId;
        init();
    }
    @Override
    public void init(){
        textureResId = getTextureResId();
        meshResId = getMeshResourceId();
        Texture texture = new Texture(context,textureResId);
        TextureManager.getInstance().addTexture("texture"+textureResId,texture);
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

        super.draw();
        GLES20.glUseProgram(programHandle);
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

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0, mModelMatrix.getValues(), 0);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    }
    Integer getTextureResId(){
        return textureResId;
    }

    int getMeshResourceId(){
        return meshResId;
    };
}
