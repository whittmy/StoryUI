package ui.story.lemoon.actor;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import ui.story.lemoon.Configer;
import ui.story.lemoon.MyGame;
import ui.story.lemoon.comunicate.MyMsg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class GenItemActor extends Actor{
	AtlasRegion mTextureRegion;
	float mPosx, mPosy;
	float mW,mH;
	boolean mbHadGrowed = false;
	int mMsg, mPgIdx;
	InputListener input;
	
	Timer mTimer = null;
	public GenItemActor(Configer cfg, 
			float x, float y, 
			String resRegion, //such as "item_carton"
			int msg, int pageIdx){  // such as MyMsg.ITEM_CARTON
		super();
		mPosx = x;
		mPosy = y;
		mMsg = msg;
		mPgIdx = pageIdx;
		
		mTextureRegion = MyGame.mCfg.getMainAtlas().findRegion(resRegion);
		mW = mTextureRegion.getRegionWidth()/32f;
		mH = mTextureRegion.getRegionHeight()/32f;
		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setBounds(mPosx, mPosy, mW, mH);
		
		
		input = new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				//Configer.mylog("", "Carton up x="+x+", y="+y);
//				MyMsg msg = new MyMsg();
//				msg.what = MyMsg.ITEM_CARTON;
//				MyGame.mCfg.game.notify(msg);
				//return super.touchDown(event, x, y, pointer, button);
				//return true;
			}
 
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Configer.mylog("", "Carton down x="+x+", y="+y);
				 
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
				msg.what = mMsg;
				MyGame.mCfg.game.notify(msg);
				Configer.mylog("", "actor touched activate");
			}
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	void checkShow(){
		if(MyGame.mCfg.mCurPage == mPgIdx){
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

	void addGrowAction(){
		SequenceAction ac_grow_carton = Actions.sequence(//Actions.sizeTo(getWidth(), getHeight(), 2f), 
				Actions.sizeTo(getWidth(), 0, 0), 
				Actions.sizeTo(getWidth(), getHeight()+0.5f, 0.1f),
				Actions.sizeTo(getWidth(), getHeight(), 0.08f));		
		addAction(ac_grow_carton);
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