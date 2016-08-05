package com.mrugas.flightsimulator.managers;

import java.util.HashMap;

/**
 * Created by mruga on 01.08.2016.
 */
public class TextureManager {

    HashMap<String,Texture> textures = new HashMap<>();

    static private TextureManager textureManager;
    static public TextureManager getInstance(){
        if(textureManager==null)
            textureManager = new TextureManager();
        return textureManager;
    }
    public void addTexture(String name,Texture texture){
        textures.put(name,texture);
    }
    public Texture getTexture(String name){
        return textures.get(name);
    }


}
