package com.example.haewonjeong.bs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class JM_customergallery extends Activity {

	GridView gridGallery;
	Handler handler;
	JM_getphoto adapter;
	ImageView imgNoMedia;
	Button btnGalleryOk;
	int requestcode;
	String filepath;
	private ImageLoader imageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestcode = 5;
		filepath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/BodyShape";
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jm_customergallery);
		Intent intent = new Intent(this.getIntent());
		requestcode= intent.getIntExtra("jm",1);
		initImageLoader();
		init();
	}
	private void initImageLoader() {
		try {
			String CACHE_DIR = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/.temp_tmp";
			new File(CACHE_DIR).mkdirs();

			File cacheDir = StorageUtils.getOwnCacheDirectory(getBaseContext(),
					CACHE_DIR);

			DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
					.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.RGB_565).build();

			ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
					getBaseContext())
					.defaultDisplayImageOptions(defaultOptions)
					.discCache(new UnlimitedDiscCache(cacheDir))
					.memoryCache(new WeakMemoryCache());

			ImageLoaderConfiguration config = builder.build();
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(config);

		} catch (Exception e) {

		}
	}

	private void init() {

		handler = new Handler();
		gridGallery = (GridView) findViewById(R.id.gridGallery);
		gridGallery.setFastScrollEnabled(true);
		adapter = new JM_getphoto(getApplicationContext(), imageLoader);
		PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader,
				true, true);
		gridGallery.setOnScrollListener(listener);

		if(requestcode==100 || requestcode==200)
		{
			findViewById(R.id.llBottomContainer).setVisibility(View.VISIBLE);
			gridGallery.setOnItemClickListener(mItemMulClickListener);
			adapter.setMultiplePick(true);
		}
		else
		{
			findViewById(R.id.llBottomContainer).setVisibility(View.VISIBLE);
			gridGallery.setOnItemClickListener(mItemSingleClickListener);
			adapter.setMultiplePick(false);
		}
		gridGallery.setAdapter(adapter);

		imgNoMedia = (ImageView) findViewById(R.id.imgNoMedia);
		btnGalleryOk = (Button) findViewById(R.id.btnGalleryOk);
		btnGalleryOk.setOnClickListener(mOkClickListener);

		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				handler.post(new Runnable() {
					@Override
					public void run() {
						adapter.addAll(getGalleryPhotos());
						checkImageStatus();
					}
				});
				Looper.loop();
			};
		}.start();
	}
	private void checkImageStatus() {
		if (adapter.isEmpty()) {
			imgNoMedia.setVisibility(View.VISIBLE);
		} else {
			imgNoMedia.setVisibility(View.GONE);
		}
	}
	View.OnClickListener mOkClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ArrayList<JM_galleryinfo> selected = adapter.getSelected();
			String[] allPath = new String[selected.size()];
			for (int i = 0; i < allPath.length; i++) {
				allPath[i] = selected.get(i).sdcardPath;
			}
			if(requestcode==100)
			{
				Intent i = new Intent(getApplicationContext(), JM_slide.class);
				i.putExtra("all_path", allPath);
				startActivity(i);
			}
			else if(requestcode==200)
			{
				Intent i = new Intent(getApplicationContext(), JM_overlap.class);
				i.putExtra("all_path", allPath);
				startActivity(i);
			}
		}
	};
	AdapterView.OnItemClickListener mItemMulClickListener =
			new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			adapter.changeSelection(v, position);
		}
	};
	AdapterView.OnItemClickListener mItemSingleClickListener =
			new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			JM_galleryinfo item = adapter.getItem(position);
			Intent data = new Intent().putExtra("background", item.sdcardPath);
			setResult(RESULT_OK, data);
			finish();
		}
	};
	private ArrayList<JM_galleryinfo> getGalleryPhotos() {
		ArrayList<JM_galleryinfo> galleryList = new ArrayList<JM_galleryinfo>();

		try {
			final String[] columns = { MediaStore.Images.Media.DATA,
					MediaStore.Images.Media._ID };
			final String orderBy = MediaStore.Images.Media._ID;

			Cursor imagecursor = managedQuery(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
					null, null, orderBy);

			if (imagecursor != null && imagecursor.getCount() > 0) {
				imagecursor.moveToFirst();
				while (imagecursor.moveToNext()) {
					JM_galleryinfo item = new JM_galleryinfo();
					int dataColumnIndex = imagecursor
							.getColumnIndex(MediaStore.Images.Media.DATA);
					item.sdcardPath = imagecursor.getString(dataColumnIndex);
					if(item.sdcardPath!=null &&
							item.sdcardPath.startsWith(filepath))
					{
						galleryList.add(item);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// show newest photo at beginning of the list
		Collections.reverse(galleryList);
		return galleryList;
	}
}
