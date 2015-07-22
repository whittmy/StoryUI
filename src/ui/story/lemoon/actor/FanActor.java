package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.utils.Align;

public class FanActor extends Actor{
	AtlasRegion mTextureRegion, mTower;
	//34,18
	float mPosx=33.9f, mPosy=13.1f; 
	float mW,mH;
	Configer mCfg;
	
	float mSpeed = 5f; //default
	boolean mbPressed = false;
	
	public FanActor(Configer cfg){
		super();
		mCfg = cfg;
		
		mTextureRegion = mCfg.mTextureAltas.findRegion("fan");
		mTower = mCfg.mTextureAltas.findRegion("item_tower");
		
		mW = mTextureRegion.getRegionWidth()/32;
		mH = mTextureRegion.getRegionHeight()/32;
		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setBounds(mPosx, mPosy, mW, mH);
		setOrigin(Align.center);     //设定原点为中心，旋转才稳定
		
		//添加 旋转Action
		
		action.setRotation(360f);
		action.setDuration(mSpeed);
		action.setReverse(true);
		
		//!!!!!!!!!! 循环执行， 没有这个默认执行一次就停止
		RepeatAction epeatAction = Actions.repeat(RepeatAction.FOREVER,  action);  
		addAction(epeatAction);
		
		
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				//Gdx.app.log("", "Carton down x="+x+", y="+y);
				mbPressed = true;
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				super.touchUp(event, x, y, pointer, button);
				mbPressed = false;
			}
		});	
	}
	
	RotateToAction action = new RotateToAction();
	
	
 	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
 		checkShow();
	}
	
	void checkShow(){
		if(mbPressed){
			if(mSpeed != 1f){
				mSpeed = 2f;
				action.setDuration(mSpeed);
			}
			
		}
		else{
			if(mSpeed != 5f){
				mSpeed = 5f;
				action.setDuration(mSpeed);
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(mTower, 34.4f, 11.6f, 2, 4); //(35, 20)=>(35,32-19.8-1=10.2)
		batch.draw(mTextureRegion, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}
	
}