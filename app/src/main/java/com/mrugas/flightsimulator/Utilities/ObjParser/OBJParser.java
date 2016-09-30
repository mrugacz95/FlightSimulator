package com.mrugas.flightsimulator.Utilities.ObjParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import com.mrugas.flightsimulator.Utilities.Bounds;
import com.mrugas.flightsimulator.Utilities.Material;
import com.mrugas.flightsimulator.Utilities.Triangulator;


public class OBJParser {
    int numVertices=0;
    int numFaces=0;
    Context context;
    static HashMap<Integer,OBJParser> models = new HashMap<>();
    public static final int BYTES_PER_FLOAT = 4;
    Vector<Short> vPointer =new Vector<Short>();
    Vector<Short> vtPointer=new Vector<Short>();
    Vector<Short> vnPointer=new Vector<Short>();
    Vector<Float> v=new Vector<Float>();
    Vector<Float> vn=new Vector<Float>();
    Vector<Float> vt=new Vector<Float>();

    private final static String TAG = "OBJParser";
    public OBJParser(Context ctx){
        context=ctx;
    }
    private Integer resId=null;
    public OBJParser parseOBJ(Integer resourceId) {
        if(models.containsKey(resourceId)) {
            resId=resourceId;
            return models.get(resourceId);
        }
        BufferedReader reader=null;
        String line = null;
        Material m=null;

        long startTime = Calendar.getInstance().getTimeInMillis();
        Log.d(TAG, "Start parsing object " + context.getResources().getResourceEntryName(resourceId));
        Log.d(TAG, "Start time " + startTime);

        InputStream inputStream = context.getResources().openRawResource(resourceId);
        reader = new BufferedReader(new InputStreamReader(inputStream));

        try {//try to read lines of the file
            while((line = reader.readLine()) != null) {
                //Log.v("obj",line);
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

        long endTime = Calendar.getInstance().getTimeInMillis();
        Log.d(TAG, "End time " + (endTime - startTime));
        resId=resourceId;
        models.put(resourceId,this);
        return this;
    }


    private void processVLine(String line){
        String [] tokens=line.split("[ ]+"); //split the line at the spaces
        int c=tokens.length;
        for(int i=1; i<c; i++){ //add the vertex to the vertex array
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
                    vPointer.add(s);
                }
            }
            else{//more vPointer
                Vector<Short> polygon=new Vector<>();
                for(int i=1; i<tokens.length; i++){
                    Short s=Short.valueOf(tokens[i]);
                    s--;
                    polygon.add(s);
                }
                vPointer.addAll(Triangulator.triangulate(polygon));//triangulate the polygon and add the resulting vPointer
            }
        }
        if(tokens[1].matches("[0-9]+/[0-9]+")){//if: v/vt
            if(c==4){//3 vPointer
                for(int i=1; i<c; i++){
                    Short s=Short.valueOf(tokens[i].split("/")[0]);
                    s--;
                    vPointer.add(s);
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
                vPointer.addAll(Triangulator.triangulate(tmpFaces));
                vtPointer.addAll(Triangulator.triangulate(tmpVt));
            }
        }
        if(tokens[1].matches("[0-9]+//[0-9]+")){//f: v//vn
            if(c==4){//3 vPointer
                for(int i=1; i<c; i++){
                    Short s=Short.valueOf(tokens[i].split("//")[0]);
                    s--;
                    vPointer.add(s);
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
                vPointer.addAll(Triangulator.triangulate(tmpFaces));
                vnPointer.addAll(Triangulator.triangulate(tmpVn));
            }
        }
        if(tokens[1].matches("[0-9]+/[0-9]+/[0-9]+")){//f: v/vt/vn

            if(c==4){//3 vPointer
                for(int i=1; i<c; i++){
                    Short s=Short.valueOf(tokens[i].split("/")[0]);
                    s--;
                    vPointer.add(s);
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
                vPointer.addAll(Triangulator.triangulate(tmpFaces));
                vtPointer.addAll(Triangulator.triangulate(tmpVn));
                vnPointer.addAll(Triangulator.triangulate(tmpVn));
            }
        }
    }

    public FloatBuffer getVertexBuffer() {
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vPointer.size() * BYTES_PER_FLOAT * 3).order(ByteOrder.nativeOrder()).asFloatBuffer();
        List<Float> vertList = new ArrayList<>();
        for(int verPtr : vPointer){
            vertexBuffer.put(v.get(verPtr*3));
            vertList.add(v.get(verPtr*3));

            vertexBuffer.put(v.get(verPtr*3+1));
            vertList.add(v.get(verPtr*3+1));

            vertexBuffer.put(v.get(verPtr*3+2));
            vertList.add(v.get(verPtr*3+2));
        }
        vertexBuffer.position(0);
        return vertexBuffer;
    }

    public FloatBuffer getUVBuffer() {
        FloatBuffer UVBuffer = ByteBuffer.allocateDirect(vPointer.size() * BYTES_PER_FLOAT * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        List<Float> vertList = new ArrayList<>();
        for(int uvPtr : vtPointer){
            UVBuffer.put(vt.get(uvPtr*2));
            vertList.add(vt.get(uvPtr*2));

            UVBuffer.put(vt.get(uvPtr*2+1));
            vertList.add(vt.get(uvPtr*2+1));
        }
        UVBuffer.position(0);
        return UVBuffer;
    }

    public int getVertexCount() {
        return vPointer.size();
    }

    @SuppressWarnings("ConstantConditions")
    public Bounds getBounds(){
        Log.d("Bounds",context.getResources().getResourceEntryName(resId));
        if(v.size()<=3) throw new RuntimeException("Object is not parsed:" + context.getResources().getResourceEntryName(resId));
        Float front = v.get(2);
        Float  back= v.get(2);
        Float bottom=v.get(1);
        Float top=v.get(1);
        Float left=v.get(0);
        Float right=v.get(0);

        for(int i=0;i<v.size();i+=3){
            right=Math.max(right,v.get(i));
            left=Math.min(left,v.get(i));
            top=Math.max(top,v.get(i+1));
            bottom=Math.min(bottom,v.get(i+1));
            back=Math.max(back,v.get(i+2));
            front=Math.min(front,v.get(i+2));
        }
        return new Bounds(front,back,bottom,top,left,right);
    }

    public FloatBuffer getNormalBuffer() {
        FloatBuffer normalBuffer = ByteBuffer.allocateDirect(vnPointer.size() * BYTES_PER_FLOAT * 3).order(ByteOrder.nativeOrder()).asFloatBuffer();
        List<Float> vertList = new ArrayList<>();
        for(int nPtr : vnPointer){
            normalBuffer.put(vn.get(nPtr*3));
            vertList.add(vn.get(nPtr*3));

            normalBuffer.put(vn.get(nPtr*3+1));
            vertList.add(vn.get(nPtr*3+1));

            normalBuffer.put(vn.get(nPtr*3+2));
            vertList.add(vn.get(nPtr*3+2));
        }
        normalBuffer.position(0);
        return normalBuffer;

    }
}

