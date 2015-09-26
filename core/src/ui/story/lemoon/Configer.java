package ui.story.lemoon;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Configer {
	public final int MAP_WIDTH = 56;
	public final int MAP_HEIGHT = 32;	
	
	public int mCurPage = 0;
	
	public boolean mBhadsettime = false;
	public int mHour,mMin,mSec;
	public boolean mBDayMode = true;
	
	
	public MyGame game;
	//public TextureAtlas mTextureAltas;
	//public BitmapFont font;
	
	
	public float mCurCameraZoom = 1f;
	
	public Timer mTimer = new Timer(true);
	public long mLastTimerTm = 0;
	public GlobalRefreshTask mGlobalTask =  new GlobalRefreshTask();
	
	static public class GlobalRefreshTask extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Gdx.graphics.requestRendering();
		}
	}
	
	
	
	public long mLastSwitchSrc_time = 0;

	public boolean mBgMusicOn = false;
	
	static public final String PREF_NAME = "uisetting";
	static public final String PREF_BGMUSIC = "bgmusic";
	
	public Configer(){
		Preferences prefs= Gdx.app.getPreferences(PREF_NAME);
		mBgMusicOn = prefs.getBoolean(PREF_BGMUSIC);
		
		mMgr =  MyGame.getManager();
		mMgr.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}
	
	
	public interface BgMusicListener{
		public void toggleMusic(boolean on);
	}
	public void setBgMusicListener(BgMusicListener l){
		mBgMusicListener = l;
	}
	public BgMusicListener mBgMusicListener = null;
	
	
	public static void mylog(String t, String msg){
		Gdx.app.log(t, msg);
	}
	
	
	AssetManager mMgr;
	//---------------------------
	public void loadRes(){
		//优先lock
		if(!mMgr.isLoaded("v2/lock_datas/lock_item.atlas")){
			mMgr.load("v2/lock_datas/lock_item.atlas", TextureAtlas.class);
			mMgr.finishLoadingAsset("v2/lock_datas/lock_item.atlas");
		}
		
		if(!mMgr.isLoaded("v2/lock_datas/lock_bg.ogg")){
			mMgr.load("v2/lock_datas/lock_bg.ogg", Music.class);
			mMgr.finishLoadingAsset("v2/lock_datas/lock_bg.ogg");
		}		
		
		
		if(!mMgr.isLoaded("v2/map/v2map.tmx")){
			mMgr.load("v2/map/v2map.tmx", TiledMap.class);
		}
		if(!mMgr.isLoaded("v2/datas/items.atlas")){
			mMgr.load("v2/datas/items.atlas", TextureAtlas.class);
		}
		if(!mMgr.isLoaded("font/arial-15.png")){
			mMgr.load("font/arial-15.png", Texture.class);
		}
		if(!mMgr.isLoaded("font/arial-15.fnt")){
			mMgr.load("font/arial-15.fnt", BitmapFont.class);
			//mMgr.finishLoadingAsset("font/arial-15.fnt");
		}
		if(!mMgr.isLoaded("v2/datas/day.ogg")){
			mMgr.load("v2/datas/day.ogg", Music.class);
		}
		if(!mMgr.isLoaded("v2/datas/night.ogg")){
			mMgr.load("v2/datas/night.ogg", Music.class);
		}
	}
	
	
	public boolean isLoadFinish(){
		if(mMgr == null)
			return false;
		
		if(mMgr.update()){
			return true;
		}
		
		return false;
	}
	
	Music night_music, day_music, lock_music;
	public Music getNightMusic(){
		if(night_music!=null){
			return night_music;
		}
		
		night_music = mMgr.get("v2/datas/night.ogg", Music.class);
		return night_music;
	}
	public void disposeNightMusic(){
		if(night_music !=null){
			night_music.stop();
			night_music.dispose();
			night_music = null;
		}
	}
	
	
	public Music getDayMusic(){
		if(day_music != null)
			return day_music;
		
		day_music = mMgr.get("v2/datas/day.ogg", Music.class);
		return day_music;
	}
	public void disposeDayMusic(){
		if(day_music !=null){
			day_music.stop();
			day_music.dispose();
			day_music = null;
		}
	}	
	
	public Music getLockMusic(){
		if(lock_music != null)
			return lock_music;
		lock_music = mMgr.get("v2/lock_datas/lock_bg.ogg", Music.class);
		return lock_music;
	}
	public void disposeLockMusic(){
		if(lock_music !=null){
			lock_music.stop();
			lock_music.dispose();
			lock_music = null;
		}
	}	
	
	
	TextureAtlas mLockAtlas, mMainAtlas;
	public TextureAtlas getLockAtlas(){
		if(mLockAtlas != null){
			return mLockAtlas;
		}
		mLockAtlas = mMgr.get("v2/lock_datas/lock_item.atlas", TextureAtlas.class);
		return mLockAtlas;
	}
	
	public void disposeLockAtlas(){
		if(mLockAtlas != null){
			mLockAtlas.dispose();
			mLockAtlas = null;
		}
	}
	
	
	
	public TextureAtlas getMainAtlas(){
		if(mMainAtlas != null){
			return mMainAtlas;
		}
		mMainAtlas = mMgr.get("v2/datas/items.atlas", TextureAtlas.class);
		return mMainAtlas;
	}
	public void disposeMainAtlas(){
		if(mMainAtlas != null){
			mMainAtlas.dispose();
			mMainAtlas = null;
		}
	}
	
	
	
	BitmapFont mFont;
	public BitmapFont getFont(){
		if(mFont != null){
			return mFont;
		}
		mFont = mMgr.get("font/arial-15.fnt", BitmapFont.class);
		return mFont;
	}
	
	TiledMap mTileMap;
	public TiledMap getMap(){
		if(mTileMap != null){
			return mTileMap;
		}
		mTileMap = mMgr.get("v2/map/v2map.tmx", TiledMap.class);
		return mTileMap;
	}
	
	
}
