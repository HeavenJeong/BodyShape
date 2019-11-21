package com.example.haewonjeong.bs;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class JM_camerasurfaceview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	Camera camera = null;

	int surfaceWidth;
	int surfaceHeight;
	int camerafacing;

	public JM_camerasurfaceview(Context context) {
		super(context);
		init();
	}
	public JM_camerasurfaceview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init() {
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		camerafacing=Camera.CameraInfo.CAMERA_FACING_FRONT;

	}
	/**
	 * surfaceCreated defined in Callback
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		try {
			camera.setDisplayOrientation(90);
			camera.setPreviewDisplay(mHolder);
		} catch (Exception e) {
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		surfaceWidth = width;
		surfaceHeight = height;
		try {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
				camera.setDisplayOrientation(90);
			} else {
				Camera.Parameters parameters = camera.getParameters();
				parameters.setRotation(90);
				camera.setParameters(parameters);
			}
			camera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			camera.release();
		}
		camera.startPreview();
	}
	/**
	 * surfaceDestroyed defined in Callback
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
		camera = null;
	}
	public boolean capture(Camera.PictureCallback handler) {
		if (camera != null) {
			camera.takePicture(null, null, handler);
			return true;
		} else {
			return false;
		}
	}
	public void switchCamera()
	{
		if(camera!=null)
		{
			camera.stopPreview();
			camera.release();
			camera=null;
		}
		if(camerafacing==Camera.CameraInfo.CAMERA_FACING_FRONT)
		{
			camerafacing=Camera.CameraInfo.CAMERA_FACING_BACK;
		}
		else
		{
			camerafacing=Camera.CameraInfo.CAMERA_FACING_FRONT;
		}
		camera=Camera.open(camerafacing);
		camera.setDisplayOrientation(90);
		try{
			camera.setPreviewDisplay(mHolder);
		}
		catch(IOException e){
			camera.release();
			camera=null;
		}
		camera.startPreview();
	}
}