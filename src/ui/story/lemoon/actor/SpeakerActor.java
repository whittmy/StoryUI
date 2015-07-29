package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;

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
	Configer mCfg;
	
	Animation mSignalAnim;
	AtlasRegion[] mSignalFrams;
	AtlasRegion mSpeakerRegion; 
	
	float mPosSpeakX,mPosSpeakY, mPosSignalX,mPosSignalY;
	float mSpeakW,mSpeakH,mSignalW,mSignalH;
	public SpeakerActor(Configer cfg){
		super();
		mCfg = cfg;
 
		mPosSpeakX = 40.25f; mPosSpeakY = 13.45f;
		mPosSignalX = 41.9f; mPosSignalY=13.8f;
		
		mSignalFrams = new AtlasRegion[4];
		mSignalFrams[0] = mCfg.mTextureAltas.findRegion("signal0");
		mSignalFrams[1] = mCfg.mTextureAltas.findRegion("signal1");
		mSignalFrams[2] = mCfg.mTextureAltas.findRegion("signal2");
		mSignalFrams[3] = mCfg.mTextureAltas.findRegion("signal3");
		mSignalAnim = new Animation(1/3f, mSignalFrams);
		mSignalAnim.setPlayMode(PlayMode.LOOP);
 		
		mSpeakerRegion = mCfg.mTextureAltas.findRegion("specker");
		
		
		mSignalW = mSignalFrams[0].getRegionWidth()/32f;
		mSignalH = mSignalFrams[0].getRegionHeight()/32f;
		mSpeakW = mSpeakerRegion.getRegionWidth()/32f;
		mSpeakH = mSpeakerRegion.getRotatedPackedHeight()/32f;
		
		
		
//		setPosition(mPosSpeakX, mPosSpeakY);
//		setSize(w, h);
//		setOrigin(Align.center);
		
		setBounds(mPosSpeakX, mPosSpeakY, mSpeakW, mSpeakH);
		
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Preferences prefs= Gdx.app.getPreferences(Configer.PREF_NAME);
				if(mCfg.mBgMusicOn){
					//off
					prefs.putBoolean(Configer.PREF_BGMUSIC, false).flush();
					mCfg.mBgMusicOn = false;
				}
				else{
					//on
					prefs.putBoolean(Configer.PREF_BGMUSIC, true).flush();
					mCfg.mBgMusicOn = true;
				}
				
				if(mCfg.mBgMusicListener!=null){
					mCfg.mBgMusicListener.toggleMusic(mCfg.mBgMusicOn);
				}
				return false;
			}
		});

	}
	
	
 	float stateTime,stateTime2,lasttime;
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		stateTime  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		stateTime2  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		//Gdx.app.log("","statime:"+stateTime+", 2="+stateTime2);
		drawSpeak(batch);
	}

	
	void drawSpeak(Batch batch){
		batch.draw(mSpeakerRegion, mPosSpeakX, mPosSpeakY, getOriginX(), getOriginY(), mSpeakW, mSpeakH, 1, 1, getRotation());
		
		if(mCfg.mBgMusicOn)
			batch.draw(mSignalAnim.getKeyFrame(stateTime, true), 
					mPosSignalX, mPosSignalY,   //左下角坐标  
						getOriginX(), getOriginY(), 
						mSignalW, mSignalH, 
		                1, 1,  getRotation()); 
	}
	
}
