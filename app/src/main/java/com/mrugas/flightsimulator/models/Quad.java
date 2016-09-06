package com.mrugas.flightsimulator.models;

import android.content.Context;
import android.opengl.GLES30;

import com.mrugas.flightsimulator.Utilities.ObjParser.OBJParser;
import com.mrugas.flightsimulator.managers.Texture;
import com.mrugas.flightsimulator.managers.TextureManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by mruga on 27.08.2016.
 */
public class Quad extends TexturedModel {

    public Quad(int programHandle, Context context) {
        super(programHandle, context);
    }
    static final public float guad_vertex_buffer_data[] = {
            -1.0f, 0.0f,-1.0f,
            1.0f,0.0f, -1.0f,
            -1.0f, 0.0f, 1.0f,
            1.0f,0.0f, -1.0f,
            -1.0f, 0.0f, 1.0f,
             1.0f, 0.0f, 1.0f
    };
    static final public float guad_uv[] = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f,  1.0f,
            1.0f, -1.0f,
            -1.0f,  1.0f,
            1.0f,  1.0f

    };

    @Override
    public void init() {
        super.initTextureData();
        vertexBuffer.clear();
        vertexBuffer = ByteBuffer.allocateDirect(Quad.guad_vertex_buffer_data.length * OBJParser.BYTES_PER_FLOAT * 3).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(Quad.guad_vertex_buffer_data);
        vertexBuffer.position(0);
        uvBuffer.clear();
        uvBuffer = ByteBuffer.allocateDirect(Quad.guad_uv.length * OBJParser.BYTES_PER_FLOAT * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        uvBuffer.put(Quad.guad_uv);
        uvBuffer.position(0);


        mPositionHandle = GLES30.glGetAttribLocation(programHandle, "a_Position");
        mMVPMatrixHandle = GLES30.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mColorHandle = GLES30.glGetAttribLocation(programHandle, "a_Color");
        mGlobaColorHandle = GLES30.glGetAttribLocation(programHandle, "glob_Color");
        mTextureUniformHandle = GLES30.glGetUniformLocation(programHandle, "u_Texture");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(programHandle, "a_TexCoordinate");
    }

    @Override
    public boolean isCollidable() {
        return false;
    }
}
