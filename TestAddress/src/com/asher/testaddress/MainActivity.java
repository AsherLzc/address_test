package com.asher.testaddress;

import java.util.Set;


import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.asher.highlight.HighLight;
import com.asher.utils.LocationUtils;
import com.asher.utils.WindowUtils;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    Context mContext=this;
    private Handler mHandler = new Handler();
    private TextView textview_citynae;
    private Button button ;
    private Button button_map;
    //jpush自定义 receiver所用数据
    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.asher.testaddress.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	//高亮引导层
	private HighLight mHightLight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initJpush();
        initView();
        
       /* Dialog dialog = new Dialog(MainActivity.this, R.style.dialog_translucent);
        dialog.setContentView(R.layout.transparent_layout);
        dialog.show();*/
    }
    
    private void showTipMask(){
        mHightLight = new HighLight(MainActivity.this)//
                .anchor(findViewById(R.id.main_container))//
                .addHighLight(R.id.textView_cityname, R.layout.info_up,
                        new HighLight.OnPosCallback(){
                            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo){
                                marginInfo.leftMargin = rectF.right - rectF.width() / 2;
                                marginInfo.topMargin = rectF.bottom;
                            }
                        })//
                /*.addHighLight(R.id.button_map, R.layout.info_down, new HighLight.OnPosCallback(){
                    *//**
                     * @param rightMargin 高亮view在anchor中的右边距
                     * @param bottomMargin 高亮view在anchor中的下边距
                     * @param rectF 高亮view的l,t,r,b,w,h都有
                     * @param marginInfo 设置你的布局的位置，一般设置l,t或者r,b
                     *//*
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo){
                        marginInfo.rightMargin = rightMargin + rectF.width() / 2;
                        marginInfo.bottomMargin = bottomMargin + rectF.height();
                    }

                })*/;

        mHightLight.show();
    }

	private void initJpush() {
		// TODO Auto-generated method stub
		JPushInterface.setDebugMode(true); 	// 设置开发模式   
        JPushInterface.init(this);     		// 初始化 JPush
        JPushInterface.setAlias(mContext, "Asher", new TagAliasCallback() {
			
			@Override
			public void gotResult(int arg0, String arg1, Set<String> arg2) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		textview_citynae=(TextView) findViewById(R.id.textView_cityname);
		LocationUtils.getCNBylocation(mContext);
		textview_citynae.setText(LocationUtils.cityName);
		textview_citynae.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				showTipMask();
			}
		});
		textview_citynae.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mHightLight.show();
			}
		});
		
		
		//桌面窗口按钮
		button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        WindowUtils.showPopupWindow(MainActivity.this);

                    }
                }, 1000 * 3);

            }
        });
        //地图按钮
        button_map=(Button) findViewById(R.id.button_map);
        
        button_map.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(mContext,BaiduMapActivity.class);
				mContext.startActivity(intent);
			}
		});
        
	}
	
	
	
}