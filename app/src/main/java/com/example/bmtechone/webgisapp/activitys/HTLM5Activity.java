package com.example.bmtechone.webgisapp.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.bmtechone.webgisapp.R;
import com.example.bmtechone.webgisapp.utils.SysConstant;
import com.example.bmtechone.webgisapp.utils.WebSerInterface;

public class HTLM5Activity extends AppCompatActivity {


     WebView mwebView;
     Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        mContext=this;
        initView();
    }

    private void initView() {
      /*  mwebView=(WebView)findViewById(R.id.webview);
        initWebViewSetting();
        WebSerInterface weninf=new WebSerInterface(mContext,mwebView);
        mwebView.addJavascriptInterface(weninf, "Android");
        mwebView.loadUrl(SysConstant.webUrl);*/
    }

    private void initWebViewSetting() {
        WebSettings webSettings = mwebView.getSettings();
        //设置webview调用JS代码
        webSettings.setJavaScriptEnabled(true);
        //使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}
