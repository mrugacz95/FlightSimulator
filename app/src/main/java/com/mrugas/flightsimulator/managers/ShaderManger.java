package com.mrugas.flightsimulator.managers;

import android.content.Context;
import android.opengl.GLES30;
import android.util.Log;

import com.mrugas.flightsimulator.ShaderHelper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by mruga on 01.08.2016.
 */
public class ShaderManger {

    static private ShaderManger shaderManger = null;
    HashMap<String,Integer> programs = new HashMap<>();
    HashMap<Integer,Integer> vertexShaders = new HashMap<>();
    HashMap<Integer,Integer> fragmentShaders = new HashMap<>();
    static public ShaderManger getInstance(){
        if(shaderManger==null) {
            shaderManger = new ShaderManger();
        }
        return shaderManger;
    }

    public Integer getProgramHandle(String name) {
        return programs.get(name);
    }

    public void addProgram(Integer vertexShaderResId, Integer fragmentShaderRedId, String programName, Context context){
        if(programs.get(programName)!=null)
            Log.e("ShaderManager", "Already Added");
            int vertHand = -1;
            InputStream inputStream;
            if(vertexShaders.get(vertexShaderResId)==null) {
                String vertexShader;
                inputStream = context.getResources().openRawResource(vertexShaderResId);
                try {
                    vertexShader = IOUtils.toString(inputStream, "UTF-8");
                    vertHand = ShaderHelper.compileShader(GLES30.GL_VERTEX_SHADER, vertexShader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                vertHand = vertexShaders.get(vertexShaderResId);
            int fragHand = -1;
            if(fragmentShaders.get(fragmentShaderRedId)==null) {
                String fragmentShader;
                inputStream = context.getResources().openRawResource(fragmentShaderRedId);
                try {
                    fragmentShader = IOUtils.toString(inputStream, "UTF-8");
                    fragHand = ShaderHelper.compileShader(GLES30.GL_FRAGMENT_SHADER,fragmentShader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                vertHand = vertexShaders.get(vertexShaderResId);

            programs.put(programName,
                    ShaderHelper.createAndLinkProgram(vertHand,fragHand,
                            new String[] {"a_Position",  "a_Color", "a_Normal", "a_TexCoordinate", "u_Texture"}));
    }





}
