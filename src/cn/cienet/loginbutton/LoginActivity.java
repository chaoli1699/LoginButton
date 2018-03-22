package cn.cienet.loginbutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import cn.cienet.loginbutton.weight.LoginButton;
import cn.cienet.loginbutton.weight.LoginButton.AnimationBtnListener;

public class LoginActivity extends Activity {
	
	private LoginButton okBtn;
	private LinearLayout loginContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		okBtn=findViewById(R.id.login_ok);
		loginContainer=findViewById(R.id.login_container);
		
		okBtn.setAnimationBtnListener(new AnimationBtnListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				loginContainer.setVisibility(View.INVISIBLE);
				okBtn.startAnim();
			}
			
			@Override
			public void onAnimationFinish() {
				// TODO Auto-generated method stub
				LoginActivity.this.finish();
			}
		});
	}
}
