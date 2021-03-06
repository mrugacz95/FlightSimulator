package com.mrugas.flightsimulator.models;

/**
 * Created by mruga on 31.07.2016.
 */
public class Cube {
    static public int vertexCount=36;

    static public float vertices[]={
            1.0f,-1.0f,-1.0f,1.0f,
            -1.0f, 1.0f,-1.0f,1.0f,
            -1.0f,-1.0f,-1.0f,1.0f,

            1.0f,-1.0f,-1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,
            -1.0f, 1.0f,-1.0f,1.0f,


            -1.0f,-1.0f, 1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,
            1.0f,-1.0f, 1.0f,1.0f,

            -1.0f,-1.0f, 1.0f,1.0f,
            -1.0f, 1.0f, 1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,

            1.0f,-1.0f, 1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,
            1.0f,-1.0f,-1.0f,1.0f,

            1.0f,-1.0f, 1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,

            -1.0f,-1.0f,-1.0f,1.0f,
            -1.0f, 1.0f, 1.0f,1.0f,
            -1.0f,-1.0f, 1.0f,1.0f,

            -1.0f,-1.0f,-1.0f,1.0f,
            -1.0f, 1.0f,-1.0f,1.0f,
            -1.0f, 1.0f, 1.0f,1.0f,

            -1.0f,-1.0f,-1.0f,1.0f,
            1.0f,-1.0f, 1.0f,1.0f,
            1.0f,-1.0f,-1.0f,1.0f,

            -1.0f,-1.0f,-1.0f,1.0f,
            -1.0f,-1.0f, 1.0f,1.0f,
            1.0f,-1.0f, 1.0f,1.0f,

            -1.0f, 1.0f, 1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,

            -1.0f, 1.0f, 1.0f,1.0f,
            -1.0f, 1.0f,-1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,

    };

    static public float colors[]={
            1.0f,0.0f,0.0f,1.0f,
            1.0f,0.0f,0.0f,1.0f,
            1.0f,0.0f,0.0f,1.0f,

            1.0f,0.0f,0.0f,1.0f,
            1.0f,0.0f,0.0f,1.0f,
            1.0f,0.0f,0.0f,1.0f,

            0.0f,1.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f,

            0.0f,1.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f,

            0.0f,0.0f,1.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,

            0.0f,0.0f,1.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,

            1.0f,1.0f,0.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,

            1.0f,1.0f,0.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,

            0.0f,1.0f,1.0f,1.0f,
            0.0f,1.0f,1.0f,1.0f,
            0.0f,1.0f,1.0f,1.0f,

            0.0f,1.0f,1.0f,1.0f,
            0.0f,1.0f,1.0f,1.0f,
            0.0f,1.0f,1.0f,1.0f,

            1.0f,1.0f,1.0f,1.0f,
            1.0f,1.0f,1.0f,1.0f,
            1.0f,1.0f,1.0f,1.0f,

            1.0f,1.0f,1.0f,1.0f,
            1.0f,1.0f,1.0f,1.0f,
            1.0f,1.0f,1.0f,1.0f,
    };

    static public float normals[]={
            0.0f, 0.0f,-1.0f,0.0f,
            0.0f, 0.0f,-1.0f,0.0f,
            0.0f, 0.0f,-1.0f,0.0f,

            0.0f, 0.0f,-1.0f,0.0f,
            0.0f, 0.0f,-1.0f,0.0f,
            0.0f, 0.0f,-1.0f,0.0f,

            0.0f, 0.0f, 1.0f,0.0f,
            0.0f, 0.0f, 1.0f,0.0f,
            0.0f, 0.0f, 1.0f,0.0f,

            0.0f, 0.0f, 1.0f,0.0f,
            0.0f, 0.0f, 1.0f,0.0f,
            0.0f, 0.0f, 1.0f,0.0f,

            1.0f, 0.0f, 0.0f,0.0f,
            1.0f, 0.0f, 0.0f,0.0f,
            1.0f, 0.0f, 0.0f,0.0f,

            1.0f, 0.0f, 0.0f,0.0f,
            1.0f, 0.0f, 0.0f,0.0f,
            1.0f, 0.0f, 0.0f,0.0f,

            -1.0f, 0.0f, 0.0f,0.0f,
            -1.0f, 0.0f, 0.0f,0.0f,
            -1.0f, 0.0f, 0.0f,0.0f,

            -1.0f, 0.0f, 0.0f,0.0f,
            -1.0f, 0.0f, 0.0f,0.0f,
            -1.0f, 0.0f, 0.0f,0.0f,

            0.0f,-1.0f, 0.0f,0.0f,
            0.0f,-1.0f, 0.0f,0.0f,
            0.0f,-1.0f, 0.0f,0.0f,

            0.0f,-1.0f, 0.0f,0.0f,
            0.0f,-1.0f, 0.0f,0.0f,
            0.0f,-1.0f, 0.0f,0.0f,

            0.0f, 1.0f, 0.0f,0.0f,
            0.0f, 1.0f, 0.0f,0.0f,
            0.0f, 1.0f, 0.0f,0.0f,

            0.0f, 1.0f, 0.0f,0.0f,
            0.0f, 1.0f, 0.0f,0.0f,
            0.0f, 1.0f, 0.0f,0.0f,
    };

    static public float vertexNormals[]={
            1.0f,-1.0f,-1.0f,1.0f,
            -1.0f, 1.0f,-1.0f,1.0f,
            -1.0f,-1.0f,-1.0f,1.0f,

            1.0f,-1.0f,-1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,
            -1.0f, 1.0f,-1.0f,1.0f,


            -1.0f,-1.0f, 1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,
            1.0f,-1.0f, 1.0f,1.0f,

            -1.0f,-1.0f, 1.0f,1.0f,
            -1.0f, 1.0f, 1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,

            1.0f,-1.0f, 1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,
            1.0f,-1.0f,-1.0f,1.0f,

            1.0f,-1.0f, 1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,

            -1.0f,-1.0f,-1.0f,1.0f,
            -1.0f, 1.0f, 1.0f,1.0f,
            -1.0f,-1.0f, 1.0f,1.0f,

            -1.0f,-1.0f,-1.0f,1.0f,
            -1.0f, 1.0f,-1.0f,1.0f,
            -1.0f, 1.0f, 1.0f,1.0f,

            -1.0f,-1.0f,-1.0f,1.0f,
            1.0f,-1.0f, 1.0f,1.0f,
            1.0f,-1.0f,-1.0f,1.0f,

            -1.0f,-1.0f,-1.0f,1.0f,
            -1.0f,-1.0f, 1.0f,1.0f,
            1.0f,-1.0f, 1.0f,1.0f,

            -1.0f, 1.0f, 1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,

            -1.0f, 1.0f, 1.0f,1.0f,
            -1.0f, 1.0f,-1.0f,1.0f,
            1.0f, 1.0f,-1.0f,1.0f,
    };

    static public float texCoords[]={
            1.0f,1.0f, 0.0f,0.0f, 0.0f,1.0f,
            1.0f,1.0f, 1.0f,0.0f, 0.0f,0.0f,

            1.0f,1.0f, 0.0f,0.0f, 0.0f,1.0f,
            1.0f,1.0f, 1.0f,0.0f, 0.0f,0.0f,

            1.0f,1.0f, 0.0f,0.0f, 0.0f,1.0f,
            1.0f,1.0f, 1.0f,0.0f, 0.0f,0.0f,

            1.0f,1.0f, 0.0f,0.0f, 0.0f,1.0f,
            1.0f,1.0f, 1.0f,0.0f, 0.0f,0.0f,

            1.0f,1.0f, 0.0f,0.0f, 0.0f,1.0f,
            1.0f,1.0f, 1.0f,0.0f, 0.0f,0.0f,

            1.0f,1.0f, 0.0f,0.0f, 0.0f,1.0f,
            1.0f,1.0f, 1.0f,0.0f, 0.0f,0.0f,
    };


}
