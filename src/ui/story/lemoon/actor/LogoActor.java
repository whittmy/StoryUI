package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;
import ui.story.lemoon.msg.MyMsg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class LogoActor extends Actor{
	//22.5,16.9=>22.5, 32-16.9-1=14.1
	AtlasRegion mTextureRegion;
	float mPosx=38.7f, mPosy=20.8f;
	float mW,mH;
	boolean mbHadGrowed = false;
	
	
	Configer mCfg;
	public LogoActor(Configer cfg){
		super();
		mCfg = cfg;
		
		mTextureRegion = mCfg.mTextureAltas.findRegion("mlogo");
		mW = mTextureRegion.getRegionWidth()/32;
		mH = mTextureRegion.getRegionHeight()/32;
		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setBounds(mPosx, mPosy, mW, mH);
 
		
		//每1分钟(60s)出现一次logo，旋转一次，停留7秒钟的时间显示
		RepeatAction ac_grow_carton = Actions.forever(Actions.sequence(
				Actions.parallel(Actions.sizeTo(0, getHeight(), 0f), Actions.alpha(0.8f, 0f)),
				Actions.delay(60f),
				Actions.parallel(Actions.sizeTo(0, getHeight(), 0.5f), Actions.moveTo(getX()+getWidth()/2f, getY(),0.5f)),
				Actions.run(mFlip) ,
				Actions.parallel(Actions.sizeTo(getWidth(), getHeight(), 0.5f), Actions.moveTo(getX(), getY(), 0.5f)),
				Actions.parallel(Actions.sizeTo(0, getHeight(), 0.5f), Actions.moveTo(getX()+getWidth()/2f, getY(),0.5f)),
				Actions.run(mFlip) ,
				Actions.parallel(Actions.sizeTo(getWidth(), getHeight(), 0.5f), Actions.moveTo(getX(), getY(), 0.5f)),
				Actions.delay(7f)
				));		
		addAction(ac_grow_carton);		
		
	}

	Runnable mFlip = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mTextureRegion.flip(true, false);
		}
	};
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);

	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		Color color = batch.getColor();  
        batch.setColor(getColor());  
		batch.draw(mTextureRegion, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
		batch.setColor(color);  
	}
}