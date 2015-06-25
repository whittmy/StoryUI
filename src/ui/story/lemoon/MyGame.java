package ui.story.lemoon;

import ui.story.lemoon.msg.MyMsg;
import ui.story.lemoon.scr.LoadingScr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class MyGame extends Game {
	BsuEvent mBEvent;
	public MyGame(BsuEvent b){
		mBEvent = b;
	}
	
	public void notify(MyMsg msg){
		mBEvent.notify(msg);
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		LoadingScr sc = new LoadingScr(this);
		this.setScreen(sc);
	}

	
	
	public static AssetManager manager;
	public static AssetManager getManager() {
		if (manager == null) {
			manager = new AssetManager();
		}
		return manager;
	}
}
