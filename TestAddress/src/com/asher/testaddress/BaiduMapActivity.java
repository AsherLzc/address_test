package com.asher.testaddress;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

public class BaiduMapActivity extends Activity{

	private BaiduMap baiduMap;
	private MapView  baiduMapView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//初始化百度地图application、
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidumap);
		
		initView();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		baiduMapView=(MapView) findViewById(R.id.baidumap);
		//消除地图图标以及缩放图标
		int count = baiduMapView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = baiduMapView.getChildAt(i);
            if (child instanceof ZoomControls || child instanceof ImageView) {
                child.setVisibility(View.INVISIBLE);
            }
        }
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		baiduMapView.onDestroy();
	}

	
}
