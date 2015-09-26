package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;
import ui.story.lemoon.MyGame;
import ui.story.lemoon.comunicate.MyMsg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class RainBowActor extends Actor{
	// 18.5,21.5 => 18.5,9.5
	AtlasRegion mRainbow;
	float mPosx=19f, mPosy=16f;
	float mW,mH;
	boolean mbHadGrowed = false;
	
	
	public RainBowActor(Configer cfg){
		super();
		
		
		mRainbow = MyGame.mCfg.getMainAtlas().findRegion("day_rainbow");
		mW = mRainbow.getRegionWidth()/32f;
		mH = mRainbow.getRegionHeight()/32f;
		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setBounds(mPosx, mPosy, mW, mH);
		
		
		addAction(Actions.forever(Actions.sequence(
				Actions.delay(5f),Actions.alpha(0.3f, 8f),Actions.delay(5f),Actions.alpha(1f,4f)
				)));
		
	}
 

	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		//super.draw(batch, parentAlpha);
 
		if(MyGame.mCfg.mBDayMode){
			Color color = batch.getColor();  
	        batch.setColor(getColor());  
			batch.draw(mRainbow, mPosx, mPosy, mW, mH);
			batch.setColor(color);  
		}
	}
}