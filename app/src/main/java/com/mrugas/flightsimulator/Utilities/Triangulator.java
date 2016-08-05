package com.mrugas.flightsimulator.Utilities;

import java.util.Vector;

/**
 * Created by mruga on 31.07.2016.
 */
public class Triangulator {
    public static Vector<Short> triangulate(Vector<Short> polygon){
        Vector<Short> triangles=new Vector<>();
        for(int i=1; i<polygon.size()-1; i++){
            triangles.add(polygon.get(0));
            triangles.add(polygon.get(i));
            triangles.add(polygon.get(i+1));
        }
        return triangles;
    }
}
