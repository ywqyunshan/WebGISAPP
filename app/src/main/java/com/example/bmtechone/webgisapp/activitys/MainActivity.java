package com.example.bmtechone.webgisapp.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.CoordinateConversion;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

import com.example.bmtechone.webgisapp.R;
import com.example.bmtechone.webgisapp.utils.SysConstant;
import com.example.bmtechone.webgisapp.utils.WebSerInterface;

public class MainActivity extends AppCompatActivity {

    MapView mapview;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button btnHTML5;
    WebView mwebView;
    Context mContext;

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
                Intent intent=new Intent(MainActivity.this,HTLM5Activity.class);
                startActivity(intent);
            }
        });*/
        mwebView=(WebView)findViewById(R.id.webview);
        initWebViewSetting();
        WebSerInterface weninf=new WebSerInterface(mContext,mwebView,mapview);
        mwebView.addJavascriptInterface(weninf, "Android");
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
        String tileUrl="http://www.gistech.com.cn/ArcGIS/rest/services/ChinaOnlineCommunity_Mobile/MapServer";
        String layerUrl="http://10.19.254.154:6080/arcgis/rest/services/SPJK/MapServer";
        ArcGISTiledMapServiceLayer tiledMapServiceLayer=new ArcGISTiledMapServiceLayer(tileUrl);
        ArcGISDynamicMapServiceLayer dynamicMapServiceLayer=new ArcGISDynamicMapServiceLayer(layerUrl);
        //设置瓦片地图最大范围为中国
        //tiledMapServiceLayer.setMinScale(18489298);
        mapview.addLayer(tiledMapServiceLayer);
        //mapview.addLayer(dynamicMapServiceLayer);
        Layer[] layers=  mapview.getLayers();
        mapview.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                if (STATUS.INITIALIZED == status && mapview == o) {
                    String string = "30.6942871496,111.2807696288";
                    //WGS 84 经纬度坐标转米制坐标
                    Point p = CoordinateConversion.degreesDecimalMinutesToPoint(string, SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR_AUXILIARY_SPHERE));
                    mapview.zoomToScale(p, 288895);
                    //mapview.centerAndZoom(lat, lon, 14);
                }
            }
        });


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
