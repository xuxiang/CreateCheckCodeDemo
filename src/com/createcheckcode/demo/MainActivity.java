package com.createcheckcode.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private CheckCodeLinearLayout mCheckCodeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCheckCodeLayout = (CheckCodeLinearLayout)findViewById(R.id.checkcode_layout);
		Button button = (Button)findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mCheckCodeLayout.isCodeRight()){
					Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
}
