package ui.story.lemoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// 这里有两种方法实现旋转哦，
//方法一： 需要构造父类，然后设置父类自身的setRotation函数来实现，但是mTexture必须为静态，因为构造父类之前不可以实例化普通对象。

//方法二：是否集成Sprite已无关紧要，因为这个类自己手动去调用draw函数，然后自己实现绘制纹理，每次按不同的旋转度

public class Anim_WindMill {
	private float mRot = 0f;

	//方法一
	//private static  Texture mTexture = new Texture(Gdx.files.internal("anims/fan.png"));
	
	//方法二
	Texture mTexture;
	TextureRegion mRegion;
	
	public Anim_WindMill(){
		//方法二
		 mTexture = new Texture(Gdx.files.internal("anims/fan.png"));
		 mRegion = new TextureRegion(mTexture,8,8);
		 
		 //方法一
		//super(mTexture);
	}
	
	
	
	//@Override 方法一
	public void draw(Batch batch) {
		// TODO Auto-generated method stub
		if(mRot > 360f)
			mRot = 0f;
		
		//方法一
		//setRotation(-mRot);
		//super.draw(batch);
 
		//方法二
		batch.draw(mRegion, 0, 0, 
				(mRegion.getRegionWidth()-mRegion.getRegionX())/2, (mRegion.getRegionHeight()-mRegion.getRegionY())/2,  //旋转时以该坐标进行的。
				mRegion.getRegionWidth(), mRegion.getRegionHeight(),1f, 1f, 
				-mRot); 
		
		mRot += 0.5f;
	}
	
	
	public void dispose(){
		mTexture.dispose();
	}
	
}
