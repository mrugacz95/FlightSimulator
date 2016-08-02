package com.mrugas.flyingsimulator.scenes;

import java.util.Stack;

/**
 * Created by mruga on 01.08.2016.
 */
public class SceneManager {
    static private SceneManager sceneManager;
    Stack<Scene> scenes= new Stack<>();

    static public SceneManager getInstance(){
        if(sceneManager==null)
            sceneManager  = new SceneManager();
        return sceneManager;
    }

    public void addScene(Scene scene){
        scenes.push(scene);
    }
    public Scene getCurrentScene(){
        if(!scenes.isEmpty())
            return scenes.peek();
        else
            return null;
    }
    public void draw(){
        scenes.peek().draw();
    }
}
