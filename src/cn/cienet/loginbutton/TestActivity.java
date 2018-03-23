package cn.cienet.loginbutton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TestActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
	};
	
	public void nextPage(View view){
		startActivity(new Intent(TestActivity.this, TestActivity.class));
	}
	
	public void backToMain(View view){
		startActivity(new Intent(TestActivity.this, MainActivity.class));
	}
}
