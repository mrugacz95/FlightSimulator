package com.mrugas.flyingsimulator.Utilities;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Created by mruga on 01.08.2016.
 */
public class FinalParser {
    Context context;
    Vector<Short> faces=new Vector<Short>();
    Vector<Short> vtPointer=new Vector<Short>();
    Vector<Short> vnPointer=new Vector<Short>();
    Vector<Float> v=new Vector<Float>();
    Vector<Float> vn=new Vector<Float>();
    Vector<Float> vt=new Vector<Float>();
    FinalParser(Context context){ this.context=context; }

    public TDModel parseOBJ(int resourceId) {
        BufferedReader reader=null;
        String line = null;
        Material m=null;

        InputStream inputStream = context.getResources().openRawResource(resourceId);
        reader = new BufferedReader(new InputStreamReader(inputStream));

        try {//try to read lines of the file
            while((line = reader.readLine()) != null) {
                Log.v("obj",line);
                if(line.startsWith("f")){//a polygonal face
                    processFLine(line);
                }
                else
                if(line.startsWith("vn")){
                    processVNLine(line);
                }
                else
                if(line.startsWith("vt")){
                    processVTLine(line);
                }
                else
                if(line.startsWith("v")){ //line having geometric position of single vertex
                    processVLine(line);
                }
            }
        }
        catch(IOException e){
            System.out.println("wtf...");
        }

        TDModel t=new TDModel(v,vn,vt,null);
        t.buildVertexBuffer();
        Log.v("models",t.toString());
        return t;
    }


    private void processVLine(String line){
        String [] tokens=line.split("[ ]+"); //split the line at the spaces
        int c=tokens.length;
        for(int i=1; i<c; i++){ //add the vertex to the vertex array
            v.add(Float.valueOf(tokens[i]));
        }
        for(int i=1; i<4; i++){ //add the vertex to the vertex array
            v.add(Float.valueOf(tokens[i]));
        }
    }
    private void processVNLine(String line){
        String [] tokens=line.split("[ ]+"); //split the line at the spaces
        int c=tokens.length;
        for(int i=1; i<c; i++){ //add the vertex to the vertex array
            vn.add(Float.valueOf(tokens[i]));
        }
    }
    private void processVTLine(String line){
        String [] tokens=line.split("[ ]+"); //split the line at the spaces
        int c=tokens.length;
        for(int i=1; i<c; i++){ //add the vertex to the vertex array
            vt.add(Float.valueOf(tokens[i]));
        }
    }
    private void processFLine(String line){
        String [] tokens=line.split("[ ]+");
        int c=tokens.length;

        if(tokens[1].matches("[0-9]+")){//f: v
            if(c==4){//3 vPointer
                for(int i=1; i<c; i++){
                    Short s=Short.valueOf(tokens[i]);
                    s--;
                    faces.add(s);
                }
            }
            else{//more vPointer
                Vector<Short> polygon=new Vector<>();
                for(int i=1; i<tokens.length; i++){
                    Short s=Short.valueOf(tokens[i]);
                    s--;
                    polygon.add(s);
                }
                faces.addAll(Triangulator.triangulate(polygon));//triangulate the polygon and add the resulting vPointer
            }
        }
        if(tokens[1].matches("[0-9]+/[0-9]+")){//if: v/vt
            if(c==4){//3 vPointer
                for(int i=1; i<c; i++){
                    Short s=Short.valueOf(tokens[i].split("/")[0]);
                    s--;
                    faces.add(s);
                    s=Short.valueOf(tokens[i].split("/")[1]);
                    s--;
                    vtPointer.add(s);
                }
            }
            else{//triangulate
                Vector<Short> tmpFaces=new Vector<Short>();
                Vector<Short> tmpVt=new Vector<Short>();
                for(int i=1; i<tokens.length; i++){
                    Short s=Short.valueOf(tokens[i].split("/")[0]);
                    s--;
                    tmpFaces.add(s);
                    s=Short.valueOf(tokens[i].split("/")[1]);
                    s--;
                    tmpVt.add(s);
                }
                faces.addAll(Triangulator.triangulate(tmpFaces));
                vtPointer.addAll(Triangulator.triangulate(tmpVt));
            }
        }
        if(tokens[1].matches("[0-9]+//[0-9]+")){//f: v//vn
            if(c==4){//3 vPointer
                for(int i=1; i<c; i++){
                    Short s=Short.valueOf(tokens[i].split("//")[0]);
                    s--;
                    faces.add(s);
                    s=Short.valueOf(tokens[i].split("//")[1]);
                    s--;
                    vnPointer.add(s);
                }
            }
            else{//triangulate
                Vector<Short> tmpFaces=new Vector<Short>();
                Vector<Short> tmpVn=new Vector<Short>();
                for(int i=1; i<tokens.length; i++){
                    Short s=Short.valueOf(tokens[i].split("//")[0]);
                    s--;
                    tmpFaces.add(s);
                    s=Short.valueOf(tokens[i].split("//")[1]);
                    s--;
                    tmpVn.add(s);
                }
                faces.addAll(Triangulator.triangulate(tmpFaces));
                vnPointer.addAll(Triangulator.triangulate(tmpVn));
            }
        }
        if(tokens[1].matches("[0-9]+/[0-9]+/[0-9]+")){//f: v/vt/vn

            if(c==4){//3 vPointer
                for(int i=1; i<c; i++){
                    Short s=Short.valueOf(tokens[i].split("/")[0]);
                    s--;
                    faces.add(s);
                    s=Short.valueOf(tokens[i].split("/")[1]);
                    s--;
                    vtPointer.add(s);
                    s=Short.valueOf(tokens[i].split("/")[2]);
                    s--;
                    vnPointer.add(s);
                }
            }
            else{//triangulate
                Vector<Short> tmpFaces=new Vector<Short>();
                Vector<Short> tmpVn=new Vector<Short>();
                //Vector<Short> tmpVt=new Vector<Short>();
                for(int i=1; i<tokens.length; i++){
                    Short s=Short.valueOf(tokens[i].split("/")[0]);
                    s--;
                    tmpFaces.add(s);
                    //s=Short.valueOf(tokens[i].split("/")[1]);
                    //s--;
                    //tmpVt.add(s);
                    //s=Short.valueOf(tokens[i].split("/")[2]);
                    //s--;
                    //tmpVn.add(s);
                }
                faces.addAll(Triangulator.triangulate(tmpFaces));
                vtPointer.addAll(Triangulator.triangulate(tmpVn));
                vnPointer.addAll(Triangulator.triangulate(tmpVn));
            }
        }
    }
}
