package com.blundell.tut.cameraoverlay.ui;

import static com.blundell.tut.cameraoverlay.util.CameraHelper.cameraAvailable;
import static com.blundell.tut.cameraoverlay.util.CameraHelper.getCameraInstance;
import static com.blundell.tut.cameraoverlay.util.MediaHelper.getOutputMediaFile;
import static com.blundell.tut.cameraoverlay.util.MediaHelper.saveToFile;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blundell.tut.cameraoverlay.FromXML;
import com.blundell.tut.cameraoverlay.R;
import com.blundell.tut.cameraoverlay.ui.widget.CameraPreview;
import com.yahoo.array_adapter.GoogleNewsDOArrayAdapter;
import com.yahoo.array_adapter.NewsHashMapArrayAdapter;
import com.yahoo.array_adapter.WeatherHashMapArrayAdapter;

import com.yahoo.client.CallBackListener;
import com.yahoo.client.YQLBaseClient;
import com.yahoo.client.YQLBaseClientUtil;
import com.yahoo.model.GoogleNewsDO;

import android.widget.AdapterView;

//import com.blundell.tut.cameraoverlay.util.Log;

/**
 * Takes a photo saves it to the SD card and returns the path of this photo to
 * the calling Activity
 * 
 * @author paul.blundell
 * 
 */
public class CameraActivity extends Activity implements PictureCallback {
	private class LoadImage extends AsyncTask<String, String, Bitmap> {

	    public ImageView itemImage;
	        protected void onPreExecute() {
	            super.onPreExecute();
	           }
	       protected Bitmap doInBackground(String... args) {
	         try {
	               bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
	        } catch (Exception e) {
	              e.printStackTrace();
	        }
	      return bitmap;
	       }
	       protected void onPostExecute(Bitmap image) {
	         if(image != null){
	     		itemImage = (ImageView) findViewById(R.id.itemImage);
	        	itemImage.setImageBitmap(image);
	         //  pDialog.dismiss();
	         }else{
	          // pDialog.dismiss();
	          // Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
	         }
	       }
	       public void setView(ImageView itemImage)
	       {
	    	   
	    	   this.itemImage = itemImage;
	       }
	   }
	public ImageView itemImage;
	public Bitmap bitmap;
	public void openBid()
	{
		
         	Uri uri = Uri.parse("http://tw.yahoo.com");
         	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
         	startActivity(intent);
             //v.getId() will give you the image id

         
		
	}
	
	protected static final String EXTRA_IMAGE_PATH = "com.blundell.tut.cameraoverlay.ui.CameraActivity.EXTRA_IMAGE_PATH";

	private Camera camera;
	private CameraPreview cameraPreview;
	private TextToSpeech tts;
	private ArrayList<HashMap> globalArrayListNews;
	private ArrayList<String> imageUrl;
	private ArrayList<GoogleNewsDO> globalGoogleNewsDOArrayList;
	private ListView globaNewsListview;
	private YQLBaseClient YQLBaseClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		imageUrl=new ArrayList<String> ();
		imageUrl.add("https://s.yimg.com/qs/mall/rebe090104/560X210_2.jpg");
		imageUrl.add("https://s.yimg.com/qs/auc/nafasp140901/f2.jpg");
		imageUrl.add("https://s.yimg.com/f/i/tw/mall/rebe041506/560x210__2.jpg");
		imageUrl.add("https://s.yimg.com/qs/auc/nafasp140901/f3.jpg");
		itemImage = new ImageView(getApplicationContext());
		itemImage = (ImageView) findViewById(R.id.itemImage);
		
		/*
		 * itemImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Uri uri = Uri.parse("http://tw.yahoo.com");
            	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            	startActivity(intent);
                //v.getId() will give you the image id

            }
        });
		*/
		
		super.onCreate(savedInstanceState);
		// / speakButton.setOnClickListener(this);
		

	    final Handler h = new Handler();
	    h.postDelayed(new Runnable()
	    {
	        private long time = 0;

	        @Override
	        public void run()
	        {
	            // do stuff then
	            // can call h again after work!
	            time += 1000;
	            Log.d("TimerExample", "Going for... " + time);
	            LoadImage LoadImage = new LoadImage();
	    		LoadImage.setView(itemImage);
	    		
	    		
	    	
	    		Random ran = new Random();
	        
	  int index=   ran.nextInt(imageUrl.size());
	    		LoadImage.execute(imageUrl.get(index));
	            h.postDelayed(this, 10000);
	        }
	    }, 1000); // 1 second delay (takes millis)

		setContentView(R.layout.activity_camera);
		setResult(RESULT_CANCELED);

		tts = new TextToSpeech(CameraActivity.this,
				new TextToSpeech.OnInitListener() {
					@Override
					public void onInit(int code) {
						if (code == TextToSpeech.SUCCESS) {

							// tts.setLanguage(Locale.getDefault());

							tts.setPitch(1.0f); // 音調
							tts.setSpeechRate(1); // 速度
							tts.setLanguage(Locale.getDefault()); // 語言
							Log.d("tts", "ok");
							Log.d("tts", "ok");

						} else {
							tts = null;
							Log.d("tts", "null");
							// Toast.makeText(this,
							// "Failed to initialize TTS engine.",
							// Toast.LENGTH_SHORT).show();
						}
					}
				});

		// setToLogView("start listener: ");

		// /cwnga
		YQLBaseClient YQLBaseClient = new YQLBaseClient();

		final Context context = this.getApplicationContext();
		YQLBaseClient.getGoogleNews(new CallBackListener() {
			@Override
			public void callback(JSONObject retResult) {
				try {
					ArrayList<GoogleNewsDO> arrayListNews = YQLBaseClientUtil
							.toGoogleNewsHashMap(retResult);

					Context context = getApplicationContext();
					// NewsArrayAdapterItem adapter = new NewsArrayAdapterItem(
					// context, R.layout., arrayListNews);
					GoogleNewsDOArrayAdapter adapter = new GoogleNewsDOArrayAdapter(
							CameraActivity.this, R.layout.item_view_news,
							arrayListNews);
					globalGoogleNewsDOArrayList = arrayListNews;
					globaNewsListview = (ListView) findViewById(R.id.newListView);
					globaNewsListview.setAdapter(adapter);
					globaNewsListview
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									// TODO Auto-generated method stub
									String title = (String) globalGoogleNewsDOArrayList
											.get(position).title;
									Log.d("google title", title);

									String content = (String) globalGoogleNewsDOArrayList
											.get(position).content;
									Log.d("google content", content);
									// tts.speak(text, queueMode, params)
									tts.speak(title + content,
											TextToSpeech.QUEUE_FLUSH, null);
									Log.d("tts speak", title + content);

								}
							});

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		/*
		 * YQLBaseClient.getNews(new CallBackListener() {
		 * 
		 * @Override public void callback(JSONObject retResult) { try {
		 * ArrayList<HashMap> arrayListNews = YQLBaseClientUtil
		 * .toNewsHashMap(retResult);
		 * 
		 * Context context = getApplicationContext(); // NewsArrayAdapterItem
		 * adapter = new NewsArrayAdapterItem( // context, R.layout.,
		 * arrayListNews); NewsHashMapArrayAdapter adapter = new
		 * NewsHashMapArrayAdapter( CameraActivity.this,
		 * R.layout.item_view_news, arrayListNews); globalArrayListNews =
		 * arrayListNews; globaNewsListview = (ListView)
		 * findViewById(R.id.newListView);
		 * globaNewsListview.setAdapter(adapter); globaNewsListview
		 * .setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { // TODO Auto-generated method stub String
		 * content = (String) globalArrayListNews .get(position).get("content");
		 * // cwnga tts.speak(content, // TextToSpeech.QUEUE_FLUSH, null);
		 * Log.d("onclick 133", content); String title = content; String href =
		 * (String) globalArrayListNews .get(position).get("href"); String[]
		 * aArray = href.split("-"); String tailHtmlUrl =
		 * aArray[aArray.length-1]; Log.d("tailHtmlUrl 133", tailHtmlUrl); title
		 * = title.replaceAll("　", "-"); title = title.replaceAll("／", "-");
		 * tailHtmlUrl = title+"-"+tailHtmlUrl; Log.d("tailHtmlUrl 133",
		 * tailHtmlUrl);
		 * 
		 * } });
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * } });
		 */
		YQLBaseClient.getWeather("2306179", new CallBackListener() {

			@Override
			public void callback(JSONObject retResult) {
				// TODO Auto-generated method stub
				Log.d("getWeather result::", retResult.toString());
				try {
					JSONObject results = retResult.getJSONObject("results");
					// / results/channel/item/description
					ArrayList<HashMap> arrayListWeather = YQLBaseClientUtil
							.toWeatherForecastHashMap(retResult);
					ArrayList<HashMap> oneArrayListWeather = new ArrayList<HashMap>();
					oneArrayListWeather.add(arrayListWeather.get(0));
					WeatherHashMapArrayAdapter adapter = new WeatherHashMapArrayAdapter(
							CameraActivity.this, R.layout.item_view_weather,
							oneArrayListWeather);

					ListView listview = (ListView) findViewById(R.id.weatherListView);
					listview.setAdapter(adapter);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// cwnga
		// Camera may be in use by another activity or the system or not
		// available at all
		camera = getCameraInstance();
		if (cameraAvailable(camera)) {
			initCameraPreview();
		} else {
			finish();
		}
	}

	// Show the camera view on the activity
	private void initCameraPreview() {
		cameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
		cameraPreview.init(camera);
	}

	@FromXML
	public void onCaptureClick(View button) {
		// Take a picture with a callback when the photo has been created
		// Here you can add callbacks if you want to give feedback when the
		// picture is being taken
		camera.takePicture(null, null, this);
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		Log.d("PPP", "Picture taken");
		String path = savePictureToFileSystem(data);
		setResult(path);
		finish();
	}

	private static String savePictureToFileSystem(byte[] data) {
		File file = getOutputMediaFile();
		saveToFile(data, file);
		return file.getAbsolutePath();
	}

	private void setResult(String path) {
		Intent intent = new Intent();
		intent.putExtra(EXTRA_IMAGE_PATH, path);
		setResult(RESULT_OK, intent);
	}

	// ALWAYS remember to release the camera when you are finished
	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera();
	}

	private void releaseCamera() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	protected void onDestroy() {

		// Close the Text to Speech Library
		if (tts != null) {

			tts.stop();
			tts.shutdown();
			Log.d("ttstts", "TTS Destroyed");
		}
		super.onDestroy();
	}

}
