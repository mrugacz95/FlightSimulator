package com.mrugas.flyingsimulator.models;

import android.content.Context;
import android.opengl.GLES20;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.TextureHelper;
import com.mrugas.flyingsimulator.Utilities.Camera;
import com.mrugas.flyingsimulator.Utilities.Matrix3;
import com.mrugas.flyingsimulator.Utilities.Matrix4;
import com.mrugas.flyingsimulator.Utilities.ObjParser.OBJParser;

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


//        OBJParser parser = new OBJParser(context);
//        parser.parseOBJ(R.raw.skybox);
//        vertexCount = parser.getVertexCount();
//        vertexBuffer = parser.getVertexBuffer();
//        uvBuffer = parser.getUVBuffer();
        vertexBuffer = ByteBuffer.allocateDirect(SkyboxCube.skyboxVertices.length * OBJParser.BYTES_PER_FLOAT * 3).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(SkyboxCube.skyboxVertices);
        vertexBuffer.position(0);
        List<Integer> texturesFaces = new ArrayList<>(6);
        texturesFaces.add(R.drawable.cloudtop_rt);
        texturesFaces.add(R.drawable.cloudtop_lf);
        texturesFaces.add(R.drawable.cloudtop_up);
        texturesFaces.add(R.drawable.cloudtop_dn);
        texturesFaces.add(R.drawable.cloudtop_ft);
        texturesFaces.add(R.drawable.cloudtop_bk);

        mTextureDataHandle = TextureHelper.loadCubeMap(context,texturesFaces);//TextureHelper.loadCubeMap(context,texturesFaces);


        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "position");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_TexCoords");

        mProjectionHandle = GLES20.glGetUniformLocation(programHandle, "projection");
        mModelHandle = GLES20.glGetUniformLocation(programHandle, "model");
        mViewHandle = GLES20.glGetUniformLocation(programHandle, "view");
        mCubeMap = GLES20.glGetUniformLocation(programHandle, "skybox");
    }

    @Override
    public void draw() {

        //GLES20.glDepthMask(false);

        mModelMatrix.idt();
        mModelMatrix.scale(scale);
        //mModelMatrix.rotate(quaternionRotation);
        //mModelMatrix.translate(position);

        GLES20.glUseProgram(programHandle);
        //GLES20.glUseProgram(ShaderManger.getInstance().getProgramHandle("simple_program"));


        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false,
                    0, vertexBuffer);
            GLES20.glEnableVertexAttribArray(mPositionHandle);


//        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
//                0, uvBuffer);
//        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

            // Bind the texture to this unit.
            GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, mTextureDataHandle);

            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
            GLES20.glUniform1i(mTextureUniformHandle, 2);

//            Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmViewMatrix(), 0, mModelMatrix.getValues(), 0);
//
//            Matrix.multiplyMM(mMVPMatrix, 0, Camera.getmProjectionMatrix(), 0, mMVPMatrix, 0);
        //Matrix3 view = new Matrix3(Camera.getmViewMatrix());
        GLES20.glUniformMatrix4fv(mViewHandle, 1, false, Camera.getmViewMatrixForSkybox(), 0);
        GLES20.glUniformMatrix4fv(mProjectionHandle, 1, false, Camera.getmProjectionMatrix(), 0);
        GLES20.glUniformMatrix4fv(mModelHandle, 1, false, mMVPMatrix, 0);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);


        //GLES20.glDepthMask(true);
    }
}
