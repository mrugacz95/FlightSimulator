package com.mrugas.flyingsimulator.scenes;

import android.content.Context;
import android.opengl.GLES20;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.ShaderHelper;
import com.mrugas.flyingsimulator.managers.ShaderManger;
import com.mrugas.flyingsimulator.models.BaseModel;
import com.mrugas.flyingsimulator.models.Model;
import com.mrugas.flyingsimulator.models.PlaneModel;
import com.mrugas.flyingsimulator.models.TexturedModel;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by mruga on 01.08.2016.
 */
public class Scene {
    HashMap<String,BaseModel> models = new HashMap<>();

    public void init(Context context){
        try {
            String vertexShader, fragmentShader;
            InputStream inputStream = context.getResources().openRawResource(R.raw.simple_vertex_shader);
            vertexShader = IOUtils.toString(inputStream, "UTF-8");
            inputStream = context.getResources().openRawResource(R.raw.texture_fragment_shader);
            fragmentShader = IOUtils.toString(inputStream, "UTF-8");
            int fragHand = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader);
            int vertHand = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER,vertexShader);
            ShaderManger.getInstance().addProgram("simple_program",
                    ShaderHelper.createAndLinkProgram(vertHand,fragHand,
                    new String[] {"a_Position",  "a_Color", "a_Normal", "a_TexCoordinate", "u_Texture"}));

        } catch (IOException e) {
            e.printStackTrace();
        }

        GLES20.glUseProgram(ShaderManger.getInstance().getProgramHandle("simple_program"));
        BaseModel cube = new TexturedModel(ShaderManger.getInstance().getProgramHandle("simple_program"),context, R.raw.cube, R.drawable.uv_checker_large);
        cube.translate(0,-12,0);
        cube.scale(20,20,20);
        models.put("cube", cube);
        BaseModel plane = new PlaneModel(ShaderManger.getInstance().getProgramHandle("simple_program"), context);
            plane.translate(0,-1,0);

        models.put("plane", plane);
    }

    void draw(){
        for(BaseModel model : models.values()){
            model.draw();
        }
    }
    public BaseModel getModel(String name){
        return models.get(name);
    }
}
