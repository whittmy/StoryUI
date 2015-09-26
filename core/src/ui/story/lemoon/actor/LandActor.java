package ui.story.lemoon.actor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import ui.story.lemoon.Configer;
import ui.story.lemoon.MyGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


public class LandActor extends Actor{
	//(13,25)=>(13, 32-25-1=6)
	AtlasRegion mTextureRegion;
	float mPosx=13f, mPosy=6f;
	float mW,mH;
 
	
	//clock
	float mClockW, mClockH;
	AtlasRegion mClock;
	AtlasRegion mRTxHour, mRTxMin, mRTxSec;
	float mPosHourX, mPosHourY;
	float mPosMinX, mPosMinY;
	float mPosSecX, mPosSecY;
	float mPosTimeX, mPosTimeY;
 
	
	//ripple
	Animation mRippleAnim;
	AtlasRegion[] mRippleFrams;
	float mPosRippleX, mPosRippleY;
	
	//flow
	Animation mFlowAnim;
	AtlasRegion[] mFlowFrams;
	float mPosFlowX, mPosFlowY;
	float stateTime_Flow;
	
	//
	
	
	//cloud-rotate
	AtlasRegion mRotCloud;
	float mPosCloudX,mPosCloudY;
	
	
	public LandActor(Configer cfg){
		super();
		//
		
		mTextureRegion = MyGame.mCfg.getMainAtlas().findRegion("land");
		mW = mTextureRegion.getRegionWidth()/32f;
		mH = mTextureRegion.getRegionHeight()/32f;
 
		//clock
		mClock = MyGame.mCfg.getMainAtlas().findRegion("clock");
		mClockW = mClock.getRegionWidth()/32f; 
		mClockH = mClock.getRegionHeight()/32f; 
		
		mRTxHour = MyGame.mCfg.getMainAtlas().findRegion("hour");
		mRTxMin =MyGame.mCfg.getMainAtlas().findRegion("min");
		mRTxSec = MyGame.mCfg.getMainAtlas().findRegion("second");

		mPosHourX  = 34.09f; mPosHourY = 20.06f;
		mPosMinX  = 34.09f;  mPosMinY = 20.06f;
		mPosSecX  = 34.13f;  mPosSecY = 20.06f;

		//time-text: (37, 19)
		mPosTimeX = 33.9f; 
		mPosTimeY = 18.5f;
		
		//flow
		mPosFlowX = 37f;  mPosFlowY = MyGame.mCfg.MAP_HEIGHT-20.05f-1;
		mFlowFrams = new AtlasRegion[5];
		mFlowFrams[0] = MyGame.mCfg.getMainAtlas().findRegion("flow1");
		mFlowFrams[1] = MyGame.mCfg.getMainAtlas().findRegion("flow2");
		mFlowFrams[2] = MyGame.mCfg.getMainAtlas().findRegion("flow3");
		mFlowFrams[3] = MyGame.mCfg.getMainAtlas().findRegion("flow4");
		mFlowFrams[4] = MyGame.mCfg.getMainAtlas().findRegion("flow5");
		mFlowAnim = new Animation(1/5f, mFlowFrams);
		mFlowAnim.setPlayMode(PlayMode.LOOP);
		
		
		//ripple
		mPosRippleX = 39; mPosRippleY = MyGame.mCfg.MAP_HEIGHT-21.1f-1;
		mRippleFrams = new AtlasRegion[3];
		mRippleFrams[0] = MyGame.mCfg.getMainAtlas().findRegion("ripple1");
		mRippleFrams[1] = MyGame.mCfg.getMainAtlas().findRegion("ripple2");
		mRippleFrams[2] = MyGame.mCfg.getMainAtlas().findRegion("ripple3");
		mRippleAnim = new Animation(1/5f, mRippleFrams);
		mRippleAnim.setPlayMode(PlayMode.LOOP);
		
		
		//cloud
		mPosCloudX = 39f; mPosCloudY=20.1f;
		mRotCloud = MyGame.mCfg.getMainAtlas().findRegion("night_cloud1");
		
		
		//mTimer = new Timer(true);
		//mTimer.schedule(mTask, 0, 1000);
	}
	
//	TimerTask mTask = new TimerTask(){
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			genTimeInfo();
//		}};
//	
	//=== 绘制时间相关
	StringBuffer mTimeStr = new StringBuffer();
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");//yyyy年MM月dd日   HH:mm:ss 
	SimpleDateFormat formatter_H = new SimpleDateFormat("HH");
	SimpleDateFormat formatter_M = new SimpleDateFormat("mm");
	SimpleDateFormat formatter_S = new SimpleDateFormat("ss");
	float mRotatHour = 0f; // x/12f * 360f
	float mRotatMin = 0f;	// x/60f *360f
	float mRotatSec= 0;		// x/60f * 360f
 	Date mCurDate = new Date();
	Timer mTimer = null;
	
	
	long mLastTm = 0;
	void genTimeInfo(){
		//time rel
		mCurDate.setTime(mLastTm);
		//mTimeStr = formatter.format(mCurDate);
		
		//这儿的获取时间应该在下面的判断之前，否则有问题。
		//mCurDate.getDate(); //1-31
		MyGame.mCfg.mHour = mCurDate.getHours();
		MyGame.mCfg.mMin = mCurDate.getMinutes();
		MyGame.mCfg.mSec = mCurDate.getSeconds();	
		MyGame.mCfg.mBhadsettime = true;
 
		//获取系统时间 
		//mCurDate = new Date();
		//Configer.mylog("", String.format("land:%d:%d:%d", MyGame.mCfg.mHour, MyGame.mCfg.mMin,MyGame.mCfg.mSec));
		if(MyGame.mCfg.mHour > 5 && MyGame.mCfg.mHour<18){
			MyGame.mCfg.mBDayMode = true;
		}
		else{
			MyGame.mCfg.mBDayMode = false;
		}
		
		//mTimeStr = String.format("%02d%s%02d", MyGame.mCfg.mHour,(MyGame.mCfg.mSec%2==0)?" ":":", MyGame.mCfg.mMin);
		mTimeStr.delete(0, mTimeStr.length());
		if(MyGame.mCfg.mHour<10){
			mTimeStr.append("0").append( MyGame.mCfg.mHour);
		}
		else{
			mTimeStr.append(MyGame.mCfg.mHour);
		}
		
		mTimeStr = ((MyGame.mCfg.mSec%2)==0) ?  mTimeStr.append(" "): mTimeStr.append(":");		
		if(MyGame.mCfg.mMin<10){
			mTimeStr.append("0").append(MyGame.mCfg.mMin);
		}
		else{
			mTimeStr.append(MyGame.mCfg.mMin);
		}		
		
		
		int h = MyGame.mCfg.mHour;
		if(h > 12) h -= 12;

		mRotatHour = 360f * h/12f;
		float tmp  = MyGame.mCfg.mMin/60f;
		mRotatMin = 360f * tmp - 90f; //因为其自身的默认角度是横着的，所以减去90度
		mRotatHour = (mRotatHour+ tmp*30f) % 360f;  //时针并非始终是整点的，再分针移动过程中其也适当的增加
		mRotatSec = 360f * MyGame.mCfg.mSec/60f - 90f;
	
	}

	//(37, 13) => (37, (32-13))==(37, 19)
	private void drawTimeRela(Batch batch) {
		//显示时间
		MyGame.mCfg.getFont().draw(batch, mTimeStr.toString(),mPosTimeX, mPosTimeY); //width-12

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
	

	float angle = -30;
	float step = 0.4f;
	boolean bshowcloud = false;
	private void computeCloudAngle(){
		if(MyGame.mCfg.mBDayMode){
			bshowcloud = false;
		}
		else{
			bshowcloud = true;
			
			angle += step;
			if(angle > 30){
				step = -step;
			}
			else if(angle < -30){
				step = Math.abs(step);
			}
		}
 
	}
	
	
	//绘制cloud
	private void drawCloud(Batch batch){
		if(bshowcloud && MyGame.mCfg.mCurCameraZoom==0.5f){
			batch.draw(mRotCloud, 
					mPosCloudX, mPosCloudY,   //左下角坐标  
					1.5f, 3f,   //旋转-所缩放基点
	                3, 3, //这儿特别注意，为风扇的 unit尺寸，虽然取出来是按像素的(目的是要取完整)， 但显示的大小是受这儿控制的
	                1, 1,  angle);
		}
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		
		computeCloudAngle();
		
		if(Math.abs(System.currentTimeMillis()-mLastTm)>2000){
			Configer.mylog("", "reset timer" );

			try{
				if(mTimer != null){
					Configer.mylog("", "cancel timer" );
					//mTask.cancel();
					mTimer.cancel();
				}
			}
			catch(IllegalStateException e){
				Configer.mylog("", "IllegalStateException" );
			}
			catch(Exception e){
				Configer.mylog("", "other Exception" );
			}
			 
			mLastTm = System.currentTimeMillis();
			mTimer = new Timer(false);
			mTimer.schedule(new TimerTask(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Gdx.graphics.requestRendering();
					mLastTm = System.currentTimeMillis();
					genTimeInfo();
				}}, 0, 1000);
		 
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		//super.draw(batch, parentAlpha);
		stateTime_Flow += Math.min(0.07f, Gdx.graphics.getDeltaTime());
 
		batch.draw(mClock, 33.1f, 17.5f, mClockW,mClockH);
		batch.draw(mTextureRegion, mPosx, mPosy, mW, mH);
		
		drawTimeRela(batch);
		
		//注意ripple和flow有顺序之分，ripple的图片覆盖了flow的一部分，所以先画ripple
		drawRipple(batch);
		drawFlow(batch);
		
		drawCloud(batch);
	}
	
	
	
}
