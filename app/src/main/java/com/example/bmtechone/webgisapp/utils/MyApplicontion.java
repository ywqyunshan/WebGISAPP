package com.example.bmtechone.webgisapp.utils;

import android.app.Application;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;

/**
 * @author ywq
 * @className MyApplicontion
 * @description
 * @date 2016-03-10
 * @time 11:44
 */
public class MyApplicontion  extends Application{


    public static Point point;

    @Override
    public void onCreate() {
        super.onCreate();
        initVar();

    }

    /*坐标转换*/
    public static Point curToWgs84(Point p){

        SpatialReference wgs84sp=SpatialReference.create(SpatialReference.WKID_WGS84);
        SpatialReference cur=SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR_AUXILIARY_SPHERE);
        Point point= (Point)GeometryEngine.project(p, cur, wgs84sp);
        return point;


    }



    private void initVar() {
        point=new Point();
    }

}
