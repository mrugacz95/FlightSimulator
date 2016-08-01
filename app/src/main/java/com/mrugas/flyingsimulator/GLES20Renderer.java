package com.mrugas.flyingsimulator;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;


import com.mrugas.flyingsimulator.Utilities.Camera;
import com.mrugas.flyingsimulator.Utilities.TDModel;
import com.mrugas.flyingsimulator.Utilities.Util;
import com.mrugas.flyingsimulator.models.Model;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Mrugi on 2016-06-04.
 */
public class GLES20Renderer implements GLSurfaceView.Renderer {
    TDModel model;
    Model myModel;

    /**
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */

    /**
     * Store the projection matrix. This is used to project the scene onto a 2D viewport.
     */

    /**
     * Allocate storage for the final combined matrix. This will be passed into the shader program.
     */

/** Store our model data in a float buffer. */

    /**
     * This will be used to pass in the transformation matrix.
     */
    private int mMVPMatrixHandle;

    /**
     * This will be used to pass in model position information.
     */
    private int mPositionHandle;

    /**
     * This will be used to pass in model color information.
     */
    private int mColorHandle;

    /**
     * How many bytes per float.
     */
    private final int mBytesPerFloat = 4;

    /**
     * How many elements per vertex.
     */
    private final int mStrideBytes = 7 * mBytesPerFloat;

    /**
     * Offset of the position data.
     */
    private final int mPositionOffset = 0;

    /**
     * Size of the position data in elements.
     */
    private final int mPositionDataSize = 3;

    /**
     * Offset of the color data.
     */
    private final int mColorOffset = 3;

    /**
     * Size of the color data in elements.
     */
    private final int mColorDataSize = 4;

    /**
     * Initialize the model data.
     */


    /**
     * This will be used to pass in the texture.
     */
    private int mTextureUniformHandle;

    /**
     * This will be used to pass in model texture coordinate information.
     */
    private int mTextureCoordinateHandle;

    /**
     * Size of the texture coordinate data in elements.
     */
    private final int mTextureCoordinateDataSize = 2;

    /**
     * This is a handle to our texture data.
     */
    private int mTextureDataHandle;
    String vertexShader = "";    // normalized screen coordinates.
    String fragmentShader = "";

//    FloatBuffer mCubeVertices;
//    FloatBuffer mCubeColors;
//    FloatBuffer mCubeCoords;
    final float[] triangle1VerticesData = {
            // X, Y, Z,
            // R, G, B, A
            -0.5f, -0.25f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            0.5f, -0.25f, 0.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            0.0f, 0.559016994f, 0.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.5f, -0.25f, 0.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            0.0f, 0.559016994f, 0.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.5f, -1.25f, -3.0f,
            1.0f, 0.0f, 0.0f, 1.0f


    };
    final int buffers[] = new int[3];
    Context context;
    public GLES20Renderer(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.simple_vertex_shader);
            vertexShader = IOUtils.toString(inputStream, "UTF-8");
            inputStream = context.getResources().openRawResource(R.raw.simple_fragment_shader);
            fragmentShader = IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.context = context;
        myModel = new Model(R.raw.teapot2,programHandle,context);

//        Object3dContainer obj = min3dParser.getParsedObject();
//        model = parser.parseOBJ(R.raw.cube);
//        //model.vertexBuffer = obj.points().buffer();
//
//
//        // Initialize the buffers.
//        mCubeVertices = ByteBuffer.allocateDirect(Cube.vertices.length * mBytesPerFloat)
//                .order(ByteOrder.nativeOrder()).asFloatBuffer();
//
//        mCubeColors = ByteBuffer.allocateDirect(Cube.colors.length * mBytesPerFloat)
//                .order(ByteOrder.nativeOrder()).asFloatBuffer();
//        mCubeCoords = ByteBuffer.allocateDirect(Cube.vertices.length*2)
//                .order(ByteOrder.nativeOrder()).asFloatBuffer();
//        mCubeVertices.put(Cube.vertices).position(0);
//        mCubeColors.put(Cube.colors).position(0);
//        mCubeCoords.put(Cube.texCoords).position(0);


//        FloatBuffer cubePositionsBuffer = ByteBuffer.allocateDirect(Cube.vertices.length * mBytesPerFloat)
//        .order(ByteOrder.nativeOrder()).asFloatBuffer();
////
////        mTriangle2Vertices.put(triangle2VerticesData).position(0);
////        mTriangle3Vertices.put(triangle3VerticesData).position(0);
//            GLES20.glGenBuffers(3, buffers, 0);
//            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
//            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mCubeBuffer.capacity() * mBytesPerFloat,
//                    mCubeBuffer, GLES20.GL_STATIC_DRAW);


    }
    int programHandle;
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to gray.
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
// Position the eye behind the origin.


        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.



        final int[] compileStatus = new int[1];

        // Load in the fragment shader shader.
        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        if (fragmentShaderHandle != 0) {
            // Pass in the shader source.
            GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

            // Compile the shader.
            GLES20.glCompileShader(fragmentShaderHandle);

// Get the compilation status.
            GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {

                Log.e(Util.LOG_TAG, "Could not compile shader :");
                Log.e(Util.LOG_TAG, GLES20.glGetShaderInfoLog(fragmentShaderHandle));
                GLES20.glDeleteShader(fragmentShaderHandle);
                fragmentShaderHandle = 0;
            }
        }

        if (fragmentShaderHandle == 0) {
            throw new RuntimeException("Error creating fragment shader.");
        }
        // Load in the vertex shader.
        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        if (vertexShaderHandle != 0) {
            // Pass in the shader source.
            GLES20.glShaderSource(vertexShaderHandle, vertexShader);

            // Compile the shader.
            GLES20.glCompileShader(vertexShaderHandle);

// Get the compilation status.
            GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {
                GLES20.glDeleteShader(vertexShaderHandle);
                vertexShaderHandle = 0;
            }
        }

        if (vertexShaderHandle == 0) {
            throw new RuntimeException("Error creating vertex shader.");
        }



        programHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[] {"a_Position",  "a_Color", "a_Normal", "a_TexCoordinate", "u_Texture"});

        mTextureDataHandle = TextureHelper.loadTexture(context, R.drawable.red);

        // Set program handles. These will later be used to pass in values to the program.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
        mTextureUniformHandle = GLES20.glGetUniformLocation(programHandle, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate");

        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(programHandle);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

// Create a new perspective projection matrix. The height will stay the same
// while the width will vary as per aspect ratio.
        Camera.setup(width,height);
    }


    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        // Do a complete rotation every 10 seconds.
        // Draw the triangle facing straight on.
//        Matrix.setIdentityM(mModelMatrix, 0);
//        Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.9f, 0.5f, 1.0f);

        //model.draw(glUnused);
        // drawTriangle(model.vertexBuffer);

        //drawTriangle(mCubeVertices);
        myModel.draw();
//
//        // Draw one translated a bit down and rotated to be flat on the ground.
//        Matrix.setIdentityM(mModelMatrix, 0);
//        Matrix.translateM(mModelMatrix, 0, 0.0f, -1.0f, 0.0f);
//        Matrix.rotateM(mModelMatrix, 0, 90.0f, 1.0f, 0.0f, 0.0f);
//        Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);
//        drawTriangle(mTriangle2Vertices);
//
//        // Draw one translated a bit to the right and rotated to be facing to the left.
//        Matrix.setIdentityM(mModelMatrix, 0);
//        Matrix.translateM(mModelMatrix, 0, 1.0f, 0.0f, 0.0f);
//        Matrix.rotateM(mModelMatrix, 0, 90.0f, 0.0f, 1.0f, 0.0f);
//        Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);
//        drawTriangle(mTriangle3Vertices);
    }

    /**
     * Draws a triangle from the given vertex data.
     *
     * @param aTriangleBuffer The buffer containing the vertex data.
     */
    private void drawTriangle(final FloatBuffer aTriangleBuffer) {

//        GLES20.glVertexAttribPointer(mPositionHandle, 4, GLES20.GL_FLOAT, false,
//                0, model.vertexBuffer);
//        GLES20.glEnableVertexAttribArray(mPositionHandle);


//        // Set the active texture unit to texture unit 0.
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//
//        // Bind the texture to this unit.
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
//
//        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
//        GLES20.glUniform1i(mTextureUniformHandle, 0);
//
////        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false,
////                0, mCubeColors);
////        GLES20.glEnableVertexAttribArray(mColorHandle);
//
//        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
//                0, mCubeCoords);
//        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
//        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
//
//        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
//        // (which now contains model * view * projection).
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
//
//        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, model.getVSize());

    }
}