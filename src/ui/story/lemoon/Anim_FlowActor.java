package ui.story.lemoon;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

// 水波纹动画
public class Anim_FlowActor extends Actor {
	Texture texture;
	TextureRegion region;
	Animation mRippleAnim;
	TextureRegion[] mRippleFrams;
	float stateTime;
	
	BitmapFont font;
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");//yyyy年MM月dd日   HH:mm:ss 
	String str = "";
	
	float actorX, actorY;
	float mUWidth = 4;
	float mUheight = 2;
	TextureRegion currentFrame;
	float mMinDelay = 0f;
	public Anim_FlowActor() {
		// TODO Auto-generated constructor stub
		texture = new Texture("anims/flow.png");
		region = new TextureRegion(texture);
		
		//(37, 20)
		actorX = 37;
		actorY = 32-20-1;
		
		font = new BitmapFont(Gdx.files.internal("font/arial-15.fnt"), Gdx.files.internal("font/arial-15.png"), false);
		font.setColor(255, 255, 255, 1);
		font.getData().setScale(0.075f, 0.06f);
		//font.getData().setScale(0.05f);
		//font.setUseIntegerPositions(true);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion[][] tmp = region.split(texture.getWidth()/5, texture.getHeight());
		mRippleFrams = new TextureRegion[5];
		mRippleFrams[0] = tmp[0][0];
		mRippleFrams[1] = tmp[0][1];
		mRippleFrams[2] = tmp[0][2];
		mRippleFrams[3] = tmp[0][3];
		mRippleFrams[4] = tmp[0][4];
		
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
		
		
		showTime(batch);
	}
	
	//(37, 13) => (37, (32-13))==(37, 19)
	private void showTime(Batch batch) {
		mMinDelay += Gdx.graphics.getDeltaTime();
		if(mMinDelay>1f){
			// 获取系统时间并显示 public void dataTime() {
			
			Date curDate = new Date(System.currentTimeMillis());
			// 获取当前时间
			str = " "+formatter.format(curDate);
			
//			Vector3 v = new Vector3();
//			v.x = 37f;
//			v.y = 19f;
			
			//v = getStage().getCamera().project(v);
			
			//float rzoom = 1f /((OrthographicCamera)getStage().getCamera()).zoom;
			//font.getData().setScale(0.075f/rzoom, 0.06f/rzoom);
			//Gdx.app.log("", "yyyy年MM月dd日   HH:mm:ss ===" + v.x+", v.y="+v.y);	
			
			mMinDelay = 0f;
		}
		font.draw(batch, str, 37, 19);

	}
	
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		font.dispose();

		texture.dispose();
		super.clear();
	}
}
