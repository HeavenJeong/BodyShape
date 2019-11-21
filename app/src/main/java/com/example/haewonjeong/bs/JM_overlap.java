package com.example.haewonjeong.bs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class JM_overlap extends Activity {

	Handler handler;
	JM_getphoto adapter;

	ImageView imgMulView[];
	ImageLoader imageLoader;
	String all_path[];
	Context now;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jm_overlap);
		now=getApplicationContext();
		all_path = getIntent().getStringArrayExtra("all_path");
		initImageLoader();
		init();
	}
	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions).memoryCache(
				new WeakMemoryCache());
		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	private void init() {
		imgMulView = new ImageView[9];
		handler = new Handler();
		adapter = new JM_getphoto(getApplicationContext(), imageLoader);
		adapter.setMultiplePick(false);
		imgMulView[0] = (ImageView) findViewById(R.id.imgMulPick1);
		imgMulView[1] = (ImageView) findViewById(R.id.imgMulPick2);
		imgMulView[2] = (ImageView) findViewById(R.id.imgMulPick3);
		imgMulView[3] = (ImageView) findViewById(R.id.imgMulPick4);
		imgMulView[4] = (ImageView) findViewById(R.id.imgMulPick5);
		imgMulView[5] = (ImageView) findViewById(R.id.imgMulPick6);
		imgMulView[6] = (ImageView) findViewById(R.id.imgMulPick7);
		imgMulView[7] = (ImageView) findViewById(R.id.imgMulPick8);
		imgMulView[8] = (ImageView) findViewById(R.id.imgMulPick9);
		int i=0;
		for(String string : all_path)
		{
			imageLoader.displayImage("file://"+string,imgMulView[i]);
			imgMulView[i].setAlpha(50);
			imgMulView[i].setVisibility(View.VISIBLE);
			i++;
		}
	}
}
