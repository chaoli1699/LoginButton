package cn.cienet.loginbutton;

import android.app.Application;
import android.util.Log;

public class MyApp extends Application {

	private static final String TAG="LoginButton APP";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "onCreate...");
	}
}
