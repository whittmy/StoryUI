package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;
import ui.story.lemoon.MyGame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class CloudActor1 extends Actor {
	//
	
	AtlasRegion mTrDayCloud, mTrNightCloud ;
	
	float mDayW,mDayH, mDayW2,mDayH2;
	float mNightW,mNightH,mNightW2,mNightH2;
	
	
	private void reset(){
		mNight1Posx = 25; mNight1Posy =18.5f ;
		mNight2Posx=15; 	mNight2Posy=17;
	}
	
	
	float mNight1Posx, mNight1Posy, mNight2Posx,mNight2Posy;
	public CloudActor1(Configer cfg){
		super();
		//
		
		mTrDayCloud = MyGame.mCfg.getMainAtlas().findRegion("day_cloud");
		mDayW = mTrDayCloud.getRegionWidth()/32f;
		mDayH = mTrDayCloud.getRegionHeight()/32f;		
		
		mTrNightCloud = MyGame.mCfg.getMainAtlas().findRegion("night_cloud");
		mNightW = mTrNightCloud.getRegionWidth()/32f;
		mNightH = mTrNightCloud.getRegionHeight()/32f;
		
 		
		
		setBounds(0, 15, 54, 5);
		
		
		reset();

		addAction(Actions.forever(Actions.sequence(
				Actions.delay(5f),Actions.alpha(0.3f, 4f),Actions.delay(5f),Actions.alpha(1f,4f)
				)));
		
	}
	
	
	int mystep = 0;
	void computNightPos(){
		if(mNight1Posx > 44){
			mNight1Posx = 11;
		}
		
		if(mNight2Posx > 46){
			mNight2Posx = 8;
		}
		
		
		mNight1Posx += 0.001f;//mNight1Posy = 17;
		mNight2Posx += 0.008f;//	mNight2Posy=20;
		
		
		if(mystep < 3500){
			mNight1Posy += 0.001f;
			mystep++;
		}
		else if(mystep>=3500 && mystep<7000 ){
			mNight1Posy -=0.001f;
			mystep++;
		}
		else if(mystep>=7000 && mystep<8000){
			mystep++;
		}
		else{
			mystep = 0;
		}
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		computNightPos();
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		
		if(MyGame.mCfg.mBDayMode){
			Color color = batch.getColor();  
	        batch.setColor(getColor());  
			batch.draw(mTrDayCloud, mNight1Posx, mNight1Posy, getOriginX(), getOriginY(), mDayW*1.1f, mDayH, getScaleX(), getScaleY(), getRotation());
			batch.setColor(color);  
			
			batch.draw(mTrDayCloud, mNight2Posx, mNight2Posy, getOriginX(), getOriginY(), mDayW, mDayH, getScaleX(), getScaleY(), getRotation());

		}
		else{
			Color color = batch.getColor();  
	        batch.setColor(getColor());  
			batch.draw(mTrNightCloud, mNight1Posx, mNight1Posy, getOriginX(), getOriginY(), mNightW*1.1f, mNightH, getScaleX(), getScaleY(), getRotation());
			
			batch.setColor(color);  
			
			batch.draw(mTrNightCloud, mNight2Posx, mNight2Posy, getOriginX(), getOriginY(), mNightW, mNightH, getScaleX(), getScaleY(), getRotation());
		}
		

	}
}
