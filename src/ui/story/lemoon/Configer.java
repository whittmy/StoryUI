package ui.story.lemoon;

import java.util.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
	
	public Timer mTimer = new Timer(true);

	public long mLastSwitchSrc_time = 0;

	public boolean mBgMusicOn = false;
	
	static public final String PREF_NAME = "uisetting";
	static public final String PREF_BGMUSIC = "bgmusic";
	
	public Configer(){
		Preferences prefs= Gdx.app.getPreferences(PREF_NAME);
		mBgMusicOn = prefs.getBoolean(PREF_BGMUSIC);
	}
	
	
	public interface BgMusicListener{
		public void toggleMusic(boolean on);
	}
	public void setBgMusicListener(BgMusicListener l){
		mBgMusicListener = l;
	}
	public BgMusicListener mBgMusicListener = null;
	
}
