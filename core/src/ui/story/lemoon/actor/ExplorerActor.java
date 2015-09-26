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

public class ExplorerActor extends Actor{
	//22.5,16.9=>22.5, 32-16.9-1=14.1
	AtlasRegion mTextureRegion;
	float mPosx=22.5f, mPosy=14.1f;
	float mW,mH;
	boolean mbHadGrowed = false;
	
	InputListener input;
	
	Timer mTimer = null;
	public ExplorerActor(Configer cfg){
		super();
	//	
		
		mTextureRegion = MyGame.mCfg.getMainAtlas().findRegion("item_explorer");
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
			}
 
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Configer.mylog("", "Explorer down x="+x+", y="+y);
				 
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
				msg.what = MyMsg.ITEM_EXPLORER;
				MyGame.mCfg.game.notify(msg);
				Configer.mylog("", "actor touched activate");
			}
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	void checkShow(){
		if(MyGame.mCfg.mCurPage == 2){
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