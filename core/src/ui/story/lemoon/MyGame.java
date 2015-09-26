package ui.story.lemoon;

import java.util.Timer;

import ui.story.lemoon.Configer.GlobalRefreshTask;
import ui.story.lemoon.comunicate.BsuEvent;
import ui.story.lemoon.comunicate.MyMsg;
import ui.story.lemoon.scr.v2.SCRLoading;
import ui.story.lemoon.scr.v2.SCRMain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MyGame extends Game {
	BsuEvent mBEvent;
	
	public static Configer mCfg;
	
	
	public MyGame(BsuEvent b){
		super();
		// 提示空指针  Configer.mylog("", "------------MyGame.MyGame");
		mBEvent = b;
		
	}
	
	public void notify(MyMsg msg){
		mBEvent.notify(msg);
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		//LoadingScr sc = new LoadingScr(this);
		//this.setScreen(sc);
		
//		Gdx.graphics.setDisplayMode(800,480,true);// set resolution to HD ready (1280 x 720) and set full-screen to true  
//	        // set resolution to default and set full-screen to true  
//	    Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);  
//	    Gdx.graphics.setVSync(true); 
 
		
	    Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
	    
		if(mCfg == null)
			mCfg = new Configer();

		mCfg.mTimer.schedule(mCfg.mGlobalTask, 0, 50);
		
		Configer.mylog("", "------------MyGame.create");
		SCRLoading sc = new SCRLoading(this);
		this.setScreen(sc);
	}

	//中断时调用
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
		Configer.mylog("", "------------MyGame.pause");
		
		
		if(mCfg.mGlobalTask != null){
			mCfg.mGlobalTask.cancel();
			mCfg.mGlobalTask = null;
		}

		
	}
	
	//恢复时调用
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
		Configer.mylog("", "------------MyGame.resume");

		if(mCfg.mGlobalTask != null){
			mCfg.mGlobalTask.cancel();
			mCfg.mGlobalTask = null;
		}
		mCfg.mGlobalTask = new GlobalRefreshTask();
		mCfg.mTimer.schedule(mCfg.mGlobalTask, 0, 50);
	}
	
	
	public static AssetManager manager;
	public static AssetManager getManager() {
		if (manager == null) {
			manager = new AssetManager();
		}
		return manager;
	}
}
