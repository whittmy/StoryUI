package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;
import ui.story.lemoon.MyGame;
import ui.story.lemoon.comunicate.MyMsg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;

public class MeteorActor extends Actor{
	float mPosx=26f, mPosy=19f, mAngle;
	float mW,mH;
	boolean mbHadGrowed = false;
	
	Animation mMeteorAnim;
	AtlasRegion[] mMeteorFrams;
	float mDelayed = 0f;
	//
	public MeteorActor(Configer cfg, float x, float y, float angle, float delay){
		super();
		//
		mPosx = x;
		mPosy = y; 
		mAngle = angle;
		mDelayed = delay;
		
		mMeteorFrams = new AtlasRegion[2];
		mMeteorFrams[0] = MyGame.mCfg.getMainAtlas().findRegion("meteor1");
		mMeteorFrams[1] = MyGame.mCfg.getMainAtlas().findRegion("meteor2");
		mW = mMeteorFrams[0].getRegionWidth()/32f;
		mH = mMeteorFrams[0].getRegionHeight()/32f;
		
		Configer.mylog("", "w="+mW+", mH="+mH);
		mMeteorAnim = new Animation(1/5f, mMeteorFrams);
		mMeteorAnim.setPlayMode(PlayMode.LOOP);
 
 		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setOrigin(Align.center);
		setBounds(mPosx, mPosy, mW, mH);
  
		addAction(Actions.rotateTo(mAngle));

		
	}

 
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		stateTime  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
 	}
	
	boolean bset = false;
	float stateTime;
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
		if(MyGame.mCfg.mBDayMode)
			return;
		Color color = batch.getColor();  
        batch.setColor(getColor());  
        if(mDelayed > 0f){
        	mDelayed -= Gdx.graphics.getDeltaTime();
        }
        else{
        	if(!bset){
        		RepeatAction ac_grow_carton = Actions.forever(Actions.sequence(
        				//Actions.rotateTo(mAngle),
        				Actions.parallel(Actions.sizeTo(0, getHeight(), 0.8f), Actions.alpha(0f, 0.8f)), // 消失
        				Actions.delay(3f),
        				Actions.parallel(Actions.sizeTo(getWidth(), getHeight(), 0f), Actions.alpha(1f, 0f))
        				));		
        		addAction(ac_grow_carton);	
        		bset = true;
        	}
        	
			batch.draw(mMeteorAnim.getKeyFrame(stateTime, true), 
					getX(), getY(),   //左下角坐标  
						getOriginX(), getOriginY(), 
		                getWidth(), getHeight(), 
		                1.2f, 1,  getRotation()); 
	    }
		batch.setColor(color);  
	}
}