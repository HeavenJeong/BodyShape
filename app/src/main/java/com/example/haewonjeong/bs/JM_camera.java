package com.example.haewonjeong.bs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

public class JM_camera extends Activity {
	FrameLayout previewFrame;
	JM_camerasurfaceview cameraView;

	RelativeLayout guideLayout;
	ImageView guideLine;
	ImageView cameraBackground;
	ImageLoader imageLoader;

	private byte ImageData[][];

	public String Filename;
	int switch_count=0;
	int switch_state=0;

	private String FileimageRoute;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jm_camera);
		previewFrame = (FrameLayout) findViewById(R.id.previewFrame);
		cameraView = (JM_camerasurfaceview) findViewById(R.id.cameraView);
		guideLayout = (RelativeLayout) findViewById(R.id.guideLayout);
		guideLine = (ImageView) findViewById(R.id.guideLine);
		cameraBackground = (ImageView) findViewById(R.id.cameraBackground);

		switch_count=0;
		ImageData = new byte[6][];
		Toast.makeText(this,"fit your face in guideline",Toast.LENGTH_SHORT).show();

		Button captureButton = (Button) findViewById(R.id.captureButton);
		captureButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cameraView.camera.takePicture(shuttercallback, picturecallbackdata, picturecallbackjpeg);
			}
		});

		Button changeButton = (Button) findViewById(R.id.changeButton);
		changeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(switch_state==0)
				{
					switch_state=1;
				}
				else
				{
					switch_state=0;
				}
				if(switch_count==0)
				{
					cameraView.switchCamera();
					cameraView.switchCamera();
					switch_count++;
				}
				else
					cameraView.switchCamera();
			}
		});
		Button backgroundbutton = (Button) findViewById(R.id.backgroundButton);
		backgroundbutton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				Intent i = new Intent(getApplicationContext(), JM_customergallery.class);
				startActivityForResult(i,500);
			}
		});
		initImageLoader();

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
	Camera.PictureCallback picturecallbackdata = new Camera.PictureCallback(){
		public void onPictureTaken(byte[] data, Camera c)
		{
		}
	};
	Camera.PictureCallback picturecallbackjpeg = new Camera.PictureCallback(){
		public void onPictureTaken(byte[] data, Camera c){
			Bitmap bf = BitmapFactory.decodeByteArray(data, 0, data.length);
			Matrix m = new Matrix();
			if(switch_state==0) {
				m.setRotate(90, (float) bf.getWidth(), (float) bf.getHeight());
			}
			else
			{
				m.setRotate(270, (float) bf.getWidth(), (float) bf.getHeight());
			}
			Bitmap rotate = Bitmap.createBitmap(bf,0,0,bf.getWidth(),bf.getHeight(),m,false);
			bf.recycle();

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			rotate.compress(Bitmap.CompressFormat.JPEG,100,stream);

			ImageData[0]=stream.toByteArray();
			FileimageRoute= Environment.getExternalStorageDirectory().getAbsolutePath()+"/BodyShape";
			ShowSaveDialog();
		}
	};
	Camera.ShutterCallback shuttercallback = new Camera.ShutterCallback() {
		public void onShutter() {
		}
	};
	private String MakeFileName_Demo() {
		DecimalFormat decimalFormat = new DecimalFormat("00");
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR) % 100;
		int month = rightNow.get(Calendar.MONTH);
		int date = rightNow.get(Calendar.DATE);
		int hour = rightNow.get(Calendar.HOUR);
		int minute = rightNow.get(Calendar.MINUTE);
		int second = rightNow.get(Calendar.SECOND);
		String result = decimalFormat.format(year)+decimalFormat.format(month) + decimalFormat.format(date)
				+ decimalFormat.format(hour)+ decimalFormat.format(minute)+ decimalFormat.format(second);
		Filename = result;
		File file = new File(FileimageRoute);
		if(file.exists()==false)
		{
			file.mkdirs();
		}
		FileimageRoute= FileimageRoute+"/"+Filename+".jpg";
		return Filename;
	}
	private void ShowSaveDialog() {
		MakeFileName_Demo();
		SaveImage();
		ImageData[0] = null;
		if (cameraView.camera != null) {
			try {
				cameraView.camera.startPreview();
			} catch (Exception e) {
			}
		}
	}
	public int SaveImage() {
		int ret = 0;
		File files = new File(FileimageRoute);
		try {
			FileOutputStream fileoutstream = new FileOutputStream(files);
			fileoutstream.write(ImageData[0]);
			fileoutstream.close();
			System.gc();

			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.parse("file://"+FileimageRoute);
			intent.setData(uri);
			sendBroadcast(intent);
			Toast.makeText(this,"finish upload",Toast.LENGTH_SHORT).show();
		}
		catch (FileNotFoundException fne)
		{
			fne.printStackTrace();
			ret = -1;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			ret = -1;
		} catch (Exception e)
		{
			ret = -1;
		}
		return ret;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode,resultCode,data);

		if(requestCode==500&&resultCode== Activity.RESULT_OK){
			String back = data.getStringExtra("background");
			imageLoader.displayImage("file://"+back,cameraBackground);
		}
	}
	/*public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_jm_main, menu);
		restoreActionBar();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}