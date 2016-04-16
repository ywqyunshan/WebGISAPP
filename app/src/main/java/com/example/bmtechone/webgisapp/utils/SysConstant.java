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
    public final static String webUrl="http://192.168.5.3/mobilegis/jqueyjs/spatialquery.html";
    //图层url
    public final static String tileUrl="http://www.gistech.com.cn/ArcGIS/rest/services/ChinaOnlineCommunity_Mobile/MapServer";
    //要素图层url
    public final static String preUrl="http://www.gistech.com.cn/ArcGIS/rest/services/Presales/ShangHai_Village/MapServer";
    //上海坐标
    public final static String string = "31.361310,121.434339";
    //地图缩放比例尺
    public final static Double mapscale=1000.0;
    //WGS84坐标系标识
    public  final  static int WGS84=4326;

}
