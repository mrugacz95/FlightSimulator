package com.mrugas.flightsimulator.models.ParticleSystem;

import android.content.Context;
import android.opengl.GLES30;

import com.mrugas.flightsimulator.Utilities.ObjParser.OBJParser;
import com.mrugas.flightsimulator.Utilities.Vector3;
import com.mrugas.flightsimulator.models.BaseModel;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mruga on 15.09.2016.
 */
public class ParticleSystem extends BaseModel {
    private int programHandle;
    private int ParticlesCount =0;
    static int maxParticles = 3;
    FloatBuffer g_particule_position_size_data;
    FloatBuffer g_particule_color_data;
    FloatBuffer vertex_buffer_data;
    List<Particle> particleList = new ArrayList<>(maxParticles);
    private Context context;

    public ParticleSystem(int programHandle, Context context) {
        super();
        this.programHandle = programHandle;
        this.context = context;
        init();
    }
    static final float g_vertex_buffer_data[] = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
    };
    final int[] billboard_vertex_buffer = new int[1];
    final int[] particles_position_buffer  = new int[1];
    final int[] particles_color_buffer  = new int[1];
    @Override
    public void init() {

        g_particule_position_size_data = ByteBuffer.allocateDirect(maxParticles*4*OBJParser.BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        float[] positions = {0,0,0, 1, 1,1,1, 1, 2,2,2, 1};
        g_particule_position_size_data.put(positions);
        g_particule_position_size_data.position(0);

        g_particule_color_data = ByteBuffer.allocateDirect(maxParticles*4*OBJParser.BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        float[] colors = {1,1,1, 0.5f,0.4f,0.3f, 1,0,0};
        g_particule_color_data.put(colors);
        g_particule_color_data.position(0);

        vertex_buffer_data = ByteBuffer.allocate(g_vertex_buffer_data.length*OBJParser.BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertex_buffer_data.put(g_vertex_buffer_data).position(0);
        GLES30.glGenBuffers(1, billboard_vertex_buffer,0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, billboard_vertex_buffer[0]);
        //GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertex_buffer_data.capacity()*OBJParser.BYTES_PER_FLOAT, vertex_buffer_data, GLES30.GL_STATIC_DRAW);

// The VBO containing the positions and sizes of the particles
        GLES30.glGenBuffers(1, particles_position_buffer,0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, particles_position_buffer[0]);
// Initialize with empty (NULL) buffer : it will be updated later, each frame.
        //GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, maxParticles * 4 * OBJParser.BYTES_PER_FLOAT, null, GLES30.GL_STREAM_DRAW);

// The VBO containing the colors of the particles
        GLES30.glGenBuffers(1, particles_color_buffer,0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, particles_color_buffer[0]);
// Initialize with empty (NULL) buffer : it will be updated later, each frame.
        //GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, maxParticles * 4 * 2/*sizeof(GLubyte)*/, null, GLES30.GL_STREAM_DRAW);

    }


    public class Particle {
        Vector3 pos, speed;
        short r,g,b,a;
        float size, angle, weight;
        float life;
    }

    @Override
    public void draw() {
        super.draw();
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, particles_position_buffer[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, maxParticles * 4 * OBJParser.BYTES_PER_FLOAT, null, GLES30.GL_STREAM_DRAW); // Buffer orphaning, a common way to improve streaming perf. See above link for details.
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, 0, ParticlesCount * OBJParser.BYTES_PER_FLOAT * 4, g_particule_position_size_data);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, particles_color_buffer[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, maxParticles * 4 * 2, null, GLES30.GL_STREAM_DRAW); // Buffer orphaning, a common way to improve streaming perf. See above link for details.
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, 0, ParticlesCount * 2 * 4, g_particule_color_data);


        GLES30.glEnableVertexAttribArray(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, billboard_vertex_buffer[0]);
        GLES30.glVertexAttribPointer(
                0, // attribute. No particular reason for 0, but must match the layout in the shader.
                3, // size
                GLES30.GL_FLOAT, // type
                false, // normalized?
                0, // stride
                0 // array buffer offset
        );

// 2nd attribute buffer : positions of particles' centers
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, particles_position_buffer[0]);
        GLES30.glVertexAttribPointer(
                1, // attribute. No particular reason for 1, but must match the layout in the shader.
                4, // size : x + y + z + size => 4
                GLES30.GL_FLOAT, // type
                false, // normalized?
                0, // stride
                0 // array buffer offset
        );

// 3rd attribute buffer : particles' colors
        GLES30.glEnableVertexAttribArray(2);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, particles_color_buffer[0]);
        GLES30.glVertexAttribPointer(
                2, // attribute. No particular reason for 1, but must match the layout in the shader.
                4, // size : r + g + b + a => 4
                GLES30.GL_UNSIGNED_BYTE, // type
                true, // normalized? *** YES, this means that the unsigned char[4] will be accessible with a vec4 (floats) in the shader ***
                0, // stride
                0 // array buffer offset
        );
        // These functions are specific to glDrawArrays*Instanced*.
// The first parameter is the attribute buffer we're talking about.
// The second parameter is the "rate at which generic vertex attributes advance when rendering multiple instances"
// http://www.opengl.org/sdk/docs/man/xhtml/glVertexAttribDivisor.xml
        GLES30.glVertexAttribDivisor(0, 0); // particles vertices : always reuse the same 4 vertices -> 0
        GLES30.glVertexAttribDivisor(1, 1); // positions : one per quad (its center) -> 1
        GLES30.glVertexAttribDivisor(2, 1); // color : one per quad -> 1

// Draw the particules !
// This draws many times a small triangle_strip (which looks like a quad).
// This is equivalent to :
// for(i in ParticlesCount) : glDrawArrays(GL_TRIANGLE_STRIP, 0, 4),
// but faster.
        GLES30.glDrawArraysInstanced(GLES30.GL_TRIANGLE_STRIP, 0, 4, ParticlesCount);
    }
}
