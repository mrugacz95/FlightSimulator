package com.mrugas.flyingsimulator;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;


import com.mrugas.flyingsimulator.Utilities.Camera;
import com.mrugas.flyingsimulator.Utilities.TDModel;
import com.mrugas.flyingsimulator.Utilities.Util;
import com.mrugas.flyingsimulator.models.BaseModel;
import com.mrugas.flyingsimulator.models.Model;
import com.mrugas.flyingsimulator.scenes.Scene;
import com.mrugas.flyingsimulator.scenes.SceneManager;

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
    String vertexShader = "";    // normalized screen coordinates.
    String fragmentShader = "";

    Context context;
    BaseModel myModel;
    public GLES20Renderer(Context context) {

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

        Scene scene = new Scene();
        scene.init(context);
        SceneManager.getInstance().addScene(scene);

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.



//        final int[] compileStatus = new int[1];
//
//        // Load in the fragment shader shader.
//        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
//
//        if (fragmentShaderHandle != 0) {
//            // Pass in the shader source.
//            GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);
//
//            // Compile the shader.
//            GLES20.glCompileShader(fragmentShaderHandle);
//
//// Get the compilation status.
//            GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
//
//            // If the compilation failed, delete the shader.
//            if (compileStatus[0] == 0) {
//
//                Log.e(Util.LOG_TAG, "Could not compile shader :");
//                Log.e(Util.LOG_TAG, GLES20.glGetShaderInfoLog(fragmentShaderHandle));
//                GLES20.glDeleteShader(fragmentShaderHandle);
//                fragmentShaderHandle = 0;
//            }
//        }
//
//        if (fragmentShaderHandle == 0) {
//            throw new RuntimeException("Error creating fragment shader.");
//        }
//        // Load in the vertex shader.
//        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
//
//        if (vertexShaderHandle != 0) {
//            // Pass in the shader source.
//            GLES20.glShaderSource(vertexShaderHandle, vertexShader);
//
//            // Compile the shader.
//            GLES20.glCompileShader(vertexShaderHandle);
//
//// Get the compilation status.
//            GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
//
//            // If the compilation failed, delete the shader.
//            if (compileStatus[0] == 0) {
//                GLES20.glDeleteShader(vertexShaderHandle);
//                vertexShaderHandle = 0;
//            }
//        }
//
//        if (vertexShaderHandle == 0) {
//            throw new RuntimeException("Error creating vertex shader.");
//        }
//
//
//
//        programHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
//                new String[] {"a_Position",  "a_Color", "a_Normal", "a_TexCoordinate", "u_Texture"});


        // Set program handles. These will later be used to pass in values to the program.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
//        mTextureUniformHandle = GLES20.glGetUniformLocation(programHandle, "u_Texture");
//        mTextureCoordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate");

        // Tell OpenGL to use this program when rendering.
        //GLES20.glUseProgram(programHandle);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Camera.setup(width,height);
    }


    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        //SceneManager.getInstance().draw();
        myModel.draw();

//        myModel.draw();
    }


}