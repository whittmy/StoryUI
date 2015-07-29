package ui.story.lemoon.actor;

import java.util.Timer;
import java.util.TimerTask;

import ui.story.lemoon.Configer;
import ui.story.lemoon.actor.LifeActor.MyTask;
import ui.story.lemoon.msg.MyMsg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class GuoXueActor extends Actor{
	//(31,21.3)=>(31,32-21.3-1=9.7)
	AtlasRegion mTextureRegion;
	float mPosx=31f, mPosy=9.6f;
	float mW,mH;
	
	boolean mbHadGrowed = false;
	Configer mCfg;
	Timer mTimer = null;
	public GuoXueActor(Configer cfg){
		super();
		mCfg = cfg;
		
		mTextureRegion = mCfg.mTextureAltas.findRegion("item_guoxue");
		mW = mTextureRegion.getRegionWidth()/32;
		mH = mTextureRegion.getRegionHeight()/32;
		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setBounds(mPosx, mPosy, mW, mH);
		addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				//Gdx.app.log("", "GuoXue up x="+x+", y="+y);
				//return super.touchDown(event, x, y, pointer, button);
				//return true;
			}
 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Gdx.app.log("", "GuoXue down x="+x+", y="+y);
				if(mTimer != null){
					mTimer.cancel();
					mTimer = null;
				}
				
				mTimer = new Timer(true);
				MyTask task = new MyTask();
				mTimer.schedule(task  ,400);
				
				return false; 
			}

		});
	}
	
	class MyTask extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			long intval = (System.currentTimeMillis()- mCfg.mLastSwitchSrc_time);
			Gdx.app.log("", "intval="+intval);
			if( intval>=400){
				MyMsg msg = new MyMsg();
				msg.what = MyMsg.ITEM_GUOXUE;
				mCfg.game.notify(msg);
				Gdx.app.log("", "actor touched activate");
			}
			
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	void checkShow(){
		if(mCfg.mCurPage == 0){
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
		SequenceAction ac_grow_guoxue = Actions.sequence(//Actions.sizeTo(getWidth(), getHeight(), 2f), 
				Actions.sizeTo(getWidth(), 0, 0), 
				Actions.sizeTo(getWidth(), getHeight()+0.5f, 0.3f),
				Actions.sizeTo(getWidth(), getHeight(), 0.08f));		
		addAction(ac_grow_guoxue);
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
		//Gdx.app.log("", getWidth()+","+getHeight()+"===,"+mW+","+mH);
		batch.draw(mTextureRegion, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}
}