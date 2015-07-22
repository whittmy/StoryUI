package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;
import ui.story.lemoon.msg.MyMsg;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class SettingActor extends Actor{
	//19.5,21.7 => 19.5,32-21.7-1=9.3
	AtlasRegion mTextureRegion;
	float mPosx=19.3f, mPosy=9.3f;
	float mW,mH;
	boolean mbHadGrowed = false;
	
	Configer mCfg;
	public SettingActor(Configer cfg){
		super();
		mCfg = cfg;
		
		mTextureRegion = mCfg.mTextureAltas.findRegion("item_setting");
		mW = mTextureRegion.getRegionWidth()/32;
		mH = mTextureRegion.getRegionHeight()/32;
		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setBounds(mPosx, mPosy, mW, mH);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				//Gdx.app.log("", "Setting down x="+x+", y="+y);
				MyMsg msg = new MyMsg();
				msg.what = MyMsg.ITEM_SETTING;
				mCfg.game.notify(msg);
				//return super.touchDown(event, x, y, pointer, button);
				return true;
			}
		});	
	}
	
	void addGrowAction(){
		SequenceAction ac_grow_setting = Actions.sequence(//Actions.sizeTo(getWidth(), getHeight(), 2f), 
		Actions.sizeTo(getWidth(), 0, 0), 
					Actions.sizeTo(getWidth(), getHeight()+0.5f, 0.3f),
					Actions.sizeTo(getWidth(), getHeight(), 0.08f));
		addAction(Actions.parallel(ac_grow_setting));				
	}
	
	void checkShow(){
		if(mCfg.mCurPage == 2){
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
		batch.draw(mTextureRegion, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}
}	