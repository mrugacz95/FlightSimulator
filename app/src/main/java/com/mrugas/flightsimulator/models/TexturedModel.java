package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.Utilities.Bounds;
import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.Utilities.ObjParser.OBJParser;
import com.mrugas.flightsimulator.Utilities.Vector3;
import com.mrugas.flightsimulator.managers.Texture;
import com.mrugas.flightsimulator.managers.TextureManager;
import com.mrugas.flightsimulator.scenes.Scene;
import com.mrugas.flightsimulator.scenes.SceneManager;

import java.nio.FloatBuffer;

/**
 * Created by mruga on 01.08.2016.
 */
public class TexturedModel extends BaseModel {
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer uvBuffer;
    protected FloatBuffer normalBuffer;
    protected int mPositionHandle,
            //mMVPMatrixHandle,
            mColorHandle,
            mTextureUniformHandle,
            mTextureCoordinateHandle,
            mGlobaColorHandle,
            mTextureDataHandle,
            programHandle,
            mNormalHandle,
            mM,
            mV,
            mP,
            mLightPosHandle;
    private Bounds bounds;
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
        OBJParser parser = new OBJParser(context);
        parser=parser.parseOBJ(getMeshResourceId());
        vertexBuffer = parser.getVertexBuffer();
        vertexCount = parser.getVertexCount();
        uvBuffer = parser.getUVBuffer();
        normalBuffer = parser.getNormalBuffer();
        bounds = parser.getBounds();
        mPositionHandle = GLES30.glGetAttribLocation(programHandle, "a_Position");
        //mMVPMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mM = GLES30.glGetUniformLocation(programHandle,"M");
        mV = GLES30.glGetUniformLocation(programHandle,"V");
        mP = GLES30.glGetUniformLocation(programHandle,"P");
        mColorHandle = GLES30.glGetAttribLocation(programHandle, "a_Color");
        mGlobaColorHandle = GLES30.glGetAttribLocation(programHandle, "glob_Color");
        mTextureUniformHandle = GLES30.glGetUniformLocation(programHandle, "u_Texture");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(programHandle, "a_TexCoordinate");
        mLightPosHandle = GLES30.glGetAttribLocation(programHandle, "lightPos0");
        mNormalHandle = GLES30.glGetAttribLocation(programHandle, "normal");
        initTextureData();
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


        GLES30.glVertexAttribPointer(mNormalHandle, 3, GLES30.GL_FLOAT, false,
                0, normalBuffer);
        GLES30.glEnableVertexAttribArray(mNormalHandle);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureDataHandle);

        GLES30.glUniformMatrix4fv(mM, 1, false, mModelMatrix.getValues(), 0);
        GLES30.glUniformMatrix4fv(mV, 1, false, Camera.getmViewMatrix(), 0);
        GLES30.glUniformMatrix4fv(mP, 1, false, Camera.getmProjectionMatrix(), 0);

        //Scene scene = SceneManager.getInstance().getCurrentScene();
        //BaseModel sun = scene.getModel("sun");

        GLES30.glUniform1fv(mLightPosHandle, 1, new float[]{10.f,30.f,40.f,1.f}, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

    }
    Integer getTextureResId(){
        return textureResId;
    }

    int getMeshResourceId(){
        return meshResId;
    };

    public Bounds getBounds(){
        return bounds;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void translate(float x, float y, float z) {
        super.translate(x, y, z);
        bounds.setModelPosition(this);
    }

    @Override
    public void scale(float x, float y, float z) {
        super.scale(x, y, z);
        if(bounds!=null)
        bounds.setModelPosition(this);
    }
}
