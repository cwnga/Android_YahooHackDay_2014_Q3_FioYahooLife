package com.blundell.tut.cameraoverlay.util;

import android.annotation.SuppressLint;
import android.hardware.Camera;

/**
 * Used to make camera use in the tutorial a bit more obvious
 * in a production environment you wouldn't make these calls static
 * as you have no way to mock them for testing
 * @author paul.blundell
 *
 */
@SuppressLint("NewApi") public class CameraHelper {

	public static boolean cameraAvailable(Camera camera) {
		return camera != null;
	}

	public static Camera getCameraInstance2() {
		Camera c = null;
		try {
			c = Camera.open();
			Camera.open( Camera.CameraInfo.CAMERA_FACING_FRONT);
		} catch (Exception e) {
			// Camera is not available or doesn't exist
			Log.d("getCamera failed", e);
		}
		return c;
	}

	public static Camera getCameraInstance() {
	    int cameraCount = 0;
	    Camera cam = null;
	    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
	    cameraCount = Camera.getNumberOfCameras();
	    for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
	        Camera.getCameraInfo(camIdx, cameraInfo);
	        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	            try {
	                cam = Camera.open(camIdx);
	            } catch (RuntimeException e) {
	                Log.d("FAIL CAMERA",e);
	            }
	        }
	    }

	    return cam;
	}
	
	
	
	
	
	
	
	
	
	/////
}
