package com.createcheckcode.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/** 
 * @ClassName CheckCodeLinearLayout 
 * @Description 动态获取验证码控件
 * @author xuxiang
 * @date 2013-10-23
 */ 
public class CheckCodeLinearLayout extends LinearLayout implements OnClickListener{
	
	private View mCheckCode;
	private EditText mCheckCodeEt;
	private ImageView mCheckCodeImg;
	private Context mContext;
	
	public CheckCodeLinearLayout(Context context) {
		super(context);
		init(context);
	}

	public CheckCodeLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context){
		mContext = context;
		View parentView = LayoutInflater.from(context).inflate(R.layout.com_checkcode_layout, null);
		mCheckCode = parentView.findViewById(R.id.chkcode_chinamobile);
		mCheckCodeEt = (EditText)mCheckCode.findViewById(R.id.chkcode_chinamobile_et);
		mCheckCodeImg = (ImageView)mCheckCode.findViewById(R.id.chkcode_chinamobile_image);
		mCheckCodeImg.setOnClickListener(this);
		setCodeImage();
		addView(parentView);
	}
	
	/** 
	 * @Title setVisibility 
	 * @Description 设置动态获取验证码控件是否可见
	 * @param @param context
	 * @return void
	 * @throws 
	 */
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		if (visibility == View.VISIBLE){
			setCodeImage();
		}
	}
	
	/** 
	 * @Title isCodeRight 
	 * @Description 校验输入的验证码和显示的是否一致
	 * @param @return
	 * @return boolean true:一致；false：不一致
	 * @throws 
	 */ 
	public boolean isCodeRight(){
		if (getVisibility() == View.GONE){//如果动态获取验证码控件不显示，则不校验是否一致
			return true;
		}
		String code = mCheckCodeEt.getText().toString();
		if (CheckCodeUtil.code.equalsIgnoreCase(code)){
			return true;
		}else{
			Bitmap img = CheckCodeUtil.makeImage(mWidth, mHeight, mDensity);
			mCheckCodeImg.setImageBitmap(img);
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		if (v == mCheckCodeImg){
			try{
				Bitmap img = CheckCodeUtil.makeImage(mWidth, mHeight, mDensity);
				mCheckCodeImg.setImageBitmap(img);
			} catch (Exception e){
				
			}
		}
	}
	
	private int mWidth=0;
	private int mHeight=0;
	private float mDensity=0f;
	private boolean mHasMeasured = false;
	private void setCodeImage(){
		try{
			mHasMeasured = false;
			DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
			mDensity = dm.density;
			ViewTreeObserver vto = mCheckCodeImg.getViewTreeObserver();
			vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
				public boolean onPreDraw() {
					if (mHasMeasured == false) {
						mWidth = mCheckCodeImg.getWidth();
						mHeight = mCheckCodeImg.getHeight();
						Bitmap img = CheckCodeUtil.makeImage(mWidth, mHeight, mDensity);
						mCheckCodeImg.setImageBitmap(img);
						mHasMeasured = true;
					}
					return true;
				}
			});
		} catch (Exception e){
			mHasMeasured = false;
		}
	}

}
