package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class SunActor extends Actor{
	Configer mCfg;
	
	Animation mSunAnim;
	AtlasRegion[] mSunFrams;
	 
	
	float mPosSunX,mPosSunY;
	public SunActor(Configer cfg){
		super();
		mCfg = cfg;
 
		mPosSunX = 41; mPosSunY = 13f;
		mSunFrams = new AtlasRegion[5];
		mSunFrams[0] = mCfg.mTextureAltas.findRegion("sun1");
		mSunFrams[1] = mCfg.mTextureAltas.findRegion("sun2");
		mSunFrams[2] = mCfg.mTextureAltas.findRegion("sun3");
		mSunFrams[3] = mCfg.mTextureAltas.findRegion("sun4");
		mSunFrams[4] = mCfg.mTextureAltas.findRegion("sun5");		
		mSunAnim = new Animation(1/2f, mSunFrams);
		mSunAnim.setPlayMode(PlayMode.LOOP);
 		
		
		
		float w = mSunFrams[0].getRegionWidth()/32f;
		float h = mSunFrams[0].getRegionHeight()/32f;
		setPosition(mPosSunX, mPosSunY);
		setSize(w, h);
		setOrigin(Align.center);
		
		setBounds(mPosSunX, mPosSunY, w, h);

		addAction(Actions.forever(Actions.sequence( 
				Actions.rotateTo(20f, 3600*11f),  	//11小时逆时针旋转 20度
				Actions.alpha(0.7f, 3600) //再留1小时将太阳淡化
				) 
				));	
	}
	
	
 	float stateTime,stateTime2,lasttime;
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		stateTime  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		stateTime2  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		//Gdx.app.log("","statime:"+stateTime+", 2="+stateTime2);
		drawSun(batch);
	}
	
	
	// org(27, 5)
	// r = 15
	// 12小时完成20->160度，即每秒  140/(3600*12)=0.00324074074074074074074074074074度
	// 早晨6点钟所代表的秒数为 3600*6=21600
	float lastangle = 20f;
	float mRadius = 15;
	float mOrgX = 27f, mOrgY=5f;
	void computePosition(){
		lastangle  = (float) ((mCfg.mHour*3600+mCfg.mMin*60+mCfg.mSec - 21600)*0.00324074074074074074074074074074) + 20; //前半部分计算相对于6点的偏移度数
		//Gdx.app.log("", "angle:"+lastangle);
		if(lastangle > 160){
			lastangle = 160;
		}
 
		float fd = (float) Math.toRadians(lastangle);
		mPosSunX = (float) (mRadius*Math.cos(fd) + mOrgX);
		mPosSunY = (float) (mRadius*Math.sin(fd) + mOrgY);
		
		//Gdx.app.log("", String.format("%d:%d:%d, x:%f,y:%f", mCfg.mHour, mCfg.mMin,mCfg.mSec,mPosSunX,mPosSunY));

		setPosition(mPosSunX, mPosSunY);
	}
	
	//实现间歇行 打哈欠 ， 10s左右一次，给3秒左右(可以计算出)的动画
	int lastsec = 0;
	void checkState(){
		if(mCfg.mBDayMode){
			//day
			setVisible(true);
			
			//计算太阳运行偏移度数
			if(mCfg.mBhadsettime && /*mCfg.mSec-lastsec > 0*/mCfg.mSec != lastsec){
				computePosition();
				lastsec = mCfg.mSec;
			}
			
			
			//太阳打哈欠动画控制
			int val = (int)(stateTime2-lasttime);
			if(val > 10 && val<13){
				//Gdx.app.log("", "10<>13");
			}
			else if(val == 13){
				//Gdx.app.log("", "=13");
				lasttime = stateTime2;
			}
			else{
				stateTime = 0;
				//
			}
			
		}
		else{
			setVisible(false);
		}
		

	}
	
	
	
	
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		
		checkState();
	}
	
	void drawSun(Batch batch){
		checkState();
		
		Color color = batch.getColor();  
        batch.setColor(getColor());  
        
		batch.draw(mSunAnim.getKeyFrame(stateTime, true), 
				getX(), getY(),   //左下角坐标  
					getOriginX(), getOriginY(), 
	                getWidth(), getHeight(), 
	                1, 1,  getRotation()); 
		batch.setColor(color);  
	}
	
}
