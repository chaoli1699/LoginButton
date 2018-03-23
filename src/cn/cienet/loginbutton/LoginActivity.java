package cn.cienet.loginbutton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.cienet.loginbutton.weight.LoginButton;
import cn.cienet.loginbutton.weight.LoginButton.AnimationBtnListener;

public class LoginActivity extends Activity {
	
	private LoginButton okBtn;
	private LinearLayout loginContainer;
	private EditText nameEt;
	private String nameStr;
	private EditText pwdEt;
	private String pwdStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		okBtn=findViewById(R.id.login_ok);
		loginContainer=findViewById(R.id.login_container);
		nameEt=findViewById(R.id.login_name);
		pwdEt=findViewById(R.id.login_pwd);
		
		nameEt.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (arg1==KeyEvent.KEYCODE_ENTER) {
					if (arg2.getAction()==KeyEvent.ACTION_UP) {
						openKeyboard(pwdEt);
					}
				}
				return false;
			}
		});
		
        pwdEt.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (arg1==KeyEvent.KEYCODE_ENTER) {
					if (arg2.getAction()==KeyEvent.ACTION_UP) {
						login();
					}
				}
				return false;
			}
		});
		
		okBtn.setAnimationBtnListener(new AnimationBtnListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				login();
			}
			
			@Override
			public void onAnimationFinish() {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("name", nameStr);
				setResult(RESULT_OK, intent);
				LoginActivity.this.finish();
			}
		});
	}
	
	private void login(){
		nameStr=nameEt.getText().toString().trim();
		pwdStr=pwdEt.getText().toString().trim();
		if (nameStr!=null&&nameStr.length()>0) {
		    closeKeyboard(pwdEt);
			LoginActivity.this.setFinishOnTouchOutside(false);
			loginContainer.setVisibility(View.INVISIBLE);
			okBtn.startAnim();
		}else {
			nameEt.setError("用户名为空");
		}
	}
	
	/**
	 * 关闭软键盘
	 */
	private void closeKeyboard(View view) {
	    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	/**
	 * 弹出软键盘
	 */
	private void openKeyboard(View view) {
	    // 获取焦点
	    view.setFocusable(true);
	    view.setFocusableInTouchMode(true);
	    view.requestFocus();
	    // 弹出软键盘
	    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.showSoftInput(view, 0);
	}
}
