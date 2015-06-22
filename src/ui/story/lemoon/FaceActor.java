package ui.story.lemoon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class FaceActor extends Actor {
	
	private  Texture mTexture;
	Sprite sprite;
	public FaceActor(){
		mTexture = new Texture(Gdx.files.internal("anims/fan.png"));
		sprite = new Sprite(mTexture);
		TextureRegion region = new TextureRegion(mTexture); 
		setBounds(getX(), getY(), region.getRegionWidth()+50, region.getRegionHeight());
		
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {  //x,y好像是基于左下角的原点
				// TODO Auto-generated method stub
				
				Gdx.app.log("", "touchdown: x="+x+", y="+y+",pointer="+pointer+", button="+button);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
 
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		
		sprite.draw(batch);
		
	}
	
	
	
	public void dispose(){
		mTexture.dispose();
	}
}
