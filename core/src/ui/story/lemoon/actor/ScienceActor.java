package ui.story.lemoon.actor;

import java.util.Timer;
import java.util.TimerTask;

import ui.story.lemoon.Configer;
import ui.story.lemoon.MyGame;
import ui.story.lemoon.actor.LifeActor.MyTask;
import ui.story.lemoon.comunicate.MyMsg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class ScienceActor extends Actor{
	//29.5,17.8 => 29.5, 13.2
	AtlasRegion mTextureRegion;
	float mPosx=29.4f, mPosy=13.2f;
	float mW,mH;
	boolean mbHadGrowed = false;
	
	
	Timer mTimer;
	InputListener input;
	
	public ScienceActor(Configer cfg){
		super();
 
		mTextureRegion = MyGame.mCfg.getMainAtlas().findRegion("item_science");
		mW = mTextureRegion.getRegionWidth()/32f;
		mH = mTextureRegion.getRegionHeight()/32f;
		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setBounds(mPosx, mPosy, mW, mH);
		
		input = new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				//Configer.mylog("", "Science up x="+x+", y="+y);
//				MyMsg msg = new MyMsg();
//				msg.what = MyMsg.ITEM_SCIENCE;
//				MyGame.mCfg.game.notify(msg);
				//return super.touchDown(event, x, y, pointer, button);
				//return true;
			}
 
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Configer.mylog("", "Science down x="+x+", y="+y);
				 
				if(mTimer != null){
					mTimer.cancel();
					mTimer = null;
				}
				
				mTimer = new Timer(true);
				MyTask task = new MyTask();
				mTimer.schedule(task  ,400);
				
				return false; 
			}
		};
		
		addListener(input);	
	}
	
	
	class MyTask extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			long intval = (System.currentTimeMillis()- MyGame.mCfg.mLastSwitchSrc_time);
			Configer.mylog("", "intval="+intval);
			if( intval>=400){
				MyMsg msg = new MyMsg();
				msg.what = MyMsg.ITEM_SCIENCE;
				MyGame.mCfg.game.notify(msg);
				Configer.mylog("", "actor touched activate");
			}
			
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	void addGrowAction(){
		SequenceAction ac_grow_science = Actions.sequence(//Actions.sizeTo(getWidth(), getHeight(), 2f), 
				Actions.sizeTo(getWidth(), 0, 0), 
				Actions.sizeTo(getWidth(), getHeight()+0.5f, 0.1f),
				Actions.sizeTo(getWidth(), getHeight(), 0.08f));		
		addAction(ac_grow_science);
	}
	
	void checkShow(){
		if(MyGame.mCfg.mCurPage == 0){
			setVisible(true);
			if(!mbHadGrowed){
				addGrowAction();
				mbHadGrowed = true;
			}
		}
		else{
			mbHadGrowed = false;
			setVisible(false);
		}			
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		checkShow();
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		//super.draw(batch, parentAlpha);
		//batch.draw(mTextureRegion, mPosx, mPosy, mW, mH);
		batch.draw(mTextureRegion, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}
}