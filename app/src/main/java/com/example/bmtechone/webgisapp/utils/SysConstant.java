package com.example.bmtechone.webgisapp.utils;

import com.esri.core.geometry.CoordinateConversion;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.ProjectionTransformation;
import com.esri.core.geometry.SpatialReference;

/**
 * @author ywq
 * @className SysConstant
 * @description
 * @date 2016-03-07
 * @time 14:03
 */
public class SysConstant {
   /* String string="30.6942871496,111.2807696288";
    //WGS 84 经纬度坐标转米制坐标
    Point p=  CoordinateConversion.degreesDecimalMinutesToPoint(string,SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR_AUXILIARY_SPHERE));
    public static  Point CenterPoint=p;*/

    //weburl地址，可放置indexjq.html文件到本地
    public final static String webUrl="http://192.168.0.107/mobilegis/jqueyjs/indexjq.html";

}
