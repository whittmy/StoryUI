package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;
import ui.story.lemoon.MyGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class SpeakerActor extends Actor{
	
	
	Animation mSignalAnim;
	AtlasRegion[] mSignalFrams;
	AtlasRegion mSpeakerRegion; 
	
	float mPosSpeakX,mPosSpeakY, mPosSignalX,mPosSignalY;
	float mSpeakW,mSpeakH,mSignalW,mSignalH;
	
	InputListener input;
	
	public SpeakerActor(Configer cfg){
		super();
		
 
		mPosSpeakX = 40.25f; mPosSpeakY = 13.45f;
		mPosSignalX = 41.9f; mPosSignalY=13.8f;
		
		mSignalFrams = new AtlasRegion[4];
		mSignalFrams[0] = MyGame.mCfg.getMainAtlas().findRegion("signal0");
		mSignalFrams[1] = MyGame.mCfg.getMainAtlas().findRegion("signal1");
		mSignalFrams[2] = MyGame.mCfg.getMainAtlas().findRegion("signal2");
		mSignalFrams[3] = MyGame.mCfg.getMainAtlas().findRegion("signal3");
		mSignalAnim = new Animation(1/3f, mSignalFrams);
		mSignalAnim.setPlayMode(PlayMode.LOOP);
 		
		mSpeakerRegion = MyGame.mCfg.getMainAtlas().findRegion("specker");
		
		
		mSignalW = mSignalFrams[0].getRegionWidth()/32f;
		mSignalH = mSignalFrams[0].getRegionHeight()/32f;
		mSpeakW = mSpeakerRegion.getRegionWidth()/32f;
		mSpeakH = mSpeakerRegion.getRotatedPackedHeight()/32f;
		
		
		
//		setPosition(mPosSpeakX, mPosSpeakY);
//		setSize(w, h);
//		setOrigin(Align.center);
		
		setBounds(mPosSpeakX, mPosSpeakY, mSpeakW, mSpeakH);
		
		input = new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Preferences prefs= Gdx.app.getPreferences(Configer.PREF_NAME);
				if(MyGame.mCfg.mBgMusicOn){
					//off
					prefs.putBoolean(Configer.PREF_BGMUSIC, false).flush();
					MyGame.mCfg.mBgMusicOn = false;
				}
				else{
					//on
					prefs.putBoolean(Configer.PREF_BGMUSIC, true).flush();
					MyGame.mCfg.mBgMusicOn = true;
				}
				
				if(MyGame.mCfg.mBgMusicListener!=null){
					MyGame.mCfg.mBgMusicListener.toggleMusic(MyGame.mCfg.mBgMusicOn);
				}
				return false;
			}
		};
				
		addListener(input);

	}
	
	
 	float stateTime,stateTime2,lasttime;
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		stateTime  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		stateTime2  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		//Configer.mylog("","statime:"+stateTime+", 2="+stateTime2);
		drawSpeak(batch);
	}

	
	void drawSpeak(Batch batch){
		batch.draw(mSpeakerRegion, mPosSpeakX, mPosSpeakY, getOriginX(), getOriginY(), mSpeakW, mSpeakH, 1, 1, getRotation());
		
		if(MyGame.mCfg.mBgMusicOn)
			batch.draw(mSignalAnim.getKeyFrame(stateTime, true), 
					mPosSignalX, mPosSignalY,   //左下角坐标  
						getOriginX(), getOriginY(), 
						mSignalW, mSignalH, 
		                1, 1,  getRotation()); 
	}
	
}
