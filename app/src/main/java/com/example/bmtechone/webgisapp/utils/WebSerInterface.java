package com.example.bmtechone.webgisapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.CoordinateConversion;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.example.bmtechone.webgisapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ywq
 * @className WebViewInterface
 * @description webview js与andorid交互接口
 * @date 2016-03-08
 * @time 16:21
 */
public class WebSerInterface {

    Context mContext;
    WebView mWebWiew;
    MapView mMapView;
    /** Instantiate the interface and set the context */
    public WebSerInterface(Context c, WebView w,MapView m) {
        mContext = c;
        mWebWiew=w;
        mMapView=m;
    }

    /**
     * js 将数据传给 java 通过WebView 的addJavascriptInterface()方法 映射一个对象
     * 然后再js中通过javascript对象.方法(参数)的方式调用
     * @param s
     */
    @JavascriptInterface
    public void jsTojava(String s) {

        Point p= CoordinateConversion.degreesDecimalMinutesToPoint(s, SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR_AUXILIARY_SPHERE));
        mMapView.zoomToScale(p, 288895);
        GraphicsLayer graphicsLayer=new GraphicsLayer();
        PictureMarkerSymbol markerSymbol= null;
        try {
            markerSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(mContext, R.drawable.ic_postion));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Graphic graphic=new Graphic(p,markerSymbol);
        graphicsLayer.addGraphic(graphic);
        mMapView.addLayer(graphicsLayer);
        //Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * java 将数据传给js  通过loadUrl()调用 js方法
     */
    public void javaToJs() {
        mWebWiew.loadUrl("javascript:iniData('" + inidata() + "')");
    }
    /**
     * 初始化数据
     * @return
     */
    public String inidata() {
        try {
            //封装json对象
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "zhangsan");
            jsonObject.put("sex", "男");
            jsonObject.put("age","16");

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("name", "lisi");
            jsonObject1.put("sex", "男");
            jsonObject1.put("age","25");

            JSONObject jsonObject2  = new JSONObject();
            jsonObject2.put("name", "wangwu");
            jsonObject2.put("sex", "男");
            jsonObject2.put("age","26");

            JSONArray array = new JSONArray();
            array.put(jsonObject);
            array.put(jsonObject1);
            array.put(jsonObject2);
            android.util.Log.d("log",array.toString());
            return array.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
