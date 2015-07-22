package ui.story.lemoon;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Configer {
	public final int MAP_WIDTH = 56;
	public final int MAP_HEIGHT = 32;	
	
	public int mCurPage = 0;
	
	public boolean mBhadsettime = false;
	public int mHour,mMin,mSec;
	public boolean mBDayMode = true;
	
	
	public MyGame game;
	public TextureAtlas mTextureAltas;
	public BitmapFont font;
	
	
	public float mCurCameraZoom = 1f;
}
