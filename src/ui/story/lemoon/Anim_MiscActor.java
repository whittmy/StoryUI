package ui.story.lemoon;

 import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

// 水波纹动画
public class Anim_MiscActor extends Actor {
	int mMapWidth = 50;
	int mMapHeight = 30;
	
	//
	boolean bfirst = true;
	
	
	//flow
	TextureRegion mRTxFlow;
	Texture mTxFlow;
	Animation mFlowAnim;
	TextureRegion[] mFlowFrams;
	float stateTime_Flow;
	
	//ripple
	TextureRegion mRTxRipple;
	Texture mTxRipple;
	Animation mRippleAnim;
	TextureRegion[] mRippleFrams;
 
	
	Texture mTxHour, mTxMin, mTxSec;
	TextureRegion mRTxHour, mRTxMin, mRTxSec;
	BitmapFont font;

	
	float mPosFlowX, mPosFlowY;
	float mPosHourX, mPosHourY;
	float mPosMinX, mPosMinY;
	float mPosSecX, mPosSecY;
	float mPosTimeX, mPosTimeY;
	float mPosRippleX, mPosRippleY;
 
	float stateTime_Time = 0f;
	public Anim_MiscActor() {
		// TODO Auto-generated constructor stub
		AssetManager mgr = MyGame.getManager();
		if (mgr.isLoaded("anims/flow.png")){
			mTxFlow = mgr.get("anims/flow.png", Texture.class);
			mRTxFlow = new TextureRegion(mTxFlow);
			
			TextureRegion[][] tmp = mRTxFlow.split(mTxFlow.getWidth()/5, mTxFlow.getHeight());
			mFlowFrams = new TextureRegion[5];
			mFlowFrams[0] = tmp[0][0];
			mFlowFrams[1] = tmp[0][1];
			mFlowFrams[2] = tmp[0][2];
			mFlowFrams[3] = tmp[0][3];
			mFlowFrams[4] = tmp[0][4];
			mFlowAnim = new Animation(1/5f, mFlowFrams);
			mFlowAnim.setPlayMode(PlayMode.LOOP);
		}
		if (mgr.isLoaded("anims/hour.png")){
			mTxHour = mgr.get("anims/hour.png", Texture.class);
			mRTxHour = new TextureRegion(mTxHour);
		}		
		if (mgr.isLoaded("anims/min.png")){
			mTxMin = mgr.get("anims/min.png", Texture.class);
			mRTxMin = new TextureRegion(mTxMin);
		}		
		if (mgr.isLoaded("anims/second.png")){
			mTxSec = mgr.get("anims/second.png", Texture.class);
			mRTxSec = new TextureRegion(mTxSec);
		}			
		if (mgr.isLoaded("font/arial-15.fnt")){
			font = mgr.get("font/arial-15.fnt", BitmapFont.class);
			font.setColor(255, 0, 0, 1);
			//font.getData().setScale(0.075f, 0.06f);
			font.getData().setScale(0.05f, 0.05f);
			font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		if (mgr.isLoaded("anims/ripple.png")){
			mTxRipple = mgr.get("anims/ripple.png", Texture.class);
			mRTxRipple = new TextureRegion(mTxRipple);

			TextureRegion[][] tmp = mRTxRipple.split(mTxRipple.getWidth(), mTxRipple.getHeight()/3);
			mRippleFrams = new TextureRegion[3];
			mRippleFrams[0] = tmp[0][0];
			mRippleFrams[1] = tmp[1][0];
			mRippleFrams[2] = tmp[2][0];
			
			mRippleAnim = new Animation(1/5f, mRippleFrams);
			mRippleAnim.setPlayMode(PlayMode.LOOP);
		}		
		
		
		
		//Flow:  (34, 19), 3*2
		mPosFlowX = 34;
		mPosFlowY = mMapHeight-19-1;
 
		//hour: (31, 10), 2*1
		mPosHourX  = 31;
		mPosHourY = mMapHeight-10-1;
		
		//minute: (31, 10), 1*2
		mPosMinX  = 31; 
		mPosMinY = mMapHeight-10-1;

		//minute: (31, 10), 2*1
		mPosSecX  = 31; 
		mPosSecY = mMapHeight-10-1;
		
		
		//time-text: (37, 19)
		mPosTimeX = 37; 
		mPosTimeY = 19;
 
		//Ripple: (36, 20), 4*2
		mPosRippleX = 36;
		mPosRippleY = 30-20-1;
 
		stateTime_Flow = 0f;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		//Gdx.app.log("", "xx："+Gdx.graphics.getDeltaTime());
		stateTime_Flow += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		drawRipple(batch);
		
		drawFlow(batch);

		drawTimeRela(batch);
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}
	
 
	//绘制水纹
	private void drawRipple(Batch batch){
		batch.draw(mRippleAnim.getKeyFrame(stateTime_Flow, true), 
				mPosRippleX, mPosRippleY,   //左下角坐标  
				0, 0,   //旋转-所缩放基点
                4, 2, //这儿特别注意，为风扇的 unit尺寸，虽然取出来是按像素的(目的是要取完整)， 但显示的大小是受这儿控制的
                1, 1,  getRotation());  	
	}
	
	//绘制水流
	private void drawFlow(Batch batch){
		batch.draw(mFlowAnim.getKeyFrame(stateTime_Flow, true), 
				mPosFlowX, mPosFlowY,   //左下角坐标  
				0, 0,   //旋转-所缩放基点
                3, 2, //这儿特别注意，为风扇的 unit尺寸，虽然取出来是按像素的(目的是要取完整)， 但显示的大小是受这儿控制的
                1, 1,  getRotation());  		
	}	
	
	
	
	
	//=== 绘制时间相关
	String mTimeStr = "";
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");//yyyy年MM月dd日   HH:mm:ss 
	SimpleDateFormat formatter_H = new SimpleDateFormat("HH");
	SimpleDateFormat formatter_M = new SimpleDateFormat("mm");
	SimpleDateFormat formatter_S = new SimpleDateFormat("ss");
	float mRotatHour = 0f; // x/12f * 360f
	float mRotatMin = 0f;	// x/60f *360f
	float mRotatSec= 0;		// x/60f * 360f
	
	float test = 0f;
	//(37, 13) => (37, (32-13))==(37, 19)
	private void drawTimeRela(Batch batch) {
		stateTime_Time += Gdx.graphics.getDeltaTime();
		//每1秒执行下面一次
		if(stateTime_Time-0.9f<=0 || bfirst){
			//获取系统时间 
			Date curDate = new Date(System.currentTimeMillis());
			mTimeStr = " "+formatter.format(curDate);
 
			// 计算时钟各指针旋转角度>>>
			int h = Integer.valueOf(formatter_H.format(curDate));
			int m = Integer.valueOf(formatter_M.format(curDate));
			int s = Integer.valueOf(formatter_S.format(curDate));
			if(h > 12) h -= 12;

			mRotatHour = 360f * h/12f;
			float tmp  = m/60f;
			mRotatMin = 360f * tmp - 90f; //因为其自身的默认角度是横着的，所以减去90度
			mRotatHour = (mRotatHour+ tmp*30f) % 360f;  //时针并非始终是整点的，再分针移动过程中其也适当的增加
			
			mRotatSec = 360f * s/60f - 90f;
			//<<<
			
			//清零，为了下一秒
			stateTime_Time = 0f;
			
			bfirst = false;
		}
		
		//显示时间
		font.draw(batch, mTimeStr, 30.5f, 18); //width-12
 
		//debug
		//Gdx.app.log("", String.format("rth=%f, rtm=%f", mRotatHour, mRotatMin));
		//test  = ++test % 360;
		//mRotatHour = mRotatMin = test;
		
		//绘制指针位置		
		batch.draw(mRTxHour, mPosHourX, mPosHourY, 
				0.5f, 0.5f, 
				1, 2, //w,h
				1, 1, -mRotatHour);
		batch.draw(mRTxMin,  mPosMinX, mPosMinY, 
				0.5f, 0.5f, 
				2, 1,  //w,h
				1, 1, -mRotatMin);
		batch.draw(mRTxSec, mPosSecX, mPosSecY, 
				0.5f, 0.5f, 
				2, 1, //w,h
				1, 1, -mRotatSec);
	}
	
	

}
