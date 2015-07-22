package ui.story.lemoon.actor;

import ui.story.lemoon.Configer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class NaviActor extends Actor{
	Configer mCfg;
	
	AtlasRegion mRight, mLeft;
	float w, h;
	public NaviActor(Configer cfg) {
		// TODO Auto-generated constructor stub
		super();
		
		mCfg = cfg;
		
		mRight = mCfg.mTextureAltas.findRegion("navi_arrow");
		h = mRight.getRegionHeight()/32f;
		w = mRight.getRegionWidth()/32f;
		
		mLeft = new AtlasRegion(mRight);
		mLeft.flip(true, false);
		
		addAction(Actions.forever(Actions.sequence(
				Actions.alpha(0.5f, 0.8f),Actions.alpha(1f,0.8f)
				)));
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		
		Color color = batch.getColor();  
        batch.setColor(getColor());  
		batch.draw(mRight, 41.2f, 15, w, h);
		batch.draw(mLeft, 14.8f, 15, w, h);
		batch.setColor(color);  
	}
}
