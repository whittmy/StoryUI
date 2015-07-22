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

public class LanguageActor extends Actor{
	//30,22.6 => 30, 32-22.6-1 = 8.4
	AtlasRegion mTextureRegion;
	float mPosx=30f, mPosy=8.4f;
	float mW,mH;
	boolean mbHadGrowed = false;
	
	Configer mCfg;
	public LanguageActor(Configer cfg){
		super();
		mCfg = cfg;
		
		mTextureRegion = mCfg.mTextureAltas.findRegion("item_language");
		mW = mTextureRegion.getRegionWidth()/32;
		mH = mTextureRegion.getRegionHeight()/32;
		setSize(mW, mH);
		setPosition(mPosx, mPosy);
		setBounds(mPosx, mPosy, mW, mH);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				//Gdx.app.log("", "Language down x="+x+", y="+y);
				MyMsg msg = new MyMsg();
				msg.what = MyMsg.ITEM_LANGUAGE;
				mCfg.game.notify(msg);
				//return super.touchDown(event, x, y, pointer, button);
				return true;
			}
		});	
	}
	
	void addGrowAction(){
		SequenceAction  ac_grow_lang = Actions.sequence(//Actions.sizeTo(getWidth(), getHeight(), 2f), 
					Actions.sizeTo(getWidth(), 0, 0), 
					Actions.sizeTo(getWidth(), getHeight()+0.5f, 0.3f),
					Actions.sizeTo(getWidth(), getHeight(), 0.08f)); 			
		addAction(ac_grow_lang);
	}

	void checkShow(){
		if(mCfg.mCurPage == 1){
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