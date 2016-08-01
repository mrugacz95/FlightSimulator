package com.mrugas.flyingsimulator.managers;

import java.util.HashMap;

/**
 * Created by mruga on 01.08.2016.
 */
public class ShaderManger {

    static private ShaderManger shaderManger = null;
    HashMap<String,Integer> programs = new HashMap<>();

    static public ShaderManger getInstance(){
        if(shaderManger==null)
            shaderManger=new ShaderManger();
        return shaderManger;
    }

    public int getProgramHandle(String name){
        return programs.get(name);
    }

    public void addProgram(String name, int programHandler){
        programs.put(name,programHandler);
    }





}
