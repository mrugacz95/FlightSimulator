package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.Utilities.ObjParser.OBJParser;
import com.mrugas.flightsimulator.managers.Texture;
import com.mrugas.flightsimulator.managers.TextureManager;

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
        initTextureData();
        OBJParser parser = new OBJParser(context);
        parser=parser.parseOBJ(getMeshResourceId());
        vertexCount = parser.getVertexCount();
        vertexBuffer = parser.getVertexBuffer();
        uvBuffer = parser.getUVBuffer();

        mPositionHandle = GLES30.glGetAttribLocation(programHandle, "a_Position");
        mMVPMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mColorHandle = GLES30.glGetAttribLocation(programHandle, "a_Color");
        mGlobaColorHandle = GLES30.glGetAttribLocation(programHandle, "glob_Color");
        mTextureUniformHandle = GLES30.glGetUniformLocation(programHandle, "u_Texture");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(programHandle, "a_TexCoordinate");
    }
    public void initTextureData(){
        textureResId = getTextureResId();
        meshResId = getMeshResourceId();
        Texture texture = new Texture(context,textureResId);
        TextureManager.getInstance().addTexture("texture"+textureResId,texture);
        mTextureDataHandle = texture.getTextureDataHandle();
        GLES30.glUniform1i(mTextureUniformHandle, 0);
    }
    @Override
    public void draw() {

        super.draw();
        GLES30.glUseProgram(programHandle);
        GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false,
                0, vertexBuffer);
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        GLES30.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES30.GL_FLOAT, false,
                0, uvBuffer);
        GLES30.glEnableVertexAttribArray(mTextureCoordinateHandle);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
       // GLES30.glUniform1i(mTextureUniformHandle, 0);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0, mModelMatrix.getValues(), 0);

        Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

    }
    Integer getTextureResId(){
        return textureResId;
    }

    int getMeshResourceId(){
        return meshResId;
    };
}
