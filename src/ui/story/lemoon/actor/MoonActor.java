package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

//从太阳代码拷贝过来，仅仅实现月亮的功能，其它未处理
public class MoonActor extends Actor{
	Configer mCfg;
	
	Animation mMoonAnim;
	AtlasRegion[] mMoonFrams;
	 
	
	float mPosMoonX,mPosMoonY;
	public MoonActor(Configer cfg){
		super();
		mCfg = cfg;
 		
		
		mPosMoonX = 15; mPosMoonY = 19;
		mMoonFrams = new AtlasRegion[1];
		mMoonFrams[0] = mCfg.mTextureAltas.findRegion("night_moon");
//		mMoonFrams[1] = mCfg.mTextureAltas.findRegion("sun2");
//		mMoonFrams[2] = mCfg.mTextureAltas.findRegion("sun3");
//		mMoonFrams[3] = mCfg.mTextureAltas.findRegion("sun4");
//		mMoonFrams[4] = mCfg.mTextureAltas.findRegion("sun5");		
		mMoonAnim = new Animation(1/2f, mMoonFrams);
		mMoonAnim.setPlayMode(PlayMode.LOOP);
 
		float w = mMoonFrams[0].getRegionWidth()/32f;
		float h = mMoonFrams[0].getRegionHeight()/32f;
 
		setPosition(mPosMoonX, mPosMoonY);
		setSize(w, h);
		setOrigin(Align.center);
		
		setBounds(mPosMoonX, mPosMoonY, w, h);
		
		addAction(Actions.forever(Actions.sequence(
				Actions.alpha(0.5f, 2f),Actions.alpha(1f,2f))));
		
	}
	
	
 	float stateTime,stateTime2,lasttime;
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		stateTime  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		stateTime2  += Math.min(0.07f, Gdx.graphics.getDeltaTime());
		//Gdx.app.log("","statime:"+stateTime+", 2="+stateTime2);
		drawMoon(batch);
	}
	
	
	// 
	void checkState(){
		if(mCfg.mBDayMode){
			//day
			setVisible(false);
		}
		else{
			setVisible(true);
		}
		
 
		stateTime = 0;
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		
		checkState();
	}
	
	void drawMoon(Batch batch){
		Color color = batch.getColor();  
        batch.setColor(getColor());  
		batch.draw(mMoonAnim.getKeyFrame(stateTime, true), 
				getX(), getY(),   //左下角坐标  
					getOriginX(), getOriginY(), 
	                getWidth(), getHeight(), 
	                1, 1,  getRotation()); 
 
		batch.setColor(color);  
	}
	
}
