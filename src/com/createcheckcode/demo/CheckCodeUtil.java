package com.createcheckcode.demo;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.util.Log;

public class CheckCodeUtil {
	// 图片的宽度。
	private static int width = 0;
	// 图片的高度。
	private static int height = 0;
	// 验证码字符个数
	private static final int codeCount =4;
	// 验证码干扰线数
	private static final int lineCount = 150;
	// 验证码
	public static String code = "";
	
	private static Bitmap buffImg=null;
	
	private static Paint p=new Paint();

	private static final char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
		'X', 'Y', 'Z',  '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	
	public static Bitmap makeImage(int _width,int _height,float density){
		width=_width;
		height=_height;
		
		if(width<40||height<20){
			Log.e("makeImage Error", "size too small");
			return null;
		}
		
		int x = 0,fontHeight=0,codeY=0;
		int red = 0, green = 0, blue = 0;
		
		if(buffImg==null){
			// 图像buffer
			buffImg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
		}
		Canvas g=new Canvas(buffImg);
		// 生成随机数
		Random random = new Random();
		// 将图像填充为白色
		p.setColor(Color.WHITE);
		p.setStyle(Paint.Style.FILL);
		g.drawRect(0, 0, width, height, p);
		p.setTypeface(Typeface.DEFAULT_BOLD);
		if(density<=0.75f){
			p.setTextSize(18);
		}else if(density==1f){
			p.setTextSize(24);
		}else if(density==1.5f){
			p.setTextSize(36);
		}else if(density>=2f){
			p.setTextSize(48);
		}
		FontMetrics fm=p.getFontMetrics();
		fontHeight=(int)Math.ceil(fm.descent - fm.ascent);//字体的高度
		x = width / (codeCount +2);//每个字符的宽度
		codeY = height-(height -fontHeight)/2;
		
		
		for (int i = 0; i < lineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs+random.nextInt(width/8);
			int ye = ys+random.nextInt(height/8);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			p.setColor( Color.rgb(red, green, blue));
			g.drawLine(xs, ys, xe, ye,p);
		}
		
		// randomCode记录随机产生的验证码
		StringBuffer randomCode = new StringBuffer();
		// 随机产生codeCount个字符的验证码。
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			p.setColor(Color.rgb(red, green, blue));
			g.drawText(strRand,(i + 1) * x, codeY, p);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		// 将四位数字的验证码保存到Session中。
		code=randomCode.toString();	
		
		return buffImg;
	}
	
}
