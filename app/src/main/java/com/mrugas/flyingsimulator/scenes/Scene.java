package com.mrugas.flyingsimulator.scenes;

import android.content.Context;
import android.opengl.GLES20;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.ShaderHelper;
import com.mrugas.flyingsimulator.managers.ShaderManger;
import com.mrugas.flyingsimulator.models.BaseModel;
import com.mrugas.flyingsimulator.models.Model;

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
            inputStream = context.getResources().openRawResource(R.raw.simple_fragment_shader);
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
        models.put("plane",new Model(R.raw.teapot2,ShaderManger.getInstance().getProgramHandle("simple_program"),context));
    }

    void draw(){
        for(BaseModel model : models.values()){
            model.draw();
        }
    }
}
