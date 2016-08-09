package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.opengl.GLES30;

import com.mrugas.flightsimulator.R;
import com.mrugas.flightsimulator.TextureHelper;
import com.mrugas.flightsimulator.Utilities.Camera;
import com.mrugas.flightsimulator.Utilities.ObjParser.OBJParser;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mruga on 04.08.2016.
 */
public class Skybox extends TexturedModel {
    private int mProjectionHandle;
    private int mViewHandle;
    private int mCubeMap;
    private int mModelHandle;

    public Skybox(int programHandle, Context context) {
        super(programHandle, context, R.raw.half_skybox, null);
    }

    @Override
    public void init() {
        textureResId = getTextureResId();
        meshResId = getMeshResourceId();

        vertexBuffer = ByteBuffer.allocateDirect(SkyboxCube.skyboxVertices.length * OBJParser.BYTES_PER_FLOAT * 3).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(SkyboxCube.skyboxVertices);
        vertexBuffer.position(0);
        List<Integer> texturesFaces = new ArrayList<>(6);
        texturesFaces.add(R.drawable.cloudtop_ft);
        texturesFaces.add(R.drawable.cloudtop_bk);
        texturesFaces.add(R.drawable.cloudtop_up);
        texturesFaces.add(R.drawable.cloudtop_dn);
        texturesFaces.add(R.drawable.cloudtop_rt);
        texturesFaces.add(R.drawable.cloudtop_lf);

        mTextureDataHandle = TextureHelper.loadCubeMap(context,texturesFaces);//TextureHelper.loadCubeMap(context,texturesFaces);


        mPositionHandle = GLES30.glGetAttribLocation(programHandle, "position");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(programHandle, "a_TexCoords");

        mProjectionHandle = GLES30.glGetUniformLocation(programHandle, "projection");
        mModelHandle = GLES30.glGetUniformLocation(programHandle, "model");
        mViewHandle = GLES30.glGetUniformLocation(programHandle, "view");
        mCubeMap = GLES30.glGetUniformLocation(programHandle, "skybox");
    }

    @Override
    public void draw() {

        GLES30.glDepthMask(false);


        GLES30.glUseProgram(programHandle);
        //GLES30.glUseProgram(ShaderManger.getInstance().getProgramHandle("simple_program"));


        GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false,
                    0, vertexBuffer);
            GLES30.glEnableVertexAttribArray(mPositionHandle);


//        GLES30.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES30.GL_FLOAT, false,
//                0, uvBuffer);
//        GLES30.glEnableVertexAttribArray(mTextureCoordinateHandle);

            GLES30.glActiveTexture(GLES30.GL_TEXTURE0);

            // Bind the texture to this unit.
            GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP, mTextureDataHandle);

            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
            GLES30.glUniform1i(mTextureUniformHandle, 2);

//            Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0, mModelMatrix.getValues(), 0);
//
//            Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);
        //Matrix3 view = new Matrix3(Camera.getmViewMatrix());
        GLES30.glUniformMatrix4fv(mViewHandle, 1, false, Camera.getmViewMatrixForSkybox(), 0);
        GLES30.glUniformMatrix4fv(mProjectionHandle, 1, false, Camera.getmProjectionMatrix(), 0);
        GLES30.glUniformMatrix4fv(mModelHandle, 1, false, mMVPMatrix, 0);

            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 36);


        GLES30.glDepthMask(true);
    }
}
