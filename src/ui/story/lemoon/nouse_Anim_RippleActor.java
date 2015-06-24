package ui.story.lemoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;

// 水波纹动画
public class nouse_Anim_RippleActor extends Actor {
	Texture texture;
	TextureRegion region;
	Animation mRippleAnim;
	TextureRegion[] mRippleFrams;
	float stateTime;
	 
	float actorX, actorY;
	float mUWidth = 4;
	float mUheight = 2;
	
	TextureRegion currentFrame;
	public nouse_Anim_RippleActor(Texture t) {
		// TODO Auto-generated constructor stub
		//super();
		//texture = new Texture("anims/ripple.png");
		texture = t;
		region = new TextureRegion(texture);
		
		//(36, 20)
		actorX = 36;
		actorY = 30-20-1;
		
		TextureRegion[][] tmp = region.split(texture.getWidth(), texture.getHeight()/3);
		mRippleFrams = new TextureRegion[3];
		mRippleFrams[0] = tmp[0][0];
		mRippleFrams[1] = tmp[1][0];
		mRippleFrams[2] = tmp[2][0];
		
		mRippleAnim = new Animation(1/5f, mRippleFrams);
		mRippleAnim.setPlayMode(PlayMode.LOOP);
		
		stateTime = 0f;
	}
	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		//super.draw(batch, parentAlpha);
		
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = mRippleAnim.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, 
				actorX, actorY,   //左下角坐标  
				0, 0,   //旋转-所缩放基点
                mUWidth, mUheight, //这儿特别注意，为风扇的 unit尺寸，虽然取出来是按像素的(目的是要取完整)， 但显示的大小是受这儿控制的
                getScaleX(), getScaleY(),  getRotation());  	
	}
	
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
		//texture.dispose();
		super.clear();
	}
}
