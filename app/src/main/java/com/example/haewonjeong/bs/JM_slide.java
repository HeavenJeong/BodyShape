package com.example.haewonjeong.bs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class JM_slide extends Activity implements View.OnTouchListener,
		CompoundButton.OnCheckedChangeListener{
	CheckBox checkBox;
	ViewFlipper flipper;
	float xAtDown;
	float xAtUp;
	ImageLoader imageLoader;
	ImageView imgslide[];
	String all_path[];

	int total_cnt;
	int now_cnt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jm_slide);
		all_path = getIntent().getStringArrayExtra("all_path");
		total_cnt=0;
		now_cnt=0;
		initImageLoader();
		init();
		checkBox = (CheckBox)findViewById(R.id.chkAuto);
		checkBox.setOnCheckedChangeListener(this);
		flipper = (ViewFlipper)findViewById(R.id.viewFlipper);
		flipper.setOnTouchListener(this);
	}
	public void init()
	{
		imgslide = new ImageView[10];
		imgslide[0] = (ImageView)findViewById(R.id.imgslide1);
		imgslide[1] = (ImageView)findViewById(R.id.imgslide2);
		imgslide[2] = (ImageView)findViewById(R.id.imgslide3);
		imgslide[3] = (ImageView)findViewById(R.id.imgslide4);
		imgslide[4] = (ImageView)findViewById(R.id.imgslide5);
		imgslide[5] = (ImageView)findViewById(R.id.imgslide6);
		imgslide[6] = (ImageView)findViewById(R.id.imgslide7);
		imgslide[7] = (ImageView)findViewById(R.id.imgslide8);
		imgslide[8] = (ImageView)findViewById(R.id.imgslide9);
		imgslide[9] = (ImageView)findViewById(R.id.imgslide10);
		for(String string : all_path)
		{
			imageLoader.displayImage("file://"+string,imgslide[total_cnt]);
			total_cnt++;
		}
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
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v != flipper)
			return false;
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			xAtDown = event.getX();
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){
			xAtUp = event.getX();

			if( xAtUp < xAtDown ) {
				flipper.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_left_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_left_out));
				if(now_cnt < total_cnt-1) {
					flipper.showNext();
					now_cnt++;
				}
			}
			else if (xAtUp > xAtDown){
				flipper.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_right_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_right_out));
				if(now_cnt > 0) {
					flipper.showPrevious();
					now_cnt--;
				}
			}
		}
		return true;
	}
	@Override
	public void onCheckedChanged(CompoundButton view, boolean isChecked) {
		if(isChecked == true) {
			flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_out));
			flipper.setFlipInterval(3000);
			flipper.startFlipping();
			if(now_cnt==total_cnt-1) {
				flipper.stopFlipping();
			}
		}
		else
		{
			flipper.stopFlipping();
		}
	}
}
