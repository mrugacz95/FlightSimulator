package com.mrugas.flyingsimulator.managers;

import java.util.HashMap;

/**
 * Created by mruga on 01.08.2016.
 */
public class ShaderManger {

    private ShaderManger shaderManger = null;
    HashMap<String,Integer> programs;

    public ShaderManger getInstance(){
        if(shaderManger==null)
            shaderManger=new ShaderManger();
        return shaderManger;
    }





}
