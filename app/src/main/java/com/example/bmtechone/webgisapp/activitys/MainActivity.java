package com.example.bmtechone.webgisapp.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.CoordinateConversion;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.ProjectionTransformation;
import com.esri.core.geometry.SpatialReference;

import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.example.bmtechone.webgisapp.R;
import com.example.bmtechone.webgisapp.utils.MyApplicontion;
import com.example.bmtechone.webgisapp.utils.SysConstant;
import com.example.bmtechone.webgisapp.utils.WebSerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    MapView mapview;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button btnHTML5;
    WebView mwebView;
    Context mContext;
    //JS与arcgis地图交互接口
    WebSerInterface weninf;
    GraphicsLayer locLayer;
    ArcGISDynamicMapServiceLayer featurelayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArcGISRuntime.setClientId("uK0DxqYT0om1uxa9");
        mContext=this;
        initView();
        initmap();
    }

    private void initView() {
        mapview = (MapView) findViewById(R.id.mapview);
   /*     btnHTML5 = (Button) findViewById(R.id.btnHTML5);
        btnHTML5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HTML5Activity.class);
                startActivity(intent);
            }
        });*/
        mwebView=(WebView)findViewById(R.id.webview);
        initWebViewSetting();
        weninf=new WebSerInterface(mContext,mwebView,mapview);
        //设置webview的js交互
        mwebView.addJavascriptInterface(weninf, "Android");
        //设置远程调试webview网页
        mwebView.setWebContentsDebuggingEnabled(true);
        //设置webview可以像chrome一样，弹框显示
        mwebView.setWebChromeClient(new WebChromeClient() {
        });
        //加载webview初始页面
        mwebView.loadUrl(SysConstant.webUrl);
        //
       /* ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);*/
       /* mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //导航栏
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }*/
    }

    private void initWebViewSetting() {
        WebSettings webSettings = mwebView.getSettings();
        //设置webview调用JS代码
        webSettings.setJavaScriptEnabled(true);
        //使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
    }

    /*
    * 初始化地图
    * */
    private void initmap(){

        ArcGISTiledMapServiceLayer tiledMapServiceLayer=new ArcGISTiledMapServiceLayer(SysConstant.tileUrl);
        featurelayer=new ArcGISDynamicMapServiceLayer(SysConstant.preUrl);
        locLayer=new GraphicsLayer();
        //设置瓦片地图最大范围为中国
        //tiledMapServiceLayer.setMinScale(18489298);
        mapview.addLayer(tiledMapServiceLayer);
        mapview.addLayer(featurelayer);
        Layer[] layers=  mapview.getLayers();
        mapview.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (STATUS.INITIALIZED == status && mapview == o) {
                    //WGS 84 经纬度坐标转米制坐标
                    String string=SysConstant.string;
                    Point p = CoordinateConversion.degreesDecimalMinutesToPoint(string, SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR_AUXILIARY_SPHERE));
                   // mapview.zoomToScale(p, 288895);
                    mapview.zoomToScale(p, SysConstant.mapscale);
                }
            }
        });

        //点击地图获取mapview点坐标
        mapview.setOnSingleTapListener(new OnSingleTapListener() {
            @Override
            public void onSingleTap(float x, float y) {
                locLayer.removeAll();
                MyApplicontion.point = mapview.toMapPoint(x,y);
                Point p = MyApplicontion.point;
                //米制投影坐标转wgs84地理坐标系
                Point latlon= MyApplicontion.curToWgs84(p);
                mapview.centerAt(p,true);
                PictureMarkerSymbol markerSymbol = null;
                try {
                    markerSymbol = new PictureMarkerSymbol(ContextCompat.getDrawable(mContext, R.drawable.ic_location));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Graphic graphic = new Graphic(p, markerSymbol);
                locLayer.addGraphic(graphic);
                mapview.addLayer(locLayer);
                final String geostr=geodata(latlon);
                weninf.javaToJs(geostr);
                //确保网页的js代码加载完成
               /* mwebView.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        //将mapview坐标数据传输到js，做周边查询
                        weninf.javaToJs(geostr);
                    }
                });*/
            }
        });


    }

    /**
     * 初始化数据
     * @return
     */
    public String geodata(Point latlon) {
        try {
            //封装json对象
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lon", latlon.getX());
            jsonObject.put("lat",latlon.getY());
           /* jsonObject.put("x",latlon.getX());
            jsonObject.put("y", latlon.getY());*/
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.sample_actions, menu);
        /*//获取系统搜索管理服务
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        //获取actionbar上的搜索控件
       // SearchView searchView=(SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        // 关联检索配置和SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(),
                       SearchActivity.class)));*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
