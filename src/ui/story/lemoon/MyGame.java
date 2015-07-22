package ui.story.lemoon;

import ui.story.lemoon.msg.MyMsg;
import ui.story.lemoon.scr.LoadingScr;
import ui.story.lemoon.scr.v2.SCRLoading;
import ui.story.lemoon.scr.v2.SCRMain;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public class MyGame extends Game {
	BsuEvent mBEvent;
	
	public MyGame(BsuEvent b){
		super();
		// 提示空指针  Gdx.app.log("", "------------MyGame.MyGame");
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
		Gdx.app.log("", "------------MyGame.create");
		SCRLoading sc = new SCRLoading(this);
		this.setScreen(sc);
	}

	//中断时调用
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
		Gdx.app.log("", "------------MyGame.pause");
	}
	
	//恢复时调用
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
		Gdx.app.log("", "------------MyGame.resume");
		//Screen s = getScreen();
//		SCRLoading sc = new SCRLoading(this);
//		this.setScreen(sc);
		//this.setScreen(new SCRMain(this));
	}
	
	
	public static AssetManager manager;
	public static AssetManager getManager() {
		if (manager == null) {
			manager = new AssetManager();
		}
		return manager;
	}
}
